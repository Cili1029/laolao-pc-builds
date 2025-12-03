package com.laolao.common.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;
import com.laolao.common.properties.AliProperties;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class AliOSSUtil {
    @Resource
    private AliProperties properties;

    private String getAccessKeyId() {
        return properties.getAccessKey().getAccessKeyId();
    }

    private String getAccessKeySecret() {
        return properties.getAccessKey().getAccessKeySecret();
    }

    private String getEndpoint() {
        return properties.getOss().getEndpoint();
    }

    private String getBucketName() {
        return properties.getOss().getBucketName();
    }

    public List<String> upload(MultipartFile[] files, String folder) throws Exception {
        if (files == null || files.length == 0) {
            throw new IllegalArgumentException("文件不得为空！");
        }
        List<String> urlList = new ArrayList<>();
        folder = folder + "/";
        OSS ossClient = new OSSClientBuilder().build(getEndpoint(), getAccessKeyId(), getAccessKeySecret());
        try {
            for (MultipartFile file : files) {
                // 获取上传的文件的输入流
                InputStream inputStream = file.getInputStream();

                // 避免文件覆盖
                String originalFilename = file.getOriginalFilename();
                if (originalFilename == null) {
                    throw new IllegalArgumentException("文件名不能为空");
                }
                String fileName = folder + UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));

                // 上传文件到 OSS
                ossClient.putObject(getBucketName(), fileName, inputStream);
                inputStream.close();

                // 文件访问路径
                String url = generateFileUrl(fileName);
                urlList.add(url);
            }
        } catch (IOException e) {
            throw new IOException("文件上传失败: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("文件上传过程中发生错误: " + e.getMessage(), e);
        } finally {
            // 关闭 OSS 客户端
            ossClient.shutdown();
        }
        return urlList;
    }

    /**
     * 生成文件的访问 URL
     */
    private String generateFileUrl(String fileName) {
        String endpoint = getEndpoint();
        String bucketName = getBucketName();

        // 如果 endpoint 包含协议（如 http:// 或 https://），则直接使用
        if (endpoint.startsWith("http://") || endpoint.startsWith("https://")) {
            return String.format("%s/%s", endpoint, fileName);
        } else {
            // 如果 endpoint 不包含协议，则默认使用 https
            return String.format("https://%s.%s/%s", bucketName, endpoint, fileName);
        }
    }

    /**
     * 批量删除文件
     *
     * @param fileUrls 文件的完整URL列表，例如 ["https://bucket.endpoint/folder/1.jpg", ...]
     * @return 成功删除的文件名列表
     */
    public List<String> delete(List<String> fileUrls) {
        if (fileUrls == null || fileUrls.isEmpty()) {
            return new ArrayList<>();
        }

        // 1. 初始化 OSS 客户端
        OSS ossClient = new OSSClientBuilder().build(getEndpoint(), getAccessKeyId(), getAccessKeySecret());
        List<String> deletedKeys = new ArrayList<>();

        try {
            // 2. 将完整的 URL 解析为 OSS 需要的 Object Name (Key)
            List<String> keys = new ArrayList<>();
            for (String urlStr : fileUrls) {
                try {
                    // (关键) 2.1 URL 解码：处理中文特殊字符
                    // 例如 "%E7%BB%84" -> "组"
                    String decodedUrl = URLDecoder.decode(urlStr, StandardCharsets.UTF_8.toString());

                    // 2.2 提取路径：去除 http://domain/ 部分，只保留 key
                    // 使用 java.net.URL 类来解析路径
                    URL url = new URL(decodedUrl);
                    String path = url.getPath();

                    // url.getPath() 获取的是 "/folder/file.jpg"，OSS 需要 "folder/file.jpg"
                    // 所以如果开头是 / 需要去掉
                    String key = path.startsWith("/") ? path.substring(1) : path;

                    keys.add(key);
                } catch (Exception e) {
                    System.err.println("解析URL失败: " + urlStr);
                    // 可以根据业务选择抛出异常还是跳过
                }
            }

            if (!keys.isEmpty()) {
                // 3. 创建删除请求
                // 注意：OSS 批量删除一次最多支持 1000 个文件
                DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(getBucketName())
                        .withKeys(keys)
                        .withQuiet(true); // quiet=true 表示简单模式，不返回删除详细信息，速度稍快

                // 4. 执行删除
                DeleteObjectsResult deleteObjectsResult = ossClient.deleteObjects(deleteObjectsRequest);
                deletedKeys = deleteObjectsResult.getDeletedObjects();
            }

        } catch (Exception e) {
            throw new RuntimeException("OSS文件删除失败: " + e.getMessage(), e);
        } finally {
            // 5. 关闭客户端
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return deletedKeys;
    }

}

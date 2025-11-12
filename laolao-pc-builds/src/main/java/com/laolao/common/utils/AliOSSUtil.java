package com.laolao.common.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.laolao.common.properties.AliProperties;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
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

}

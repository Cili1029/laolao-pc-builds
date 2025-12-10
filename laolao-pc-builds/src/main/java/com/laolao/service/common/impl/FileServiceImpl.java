package com.laolao.service.common.impl;

import com.laolao.common.result.Result;
import com.laolao.common.utils.AliOSSUtil;
import com.laolao.mapper.common.SysFileMapper;
import com.laolao.service.common.FileService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class FileServiceImpl implements FileService {
    @Resource
    private AliOSSUtil ossUtil;
    @Resource
    private SysFileMapper sysFileMapper;

    @Override
    public Result<List<String>> uploadImages(MultipartFile[] images, String type) throws Exception {
        List<String> urls = ossUtil.upload(images, type);
        // 记录到文件管理表里以便定时清除临时图片
        if (!CollectionUtils.isEmpty(urls)) {
            sysFileMapper.insert(urls);
        }
        return Result.success(urls, "上传成功！");
    }

    @Override
    public Result<String> deleteImages(Map<String, String> url) {
        String res = url.get("url");
        ossUtil.delete(Collections.singletonList(res));
        return Result.success("删除成功！");
    }
}

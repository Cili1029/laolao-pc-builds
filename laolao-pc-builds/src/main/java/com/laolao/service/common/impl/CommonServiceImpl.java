package com.laolao.service.common.impl;

import com.laolao.common.result.Result;
import com.laolao.common.utils.AliOSSUtil;
import com.laolao.pojo.forum.vo.ImageVO;
import com.laolao.service.common.CommonService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class CommonServiceImpl implements CommonService {
    @Resource
    private AliOSSUtil ossUtil;

    @Override
    public Result<ImageVO> uploadImages(MultipartFile[] images, String type) throws Exception {
        List<String> upload = ossUtil.upload(images, type);
        ImageVO imageVO = ImageVO.builder()
                .count(upload.size())
                .images(upload)
                .build();
        return Result.success(imageVO, "上传成功！");
    }

    @Override
    public Result<ImageVO> deleteImages(Map<String, String> url) {
        String res = url.get("url");
        ossUtil.delete(Collections.singletonList(res));
        return Result.success("删除成功！");
    }
}

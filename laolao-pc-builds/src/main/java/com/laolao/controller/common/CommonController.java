package com.laolao.controller.common;

import com.laolao.common.result.Result;
import com.laolao.pojo.forum.vo.ImageVO;
import com.laolao.service.common.CommonService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 通用接口相关
 */
@RestController
@RequestMapping("/common")
public class CommonController {
    @Resource
    private CommonService commonService;

    /**
     * 图片上传
     *
     * @param images 图片
     * @return 消息结果
     */
    @PostMapping("/upload")
    public Result<ImageVO> uploadImages(@RequestParam("images") MultipartFile[] images, @RequestParam("type") String type) throws Exception {
        return commonService.uploadImages(images, type);
    }
}

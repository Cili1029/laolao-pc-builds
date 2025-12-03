package com.laolao.controller.common;

import com.laolao.common.result.Result;
import com.laolao.pojo.forum.vo.ImageVO;
import com.laolao.service.common.CommonService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

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

    /**
     * 图片删除
     *
     * @param url 图片
     * @return 消息结果
     */
    @PostMapping("/delete")
    public Result<ImageVO> deleteImages(@RequestBody Map<String, String> url) {
        return commonService.deleteImages(url);
    }
}

package com.laolao.controller.common;

import com.laolao.common.result.Result;
import com.laolao.service.common.FileService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 通用-上传相关
 */
@RestController
@RequestMapping("/common/file")
public class FileController {
    @Resource
    private FileService fileService;

    /**
     * 图片上传
     *
     * @param images 图片
     * @return 消息结果
     */
    @PostMapping("/upload")
    public Result<List<String>> uploadImages(@RequestParam("images") MultipartFile[] images, @RequestParam("type") String type) throws Exception {
        return fileService.uploadImages(images, type);
    }

    /**
     * 图片删除
     *
     * @param url 图片
     * @return 消息结果
     */
    @PostMapping("/delete")
    public Result<String> deleteImages(@RequestBody Map<String, String> url) {
        return fileService.deleteImages(url);
    }
}

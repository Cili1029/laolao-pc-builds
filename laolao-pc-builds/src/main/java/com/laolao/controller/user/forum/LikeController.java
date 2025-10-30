package com.laolao.controller.user.forum;

import com.laolao.common.result.Result;
import com.laolao.pojo.forum.dto.LikeDTO;
import com.laolao.service.forum.LikeService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 论坛点赞相关
 */
@RestController
@RequestMapping("/user/forum/like")
public class LikeController {
    @Resource
    private LikeService likeService;

    @PostMapping
    public Result<String> like(@RequestBody LikeDTO likeDTO) {
        return likeService.like(likeDTO);
    }

}

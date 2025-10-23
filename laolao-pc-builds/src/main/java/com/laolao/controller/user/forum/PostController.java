package com.laolao.controller.user.forum;

import com.laolao.common.result.Result;
import com.laolao.pojo.forum.vo.PostSimpleVO;
import com.laolao.service.forum.PostService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 论坛帖子相关
 */
@RestController
@RequestMapping("/user/forum/post")
public class PostController {
    @Resource
    private PostService postService;

    /**
     * 获取帖子简略数据
     * 根据类别Id
     *
     * @return 帖子简略数据
     */
    @GetMapping("/simple")
    public Result<List<PostSimpleVO>> getPostSimple(int categoryId) {
        return postService.getPostSimple(categoryId);
    }
}

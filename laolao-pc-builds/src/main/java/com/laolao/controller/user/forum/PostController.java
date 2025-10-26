package com.laolao.controller.user.forum;

import com.laolao.common.result.Result;
import com.laolao.pojo.forum.vo.PostSimpleVO;
import com.laolao.pojo.forum.vo.PostVO;
import com.laolao.service.forum.PostService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 获取帖子详细
     * @param id 帖子id
     * @return 帖子数据
     */
    @GetMapping
    public Result<PostVO> getPost(int id) {
        return postService.getPost(id);
    }
}

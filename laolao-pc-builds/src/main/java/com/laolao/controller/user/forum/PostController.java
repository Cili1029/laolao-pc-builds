package com.laolao.controller.user.forum;

import com.laolao.common.result.Result;
import com.laolao.pojo.forum.dto.CreatePostDTO;
import com.laolao.pojo.forum.vo.CommentReplyVO;
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
     * 只获取直接评论
     *
     * @param id 帖子id
     * @return 帖子数据
     */
    @GetMapping
    public Result<PostVO> getPost(int id) {
        return postService.getPost(id);
    }

    /**
     * 获取回复详细
     *
     * @param id 帖子id
     * @return 回复数据
     */
    @GetMapping("/reply")
    public Result<List<CommentReplyVO>> getReply(int id) {
        return postService.getReply(id);
    }

    /**
     * 搜索各类别的帖子
     *
     * @param categoryId 类别Id
     * @param searchContent 内容
     * @return 帖子简略数据
     */
    @GetMapping("/search")
    public Result<List<PostSimpleVO>> search(int categoryId, String searchContent) {
        return postService.search(categoryId, searchContent);
    }

    /**
     * 发布帖子
     *
     * @param createPostDTO 内容
     * @return 帖子简略数据
     */
    @PostMapping("/create")
    public Result<PostSimpleVO> createPost(@RequestBody CreatePostDTO createPostDTO) {
        return postService.createPost(createPostDTO);
    }

    /**
     * 删除帖子
     *
     * @param id 帖子Id
     * @return 结果信息
     */
    @DeleteMapping("/{id}")
    public Result<String> deletePost(@PathVariable int id) {
        return postService.deletePost(id);
    }
}

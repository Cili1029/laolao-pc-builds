package com.laolao.controller.user.forum;

import com.laolao.common.result.Result;
import com.laolao.pojo.forum.dto.AddCommentDTO;
import com.laolao.pojo.forum.dto.AddReplyDTO;
import com.laolao.pojo.forum.vo.CommentReplyVO;
import com.laolao.pojo.forum.vo.CommentVO;
import com.laolao.service.forum.CommentService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 评论功能相关
 */
@RestController
@RequestMapping("/user/forum/comment")
public class CommentController {
    @Resource
    private CommentService commentService;

    /**
     * 评论
     *
     * @param addCommentDTO 所需数据
     * @return 该评论的数据
     */
    @PostMapping
    public Result<CommentVO> addComment(@RequestBody AddCommentDTO addCommentDTO) {
        return commentService.addComment(addCommentDTO);
    }

    /**
     * 回复评论
     *
     * @param addReplyDTO 所需数据
     * @return 该回复大的数据
     */
    @PostMapping("reply")
    public Result<CommentReplyVO> addReply(@RequestBody AddReplyDTO addReplyDTO) {
        return commentService.addReply(addReplyDTO);
    }

    /**
     * 删除评论
     *
     * @param id 品论Id
     * @return 结果信息
     */
    @DeleteMapping("/{id}")
    public Result<String> deleteComment(@PathVariable int id) {
        return commentService.deleteComment(id);
    }

    /**
     * 删除评论回复
     *
     * @param id 回复id
     * @return 结果信息
     */
    @DeleteMapping("/reply/{id}")
    public Result<String> deleteReply(@PathVariable int id) {
        return commentService.deleteReply(id);
    }
}

package com.laolao.controller.user.forum;

import com.laolao.common.result.Result;
import com.laolao.pojo.forum.dto.AddCommentDTO;
import com.laolao.pojo.forum.dto.AddReplyDTO;
import com.laolao.pojo.forum.vo.CommentReplyVO;
import com.laolao.pojo.forum.vo.CommentVO;
import com.laolao.service.user.forum.CommentService;
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
     * @param postId 帖子id
     * @param commentId 评论Id
     * @return 结果信息
     */
    @DeleteMapping
    public Result<String> deleteComment(int postId, int commentId) {
        return commentService.deleteComment(postId, commentId);
    }

    /**
     * 删除评论回复
     *
     * @param postId 帖子id
     * @param replyId 回复id
     * @return 结果信息
     */
    @DeleteMapping("/reply")
    public Result<String> deleteReply(int postId, int replyId) {
        return commentService.deleteReply(postId, replyId);
    }
}

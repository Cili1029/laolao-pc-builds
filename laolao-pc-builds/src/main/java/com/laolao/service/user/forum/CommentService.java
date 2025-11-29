package com.laolao.service.user.forum;

import com.laolao.common.result.Result;
import com.laolao.pojo.forum.dto.AddCommentDTO;
import com.laolao.pojo.forum.dto.AddReplyDTO;
import com.laolao.pojo.forum.vo.CommentReplyVO;
import com.laolao.pojo.forum.vo.CommentVO;

public interface CommentService {
    Result<CommentVO> addComment(AddCommentDTO addCommentDTO);

    Result<CommentReplyVO> addReply(AddReplyDTO addReplyDTO);

    Result<String> deleteComment(int postId, int commentId);

    Result<String> deleteReply(int postId, int id);
}

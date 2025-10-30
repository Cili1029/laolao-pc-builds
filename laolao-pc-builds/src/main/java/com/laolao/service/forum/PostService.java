package com.laolao.service.forum;

import com.laolao.common.result.Result;
import com.laolao.pojo.forum.dto.CreatePostDTO;
import com.laolao.pojo.forum.vo.CommentReplyVO;
import com.laolao.pojo.forum.vo.PostSimpleVO;
import com.laolao.pojo.forum.vo.PostVO;

import java.util.List;

public interface PostService {
    Result<List<PostSimpleVO>> getPostSimple(int categoryId);

    Result<PostVO> getPost(int id);

    Result<List<PostSimpleVO>> search(int categoryId, String searchContent);

    Result<PostSimpleVO> createPost(CreatePostDTO createPostDTO);

    Result<String> deletePost(int id);

    Result<List<CommentReplyVO>> getReply(int id);
}

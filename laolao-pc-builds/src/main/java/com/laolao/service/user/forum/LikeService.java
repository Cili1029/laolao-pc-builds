package com.laolao.service.user.forum;

import com.laolao.common.result.Result;
import com.laolao.pojo.forum.dto.LikeDTO;

public interface LikeService {
    Result<Integer> like(LikeDTO likeDTO);
}

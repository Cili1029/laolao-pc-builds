package com.laolao.service.forum;

import com.laolao.common.result.Result;
import com.laolao.pojo.forum.vo.PostSimpleVO;

import java.util.List;

public interface PostService {
    Result<List<PostSimpleVO>> getPostSimple(int categoryId);
}

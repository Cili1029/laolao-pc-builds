package com.laolao.service.forum;

import com.laolao.common.result.Result;
import com.laolao.pojo.forum.vo.ForumCategoryVO;

import java.util.List;

public interface ForumCategoryService {
    Result<List<ForumCategoryVO>> list();
}

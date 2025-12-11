package com.laolao.service.admin.forum;

import com.laolao.common.result.Result;


public interface AdminCommentService {
    Result<String> deleteComment(Integer type ,Integer id);
}

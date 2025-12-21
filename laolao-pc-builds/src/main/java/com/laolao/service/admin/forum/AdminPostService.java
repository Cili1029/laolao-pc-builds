package com.laolao.service.admin.forum;

import com.github.pagehelper.PageInfo;
import com.laolao.common.result.Result;
import com.laolao.pojo.forum.entity.Post;


public interface AdminPostService {
    Result<PageInfo<Post>> getPost(Integer pageNum, Integer pageSize, String searchContent);

    Result<String> updateCategory(Integer id, int categoryId);

    Result<String> deletePost(Integer id);

    Result<String> changeStatus(Integer id, int status);
}

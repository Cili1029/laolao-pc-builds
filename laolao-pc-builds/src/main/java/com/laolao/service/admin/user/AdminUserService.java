package com.laolao.service.admin.user;

import com.github.pagehelper.PageInfo;
import com.laolao.common.result.Result;
import com.laolao.pojo.user.dto.AdminUserUpdateDTO;
import com.laolao.pojo.user.vo.AdminUserVO;


public interface AdminUserService {
    Result<PageInfo<AdminUserVO>> getUser(Integer pageNum, Integer pageSize, String searchContent);

    Result<String> changeStatus(int id, int status);

    Result<String> updateUser(AdminUserUpdateDTO adminUserUpdateDTO);
}

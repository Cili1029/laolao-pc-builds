package com.laolao.service.admin;

import com.laolao.common.result.Result;
import com.laolao.pojo.user.dto.AdminUserUpdateDTO;
import com.laolao.pojo.user.vo.AdminUserVO;

import java.util.List;

public interface AdminUserService {
    Result<List<AdminUserVO>> getUser();

    Result<String> changeStatus(int userId, int status);

    Result<String> updateUser(AdminUserUpdateDTO adminUserUpdateDTO);

    Result<List<AdminUserVO>> searchUser(String searchContent);
}

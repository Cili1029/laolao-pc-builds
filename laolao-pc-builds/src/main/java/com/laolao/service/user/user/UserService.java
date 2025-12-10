package com.laolao.service.user.user;

import com.laolao.pojo.forum.vo.PostSimpleVO;
import com.laolao.pojo.user.dto.UpdateUserDTO;
import com.laolao.common.result.Result;
import com.laolao.pojo.user.vo.UserVO;

import java.util.List;

public interface UserService {

    Result<UserVO> getUser(Integer id);

    Result<String> update(UpdateUserDTO updateUserDTO);

    Result<List<PostSimpleVO>> getLike(Integer id);
}

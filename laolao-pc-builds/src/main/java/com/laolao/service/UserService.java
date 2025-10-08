package com.laolao.service;

import com.laolao.pojo.dto.UserLoginOrRegisterDTO;
import com.laolao.pojo.vo.UserVO;
import com.laolao.result.Result;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {
    void getSmsCode(String phone);

    Result<UserVO> login(UserLoginOrRegisterDTO userLoginOrRegisterDTO, HttpServletResponse res);

    Result<UserVO> register(UserLoginOrRegisterDTO userRegisterDTO, HttpServletResponse res) ;

    Result<String> logout(HttpServletResponse res);
}

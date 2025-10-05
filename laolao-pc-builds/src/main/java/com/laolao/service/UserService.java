package com.laolao.service;

import com.laolao.pojo.dto.UserLoginOrRegisterDTO;
import com.laolao.pojo.vo.UserLoginVO;
import com.laolao.result.Result;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {
    void getSmsCode(String phone);

    Result<UserLoginVO> login(UserLoginOrRegisterDTO userLoginOrRegisterDTO, HttpServletResponse res);

    Result<UserLoginVO> register(UserLoginOrRegisterDTO userRegisterDTO, HttpServletResponse res) ;
}

package com.laolao.service;

import com.laolao.pojo.dto.UserLoginOrRegisterDTO;
import com.laolao.pojo.vo.UserLoginVO;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {
    UserLoginVO login(UserLoginOrRegisterDTO userLoginOrRegisterDTO, HttpServletResponse res);

    UserLoginVO register(UserLoginOrRegisterDTO userRegisterDTO, HttpServletResponse res) ;

    void getSmsCode(String phone);
}

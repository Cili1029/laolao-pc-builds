package com.laolao.service.user;

import com.laolao.pojo.user.dto.LoginByEmailDTO;
import com.laolao.pojo.user.dto.LoginByUsernameDTO;
import com.laolao.pojo.user.dto.RegisterDTO;
import com.laolao.pojo.user.vo.UserVO;
import com.laolao.common.result.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {
    Result<String> getEmailCode(String email) throws Exception;

    Result<UserVO> loginByUsername(LoginByUsernameDTO loginByUsernameDTO, HttpServletResponse res);

    Result<UserVO> loginByEmail(LoginByEmailDTO loginByEmailDTO, HttpServletResponse res);

    Result<UserVO> register(RegisterDTO userRegisterDTO, HttpServletResponse res) ;

    Result<String> logout(HttpServletResponse res);

    Result<UserVO> getProfile(HttpServletRequest req);
}

package com.laolao.service.user;

import com.laolao.pojo.user.dto.SignInWithEmailDTO;
import com.laolao.pojo.user.dto.SignInWithUsernameDTO;
import com.laolao.pojo.user.dto.SignUpDTO;
import com.laolao.pojo.user.vo.UserVO;
import com.laolao.common.result.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {
    Result<String> getEmailCode(String email) throws Exception;

    Result<UserVO> signInWithUsername(SignInWithUsernameDTO signInWithUsernameDTO, HttpServletResponse res);

    Result<UserVO> signInWithEmail(SignInWithEmailDTO signInWithEmailDTO, HttpServletResponse res);

    Result<UserVO> signUp(SignUpDTO userSignUpDTO, HttpServletResponse res) ;

    Result<String> signOut(HttpServletResponse res);

    Result<UserVO> getProfile(HttpServletRequest req);
}

package com.laolao.service.common;

import com.laolao.common.result.Result;
import com.laolao.pojo.user.dto.SignInWithEmailDTO;
import com.laolao.pojo.user.dto.SignInWithUsernameDTO;
import com.laolao.pojo.user.dto.SignUpDTO;
import com.laolao.pojo.user.vo.UserSimpleVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface CommonUserService {
    Result<String> getEmailCode(String email, HttpServletRequest rq) throws Exception;

    Result<UserSimpleVO> signInWithUsername(SignInWithUsernameDTO signInWithUsernameDTO, HttpServletResponse res);

    Result<UserSimpleVO> signInWithEmail(SignInWithEmailDTO signInWithEmailDTO, HttpServletResponse res);

    Result<UserSimpleVO> signUp(SignUpDTO userSignUpDTO, HttpServletResponse res) ;

    Result<String> signOut(HttpServletResponse res);

    Result<UserSimpleVO> getProfile(HttpServletRequest req, HttpServletResponse res);
}

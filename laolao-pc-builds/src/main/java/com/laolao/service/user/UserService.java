package com.laolao.service.user;

import com.laolao.pojo.forum.vo.PostSimpleVO;
import com.laolao.pojo.user.dto.SignInWithEmailDTO;
import com.laolao.pojo.user.dto.SignInWithUsernameDTO;
import com.laolao.pojo.user.dto.SignUpDTO;
import com.laolao.pojo.user.vo.UpdateUserVO;
import com.laolao.pojo.user.vo.UserSimpleVO;
import com.laolao.common.result.Result;
import com.laolao.pojo.user.vo.UserVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface UserService {
    Result<String> getEmailCode(String email) throws Exception;

    Result<UserSimpleVO> signInWithUsername(SignInWithUsernameDTO signInWithUsernameDTO, HttpServletResponse res);

    Result<UserSimpleVO> signInWithEmail(SignInWithEmailDTO signInWithEmailDTO, HttpServletResponse res);

    Result<UserSimpleVO> signUp(SignUpDTO userSignUpDTO, HttpServletResponse res) ;

    Result<String> signOut(HttpServletResponse res);

    Result<UserSimpleVO> getProfile(HttpServletRequest req);

    Result<UserVO> getUser(Integer id);

    Result<String> update(UpdateUserVO updateUserVO);

    Result<List<PostSimpleVO>> getLike(Integer id);
}

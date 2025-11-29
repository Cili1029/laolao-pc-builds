package com.laolao.controller.user.user;

import com.laolao.pojo.forum.vo.PostSimpleVO;
import com.laolao.pojo.user.dto.SignInWithEmailDTO;
import com.laolao.pojo.user.dto.SignInWithUsernameDTO;
import com.laolao.pojo.user.dto.SignUpDTO;
import com.laolao.pojo.user.vo.UpdateUserVO;
import com.laolao.pojo.user.vo.UserSimpleVO;
import com.laolao.common.result.Result;
import com.laolao.pojo.user.vo.UserVO;
import com.laolao.service.user.user.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 用户相关
 */
@RestController
@RequestMapping("/user/user")
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 获取邮箱验证码
     *
     * @param request 请求
     * @return 结果信息
     * @throws Exception 阿里云邮件推送异常
     */
    @PostMapping("/email-code")
    public Result<String> getEmailCode(@RequestBody Map<String, String> request) throws Exception {
        return userService.getEmailCode(request.get("email"));
    }

    /**
     * 账号密码登录
     *
     * @param signInWithUsernameDTO 登录所需的基本信息
     * @param res 响应
     * @return 用户基本数据
     */
    @PostMapping("/sign-in/username")
    public Result<UserSimpleVO> signInWithUsername(@RequestBody SignInWithUsernameDTO signInWithUsernameDTO, HttpServletResponse res) {
        return userService.signInWithUsername(signInWithUsernameDTO, res);
    }

    /**
     * 账号密码登录
     *
     * @param signInWithEmailDTO 登录所需的基本信息
     * @param res 响应
     * @return 用户基本数据
     */
    @PostMapping("/sign-in/email")
    public Result<UserSimpleVO> signInWithEmail(@RequestBody SignInWithEmailDTO signInWithEmailDTO, HttpServletResponse res) {
        return userService.signInWithEmail(signInWithEmailDTO, res);
    }

    /**
     * 用户注册
     * 注册成功后自动登录
     *
     * @param signUpDTO 注册所需的基本信息
     * @param res 响应
     * @return 用户基本数据
     */
    @PostMapping("/sign-up")
    public Result<UserSimpleVO> signUp(@RequestBody SignUpDTO signUpDTO, HttpServletResponse res) {
        return userService.signUp(signUpDTO, res);
    }


    /**
     * 获取用户基本信息
     * 从jwt里获取userId
     *
     * @param req 请求
     * @return 用户基本数据
     */
    @GetMapping("/profile")
    public Result<UserSimpleVO> getProfile(HttpServletRequest req) {
        return userService.getProfile(req);
    }

    /**
     * 用户退出
     * 同时清除Cookie，JWT
     *
     * @param res 响应
     * @return 结果信息
     */
    @GetMapping("/sign-out")
    public Result<String> signOut(HttpServletResponse res) {
        return userService.signOut(res);
    }

    /**
     * 获取用户主页信息
     *
     * @param id 用户Id
     * @return 用户主页数据
     */
    @GetMapping("/{id}")
    public Result<UserVO> getUser(@PathVariable Integer id) {
        return userService.getUser(id);
    }

    /**
     * 获取用户喜欢的贴纸
     *
     * @param id 用户Id
     * @return 用户主页数据
     */
    @GetMapping("/{id}/like")
    public Result<List<PostSimpleVO>> getLike(@PathVariable Integer id) {
        return userService.getLike(id);
    }

    /**
     * 更新用户信息
     *
     * @param updateUserVO 新数据
     * @return 消息结果
     */
    @PostMapping("/update")
    public Result<String> updateUser(@RequestBody UpdateUserVO updateUserVO) {
        return userService.update(updateUserVO);
    }
}

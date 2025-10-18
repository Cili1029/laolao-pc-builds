package com.laolao.controller.user;

import com.laolao.pojo.dto.UserLoginOrRegisterDTO;
import com.laolao.pojo.vo.UserVO;
import com.laolao.common.result.Result;
import com.laolao.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

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
     * 获取验证码
     *
     * @param request 请求
     * @return 结果信息
     * @throws Exception 阿里云邮件推送异常
     */
    @PostMapping("/email-code")
    public Result<String> getEmailCode(@RequestBody Map<String, String> request) throws Exception {
        userService.getEmailCode(request.get("email"));
        return Result.success("发送成功！");
    }

    /**
     * 用户登录
     *
     * @param userLoginOrRegisterDTO 登录所需的基本信息
     * @param res 响应
     * @return 登陆后的基本信息
     */
    @PostMapping("/login")
    public Result<UserVO> login(@RequestBody UserLoginOrRegisterDTO userLoginOrRegisterDTO, HttpServletResponse res) {
        return userService.login(userLoginOrRegisterDTO, res);
    }

    /**
     * 用户注册
     * 注册成功后自动登录
     *
     * @param userLoginOrRegisterDTO 注册所需的基本信息
     * @param res 响应
     * @return 登陆后的基本信息
     */
    @PostMapping("/register")
    public Result<UserVO> register(@RequestBody UserLoginOrRegisterDTO userLoginOrRegisterDTO, HttpServletResponse res) {
        return userService.register(userLoginOrRegisterDTO, res);
    }


    /**
     * 获取用户基本信息
     * 从jwt里获取userId
     *
     * @param req 请求
     * @return 登陆后的基本信息
     */
    @GetMapping("/profile")
    public Result<UserVO> getProfile(HttpServletRequest req) {
        return userService.getProfile(req);
    }

    /**
     * 用户退出
     * 同时清除Cookie，JWT
     *
     * @param res 响应
     * @return 结果信息
     */
    @GetMapping("/logout")
    public Result<String> logout(HttpServletResponse res) {
        return userService.logout(res);
    }
}

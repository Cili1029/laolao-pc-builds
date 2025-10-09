package com.laolao.controller.user;

import com.laolao.pojo.dto.UserLoginOrRegisterDTO;
import com.laolao.pojo.vo.UserVO;
import com.laolao.common.result.Result;
import com.laolao.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/user/user")
public class UserController {
    @Resource
    private UserService userService;

    // 生成验证码
    @PostMapping("/smscode")
    public Result<String> getSmsCode(@RequestBody Map<String, String> request) {
        userService.getSmsCode(request.get("phone"));
        return Result.success("发送成功！");
    }

    // 登录
    @PostMapping("/login")
    public Result<UserVO> login(@RequestBody UserLoginOrRegisterDTO userLoginOrRegisterDTO, HttpServletResponse res) {
        return userService.login(userLoginOrRegisterDTO, res);
    }

    // 注册，注册成功后自动登录
    @PostMapping("/register")
    public Result<UserVO> register(@RequestBody UserLoginOrRegisterDTO userLoginOrRegisterDTO, HttpServletResponse res) {
        return userService.register(userLoginOrRegisterDTO, res);
    }

    @GetMapping("/logout")
    public Result<String> logout(HttpServletResponse res) {
        return userService.logout(res);
    }
}

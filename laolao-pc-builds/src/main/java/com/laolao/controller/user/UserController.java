package com.laolao.controller.user;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.laolao.constant.RedisConstant;
import com.laolao.pojo.dto.UserLoginOrRegisterDTO;
import com.laolao.pojo.vo.UserLoginVO;
import com.laolao.result.Result;
import com.laolao.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
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
        return Result.success();
    }

    // 登录
    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody UserLoginOrRegisterDTO userLoginOrRegisterDTO, HttpServletResponse res) {
        UserLoginVO userLoginVo = userService.login(userLoginOrRegisterDTO, res);
        return Result.success(userLoginVo);
    }

    // 注册，注册成功后自动登录
    @PostMapping("/register")
    public Result<UserLoginVO> register(@RequestBody UserLoginOrRegisterDTO userLoginOrRegisterDTO, HttpServletResponse res) {
        UserLoginVO userRegisterVo = userService.register(userLoginOrRegisterDTO, res);
        return Result.success(userRegisterVo);
    }
}

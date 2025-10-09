package com.laolao.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.util.RandomUtil;
import com.laolao.common.constant.JwtClaimsConstant;
import com.laolao.common.constant.MessageConstant;
import com.laolao.common.constant.RedisConstant;
import com.laolao.converter.MapStruct;
import com.laolao.common.exception.UnknownError;
import com.laolao.mapper.UserMapper;
import com.laolao.pojo.dto.UserLoginOrRegisterDTO;
import com.laolao.pojo.entity.User;
import com.laolao.pojo.vo.UserVO;
import com.laolao.common.properties.JwtProperties;
import com.laolao.common.result.Result;
import com.laolao.service.UserService;
import com.laolao.common.utils.JwtUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private JwtProperties jwtProperties;
    @Resource
    private MapStruct mapStruct;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void getSmsCode(String phone) {
        // 手机号位数验证待开发 应该先验证用户是否存在，在验证验证码
        if (!StringUtils.hasText(phone)) {
            throw new UnknownError(MessageConstant.UNKNOWN_ERROR);
        }

        // 暂且用图形验证码代替
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(200, 100);
        // 验证码存入Redis用于验证
        stringRedisTemplate.opsForValue().set(RedisConstant.LOGIN_CODE_KEY + phone, captcha.getCode() , RedisConstant.LOGIN_CODE_TTL, TimeUnit.MINUTES);
        System.out.println(captcha.getCode());
    }

    @Override
    public Result<UserVO> login(UserLoginOrRegisterDTO userLoginOrRegisterDTO, HttpServletResponse res) {
        if (StringUtils.hasText(userLoginOrRegisterDTO.getUsername()) && StringUtils.hasText(userLoginOrRegisterDTO.getPassword())) {
            // 账户密码登录
            User user = userMapper.checkUserExists(userLoginOrRegisterDTO);

            // 用户不存在
            if (user == null) {
                return Result.error(MessageConstant.USER_NOT_FOUND);
            }

            // 加密密码进行比对，错误则密码错误
            String password = DigestUtils.md5DigestAsHex(userLoginOrRegisterDTO.getPassword().getBytes());
            if (!password.equals(user.getPassword())) {
                return Result.error(MessageConstant.PASSWORD_ERROR);
            }

            // 设置jwt令牌并返回前端
            return setJwtToLogin(user, res);
        } else if (StringUtils.hasText(userLoginOrRegisterDTO.getPhone()) && StringUtils.hasText(userLoginOrRegisterDTO.getSmsCode())) {
            // 电话验证码登录
            Result<String> result = phone(userLoginOrRegisterDTO);
            if (result != null) {
                return Result.error(result.getMsg());
            }
            User user = userMapper.checkUserExists(userLoginOrRegisterDTO);
            // 用户不存在
            if (user == null) {
                return Result.error(MessageConstant.USER_NOT_FOUND);
            }
            return setJwtToLogin(user, res);
        }
        return Result.error(MessageConstant.UNKNOWN_ERROR);
    }

    @Override
    public Result<UserVO> register(UserLoginOrRegisterDTO userLoginOrRegisterDTO, HttpServletResponse res) {
        if (!StringUtils.hasText(userLoginOrRegisterDTO.getUsername()) || !StringUtils.hasText(userLoginOrRegisterDTO.getPassword()) || !StringUtils.hasText(userLoginOrRegisterDTO.getPhone()) || !StringUtils.hasText(userLoginOrRegisterDTO.getSmsCode())) {
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }

        Result<String> result = phone(userLoginOrRegisterDTO);
        if (result != null) {
            return Result.error(result.getMsg());
        }

        if (userMapper.checkUserExists(userLoginOrRegisterDTO) != null) {
            return Result.error(MessageConstant.USER_ALREADY_EXISTS);
        }

        User user = new User();
        user.setName("user_" + RandomUtil.randomString(10));
        user.setUsername(userLoginOrRegisterDTO.getUsername());
        user.setPassword(DigestUtils.md5DigestAsHex(userLoginOrRegisterDTO.getPassword().getBytes()));
        user.setPhone(userLoginOrRegisterDTO.getPhone());
        user.setCreateTime(LocalDateTime.now());
        // 写入数据库
        userMapper.addUser(user);

        // 设置jwt令牌并返回前端
        return setJwtToLogin(user, res);
    }

    @Override
    public Result<String> logout(HttpServletResponse res) {
        Cookie cookie = new Cookie("jwt_token", null);
        cookie.setHttpOnly(true);        // 防止 XSS 攻击
        cookie.setSecure(false);         // 本地开发用 false，生产环境用 true (HTTPS)
        cookie.setPath("/");             // 对整个应用有效
        cookie.setMaxAge(0); // 7天过期
        res.addCookie(cookie);
        return Result.success("已退出！");
    }

    // 电话合理性以及验证码验证
    private Result<String> phone(UserLoginOrRegisterDTO userLoginOrRegisterDTO) {
        // 手机号位数验证待开发

        // 验证验证码
        String smsCode = stringRedisTemplate.opsForValue().get(RedisConstant.LOGIN_CODE_KEY + userLoginOrRegisterDTO.getPhone());
        if (smsCode == null || !smsCode.equals(userLoginOrRegisterDTO.getSmsCode())) {
            return Result.error(MessageConstant.SMSCODE_ERROR);
        }

        // 合理，通过
        return null;
    }

    private Result<UserVO> setJwtToLogin(User user, HttpServletResponse res) {
        // 转换为VO
        UserVO userVO = mapStruct.userToUserVO(user);

        //设置jwt
        HashMap<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        claims.put(JwtClaimsConstant.USERNAME, user.getUsername());
        String jwt = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);

        // 存入Cookie
        Cookie cookie = new Cookie("jwt_token", jwt);
        cookie.setHttpOnly(true);        // 防止 XSS 攻击
        cookie.setSecure(false);         // 本地开发用 false，生产环境用 true (HTTPS)
        cookie.setPath("/");             // 对整个应用有效
        cookie.setMaxAge(7 * 24 * 60 * 60); // 7天过期
        res.addCookie(cookie);

        return Result.success(userVO, "登陆/注册成功！");
    }

}

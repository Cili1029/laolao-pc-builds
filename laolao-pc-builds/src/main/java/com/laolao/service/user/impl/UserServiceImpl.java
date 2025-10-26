package com.laolao.service.user.impl;

import cn.hutool.core.util.RandomUtil;
import com.laolao.common.constant.JwtClaimsConstant;
import com.laolao.common.constant.MessageConstant;
import com.laolao.common.constant.RedisConstant;
import com.laolao.common.utils.AliyunDirectMailUtil;
import com.laolao.converter.MapStruct;
import com.laolao.common.exception.UnknownError;
import com.laolao.mapper.user.UserMapper;
import com.laolao.pojo.user.dto.LoginByEmailDTO;
import com.laolao.pojo.user.dto.LoginByUsernameDTO;
import com.laolao.pojo.user.dto.RegisterDTO;
import com.laolao.pojo.user.entity.User;
import com.laolao.pojo.user.vo.UserVO;
import com.laolao.common.result.Result;
import com.laolao.service.user.UserService;
import com.laolao.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private MapStruct mapStruct;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private AliyunDirectMailUtil aliyunDirectMailUtil;

    @Override
    public Result<String> getEmailCode(String email) throws Exception {
        // 手机号位数验证待开发 应该先验证用户是否存在，在验证验证码
        if (!StringUtils.hasText(email)) {
            throw new UnknownError(MessageConstant.UNKNOWN_ERROR);
        }

        String code = String.format("%06d", ThreadLocalRandom.current().nextInt(0, 1000000));

        Boolean result =aliyunDirectMailUtil.sendEmail(email, "您的验证码是<strong>" + code + "</strong>");
        if (!result) {
            throw new UnknownError(MessageConstant.UNKNOWN_ERROR);
        }

        // 验证码存入Redis用于验证
        stringRedisTemplate.opsForValue().set(RedisConstant.LOGIN_CODE_KEY + email, code, RedisConstant.LOGIN_CODE_TTL, TimeUnit.MINUTES);
        return Result.success("已发送！");
    }

    @Override
    public Result<UserVO> loginByUsername(LoginByUsernameDTO loginByUsernameDTO, HttpServletResponse res) {
            User user = userMapper.checkUserExists(loginByUsernameDTO.getUsername(), null);

            // 用户不存在
            if (user == null) {
                return Result.error(MessageConstant.USER_NOT_FOUND);
            }

            // 加密密码进行比对，错误则密码错误
            String password = DigestUtils.md5DigestAsHex(loginByUsernameDTO.getPassword().getBytes());
            if (!password.equals(user.getPassword())) {
                return Result.error(MessageConstant.PASSWORD_ERROR);
            }

            // 设置jwt令牌并返回前端
            return setJwtToLogin(user, res);
    }

    @Override
    public Result<UserVO> loginByEmail(LoginByEmailDTO loginByEmailDTO, HttpServletResponse res) {
            Result<String> result = email(loginByEmailDTO.getEmail(), loginByEmailDTO.getEmailCode());
            if (result != null) {
                return Result.error(result.getMsg());
            }
            User user = userMapper.checkUserExists(null,loginByEmailDTO.getEmail());
            // 用户不存在
            if (user == null) {
                return Result.error(MessageConstant.USER_NOT_FOUND);
            }
            return setJwtToLogin(user, res);
    }

    @Override
    public Result<UserVO> register(RegisterDTO registerDTO, HttpServletResponse res) {
        if (!StringUtils.hasText(registerDTO.getUsername()) || !StringUtils.hasText(registerDTO.getPassword()) || !StringUtils.hasText(registerDTO.getEmail()) || !StringUtils.hasText(registerDTO.getEmailCode())) {
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }

        Result<String> result = email(registerDTO.getEmail(), registerDTO.getEmailCode());
        if (result != null) {
            return Result.error(result.getMsg());
        }

        if (userMapper.checkUserExists(registerDTO.getUsername(), registerDTO.getEmail()) != null) {
            return Result.error(MessageConstant.USER_ALREADY_EXISTS);
        }

        User user = new User();
        user.setName("user_" + RandomUtil.randomString(10));
        user.setUsername(registerDTO.getUsername());
        user.setPassword(DigestUtils.md5DigestAsHex(registerDTO.getPassword().getBytes()));
        user.setEmail(registerDTO.getEmail());
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

    @Override
    public Result<UserVO> getProfile(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        String jwt = getJwtFromCookie(cookies);
        try {
            Claims claims = jwtUtil.parseJWT(jwt);
            long userId = Long.parseLong(claims.get(JwtClaimsConstant.USER_ID).toString());
            User user = userMapper.getUser(userId);
            UserVO userVO = mapStruct.userToUserVO(user);
            return Result.success(userVO);
        }  catch (Exception e) {
            return Result.error();
        }
    }

    public String getJwtFromCookie(Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("jwt_token")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    // 电话合理性以及验证码验证
    private Result<String> email(String email, String code) {
        // 手机号位数验证待开发

        // 验证验证码
        String emailCode = stringRedisTemplate.opsForValue().get(RedisConstant.LOGIN_CODE_KEY + email);
        if (emailCode == null || !emailCode.equals(code)) {
            return Result.error(MessageConstant.EMAILCODE_ERROR);
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
        claims.put(JwtClaimsConstant.NAME, user.getName());
        String jwt = jwtUtil.createJWT(claims);

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

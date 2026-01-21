package com.laolao.service.common.impl;

import com.laolao.common.constant.ExpireConstant;
import com.laolao.common.constant.StatusConstant;
import com.laolao.common.constant.JwtConstant;
import com.laolao.common.constant.RedisConstant;
import com.laolao.common.context.UserContext;
import com.laolao.common.exception.UnknownError;
import com.laolao.common.result.Result;
import com.laolao.common.utils.AliDirectMailUtil;
import com.laolao.common.utils.JwtUtil;
import com.laolao.common.utils.NameUtil;
import com.laolao.converter.MapStruct;
import com.laolao.mapper.common.UserCommonMapper;
import com.laolao.pojo.user.dto.SignInWithEmailDTO;
import com.laolao.pojo.user.dto.SignInWithUsernameDTO;
import com.laolao.pojo.user.dto.SignUpDTO;
import com.laolao.pojo.user.entity.User;
import com.laolao.pojo.user.vo.UserSimpleVO;
import com.laolao.service.common.CommonUserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Service
public class CommonUserServiceImpl implements CommonUserService {

    @Resource
    private UserCommonMapper userCommonMapper;
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private MapStruct mapStruct;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private AliDirectMailUtil aliDirectMailUtil;
    @Resource
    private RedissonClient redissonClient;

    @Override
    public Result<String> getEmailCode(String email, HttpServletRequest rq) throws Exception {
        // 手机号位数验证待开发 应该先验证用户是否存在，在验证验证码
        if (!StringUtils.hasText(email)) {
            throw new UnknownError("未知错误");
        }

        // 判断是否限流
        String remoteAddr = rq.getRemoteAddr();
        Result<String> checkResult = checkEmailRateLimit(email, remoteAddr);
        if (checkResult.getCode() == 0) {
            return checkResult;
        }

        String code = String.format("%06d", ThreadLocalRandom.current().nextInt(0, 1000000));

        Boolean result = aliDirectMailUtil.sendEmail(email, "您的验证码是<strong>" + code + "</strong>");
        if (!result) {
            throw new UnknownError("未知错误");
        }

        // 验证码存入Redis用于验证
        stringRedisTemplate.opsForValue().set(RedisConstant.User.SIGN_IN_CODE + email, code, ExpireConstant.MIN_2, TimeUnit.MINUTES);
        return Result.success("已发送！");
    }

    public Result<String> checkEmailRateLimit(String email, String ip){
        // 限流一 邮箱限流 一个邮箱1分钟只能发送1次
        RRateLimiter emailRateLimiter = redissonClient.getRateLimiter(RedisConstant.User.LIMIT_EMAIL_RATE + email);
        // 初始化
        emailRateLimiter.trySetRate(RateType.OVERALL, 1, Duration.ofMinutes(ExpireConstant.MIN_1));
        if (!emailRateLimiter.tryAcquire()) {
            return Result.error("请求过于频繁，请 1 分钟后再试");
        }

        // 限流二 IP限流 一个IP24小时只能发送10次
        RRateLimiter ipRateLimiter = redissonClient.getRateLimiter(RedisConstant.User.LIMIT_IP_RATE + ip);
        // 初始化
        ipRateLimiter.trySetRate(RateType.OVERALL, 10, Duration.ofMinutes(ExpireConstant.DAY_1));
        if (!ipRateLimiter.tryAcquire()) {
            return Result.error("今日该 IP 发送次数已达上限，请明日再试");
        }
        return Result.success();
    }

    @Override
    public Result<UserSimpleVO> signInWithUsername(SignInWithUsernameDTO signInWithUsernameDTO, HttpServletResponse res) {
        User user = userCommonMapper.checkUserExists(signInWithUsernameDTO.getUsername(), null);

        // 用户不存在
        if (user == null) {
            return Result.error("用户不存在");
        }

        if (user.getStatus() == StatusConstant.DISABLED) {
            return Result.error("账号被锁定");
        }

        // 加密密码进行比对，错误则密码错误
        String password = DigestUtils.md5DigestAsHex(signInWithUsernameDTO.getPassword().getBytes());
        if (!password.equals(user.getPassword())) {
            return Result.error("密码错误");
        }

        // 设置jwt令牌并返回前端
        return setJwtToSignIn(user, res);
    }

    @Override
    public Result<UserSimpleVO> signInWithEmail(SignInWithEmailDTO signInWithEmailDTO, HttpServletResponse res) {
        Result<String> result = email(signInWithEmailDTO.getEmail(), signInWithEmailDTO.getEmailCode());
        if (result != null) {
            return Result.error(result.getMsg());
        }
        User user = userCommonMapper.checkUserExists(null, signInWithEmailDTO.getEmail());
        // 用户不存在
        if (user == null) {
            return Result.error("用户不存在");
        }

        if (user.getStatus() == StatusConstant.DISABLED) {
            return Result.error("账号被锁定，有疑问联系管理员");
        }
        return setJwtToSignIn(user, res);
    }

    @Override
    public Result<UserSimpleVO> signUp(SignUpDTO signUpDTO, HttpServletResponse res) {
        if (!StringUtils.hasText(signUpDTO.getUsername()) || !StringUtils.hasText(signUpDTO.getPassword()) || !StringUtils.hasText(signUpDTO.getEmail()) || !StringUtils.hasText(signUpDTO.getEmailCode())) {
            return Result.error("未知错误");
        }

        Result<String> result = email(signUpDTO.getEmail(), signUpDTO.getEmailCode());
        if (result != null) {
            return Result.error(result.getMsg());
        }

        if (userCommonMapper.checkUserExists(signUpDTO.getUsername(), signUpDTO.getEmail()) != null) {
            return Result.error("用户已存在");
        }

        User user = new User();
        user.setName(NameUtil.getName());
        user.setUsername(signUpDTO.getUsername());
        user.setPassword(DigestUtils.md5DigestAsHex(signUpDTO.getPassword().getBytes()));
        user.setEmail(signUpDTO.getEmail());
        // 写入数据库
        userCommonMapper.addUser(user);

        // 设置jwt令牌并返回前端
        return setJwtToSignIn(user, res);
    }

    @Override
    public Result<String> signOut(HttpServletResponse res) {
        Cookie cookie = new Cookie("jwt_token", null);
        cookie.setHttpOnly(true);        // 防止 XSS 攻击
        cookie.setSecure(false);         // 本地开发用 false，生产环境用 true (HTTPS)
        cookie.setPath("/");             // 对整个应用有效
        cookie.setMaxAge(0);
        res.addCookie(cookie);
        stringRedisTemplate.delete(RedisConstant.User.SIGN_IN_JWT + UserContext.getCurrentId());
        return Result.success("已退出！");
    }

    // 电话合理性以及验证码验证
    private Result<String> email(String email, String code) {
        // 手机号位数验证待开发

        // 验证验证码
        String emailCode = stringRedisTemplate.opsForValue().get(RedisConstant.User.SIGN_IN_CODE + email);
        if (emailCode == null || !emailCode.equals(code)) {
            return Result.error("验证码错误");
        }

        // 合理，通过
        return null;
    }

    private Result<UserSimpleVO> setJwtToSignIn(User user, HttpServletResponse res) {
        // 转换为VO
        UserSimpleVO userSimpleVO = mapStruct.userToUserSimpleVO(user);

        //设置jwt
        HashMap<String, Object> claims = new HashMap<>();
        claims.put(JwtConstant.USER_ID, user.getId());
        claims.put(JwtConstant.USERNAME, user.getUsername());
        claims.put(JwtConstant.NAME, user.getName());
        claims.put(JwtConstant.ADMIN, user.getAdmin());
        String jwt = jwtUtil.createJWT(claims);

        // 存入Cookie
        Cookie cookie = new Cookie("jwt_token", jwt);
        cookie.setHttpOnly(true);        // 防止 XSS 攻击
        cookie.setSecure(false);         // 本地开发用 false，生产环境用 true (HTTPS)
        cookie.setPath("/");             // 对整个应用有效
        cookie.setMaxAge(7 * 24 * 60 * 60); // 7天过期
        res.addCookie(cookie);

        // 同时存入Redis做双重校验
        stringRedisTemplate.opsForValue().set(RedisConstant.User.SIGN_IN_JWT + user.getId(), jwt, ExpireConstant.DAY_7, TimeUnit.MINUTES);

        return Result.success(userSimpleVO, "登陆/注册成功！");
    }

    @Override
    public Result<UserSimpleVO> getProfile(HttpServletRequest req, HttpServletResponse res) {
        if (UserContext.getCurrentId() == null) {
            return Result.error();
        }

        User user = userCommonMapper.getUser(UserContext.getCurrentId());
        UserSimpleVO userSimpleVO = mapStruct.userToUserSimpleVO(user);
        return Result.success(userSimpleVO);
    }
}

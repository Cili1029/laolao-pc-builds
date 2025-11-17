package com.laolao.service.user.impl;

import com.laolao.common.constant.JwtClaimsConstant;
import com.laolao.common.constant.MessageConstant;
import com.laolao.common.constant.RedisConstant;
import com.laolao.common.context.BaseContext;
import com.laolao.common.utils.AliDirectMailUtil;
import com.laolao.common.utils.NameUtil;
import com.laolao.converter.MapStruct;
import com.laolao.common.exception.UnknownError;
import com.laolao.mapper.forum.LikeMapper;
import com.laolao.mapper.forum.PostMapper;
import com.laolao.mapper.user.UserMapper;
import com.laolao.pojo.forum.entity.Post;
import com.laolao.pojo.forum.vo.PostSimpleVO;
import com.laolao.pojo.shop.entity.ShopCoupon;
import com.laolao.pojo.shop.vo.ShopCouponVO;
import com.laolao.pojo.user.dto.SignInWithEmailDTO;
import com.laolao.pojo.user.dto.SignInWithUsernameDTO;
import com.laolao.pojo.user.dto.SignUpDTO;
import com.laolao.pojo.user.entity.User;
import com.laolao.pojo.user.vo.UpdateUserVO;
import com.laolao.pojo.user.vo.UserSimpleVO;
import com.laolao.common.result.Result;
import com.laolao.pojo.user.vo.UserVO;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private AliDirectMailUtil aliDirectMailUtil;
    @Resource
    private PostMapper postMapper;
    @Resource
    private LikeMapper likeMapper;

    @Override
    public Result<String> getEmailCode(String email) throws Exception {
        // 手机号位数验证待开发 应该先验证用户是否存在，在验证验证码
        if (!StringUtils.hasText(email)) {
            throw new UnknownError(MessageConstant.UNKNOWN_ERROR);
        }

        String code = String.format("%06d", ThreadLocalRandom.current().nextInt(0, 1000000));

        Boolean result = aliDirectMailUtil.sendEmail(email, "您的验证码是<strong>" + code + "</strong>");
        if (!result) {
            throw new UnknownError(MessageConstant.UNKNOWN_ERROR);
        }

        // 验证码存入Redis用于验证
        stringRedisTemplate.opsForValue().set(RedisConstant.SIGN_IN_CODE_KEY + email, code, RedisConstant.SIGN_IN_CODE_TTL, TimeUnit.MINUTES);
        return Result.success("已发送！");
    }

    @Override
    public Result<UserSimpleVO> signInWithUsername(SignInWithUsernameDTO signInWithUsernameDTO, HttpServletResponse res) {
            User user = userMapper.checkUserExists(signInWithUsernameDTO.getUsername(), null);

            // 用户不存在
            if (user == null) {
                return Result.error(MessageConstant.USER_NOT_FOUND);
            }

            // 加密密码进行比对，错误则密码错误
            String password = DigestUtils.md5DigestAsHex(signInWithUsernameDTO.getPassword().getBytes());
            if (!password.equals(user.getPassword())) {
                return Result.error(MessageConstant.PASSWORD_ERROR);
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
            User user = userMapper.checkUserExists(null, signInWithEmailDTO.getEmail());
            // 用户不存在
            if (user == null) {
                return Result.error(MessageConstant.USER_NOT_FOUND);
            }
            return setJwtToSignIn(user, res);
    }

    @Override
    public Result<UserSimpleVO> signUp(SignUpDTO signUpDTO, HttpServletResponse res) {
        if (!StringUtils.hasText(signUpDTO.getUsername()) || !StringUtils.hasText(signUpDTO.getPassword()) || !StringUtils.hasText(signUpDTO.getEmail()) || !StringUtils.hasText(signUpDTO.getEmailCode())) {
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }

        Result<String> result = email(signUpDTO.getEmail(), signUpDTO.getEmailCode());
        if (result != null) {
            return Result.error(result.getMsg());
        }

        if (userMapper.checkUserExists(signUpDTO.getUsername(), signUpDTO.getEmail()) != null) {
            return Result.error(MessageConstant.USER_ALREADY_EXISTS);
        }

        User user = new User();
        user.setName(NameUtil.generateName());
        user.setUsername(signUpDTO.getUsername());
        user.setPassword(DigestUtils.md5DigestAsHex(signUpDTO.getPassword().getBytes()));
        user.setEmail(signUpDTO.getEmail());
        // 写入数据库
        userMapper.addUser(user);

        // 设置jwt令牌并返回前端
        return setJwtToSignIn(user, res);
    }

    @Override
    public Result<String> signOut(HttpServletResponse res) {
        Cookie cookie = new Cookie("jwt_token", null);
        cookie.setHttpOnly(true);        // 防止 XSS 攻击
        cookie.setSecure(false);         // 本地开发用 false，生产环境用 true (HTTPS)
        cookie.setPath("/");             // 对整个应用有效
        cookie.setMaxAge(0); // 7天过期
        res.addCookie(cookie);
        return Result.success("已退出！");
    }

    @Override
    public Result<UserSimpleVO> getProfile(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        String jwt = getJwtFromCookie(cookies);
        try {
            Claims claims = jwtUtil.parseJWT(jwt);
            long userId = Long.parseLong(claims.get(JwtClaimsConstant.USER_ID).toString());
            User user = userMapper.getUser(userId);
            UserSimpleVO userSimpleVO = mapStruct.userToUserSimpleVO(user);
            return Result.success(userSimpleVO);
        }  catch (Exception e) {
            return Result.error();
        }
    }

    @Override
    public Result<UserVO> getUser(Integer id) {
        // 获取用户的信息
        User user = userMapper.getUser(id);
        UserSimpleVO userSimpleVO = mapStruct.userToUserSimpleVO(user);
        // 获取用户的帖子
        List<Post> postList = postMapper.selectPostByUserid(id);
        ArrayList<PostSimpleVO> userPostList = new ArrayList<>();
        for (Post post : postList) {
            PostSimpleVO postSimpleVO = mapStruct.PostToSimpleVO(post);
            userPostList.add(postSimpleVO);
        }
        // 组装
        UserVO userVO = UserVO.builder()
                .user(userSimpleVO)
                .userPostList(userPostList)
                .build();
        return Result.success(userVO);
    }

    @Override
    public Result<List<PostSimpleVO>> getLike(Integer id) {
        // 获取喜欢的帖子
        List<Post> postList;
        List<Integer> postIdList = likeMapper.getLikePost(id);
        ArrayList<PostSimpleVO> likePostList = new ArrayList<>();
        if (!postIdList.isEmpty()) {
            postList = postMapper.getPostBatch(postIdList);
            for (Post post : postList) {
                PostSimpleVO postSimpleVO = mapStruct.PostToSimpleVO(post);
                likePostList.add(postSimpleVO);
            }
        }
        return Result.success(likePostList);
    }

    @Override
    public Result<String> update(UpdateUserVO updateUserVO) {
        updateUserVO.setId(BaseContext.getCurrentId());
        userMapper.updateUser(updateUserVO);
        return Result.success("修改成功！");
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
        String emailCode = stringRedisTemplate.opsForValue().get(RedisConstant.SIGN_IN_CODE_KEY + email);
        if (emailCode == null || !emailCode.equals(code)) {
            return Result.error(MessageConstant.EMAILCODE_ERROR);
        }

        // 合理，通过
        return null;
    }

    private Result<UserSimpleVO> setJwtToSignIn(User user, HttpServletResponse res) {
        // 转换为VO
        UserSimpleVO userSimpleVO = mapStruct.userToUserSimpleVO(user);

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

        return Result.success(userSimpleVO, "登陆/注册成功！");
    }

}

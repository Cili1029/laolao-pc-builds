package com.laolao.controller.user.user;

import com.laolao.pojo.forum.vo.PostSimpleVO;
import com.laolao.pojo.user.dto.UpdateUserDTO;
import com.laolao.common.result.Result;
import com.laolao.pojo.user.vo.UserVO;
import com.laolao.service.user.user.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户端-用户相关
 */
@RestController
@RequestMapping("/user/user")
public class UserController {
    @Resource
    private UserService userService;

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
     * 获取用户喜欢的帖子
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
     * @param updateUserDTO 新数据
     * @return 消息结果
     */
    @PostMapping("/update")
    public Result<String> updateUser(@RequestBody UpdateUserDTO updateUserDTO) {
        return userService.update(updateUserDTO);
    }
}

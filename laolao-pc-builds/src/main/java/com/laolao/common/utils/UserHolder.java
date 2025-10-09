package com.laolao.common.utils;

import com.laolao.pojo.vo.UserVO;

// 获取当前用户id
public class UserHolder {
    private static final ThreadLocal<UserVO> userHolder = new ThreadLocal<>();

    public static void setUser(UserVO user) {
        userHolder.set(user);
    }

    public static UserVO getUser() {
        return userHolder.get();
    }

    public static void removeUser() {
        userHolder.remove();
    }
}

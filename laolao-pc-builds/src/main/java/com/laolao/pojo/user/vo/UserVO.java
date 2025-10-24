package com.laolao.pojo.user.vo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserVO {
    /**
     * 主键
     */
    private int id;

    /**
     * 账号
     */
    private String username;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 用户名
     */
    private String name;
}

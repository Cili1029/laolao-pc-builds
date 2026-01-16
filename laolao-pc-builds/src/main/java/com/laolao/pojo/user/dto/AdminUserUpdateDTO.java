package com.laolao.pojo.user.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdminUserUpdateDTO {
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

    /**
     * 邮箱
     */
    private String email;

    /**
     * 管理权限
     */
    private int admin;
}

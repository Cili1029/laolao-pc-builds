package com.laolao.pojo.user.entity;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User implements Serializable {
    /**
     * 主键
     */
    private int id;

    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

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
     * 状态
     */
    private int status;

    /**
     * 管理权限
     */
    private int admin;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
}

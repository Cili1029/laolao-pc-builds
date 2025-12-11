package com.laolao.pojo.user.vo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserSimpleVO {
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
     * 管理权限
     */
    private int admin;
}

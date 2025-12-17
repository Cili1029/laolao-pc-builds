package com.laolao.pojo.dashboard.vo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDashboardVO {
    /**
     * 主键
     */
    private int id;

    /**
     * 昵称
     */
    private String username;

    /**
     * 头像
     */
    private String avatar;
}

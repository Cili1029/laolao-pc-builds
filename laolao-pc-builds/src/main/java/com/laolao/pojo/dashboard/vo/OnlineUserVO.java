package com.laolao.pojo.dashboard.vo;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OnlineUserVO {
    /**
     * 在线用户
     */
    private int onlineCount;

    /**
     * 用户信息
     */
    private List<UserDashboardVO> onlineUsers;
}

package com.laolao.pojo.dashboard.vo;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDashboardSummaryVO {
    /**
     * 新增用户
     */
    private int newCount;

    /**
     * 用户数据
     */
    private List<UserDashboardVO> newUsers;

    /**
     * 总用户数
     */
    private int totalCount;

    /**
     * 每月新增用户数
     */
    private List<MonthCountVO> monthCounts;

}


package com.laolao.pojo.dashboard.vo;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ForumDashboardSummaryVO {
    /**
     * 分类数
     */
    private int categoryCount;

    /**
     * 贴子数
     */
    private int postCount;

    /**
     * 评论数
     */
    private int commentCount;

    /**
     * 本月发布贴子数
     */
    private int monthPostCount;

    /**
     * 本月发布评论数
     */
    private int monthCommentCount;

    /**
     * 每月贴子数
     */
    private List<MonthDataVO> allMonthPostCount;

    /**
     * 论坛分类排名
     */
    private List<HotForumCategoryVO> hotCategories;
}


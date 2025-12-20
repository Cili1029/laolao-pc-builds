package com.laolao.service.admin.dashboard;

import com.laolao.common.result.Result;
import com.laolao.pojo.dashboard.vo.ForumDashboardSummaryVO;
import com.laolao.pojo.dashboard.vo.ShopDashboardSummaryVO;
import com.laolao.pojo.dashboard.vo.UserDashboardSummaryVO;

public interface DashboardService {
    Result<UserDashboardSummaryVO> getUserSummary();

    Result<ShopDashboardSummaryVO> getShopSummary();

    Result<ForumDashboardSummaryVO> getForumSummary();
}

package com.laolao.controller.admin.dashboard;

import com.laolao.common.result.Result;
import com.laolao.pojo.dashboard.vo.ForumDashboardSummaryVO;
import com.laolao.pojo.dashboard.vo.ShopDashboardSummaryVO;
import com.laolao.pojo.dashboard.vo.UserDashboardSummaryVO;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理端-用户仪表盘统计相关
 */
@RestController
@RequestMapping("/admin/dashboard")
public class DashboardController {
    @Resource
    private com.laolao.service.admin.dashboard.DashboardService DashboardService;

    /**
     * 获取用户汇总数据
     *
     * @return 汇总数据
     */
    @GetMapping("/user_summary")
    public Result<UserDashboardSummaryVO> getUserSummary() {
        return DashboardService.getUserSummary();
    }

    /**
     * 获取商城汇总数据
     *
     * @return 汇总数据
     */
    @GetMapping("/shop_summary")
    public Result<ShopDashboardSummaryVO> getShopSummary() {
        return DashboardService.getShopSummary();
    }

    /**
     * 获取论坛汇总数据
     *
     * @return 汇总数据
     */
    @GetMapping("/forum_summary")
    public Result<ForumDashboardSummaryVO> getForumSummary() {
        return DashboardService.getForumSummary();
    }
}

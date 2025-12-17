package com.laolao.controller.admin.dashBoard;

import com.laolao.common.result.Result;
import com.laolao.pojo.dashboard.vo.UserDashboardSummaryVO;
import com.laolao.service.admin.dashboard.UserDashboardService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理端-用户仪表盘统计相关
 */
@RestController
@RequestMapping("/admin/dashboard")
public class UserDashboardController {
    @Resource
    private UserDashboardService userDashboardService;

    /**
     * 获取用户汇总数据
     *
     * @return 汇总数据
     */
    @GetMapping("/user_summary")
    public Result<UserDashboardSummaryVO> list() {
        return userDashboardService.getSummary();
    }
}

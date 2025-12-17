package com.laolao.service.admin.dashboard;

import com.laolao.common.result.Result;
import com.laolao.pojo.dashboard.vo.UserDashboardSummaryVO;

public interface UserDashboardService {
    Result<UserDashboardSummaryVO> getSummary();
}

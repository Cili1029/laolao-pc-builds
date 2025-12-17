package com.laolao.service.admin.dashboard.Impl;

import com.laolao.common.result.Result;
import com.laolao.mapper.dashboard.UserDashboardMapper;
import com.laolao.pojo.dashboard.vo.MonthCountVO;
import com.laolao.pojo.dashboard.vo.UserDashboardSummaryVO;
import com.laolao.pojo.dashboard.vo.UserDashboardVO;
import com.laolao.service.admin.dashboard.UserDashboardService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class UserDashboardServiceImpl implements UserDashboardService {
    @Resource
    private UserDashboardMapper userDashboardMapper;

    @Override
    public Result<UserDashboardSummaryVO> getSummary() {
        UserDashboardSummaryVO userDashboardSummaryVO = new UserDashboardSummaryVO();
        // 总用户
        userDashboardSummaryVO.setTotalCount(userDashboardMapper.selectTotalUser());

        // 新用户
        LocalDateTime monthStart = LocalDateTime.of(
                LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()), // 本月第一天
                LocalTime.MIN // 00:00:00
        );
        List<UserDashboardVO> newUserList = userDashboardMapper.selectNewUser(monthStart);
        if (!CollectionUtils.isEmpty(newUserList)) {
            userDashboardSummaryVO.setNewCount(newUserList.size());
            userDashboardSummaryVO.setNewUsers(newUserList);
        }

        // 表数据
        List<MonthCountVO> monthCountVOList = userDashboardMapper.selectMonthCount();
        userDashboardSummaryVO.setMonthCounts(monthCountVOList);
        return Result.success(userDashboardSummaryVO);
    }
}

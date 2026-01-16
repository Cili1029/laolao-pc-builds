package com.laolao.service.admin.dashboard.Impl;

import com.laolao.common.constant.RedisConstant;
import com.laolao.common.result.Result;
import com.laolao.mapper.dashboard.ForumDashboardMapper;
import com.laolao.mapper.dashboard.ShopDashboardMapper;
import com.laolao.mapper.dashboard.UserDashboardMapper;
import com.laolao.pojo.dashboard.vo.*;
import com.laolao.service.admin.dashboard.DashboardService;
import jakarta.annotation.Resource;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {
    @Resource
    private UserDashboardMapper userDashboardMapper;
    @Resource
    private ShopDashboardMapper shopDashboardMapper;
    @Resource
    private ForumDashboardMapper forumDashboardMapper;

    @Override
    @Cacheable(value = RedisConstant.Admin.CACHE_NAME + RedisConstant.Expire.MIN_10,
            key = "T(com.laolao.common.constant.RedisConstant$Admin).USER")
    public Result<UserDashboardSummaryVO> getUserSummary() {
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
        List<MonthDataVO> monthDataVOList = userDashboardMapper.selectMonthCount();
        userDashboardSummaryVO.setMonthCounts(monthDataVOList);
        return Result.success(userDashboardSummaryVO);
    }

    @Override
    @Cacheable(value = RedisConstant.Admin.CACHE_NAME + RedisConstant.Expire.MIN_10,
            key = "T(com.laolao.common.constant.RedisConstant$Admin).SHOP")
    public Result<ShopDashboardSummaryVO> getShopSummary() {
        // 获取商品数据
        ShopDashboardSummaryVO shopDashboardSummaryVO = new ShopDashboardSummaryVO();
        shopDashboardSummaryVO.setComponentCount(shopDashboardMapper.selectComponentCount());
        shopDashboardSummaryVO.setBundleCount(shopDashboardMapper.selectBundleCount());

        // 本月销售额
        LocalDateTime monthStart = LocalDateTime.of(
                LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()), // 本月第一天
                LocalTime.MIN // 00:00:00
        );
        CompleteOrderDataVO completeOrderDataVO =  shopDashboardMapper.selectMonthSalesAmount(monthStart);
        shopDashboardSummaryVO.setCompletedOrderCount(completeOrderDataVO.getCompletedOrderCount());
        shopDashboardSummaryVO.setMonthSalesAmount(completeOrderDataVO.getMonthSalesAmount());
        shopDashboardSummaryVO.setOrderCount(shopDashboardMapper.selectOrderCount(monthStart));

        // 表数据
        List<MonthOrderAmountVO> monthDataVOList = shopDashboardMapper.selectMonthAmount();
        shopDashboardSummaryVO.setMonthAmount(monthDataVOList);

        // 六个最高销售商品
        shopDashboardSummaryVO.setMonthProducts(shopDashboardMapper.getHot(monthStart));
        return Result.success(shopDashboardSummaryVO);
    }

    @Override
    @Cacheable(value = RedisConstant.Admin.CACHE_NAME + RedisConstant.Expire.MIN_10,
            key = "T(com.laolao.common.constant.RedisConstant$Admin).FORUM")
    public Result<ForumDashboardSummaryVO> getForumSummary() {
        ForumDashboardSummaryVO forumDashboardSummaryVO = new ForumDashboardSummaryVO();
        forumDashboardSummaryVO.setCategoryCount(forumDashboardMapper.getCategoryCount());
        PostCommentVO postCommentVO = forumDashboardMapper.getPostComment();
        forumDashboardSummaryVO.setPostCount(postCommentVO.getPostCount());
        forumDashboardSummaryVO.setCommentCount(postCommentVO.getCommentCount());

        LocalDateTime monthStart = LocalDateTime.of(
                LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()), // 本月第一天
                LocalTime.MIN // 00:00:00
        );
        forumDashboardSummaryVO.setMonthPostCount(forumDashboardMapper.getMonthPostCount(monthStart));
        forumDashboardSummaryVO.setMonthCommentCount(forumDashboardMapper.getMonthCommentCount(monthStart));
        forumDashboardSummaryVO.setAllMonthPostCount(forumDashboardMapper.getAllMonthPostCount());
        forumDashboardSummaryVO.setHotCategories(forumDashboardMapper.getHotCategories(monthStart));
        return Result.success(forumDashboardSummaryVO);
    }
}

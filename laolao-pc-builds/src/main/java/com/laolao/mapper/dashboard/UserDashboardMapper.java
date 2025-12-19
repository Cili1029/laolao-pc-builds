package com.laolao.mapper.dashboard;

import com.laolao.pojo.dashboard.vo.MonthDataVO;
import com.laolao.pojo.dashboard.vo.UserDashboardVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface UserDashboardMapper {
    List<UserDashboardVO> selectOnlineUser(List<Integer> onlineUsersIds);

    @Select("select id, username, avatar from user " +
            "where created_at >= #{monthStart} " +
            "and created_at <= NOW()")
    List<UserDashboardVO> selectNewUser(LocalDateTime today);

    @Select("select count(id) from user")
    int selectTotalUser();

    @Select("""
                WITH RECURSIVE MonthSequence AS (
                    SELECT DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 11 MONTH), '%Y-%m') AS month_str,
                            DATE_SUB(CURDATE(), INTERVAL 11 MONTH)                       AS raw_date
                    UNION ALL
                    SELECT DATE_FORMAT(DATE_ADD(raw_date, INTERVAL 1 MONTH), '%Y-%m'),
                            DATE_ADD(raw_date, INTERVAL 1 MONTH)
                    FROM MonthSequence
                    WHERE raw_date < DATE_FORMAT(CURDATE(), '%Y-%m-01'))
                SELECT m.month_str AS month,
                        COUNT(u.id) AS value
                FROM MonthSequence m
                            LEFT JOIN
                        user u ON DATE_FORMAT(u.created_at, '%Y-%m') = m.month_str
                GROUP BY m.month_str
                ORDER BY m.month_str;
            """)
    List<MonthDataVO> selectMonthCount();
}

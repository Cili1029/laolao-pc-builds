package com.laolao.mapper.dashboard;

import com.laolao.pojo.dashboard.vo.CompleteOrderDataVO;
import com.laolao.pojo.dashboard.vo.MonthOrderAmountVO;
import com.laolao.pojo.dashboard.vo.MonthProductVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ShopDashboardMapper {
    @Select("select count(id) from shop_bundle")
    int selectBundleCount();

    @Select("select count(id) from shop_component")
    int selectComponentCount();

    @Select("select count(id) as completed_order_count, IFNULL(sum(original_amount - discount_amount), 0) as month_sales_amount " +
            "from shop_order where status = 5 and receive_time >= #{monthStart} and receive_time <= NOW()")
    CompleteOrderDataVO selectMonthSalesAmount(LocalDateTime monthStart);

    @Select("select count(id) from shop_order where created_at >= #{monthStart} and created_at <= NOW()")
    int selectOrderCount(LocalDateTime monthStart);

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
                        sum(original_amount - discount_amount) AS amount
                FROM MonthSequence m
                            LEFT JOIN
                        shop_order u ON DATE_FORMAT(u.receive_time, '%Y-%m') = m.month_str
                GROUP BY m.month_str
                ORDER BY m.month_str;
            """)
    List<MonthOrderAmountVO> selectMonthAmount();

    @Select("select name, count(*) sales from shop_order_detail " +
            "where created_at >= #{monthStart} and created_at <= NOW() group by name order by sales desc")
    List<MonthProductVO> getHot(LocalDateTime monthStart);
}

package com.laolao.mapper.dashboard;

import com.laolao.pojo.dashboard.vo.HotForumCategoryVO;
import com.laolao.pojo.dashboard.vo.MonthDataVO;
import com.laolao.pojo.dashboard.vo.PostCommentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ForumDashboardMapper {

    @Select("select count(id) from forum_category")
    int getCategoryCount();

    @Select("select count(id) postCount, sum(comment_count) commentCount from forum_post")
    PostCommentVO getPostComment();

    @Select("select count(id) from forum_post where created_at >= #{monthStart} and created_at <= NOW()")
    int getMonthPostCount(LocalDateTime monthStart);

    @Select("select count(id) from forum_comment where created_at >= #{monthStart} and created_at <= NOW()")
    int getMonthCommentCount(LocalDateTime monthStart);

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
                        count(p.id) AS value
                FROM MonthSequence m
                            LEFT JOIN
                        forum_post p ON DATE_FORMAT(p.created_at, '%Y-%m') = m.month_str
                GROUP BY m.month_str
                ORDER BY m.month_str;
            """)
    List<MonthDataVO> getAllMonthPostCount();

    @Select("""
                select c.name, count(f.id) post_count
                from forum_post f
                         join forum_category c on f.category_id = c.id
                where f.created_at >= #{monthStart} and f.created_at <= NOW()
                group by f.category_id
                order by post_count desc;
            """)
    List<HotForumCategoryVO> getHotCategories(LocalDateTime monthStart);
}

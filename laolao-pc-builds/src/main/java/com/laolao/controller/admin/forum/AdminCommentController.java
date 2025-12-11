package com.laolao.controller.admin.forum;

import com.laolao.common.annotation.LogSql;
import com.laolao.common.result.Result;
import com.laolao.service.admin.forum.AdminCommentService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;


/**
 * 客户端帖子相关
 */
@RestController
@RequestMapping("/admin/forum/post")
public class AdminCommentController {
    @Resource
    private AdminCommentService adminCommentService;

    /**
     * 删除评论/回复
     *
     * @param id 主键
     * @return 结果信息
     */
    @LogSql(description = "删除评论")
    @DeleteMapping("/comment/{type}/{id}")
    public Result<String> deletePost(@PathVariable Integer type, @PathVariable Integer id) {
        return adminCommentService.deleteComment(type, id);
    }
}

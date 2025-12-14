package com.laolao.controller.admin.forum;

import com.github.pagehelper.PageInfo;
import com.laolao.common.annotation.LogSql;
import com.laolao.common.result.Result;
import com.laolao.pojo.forum.entity.Post;
import com.laolao.service.admin.forum.AdminPostService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;


/**
 * 管理端-帖子相关
 */
@RestController
@RequestMapping("/admin/forum/post")
public class AdminPostController {
    @Resource
    private AdminPostService adminPostService;

    /**
     * 获取帖子
     *
     * @return 帖子数据
     */
    @GetMapping()
    public Result<PageInfo<Post>> getPost(Integer pageNum, Integer pageSize) {
        return adminPostService.getPost(pageNum, pageSize);
    }

    /**
     * 更新类别
     *
     * @param id 主键
     * @param categoryId 类别主键
     * @return 结果信息
     */
    @LogSql(description = "更新帖子类别")
    @PatchMapping("/{id}/category")
    public Result<String> updateCategory(@PathVariable Integer id, @RequestParam int categoryId) {
        return adminPostService.updateCategory(id, categoryId);
    }

    /**
     * 删除帖子
     *
     * @param id 主键
     * @return 结果信息
     */
    @LogSql(description = "删除帖子")
    @DeleteMapping()
    public Result<String> deletePost(@RequestParam Integer id) {
        return adminPostService.deletePost(id);
    }

    /**
     * 解锁帖子
     *
     * @param id 帖子Id
     * @return 结果信息
     */
    @LogSql(description = "解锁帖子")
    @PatchMapping("/{id}/activate")
    public Result<String> activate(@PathVariable Integer id) {
        return adminPostService.changeStatus(id, 1);
    }

    /**
     * 锁定帖子
     *
     * @param id 帖子Id
     * @return 结果信息
     */
    @LogSql(description = "锁定帖子")
    @PatchMapping("/{id}/deactivate")
    public Result<String> deactivate(@PathVariable Integer id) {
        return adminPostService.changeStatus(id, 0);
    }
}

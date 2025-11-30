package com.laolao.controller.admin.forum;

import com.laolao.common.result.Result;
import com.laolao.pojo.common.StatusDTO;
import com.laolao.pojo.forum.dto.AdminForumCategoryDTO;
import com.laolao.pojo.forum.entity.ForumCategory;
import com.laolao.service.admin.forum.AdminForumCategoryService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 客户端帖子分类相关
 */
@RestController
@RequestMapping("/admin/forum/category")
public class AdminForumCategoryController {
    @Resource
    private AdminForumCategoryService adminForumCategoryService;

    /**
     * 获取帖子分类
     *
     * @return 帖子分类数据
     */
    @GetMapping("list")
    public Result<List<ForumCategory>> list() {
        return adminForumCategoryService.get();
    }

    /**
     * 启用分类
     *
     * @param statusDTO 分类Id
     * @return 结果信息
     */
    @PatchMapping("/activate")
    public Result<String> activate(@RequestBody StatusDTO statusDTO) {
        return adminForumCategoryService.changeStatus(statusDTO.getId(), 1);
    }

    /**
     * 禁用分类
     *
     * @param statusDTO 分类Id
     * @return 结果信息
     */
    @PatchMapping("/deactivate")
    public Result<String> deactivate(@RequestBody StatusDTO statusDTO) {
        return adminForumCategoryService.changeStatus(statusDTO.getId(), 0);
    }

    /**
     * 更新分类信息
     *
     * @param adminForumCategoryDTO 更新信息
     * @return 结果信息
     */
    @PatchMapping("/update")
    public Result<String> update(@RequestBody AdminForumCategoryDTO adminForumCategoryDTO) {
        return adminForumCategoryService.update(adminForumCategoryDTO);
    }

    /**
     * 新增分类
     *
     * @param adminForumCategoryDTO 信息
     * @return 结果消息
     */
    @PostMapping("/add")
    public Result<String> add(@RequestBody AdminForumCategoryDTO adminForumCategoryDTO) {
        return adminForumCategoryService.add(adminForumCategoryDTO);
    }

    /**
     * 删除分类
     *
     * @param id id
     * @return 消息结果
     */
    @DeleteMapping("/delete")
    public Result<String> add(@RequestParam int id) {
        return adminForumCategoryService.delete(id);
    }
}

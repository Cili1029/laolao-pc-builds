package com.laolao.controller.user.forum;

import com.laolao.common.result.Result;
import com.laolao.pojo.forum.vo.ForumCategoryVO;
import com.laolao.service.forum.ForumCategoryService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 论坛帖子分类相关
 */
@RestController
@RequestMapping("/user/forum/category")
public class FormCategoryController {

    @Resource
    private ForumCategoryService forumCategoryService;

    /**
     * 获取帖子分类
     *
     * @return 帖子分类数据
     */
    @GetMapping("list")
    public Result<List<ForumCategoryVO>> list() {
        return forumCategoryService.list();
    }
}

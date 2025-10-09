package com.laolao.controller.user;

import com.laolao.pojo.vo.CategoryVO;
import com.laolao.common.result.Result;
import com.laolao.service.CategoryService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @GetMapping("list")
    public Result<List<CategoryVO>> list() {

        return categoryService.list();
    }
}

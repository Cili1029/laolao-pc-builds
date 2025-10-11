package com.laolao.controller.user;

import com.laolao.pojo.vo.ComponentVariantVO;
import com.laolao.pojo.vo.ProductVO;
import com.laolao.common.result.Result;
import com.laolao.service.ProductService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/products")
public class ProductController {

    @Resource
    private ProductService productService;

    // 单件
    @GetMapping("/components")
    public Result<List<ProductVO>> Components(int categoryId) {

        return productService.listWithCategoryId(categoryId);
    }

    @GetMapping("/variants")
    public Result<List<ComponentVariantVO>> Variants(int id, int type) {

        return productService.listWithComponentId(id, type);
    }

    @GetMapping("/search")
    public Result<List<ProductVO>> SearchComponentByName(int categoryId, String searchContent) {
        return productService.searchByName(categoryId,searchContent);
    }


    // 主机
    @GetMapping("/bundles")
    public Result<List<ProductVO>> Bundles(int categoryId) {
        return productService.listWithCategoryId(categoryId);
    }
}

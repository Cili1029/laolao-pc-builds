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

/**
 * 商品相关
 */

@RestController
@RequestMapping("/user/products")
public class ProductController {

    @Resource
    private ProductService productService;

    /**
     * 获取组件
     * 如显卡，CPU。。。
     *
     * @param categoryId 商品分类id
     * @return 组件信息
     */
    @GetMapping("/components")
    public Result<List<ProductVO>> Components(int categoryId) {

        return productService.listWithCategoryId(categoryId);
    }

    /**
     * 获取组件的各种版本
     * 8G，16G，，，
     *
     * @param id 组件Id
     * @param productType 商品类型
     * @return 版本信息
     */
    @GetMapping("/variants")
    public Result<List<ComponentVariantVO>> Variants(int id, int productType) {

        return productService.listWithComponentId(id, productType);
    }

    /**
     * 搜索功能
     * 支持模糊搜索，searchContent可有可无
     *
     * @param categoryId 商品分类Id
     * @param searchContent 搜索内容
     * @return 符合的商品信息
     */
    @GetMapping("/search")
    public Result<List<ProductVO>> SearchComponentByName(int categoryId, String searchContent) {
        return productService.searchByName(categoryId,searchContent);
    }


    /**获取套餐
     * 如站长推荐，网友推荐
     *
     * @param categoryId 商品分类Id
     * @return 套餐信息
     */
    @GetMapping("/bundles")
    public Result<List<ProductVO>> Bundles(int categoryId) {
        return productService.listWithCategoryId(categoryId);
    }
}

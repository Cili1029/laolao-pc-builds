package com.laolao.controller.user.shop;

import com.laolao.pojo.shop.vo.BundleDetailsVO;
import com.laolao.pojo.shop.vo.ComponentDetailsVO;
import com.laolao.pojo.shop.vo.ProductVO;
import com.laolao.common.result.Result;
import com.laolao.service.shop.ProductService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商品相关
 */
@RestController
@RequestMapping("/user/shop/products")
public class ProductController {

    @Resource
    private ProductService productService;

    /**
     * 获取组件（简页）
     * 如显卡，CPU。。。
     *
     * @param categoryId 商品分类id
     * @return 组件数据
     */
    @GetMapping("/components")
    public Result<List<ProductVO>> Components(int categoryId) {
        return productService.getComponentListWithCategoryId(categoryId);
    }

    /**
     * 获取整机（简页）
     * 如站长推荐，网友推荐
     *
     * @param categoryId 商品分类Id
     * @return 整机数据
     */
    @GetMapping("/bundles")
    public Result<List<ProductVO>> Bundles(int categoryId) {
        return productService.getBundleListWithCategoryId(categoryId);
    }

    /**
     * 搜索功能
     * 支持模糊搜索，searchContent可有可无
     *
     * @param categoryId    商品分类Id
     * @param searchContent 搜索内容
     * @return 符合的商品数据
     */
    @GetMapping("/search")
    public Result<List<ProductVO>> SearchComponentByName(int categoryId, String searchContent) {
        return productService.searchByName(categoryId, searchContent);
    }


    /**
     * 商品详细页(组件)
     *
     * @param id 商品Id
     * @return 详细数据
     */
    @GetMapping("/component")
    public Result<ComponentDetailsVO> getComponentDetails(int id) {
        return productService.getComponentDetails(id);
    }

    /**
     * 商品详细页(整机)
     *
     * @param id 商品Id
     * @return 详细数据
     */
    @GetMapping("/bundle")
    public Result<BundleDetailsVO> getBundleDetails(int id) {
        return productService.getBundleDetails(id);
    }

    /**
     * 获取热门商品
     *
     * @param count 数量
     * @return 帖子简单信息
     */
    @GetMapping("/hot")
    public Result<List<ProductVO>> getHot(int count) {
        return productService.getHot(count);
    }
}

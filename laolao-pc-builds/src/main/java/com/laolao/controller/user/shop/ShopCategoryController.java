package com.laolao.controller.user.shop;

import com.laolao.pojo.shop.vo.ShopCategoryVO;
import com.laolao.common.result.Result;
import com.laolao.service.shop.ShopCategoryService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商品分类相关
 */
@Controller("shopCategoryController")
@RestController
@RequestMapping("/user/shop/category")
public class ShopCategoryController {

    @Resource
    private ShopCategoryService shopCategoryService;

    /**
     * 获取商品分类
     * 分组件和整机
     *
     * @return 商品分类数据
     */
    @GetMapping("list")
    public Result<List<ShopCategoryVO>> list() {

        return shopCategoryService.list();
    }
}

package com.laolao.controller.user;

import com.laolao.pojo.vo.ProductsEditionVO;
import com.laolao.pojo.vo.ProductsVO;
import com.laolao.result.Result;
import com.laolao.service.ProductsService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/products")
public class ProductsController {

    @Resource
    private ProductsService productsService;

    @GetMapping("/products-list")
    public Result<List<ProductsVO>> productsList(int categoryId) {

        return productsService.listWithCategoryId(categoryId);
    }

    @GetMapping("/edition-list")
    public Result<List<ProductsEditionVO>> editionList(int productsId) {

        return productsService.listWithProductsId(productsId);
    }

    @GetMapping("/search")
    public Result<List<ProductsVO>> SearchByName(int categoryId, String searchContent) {
        return productsService.searchByName(categoryId,searchContent);
    }
}

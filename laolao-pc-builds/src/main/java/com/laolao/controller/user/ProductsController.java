package com.laolao.controller.user;

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

    @GetMapping("list")
    public Result<List<ProductsVO>> list(int categoryId) {

        return productsService.listWithId(categoryId);
    }
}

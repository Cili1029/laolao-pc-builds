package com.laolao.service.impl;

import com.laolao.common.context.BaseContext;
import com.laolao.common.result.Result;
import com.laolao.mapper.BundleMapper;
import com.laolao.mapper.CartMapper;
import com.laolao.mapper.ComponentMapper;
import com.laolao.pojo.dto.CartProductDTO;
import com.laolao.pojo.entity.Bundle;
import com.laolao.pojo.entity.CartItem;
import com.laolao.pojo.entity.Variant;
import com.laolao.service.CartService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    @Resource
    private CartMapper cartMapper;
    @Resource
    private ComponentMapper componentMapper;
    @Resource
    private BundleMapper bundleMapper;

    @Override
    public Result<String> addToCart(CartProductDTO cartProductDTO) {
        // 查询是否在售,库存是否充足
        if (cartProductDTO.getType() == 1) {
            Variant variant = componentMapper.check(cartProductDTO.getProductId());
            if (variant == null) {
                return Result.error("商品不可购买（停售或无库存）");
            }
        } else {
            Bundle bundle = bundleMapper.check(cartProductDTO.getProductId());
            if (bundle == null) {
                return Result.error("商品不可购买（停售或无库存）");
            }
        }

        // 查询购物车表里有没有这个商品
        int userId = BaseContext.getCurrentId();
        CartItem cartItem = cartMapper.isExists(userId, cartProductDTO);
        if (cartItem == null) {
            // 无，添加
            cartMapper.add(userId,cartProductDTO);
        } else {
            // 有，数量加1
            cartMapper.update(userId,cartProductDTO);
        }

        return Result.success("加入购物车了！");
    }
}

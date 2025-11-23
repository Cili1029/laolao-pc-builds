package com.laolao.service.shop.impl;

import com.laolao.common.context.BaseContext;
import com.laolao.common.result.Result;
import com.laolao.converter.MapStruct;
import com.laolao.mapper.shop.BundleMapper;
import com.laolao.mapper.shop.CartMapper;
import com.laolao.mapper.shop.ComponentMapper;
import com.laolao.pojo.shop.dto.BuyProductDTO;
import com.laolao.pojo.shop.entity.Bundle;
import com.laolao.pojo.shop.entity.CartItem;
import com.laolao.pojo.shop.entity.Variant;
import com.laolao.pojo.shop.vo.CartProductVO;
import com.laolao.service.shop.CartService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Resource
    private CartMapper cartMapper;
    @Resource
    private ComponentMapper componentMapper;
    @Resource
    private BundleMapper bundleMapper;
    @Resource
    private MapStruct mapStruct;

    @Override
    public Result<List<CartProductVO>> getCartProductList() {
        int userId = BaseContext.getCurrentId();
        List<CartItem> cartItemList = cartMapper.getList(userId);

        // 组件的id
        List<Integer> componentIds = cartItemList.stream()
                .filter(cartItem -> cartItem.getProductType() == 1)
                .map(CartItem::getProductId)
                .toList();
        // 整机的id
        List<Integer> bundleIds = cartItemList.stream()
                .filter(cartItem -> cartItem.getProductType() == 2)
                .map(CartItem::getProductId)
                .toList();
        if (componentIds.isEmpty() && bundleIds.isEmpty()) {
            return Result.success(null);
        }

        List<CartProductVO> cartProductVOList = new ArrayList<>();
        if (!componentIds.isEmpty()) {
            cartProductVOList = componentMapper.ListFromCart(userId, componentIds);
        }
        if (!bundleIds.isEmpty()) {
            cartProductVOList.addAll(bundleMapper.ListFromCart(userId, bundleIds));
        }

        return Result.success(cartProductVOList);
    }

    @Override
    public Result<String> addToCart(BuyProductDTO buyProductDTO) {
        // 查询是否在售,库存是否充足
        if (buyProductDTO.getProductType() == 1) {
            Variant variant = componentMapper.check(buyProductDTO.getProductId());
            if (variant == null) {
                return Result.error("商品不可购买（停售或无库存）");
            }
        } else {
            Bundle bundle = bundleMapper.check(buyProductDTO.getProductId());
            if (bundle == null) {
                return Result.error("商品不可购买（停售或无库存）");
            }
        }

        // 查询购物车表里有没有这个商品
        int userId = BaseContext.getCurrentId();
        CartItem cartItem = mapStruct.cartProductDTOToCartItem(buyProductDTO);
        cartItem.setUserId(userId);

        CartItem res = cartMapper.isExists(cartItem);
        if (res == null) {
            // 无，添加
            cartMapper.add(cartItem);
        } else {
            // 有，加数量
            cartMapper.updateQuantity(cartItem, +buyProductDTO.getQuantity());
        }

        return Result.success("加入购物车了！");
    }


    @Override
    public Result<String> deleteFromCart(BuyProductDTO buyProductDTO) {
        // 判断数量是一个还是多个
        CartItem cartItem = mapStruct.cartProductDTOToCartItem(buyProductDTO);
        cartItem.setUserId(BaseContext.getCurrentId());
        CartItem res = cartMapper.isExists(cartItem);
        if (res == null) {
            return Result.error("购物车中不存在该商品");
        }
        // 大于一个
        if (res.getQuantity() > 1) {
            cartMapper.updateQuantity(cartItem, -1);
        } else {
            // 不止一个
            cartMapper.del(cartItem);
        }

        return Result.success("删除成功！");
    }

    @Override
    public Result<String> clearCart() {
        int userId = BaseContext.getCurrentId();
        cartMapper.clear(userId);
        return Result.success("清空购物车！");
    }
}

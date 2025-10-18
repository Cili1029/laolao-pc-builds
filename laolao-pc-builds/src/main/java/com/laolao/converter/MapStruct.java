package com.laolao.converter;

import com.laolao.pojo.dto.CartProductDTO;
import com.laolao.pojo.entity.*;
import com.laolao.pojo.vo.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * MapStruct的映射器
 * 用于VO entity VO的相互转换
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MapStruct {
    UserVO userToUserVO(User user);

    CategoryVO categoryToCategoryVO(Category category);

    ProductVO BundleToComponentVO(Bundle bundle);

    CartItem cartProductDTOToCartItem(CartProductDTO cartProductDTO);

    AddressVO addressToAddressVO(Address address);

    @Mapping(target = "id", ignore = true)
    OrderDetail CartProductVOToOrderDetail(CartProductVO cartProductVO);

}

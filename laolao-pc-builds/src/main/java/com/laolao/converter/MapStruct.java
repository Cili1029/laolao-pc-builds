package com.laolao.converter;

import com.laolao.pojo.dto.CartProductDTO;
import com.laolao.pojo.entity.*;
import com.laolao.pojo.vo.AddressVO;
import com.laolao.pojo.vo.CategoryVO;
import com.laolao.pojo.vo.ProductVO;
import com.laolao.pojo.vo.UserVO;
import org.mapstruct.Mapper;
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
}

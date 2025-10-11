package com.laolao.converter;

import com.laolao.pojo.entity.Bundle;
import com.laolao.pojo.entity.Category;
import com.laolao.pojo.entity.User;
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
}

package com.laolao.converter;

import com.laolao.pojo.entity.User;
import com.laolao.pojo.vo.UserLoginVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * MapStruct的映射器
 * 用于VO entity VO的相互转换
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapStruct {
    UserLoginVO userToUserLoginVO(User user);
}

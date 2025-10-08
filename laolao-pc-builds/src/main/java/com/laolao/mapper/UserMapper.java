package com.laolao.mapper;

import com.laolao.pojo.dto.UserLoginOrRegisterDTO;
import com.laolao.pojo.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface UserMapper {
    User checkUserExists(UserLoginOrRegisterDTO userLoginOrRegisterDTO);

    @Insert("insert into user (id, username, password, name, phone, create_time) " +
            "values (#{id}, #{username}, #{password}, #{name}, #{phone}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void addUser(User user);
}

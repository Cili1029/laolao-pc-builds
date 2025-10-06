package com.laolao.mapper;

import com.laolao.pojo.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select * from user where username = #{username}")
    User getByUsername(String username);

    @Insert("insert into user (id, username, password, name, phone, create_time) " +
            "values (#{id}, #{username}, #{password}, #{name}, #{phone}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void addUser(User user);
}

package com.laolao.mapper;

import com.laolao.pojo.dto.UserLoginOrRegisterDTO;
import com.laolao.pojo.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    User checkUserExists(UserLoginOrRegisterDTO userLoginOrRegisterDTO);

    @Insert("insert into users (id, username, password, name, email) " +
            "values (#{id}, #{username}, #{password}, #{name}, #{email})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void addUser(User user);


    @Select("select * from users where id = #{userId}")
    User getUser(long userId);
}

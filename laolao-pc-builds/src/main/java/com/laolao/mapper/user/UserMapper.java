package com.laolao.mapper.user;

import com.laolao.pojo.user.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    User checkUserExists(String username, String email);

    @Insert("insert into user (id, username, password, name, email) " +
            "values (#{id}, #{username}, #{password}, #{name}, #{email})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void addUser(User user);


    @Select("select * from user where id = #{userId}")
    User getUser(long userId);

    List<User> selectUser(List<Integer> userIdList1);
}

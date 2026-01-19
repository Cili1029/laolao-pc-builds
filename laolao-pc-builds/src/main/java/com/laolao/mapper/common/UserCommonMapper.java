package com.laolao.mapper.common;

import com.laolao.pojo.user.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserCommonMapper {
    User checkUserExists(String username, String email);

    @Insert("insert into user (id, username, password, name, email) " +
            "values (#{id}, #{username}, #{password}, #{name}, #{email})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void addUser(User user);


    @Select("select * from user where id = #{userId}")
    User getUser(int userId);

    List<User> selectUser(List<Integer> userIdList1);

    void updateUser(User user);
}

package com.laolao.mapper.user;

import com.laolao.pojo.shop.entity.ShopCoupon;
import com.laolao.pojo.user.entity.User;
import com.laolao.pojo.user.vo.UpdateUserVO;
import org.apache.ibatis.annotations.*;

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

    void updateUser(UpdateUserVO updateUserVO);

    @Select("select * from user_coupon where user_id = #{userId}")
    List<ShopCoupon> selectCoupon(int userId);
}

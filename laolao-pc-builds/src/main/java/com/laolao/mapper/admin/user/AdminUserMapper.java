package com.laolao.mapper.admin.user;

import com.laolao.pojo.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;

@Mapper
public interface AdminUserMapper {

    ArrayList<User> selectUser(String searchContent);

    @Update("update user set status = #{status}, updated_by = #{updatedBy}, updated_at = #{updatedAt} where id = #{id}")
    void updateStatus(User user);

    void updateUser(User user);
}

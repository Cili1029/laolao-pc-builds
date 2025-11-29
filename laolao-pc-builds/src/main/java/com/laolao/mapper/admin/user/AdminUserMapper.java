package com.laolao.mapper.admin.user;

import com.laolao.pojo.user.dto.AdminUserUpdateDTO;
import com.laolao.pojo.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;

@Mapper
public interface AdminUserMapper {

    ArrayList<User> selectUser(String searchContent);

    @Update("update user set status = #{status} where id = #{id}")
    void updateStatus(int id, int status);

    void updateUser(AdminUserUpdateDTO adminUserUpdateDTO);
}

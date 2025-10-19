package com.laolao.mapper;

import com.laolao.pojo.entity.Address;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AddressMapper {
    @Select("select * from address where user_id = #{userId}")
    List<Address> getList(int userId);

    @Insert("insert into address(user_id, consignee, phone, province, city, district, detail_address) " +
            "values (#{userId}, #{consignee}, #{phone}, #{province}, #{city}, #{district}, #{detailAddress})")
    void insert(Address address);


    @Update("update address set consignee = #{consignee}, phone = #{phone}, " +
            "province = #{province}, city = #{city}, district = #{district}, detail_address = #{detailAddress} " +
            "where id = #{id} and user_id = #{userId}")
    void update(Address address);

    @Delete("delete from address where id = #{id} and user_id = #{userId}")
    void delete(int id, int userId);


    void setDefault(int isDefault, Integer id, int userId);

    @Select("select * from address where user_id = #{userId} and is_default = 1")
    Address getDefault(int userId);

    @Select("select * from address where user_id = #{userId} and id = #{id}")
    Address get(int userId, int id);
}

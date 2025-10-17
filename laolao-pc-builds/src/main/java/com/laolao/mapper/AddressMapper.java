package com.laolao.mapper;

import com.laolao.pojo.entity.Address;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AddressMapper {
    @Select("select * from user_address where user_id = #{userId}")
    List<Address> getList(int userId);

    @Insert("insert into user_address(user_id, consignee, phone, province, city, district, detail_address) " +
            "values (#{userId}, #{consignee}, #{phone}, #{province}, #{city}, #{district}, #{detailAddress})")
    void insert(Address address);


    @Update("update user_address set consignee = #{consignee}, phone = #{phone}, " +
            "province = #{province}, city = #{city}, district = #{district}, detail_address = #{detailAddress} " +
            "where id = #{id} and user_id = #{userId}")
    void update(Address address);
}

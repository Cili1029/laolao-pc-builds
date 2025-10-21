package com.laolao.service.shop;

import com.laolao.common.result.Result;
import com.laolao.pojo.shop.entity.Address;
import com.laolao.pojo.shop.vo.AddressVO;
import com.laolao.pojo.shop.vo.DistrictVO;

import java.util.List;

public interface AddressService {
    Result<List<DistrictVO>> getDistrictList(Integer adcode, String name);

    Result<List<AddressVO>> updateAddress(Address address);

    Result<List<AddressVO>> getAddressList();

    Result<List<AddressVO>> delAddress(int id);

    Result<List<AddressVO>> setDefault(int id);

    Result<List<AddressVO>> addAddress(Address address);
}

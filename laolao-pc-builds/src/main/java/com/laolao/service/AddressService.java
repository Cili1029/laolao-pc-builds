package com.laolao.service;

import com.laolao.common.result.Result;
import com.laolao.pojo.entity.Address;
import com.laolao.pojo.vo.AddressVO;
import com.laolao.pojo.vo.DistrictVO;

import java.util.List;

public interface AddressService {
    Result<List<DistrictVO>> getDistrictList(Integer adcode, String name);

    Result<List<AddressVO>> updateAddress(Address address);

    Result<List<AddressVO>> getAddressList();

    Result<List<AddressVO>> delAddress(int id);

    Result<List<AddressVO>> setDefault(int id);

    Result<List<AddressVO>> addAddress(Address address);
}

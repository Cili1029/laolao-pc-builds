package com.laolao.service;

import com.laolao.common.result.Result;
import com.laolao.pojo.vo.DistrictVO;

import java.util.List;

public interface AddressService {
    Result<List<DistrictVO>> getDistrictList(Integer adcode);
}

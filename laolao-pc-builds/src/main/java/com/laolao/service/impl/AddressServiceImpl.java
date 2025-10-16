package com.laolao.service.impl;

import com.laolao.common.result.Result;
import com.laolao.common.utils.AMAPUtil;
import com.laolao.mapper.AddressMapper;
import com.laolao.pojo.vo.DistrictVO;
import com.laolao.service.AddressService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    @Resource
    private AddressMapper addressMapper;
    @Resource
    private AMAPUtil amapUtil;

    public Result<List<DistrictVO>> getDistrictList(Integer adcode) {
        List<DistrictVO> district = amapUtil.getDistrict(adcode);
        System.out.println(district);
        return Result.success(district);
    }
}

package com.laolao.service.impl;

import com.laolao.common.context.BaseContext;
import com.laolao.common.result.Result;
import com.laolao.common.utils.AMAPUtil;
import com.laolao.converter.MapStruct;
import com.laolao.mapper.AddressMapper;
import com.laolao.pojo.entity.Address;
import com.laolao.pojo.vo.AddressVO;
import com.laolao.pojo.vo.DistrictVO;
import com.laolao.service.AddressService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    @Resource
    private MapStruct mapStruct;
    @Resource
    private AddressMapper addressMapper;
    @Resource
    private AMAPUtil amapUtil;

    public Result<List<DistrictVO>> getDistrictList(Integer adcode, String name) {
        List<DistrictVO> district;
        if (name == null) {
            district = amapUtil.getDistrictByAdcade(adcode);
        } else {
            district = amapUtil.getDistrictByName(name);
        }
        return Result.success(district);
    }

    public Result<List<AddressVO>> getAddressList() {
        int userId = BaseContext.getCurrentId();
        List<Address> addressList = addressMapper.getList(userId);
        ArrayList<AddressVO> addressVOList = new ArrayList<>();
        for (Address address : addressList) {
            AddressVO addressVO = mapStruct.addressToAddressVO(address);
            addressVOList.add(addressVO);
        }
        return Result.success(addressVOList);
    }

    @Override
    public Result<List<AddressVO>> updateAddress(Address address) {
        address.setUserId(BaseContext.getCurrentId());
        if (address.getId() == 0) {
            // 为新增
            address.setUserId(BaseContext.getCurrentId());
            addressMapper.insert(address);

            Result<List<AddressVO>> addressList = getAddressList();
            addressList.setMsg("新增成功！");
            return addressList;
//            return Result.success("新增成功！");
        }

        // 有，更新
        addressMapper.update(address);
        Result<List<AddressVO>> addressList = getAddressList();
        addressList.setMsg("修改成功！");
        return addressList;
//        return Result.success("修改成功！");
    }

}

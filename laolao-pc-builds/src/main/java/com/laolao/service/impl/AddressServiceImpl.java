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
import org.springframework.transaction.annotation.Transactional;

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
    public Result<List<AddressVO>> addAddress(Address address) {
        address.setUserId(BaseContext.getCurrentId());
        addressMapper.insert(address);

        Result<List<AddressVO>> addressList = getAddressList();
        addressList.setMsg("新增成功！");
        return addressList;
    }

    @Override
    public Result<List<AddressVO>> updateAddress(Address address) {
        address.setUserId(BaseContext.getCurrentId());
        addressMapper.update(address);
        Result<List<AddressVO>> addressList = getAddressList();
        addressList.setMsg("修改成功！");
        return addressList;
    }

    @Override
    public Result<List<AddressVO>> delAddress(int id) {
        int userId = BaseContext.getCurrentId();
        addressMapper.delete(id, userId);
        Result<List<AddressVO>> addressList = getAddressList();
        addressList.setMsg("删除成功！");
        return addressList;
    }

    @Transactional
    @Override
    public Result<List<AddressVO>> setDefault(int id) {
        int userId = BaseContext.getCurrentId();
        // 取消旧默认
        addressMapper.setDefault(0, null, userId);
        // 设置新默认
        addressMapper.setDefault(1, id, userId);

        Result<List<AddressVO>> addressList = getAddressList();
        addressList.setMsg("设置成功！");
        return addressList;
    }

}

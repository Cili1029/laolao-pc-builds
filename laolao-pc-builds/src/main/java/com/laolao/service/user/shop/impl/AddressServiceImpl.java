package com.laolao.service.user.shop.impl;

import com.laolao.common.context.UserContext;
import com.laolao.common.result.Result;
import com.laolao.common.utils.AMAPUtil;
import com.laolao.converter.MapStruct;
import com.laolao.mapper.user.shop.AddressMapper;
import com.laolao.mapper.user.shop.OrderMapper;
import com.laolao.pojo.shop.dto.AddressDTO;
import com.laolao.pojo.shop.entity.Address;
import com.laolao.pojo.shop.entity.Order;
import com.laolao.pojo.shop.vo.AddressVO;
import com.laolao.pojo.shop.vo.DistrictVO;
import com.laolao.service.user.shop.AddressService;
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
    @Resource
    private OrderMapper orderMapper;

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
        int userId = UserContext.getCurrentId();
        List<Address> addressList = addressMapper.getList(userId);
        ArrayList<AddressVO> addressVOList = new ArrayList<>();
        for (Address address : addressList) {
            AddressVO addressVO = mapStruct.addressToAddressVO(address);
            addressVOList.add(addressVO);
        }
        return Result.success(addressVOList);
    }

    @Override
    public Result<List<AddressVO>> addAddress(AddressDTO addressDTO) {
        addressDTO.setUserId(UserContext.getCurrentId());
        // 添加并设置为当前订单选择的地址
        addressMapper.insert(addressDTO);
        // 更新地址
        String address = addressDTO.getProvince() + addressDTO.getCity() + addressDTO.getDistrict() + addressDTO.getDetailAddress();
        Order order = mapStruct.addressDTOToOrder(addressDTO);
        order.setAddress(address);
        order.setAddressId(addressDTO.getId());
        orderMapper.update(order);

        Result<List<AddressVO>> addressList = getAddressList();
        addressList.setMsg("新增成功！");
        return addressList;
    }

    @Override
    public Result<List<AddressVO>> updateAddress(AddressDTO addressDTO) {
        addressDTO.setUserId(UserContext.getCurrentId());
        addressMapper.update(addressDTO);
        Result<List<AddressVO>> addressList = getAddressList();
        addressList.setMsg("修改成功！");
        return addressList;
    }

    @Override
    public Result<List<AddressVO>> delAddress(int id) {
        int userId = UserContext.getCurrentId();
        addressMapper.delete(id, userId);
        Result<List<AddressVO>> addressList = getAddressList();
        addressList.setMsg("删除成功！");
        return addressList;
    }

    @Transactional
    @Override
    public Result<List<AddressVO>> setDefault(int id) {
        int userId = UserContext.getCurrentId();
        // 取消旧默认
        addressMapper.setDefault(0, null, userId);
        // 设置新默认
        addressMapper.setDefault(1, id, userId);

        Result<List<AddressVO>> addressList = getAddressList();
        addressList.setMsg("设置成功！");
        return addressList;
    }

}

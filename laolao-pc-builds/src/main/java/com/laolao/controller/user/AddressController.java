package com.laolao.controller.user;

import com.laolao.common.result.Result;
import com.laolao.pojo.entity.Address;
import com.laolao.pojo.vo.AddressVO;
import com.laolao.pojo.vo.DistrictVO;
import com.laolao.service.AddressService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 收货地址相关
 */
@RestController
@RequestMapping("/user/address")
public class AddressController {
    @Resource
    private AddressService addressService;

    /**
     * 获取行政区划分
     * 高德地图的api
     *
     * @param adcode 城市编码
     * @return 行政区信息
     */
    @GetMapping("/district")
    public Result<List<DistrictVO>> getDistrictList(Integer adcode, String name) {
        return addressService.getDistrictList(adcode, name);
    }

    /**
     * 获取用户的收货地址
     *
     * @return 地址信息
     */
    @GetMapping("/get")
    public Result<List<AddressVO>> getAddressList() {
        return addressService.getAddressList();
    }

    /**
     * 新增/更新收货地址
     * id为1则为更新，为0则为新增
     *
     * @param address 基础信息
     * @return 返回最新地址信息
     */
    @PostMapping("/update")
    public Result<List<AddressVO>> updateAddress(@RequestBody Address address) {
        return addressService.updateAddress(address);
    }
}

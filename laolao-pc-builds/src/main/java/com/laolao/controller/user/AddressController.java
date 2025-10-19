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
     * 行政区域查询
     * 使用高德地图的api
     *
     * @param adcode 城市编码
     * @return 行政区域数据
     */
    @GetMapping("/district")
    public Result<List<DistrictVO>> getDistrictList(Integer adcode, String name) {
        return addressService.getDistrictList(adcode, name);
    }

    /**
     * 获取收货地址
     *
     * @return 最新收货地址数据
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
     * @return 最新地址数据
     */
    @PostMapping("/update")
    public Result<List<AddressVO>> updateAddress(@RequestBody Address address) {
        return addressService.updateAddress(address);
    }

    /**根据Id删除收货地址
     *
     * @param id 收货地址Id
     * @return 最新地址数据
     */
    @DeleteMapping("/del/{id}")
    public Result<List<AddressVO>> delAddress(@PathVariable int id) {
        return addressService.delAddress(id);
    }

    /**根据Id设置默认收货地址
     *
     * @param id 收货地址Id
     * @return 最新地址数据
     */
    @PatchMapping("/default/{id}")
    public Result<List<AddressVO>> setDefault(@PathVariable int id) {
        return addressService.setDefault(id);
    }
}

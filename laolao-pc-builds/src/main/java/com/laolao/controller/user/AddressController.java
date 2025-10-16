package com.laolao.controller.user;

import com.laolao.common.result.Result;
import com.laolao.pojo.vo.DistrictVO;
import com.laolao.service.AddressService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/address")
public class AddressController {
    @Resource
    private AddressService addressService;

    @GetMapping("/district")
    public Result<List<DistrictVO>> getDistrictList(Integer adcode) {
        return addressService.getDistrictList(adcode);
    }
}

package com.laolao.service.admin.shop.impl;

import com.laolao.common.result.Result;
import com.laolao.converter.MapStruct;
import com.laolao.mapper.admin.shop.AdminComponentMapper;
import com.laolao.pojo.shop.entity.Component;
import com.laolao.pojo.shop.entity.Variant;
import com.laolao.pojo.shop.vo.AdminComponentVO;
import com.laolao.service.admin.shop.AdminComponentService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminComponentServiceImpl implements AdminComponentService {
    @Resource
    private MapStruct mapStruct;
    @Resource
    private AdminComponentMapper adminComponentMapper;


    @Override
    public Result<List<AdminComponentVO>> getComponent() {
        List<Component> componentList = adminComponentMapper.selectComponent();
        List<AdminComponentVO> adminComponentVOList = new ArrayList<>();
        for (Component component : componentList) {
            AdminComponentVO adminComponentVO = mapStruct.componentToAdminComponentVO(component);
            adminComponentVOList.add(adminComponentVO);
        }
        return Result.success(adminComponentVOList);
    }

    @Override
    public Result<List<Variant>> getVariant(int id) {
        List<Variant> variantList = adminComponentMapper.selectVariant(id);
        return Result.success(variantList);
    }
}

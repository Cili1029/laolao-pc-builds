package com.laolao.service.admin.shop.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.laolao.common.context.BaseContext;
import com.laolao.common.result.Result;
import com.laolao.converter.MapStruct;
import com.laolao.mapper.admin.shop.AdminComponentMapper;
import com.laolao.mapper.admin.shop.AdminVariantMapper;
import com.laolao.pojo.shop.entity.Component;
import com.laolao.pojo.shop.dto.AdminAddComponentDTO;
import com.laolao.pojo.shop.vo.AdminComponentVO;
import com.laolao.pojo.shop.dto.AdminUpdateComponentDTO;
import com.laolao.service.admin.shop.AdminComponentService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminComponentServiceImpl implements AdminComponentService {
    @Resource
    private MapStruct mapStruct;
    @Resource
    private AdminComponentMapper adminComponentMapper;
    @Resource
    private AdminVariantMapper adminVariantMapper;


    @Override
    public Result<PageInfo<AdminComponentVO>> getComponent(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Component> componentList = adminComponentMapper.selectComponent();
        List<AdminComponentVO> adminComponentVOList = new ArrayList<>();
        for (Component component : componentList) {
            AdminComponentVO vo = mapStruct.componentToAdminComponentVO(component);
            adminComponentVOList.add(vo);
        }

        // 转换提取
        PageInfo<Component> componentPageInfo = new PageInfo<>(componentList);
        PageInfo<AdminComponentVO> resultPageInfo = new PageInfo<>();
        BeanUtils.copyProperties(componentPageInfo, resultPageInfo);

        // setVO
        resultPageInfo.setList(adminComponentVOList);
        return Result.success(resultPageInfo);
    }

    @Override
    @Transactional
    public Result<String> changeStatus(int id, int status) {
        if (status == 0) {
            // 先禁用版本
            adminVariantMapper.updateVariantStatusByComponentId(id, status);
        }
        adminComponentMapper.updateComponentStatus(id, status);
        return Result.success(status == 1 ? "已启用！" : "已禁用！");
    }

    @Override
    @Transactional
    public Result<String> delete(int id) {
        // 先删除子版本
        adminVariantMapper.deleteByComponentId(id);
        adminComponentMapper.delete(id);
        return Result.success("删除成功！");
    }

    @Override
    public Result<String> add(AdminAddComponentDTO adminAddComponentDTO) {
        adminAddComponentDTO.setCreatedBy(BaseContext.getCurrentId());
        adminComponentMapper.insert(adminAddComponentDTO);
        return Result.success("新增成功");
    }

    @Override
    public Result<String> update(AdminUpdateComponentDTO adminUpdateComponentDTO) {
        adminComponentMapper.update(adminUpdateComponentDTO);
        return Result.success("修改成功！");
    }
}

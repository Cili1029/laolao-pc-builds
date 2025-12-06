package com.laolao.service.admin.shop.impl;

import com.laolao.common.context.BaseContext;
import com.laolao.common.result.Result;
import com.laolao.mapper.admin.shop.AdminComponentMapper;
import com.laolao.mapper.admin.shop.AdminVariantMapper;
import com.laolao.pojo.common.StockOrQuantityDTO;
import com.laolao.pojo.shop.entity.Variant;
import com.laolao.pojo.shop.dto.AdminAddVariantDTO;
import com.laolao.pojo.shop.dto.AdminUpdateVariantDTO;
import com.laolao.service.admin.shop.AdminVariantService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminVariantServiceImpl implements AdminVariantService {
    @Resource
    private AdminVariantMapper adminVariantMapper;
    @Resource
    private AdminComponentMapper adminComponentMapper;

    @Override
    public Result<List<Variant>> getVariant(int id) {
        List<Variant> variantList = adminVariantMapper.selectVariant(id);
        return Result.success(variantList);
    }

    @Override
    public Result<String> changeStatus(int id, int status) {
        // 先判断组件是否被禁用
        // 查询父组件状态
        int currentStatus = adminComponentMapper.getStatusByVariantId(id);
        if (currentStatus == 0 && status == 1) {
            // 禁用中 不可启用
            return Result.error("父组件禁用状态下不可启用版本！");
        }
        // 启用
        adminVariantMapper.updateVariantStatusByVariantId(id, status);
        return Result.success(status == 1 ? "已启用！" : "已禁用！");
    }

    @Override
    public Result<String> delete(int id) {
        adminVariantMapper.delete(id);
        return Result.success("删除成功！");
    }

    @Override
    public Result<Variant> add(AdminAddVariantDTO adminAddVariantDTO) {
        Variant variant = Variant.builder()
                .componentId(adminAddVariantDTO.getComponentId())
                .variantName(adminAddVariantDTO.getVariantName())
                .price(adminAddVariantDTO.getPrice())
                .description(adminAddVariantDTO.getDescription())
                .stock(adminAddVariantDTO.getStock())
                .status(0)
                .createdAt(LocalDateTime.now())
                .createdBy(BaseContext.getCurrentId()).build();
        adminVariantMapper.insert(variant);
        return Result.success(variant, "添加成功！");
    }

    @Override
    public Result<String> updateStock(StockOrQuantityDTO stockOrQuantityDTO) {
        adminVariantMapper.updateStock(stockOrQuantityDTO);
        return Result.success("货量修改成功！");
    }

    @Override
    public Result<String> update(AdminUpdateVariantDTO adminUpdateVariantDTO) {
        adminVariantMapper.update(adminUpdateVariantDTO);
        return Result.success("修改成功！");
    }
}

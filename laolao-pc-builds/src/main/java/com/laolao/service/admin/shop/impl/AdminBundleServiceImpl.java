package com.laolao.service.admin.shop.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.laolao.common.constant.RedisConstant;
import com.laolao.common.result.Result;
import com.laolao.converter.MapStruct;
import com.laolao.mapper.admin.shop.AdminBundleMapper;
import com.laolao.mapper.common.SysFileMapper;
import com.laolao.pojo.common.StockOrQuantityDTO;
import com.laolao.pojo.shop.dto.AdminAddBundleDTO;
import com.laolao.pojo.shop.dto.AdminBundleAddVariantDTO;
import com.laolao.pojo.shop.dto.AdminUpdateBundleDTO;
import com.laolao.pojo.shop.entity.Bundle;
import com.laolao.pojo.shop.vo.*;
import com.laolao.service.admin.shop.AdminBundleService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBloomFilter;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminBundleServiceImpl implements AdminBundleService {
    @Resource
    private MapStruct mapStruct;
    @Resource
    private AdminBundleMapper adminBundleMapper;
    @Resource
    private SysFileMapper sysFileMapper;
    @Resource
    private RBloomFilter<Integer> bundleBloomFilter;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private static final String BUNDLE_SIMPLE = RedisConstant.Shop.CACHE_NAME + "::" + RedisConstant.Shop.BUNDLE_SIMPLE;
    private static final String BUNDLE_DETAIL = RedisConstant.Shop.CACHE_NAME + "::" + RedisConstant.Shop.BUNDLE_DETAIL;

    @Override
    public Result<PageInfo<AdminBundleVO>> getBundle(Integer pageNum, Integer pageSize, String searchContent) {
        PageHelper.startPage(pageNum, pageSize);
        List<Bundle> bundleList = StringUtils.isNotBlank(searchContent)
                ? adminBundleMapper.search(searchContent)
                : adminBundleMapper.selectBundle();

        List<AdminBundleVO> adminBundleVOList = new ArrayList<>();
        for (Bundle bundle : bundleList) {
            AdminBundleVO vo = mapStruct.bundleToAdminBundleVO(bundle);
            adminBundleVOList.add(vo);
        }

        // 转换提取
        PageInfo<Bundle> componentPageInfo = new PageInfo<>(bundleList);
        PageInfo<AdminBundleVO> resultPageInfo = new PageInfo<>();
        BeanUtils.copyProperties(componentPageInfo, resultPageInfo);

        // setVO
        resultPageInfo.setList(adminBundleVOList);
        return Result.success(resultPageInfo);
    }

    @Override
    @Transactional
    public Result<String> changeStatus(int id, int status) {
        Bundle bundle = new Bundle();
        bundle.setId(id);
        bundle.setStatus(status);
        adminBundleMapper.updateStatus(bundle);
        // 删除重建Redis的分页缓存和详细页缓存
        int categoryId = adminBundleMapper.selectCategoryId(id);
        stringRedisTemplate.delete(BUNDLE_SIMPLE + categoryId);
        stringRedisTemplate.delete(BUNDLE_DETAIL + id);
        return Result.success(status == 1 ? "已启用！" : "已禁用！");
    }

    @Override
    public Result<String> delete(int id) {
        // 删除整机
        adminBundleMapper.delete(id);
        // 删除整机配置项
        adminBundleMapper.deleteVariantByBundleId(id);

        // 查询所在category
        int categoryId =  adminBundleMapper.selectCategoryId(id);
        // 删除Redis的分页缓存和详细页缓存
        stringRedisTemplate.delete(BUNDLE_SIMPLE + categoryId);
        stringRedisTemplate.delete(BUNDLE_DETAIL + id);
        return Result.success("删除成功！");
    }

    @Override
    public Result<String> add(AdminAddBundleDTO adminAddBundleDTO) {
        Bundle bundle = mapStruct.AdminAddBundleDTOToBundle(adminAddBundleDTO);
        adminBundleMapper.insert(bundle);
        // 写入布隆过滤器
        bundleBloomFilter.add(bundle.getId());
        // 修改文件状态
        if (!CollectionUtils.isEmpty(adminAddBundleDTO.getImages())) {
            sysFileMapper.update(adminAddBundleDTO.getImages());
        }

        // 删除重建Redis的分页缓存
        stringRedisTemplate.delete(BUNDLE_SIMPLE + bundle.getCategoryId());
        return Result.success("新增成功");
    }

    @Override
    public Result<String> update(AdminUpdateBundleDTO adminUpdateBundleDTO) {
        Bundle bundle = mapStruct.AdminUpdateBundleDTOToBundle(adminUpdateBundleDTO);
        adminBundleMapper.update(bundle);
        // 修改文件状态
        if (!CollectionUtils.isEmpty(adminUpdateBundleDTO.getImages())) {
            sysFileMapper.update(adminUpdateBundleDTO.getImages());
        }

        // 删除重建Redis的分页缓存和详细页缓存
        stringRedisTemplate.delete(BUNDLE_SIMPLE + bundle.getCategoryId());
        stringRedisTemplate.delete(BUNDLE_DETAIL + bundle.getId());

        // 更新详细页数据
        return Result.success("修改成功！");
    }

    @Override
    public Result<String> updateStock(StockOrQuantityDTO stockOrQuantityDTO) {
        adminBundleMapper.updateStock(stockOrQuantityDTO);
        // 删除重建Redis的详细页缓存
        stringRedisTemplate.delete(BUNDLE_DETAIL + stockOrQuantityDTO.getId());

        return Result.success("货量修改成功！");
    }

    @Override
    public Result<List<AdminBundleVariantVO>> getVariant(int id) {
        List<AdminBundleVariantVO> adminBundleVariantVOS = adminBundleMapper.getVariant(id);
        return Result.success(adminBundleVariantVOS);
    }

    @Override
    public Result<String> updateQuantity(StockOrQuantityDTO stockOrQuantityDTO) {
        adminBundleMapper.updateQuantity(stockOrQuantityDTO);

        // 删除详细页缓存
        stringRedisTemplate.delete(BUNDLE_DETAIL + stockOrQuantityDTO.getId());
        return Result.success("修改成功！");
    }

    @Override
    public Result<String> deleteVariant(int id) {
        adminBundleMapper.deleteVariant(id);

        // 查询相关整机Id
        Integer bundleId = adminBundleMapper.selectBundleIdByConfigId(id);
        // 更新详细页缓存
        stringRedisTemplate.delete(BUNDLE_DETAIL + bundleId);
        return Result.success("删除成功！");
    }

    @Override
    public Result<List<AdminBundleVariantVO>> searchVariant(String searchContent) {
        List<AdminBundleVariantVO> adminBundleVariantVOS = adminBundleMapper.searchVariant(searchContent);
        return Result.success(adminBundleVariantVOS);
    }

    @Override
    public Result<String> addVariant(AdminBundleAddVariantDTO adminBundleAddVariantDTO) {
        adminBundleMapper.insertVariant(adminBundleAddVariantDTO);
        // 更新详细页缓存
        stringRedisTemplate.delete(BUNDLE_DETAIL + adminBundleAddVariantDTO.getBundleId());
        return Result.success("新增成功！");
    }
}

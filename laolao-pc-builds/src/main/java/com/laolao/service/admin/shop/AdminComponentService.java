package com.laolao.service.admin.shop;

import com.github.pagehelper.PageInfo;
import com.laolao.common.result.Result;
import com.laolao.pojo.shop.vo.AdminAddComponentVO;
import com.laolao.pojo.shop.vo.AdminComponentVO;
import com.laolao.pojo.shop.vo.AdminUpdateComponentVO;


public interface AdminComponentService {
    Result<PageInfo<AdminComponentVO>> getComponent(Integer pageNum, Integer pageSize);

    Result<String> changeStatus(int id, int status);

    Result<String> delete(int id);

    Result<String> add(AdminAddComponentVO adminAddComponentVO);

    Result<String> update(AdminUpdateComponentVO adminUpdateComponentVO);
}

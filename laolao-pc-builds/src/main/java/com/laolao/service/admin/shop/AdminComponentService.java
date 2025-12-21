package com.laolao.service.admin.shop;

import com.github.pagehelper.PageInfo;
import com.laolao.common.result.Result;
import com.laolao.pojo.shop.dto.AdminAddComponentDTO;
import com.laolao.pojo.shop.vo.AdminComponentVO;
import com.laolao.pojo.shop.dto.AdminUpdateComponentDTO;


public interface AdminComponentService {
    Result<PageInfo<AdminComponentVO>> getComponent(Integer pageNum, Integer pageSize, String searchContent);

    Result<String> changeStatus(int id, int status);

    Result<String> delete(int id);

    Result<String> add(AdminAddComponentDTO adminAddComponentDTO);

    Result<String> update(AdminUpdateComponentDTO adminUpdateComponentDTO);
}

package com.laolao.service.admin.shop;

import com.github.pagehelper.PageInfo;
import com.laolao.common.result.Result;
import com.laolao.pojo.shop.vo.AdminComponentVO;


public interface AdminComponentService {
    Result<PageInfo<AdminComponentVO>> getComponent(Integer pageNum, Integer pageSize);

    Result<String> changeStatus(int id, int status);
}

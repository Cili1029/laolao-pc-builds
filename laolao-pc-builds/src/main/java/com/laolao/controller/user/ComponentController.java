package com.laolao.controller.user;

import com.laolao.pojo.vo.ComponentVariantVO;
import com.laolao.pojo.vo.ComponentVO;
import com.laolao.common.result.Result;
import com.laolao.service.ComponentService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/components")
public class ComponentController {

    @Resource
    private ComponentService componentService;

    @GetMapping("/components")
    public Result<List<ComponentVO>> Components(int categoryId) {

        return componentService.listWithCategoryId(categoryId);
    }

    @GetMapping("/variants")
    public Result<List<ComponentVariantVO>> Variants(int componentId) {

        return componentService.listWithComponentId(componentId);
    }

    @GetMapping("/search")
    public Result<List<ComponentVO>> SearchByName(int categoryId, String searchContent) {
        return componentService.searchByName(categoryId,searchContent);
    }
}

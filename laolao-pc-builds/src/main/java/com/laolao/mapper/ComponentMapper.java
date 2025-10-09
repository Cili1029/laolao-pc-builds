package com.laolao.mapper;

import com.laolao.pojo.vo.ComponentVariantVO;
import com.laolao.pojo.vo.ComponentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ComponentMapper {
    List<ComponentVO> getByConditions(int categoryId, String searchContent);

    @Select("SELECT id,component_id, variant_name, price, description FROM component_variants WHERE component_id = #{componentId};")
    List<ComponentVariantVO> getByComponentsId(int componentId);
}

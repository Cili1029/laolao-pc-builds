package com.laolao.mapper;

import com.laolao.pojo.entity.Variant;
import com.laolao.pojo.vo.ComponentVariantVO;
import com.laolao.pojo.vo.ProductVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ComponentMapper {
    List<ProductVO> getByConditions(int categoryId, String searchContent);

    @Select("SELECT id,component_id, variant_name, price, description FROM component_variants WHERE component_id = #{componentId};")
    List<ComponentVariantVO> getByComponentsId(int componentId);

    @Select("select type from laolao_pc_builds.categories where id = #{categoryId}")
    int getType(int categoryId);

    @Select("select * from component_variants where id = #{id} and status = 1 and stock > 0")
    Variant check(int id);
}

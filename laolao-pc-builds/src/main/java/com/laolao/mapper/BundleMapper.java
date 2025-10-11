package com.laolao.mapper;

import com.laolao.pojo.entity.Bundle;
import com.laolao.pojo.vo.ComponentVariantVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BundleMapper {

    List<Bundle> getByConditions(int categoryId, String searchContent);

    @Select("select cv.id, c.name, cv.variant_name, c.image ,cv.price\n" +
            "from component_variants cv\n" +
            "         join components c on cv.component_id = c.id\n" +
            "where cv.id in (select variant_id from bundle_configurations b where bundle_id = #{id})")
    List<ComponentVariantVO> getByComponentsId(int id);
}

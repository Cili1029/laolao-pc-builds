package com.laolao.mapper.admin.shop;

import com.laolao.pojo.common.StockOrQuantityDTO;
import com.laolao.pojo.shop.entity.Variant;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminVariantMapper {
    @Select("select * from shop_component_variant where component_id = #{id}")
    List<Variant> selectVariant(int id);

    @Update("update shop_component_variant set status = #{status}, updated_by = #{updatedBy}, updated_at = #{updatedAt} where component_id = #{id}")
    void updateVariantStatusByComponentId(Variant variant);

    @Update("update shop_component_variant set status = #{status} where id = #{id}")
    void updateVariantStatusByVariantId(Variant variant);

    @Delete("delete from shop_component_variant where component_id = #{id}")
    void deleteByComponentId(int id);

    @Delete("delete from shop_component_variant where id = #{id}")
    void delete(int id);

    @Insert("""
                insert into shop_component_variant (
                    component_id,
                    variant_name,
                    price,
                    description,
                    stock,
                    status,
                    created_by,
                    created_at,
                    updated_by,
                    updated_at
                ) value (
                    #{componentId},
                    #{variantName},
                    #{price},
                    #{description},
                    #{stock},
                    #{status},
                    #{createdBy},
                    #{createdAt},
                    #{updatedBy},
                    #{updatedAt}
                )
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Variant variant);

    @Update("update shop_component_variant set stock = #{stock}, updated_by = #{updatedBy}, updated_at = #{updatedAt} where id = #{id}")
    void updateStock(StockOrQuantityDTO stockOrQuantityDTO);

    @Update("update shop_component_variant set variant_name = #{variantName}, price = #{price}, description = #{description}, updated_by = #{updatedBy}, updated_at = #{updatedAt} where id = #{id}")
    void update(Variant variant);
}

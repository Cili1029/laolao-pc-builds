package com.laolao.mapper.admin.shop;

import com.laolao.pojo.common.StockDTO;
import com.laolao.pojo.shop.entity.Variant;
import com.laolao.pojo.shop.vo.AdminAddVariantVO;
import com.laolao.pojo.shop.vo.AdminUpdateVariantVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminVariantMapper {
    @Select("select * from shop_component_variant where component_id = #{id}")
    List<Variant> selectVariant(int id);

    @Update("update shop_component_variant set status = #{status} where component_id = #{id}")
    void updateVariantStatusByComponentId(int id, int status);

    @Update("update shop_component_variant set status = #{status} where id = #{id}")
    void updateVariantStatusByVariantId(int id, int status);

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
                    created_at
                ) value (
                    #{componentId},
                    #{variantName},
                    #{price},
                    #{description},
                    #{stock},
                    #{status},
                    #{createdBy},
                    #{createdAt}
                )
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Variant variant);

    @Update("update shop_component_variant set stock = #{stock} where id = #{id};")
    void updateStock(StockDTO stockDTO);

    @Update("update shop_component_variant set variant_name = #{variantName}, price = #{price}, description = #{description} where id = #{id};")
    void update(AdminUpdateVariantVO adminUpdateVariantVO);
}

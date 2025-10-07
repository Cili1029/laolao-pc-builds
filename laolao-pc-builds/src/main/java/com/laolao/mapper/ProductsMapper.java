package com.laolao.mapper;

import com.laolao.pojo.vo.ProductsEditionVO;
import com.laolao.pojo.vo.ProductsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProductsMapper {
    List<ProductsVO> getByConditions(int categoryId, String searchContent);

    @Select("SELECT product_id, edition_name, price, description FROM products_edition WHERE products_edition.product_id = #{productsId};")
    List<ProductsEditionVO> getByProductsId(int productsId);
}

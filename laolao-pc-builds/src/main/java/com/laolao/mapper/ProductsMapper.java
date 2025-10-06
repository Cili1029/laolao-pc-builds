package com.laolao.mapper;

import com.laolao.pojo.entity.Products;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductsMapper {
    List<Products> getByCategoryId(int categoryId);
}

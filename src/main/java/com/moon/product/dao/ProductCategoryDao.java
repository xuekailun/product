package com.moon.product.dao;

import com.moon.product.entity.ProductCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductCategoryDao {

    /* 查询商品类目和商品明细 */
    List<ProductCategory> selectProductCateAndInfo();

    List<ProductCategory> selectProcutType();
}

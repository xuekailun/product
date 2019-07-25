package com.moon.product.dao;

import com.moon.product.entity.ProductInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductInfoDao {

    /* 查询商品上架*/
    List<ProductInfo> selectProductStatus(@Param("status") Integer status,@Param("type") Integer type);

    /* 通过商品id去查询商品 --> List*/
    List<ProductInfo> selectProductListById(List<String> productId);

    Integer updateProductById(ProductInfo productInfo);
}

package com.moon.product.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 商品类目
 */
@Data
public class ProductCategory {

    private Integer categoryId;

    /* 类目名字 */
    private String categoryName;

    /* 类目编号 */
    private Integer categoryType;

    private Date createTime;

    private Date updateTime;

    private List<ProductInfo> productInfos;
}

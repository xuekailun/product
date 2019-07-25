package com.moon.product.dao.dto;

import lombok.Data;

@Data
public class CarDTO {

    /* 商品id */
    private String productId;

    /* 购买数量 */
    private Integer productQuantity;
}

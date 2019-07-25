package com.moon.product.enums;

import lombok.Getter;

/* 返回结果的枚举*/
@Getter
public enum ResultEnum {

    PASS_LIST_NULL(000001,"传值集合异常为空或NULL"),
    INQUIRE_LIST_NULL(000002,"查询集合异常为空或NULL"),
    INQUIRE_Model_NULL(000003,"查询对象异常为空或NULL"),
    STOCK_NULL(000004,"库存不足"),
    ;
    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

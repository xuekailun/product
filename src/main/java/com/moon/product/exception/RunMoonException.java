package com.moon.product.exception;


import com.moon.product.enums.ResultEnum;

public class RunMoonException extends Exception {

    private Integer code;

    public RunMoonException(ResultEnum resultEnum){
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
}

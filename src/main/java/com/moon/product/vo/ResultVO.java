package com.moon.product.vo;

import lombok.Data;

/***
 * 返回的封装类
 */
@Data
public class ResultVO<T> {

    private Integer code;

    private String msg;

    private T data;

    private  ResultVO(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T>  ResultVO<T> ResponseServer(Integer code, String msg, T data){
        return new ResultVO<T>(code,msg,data);
    }

}

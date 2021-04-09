package com.imooc.exception;

import com.imooc.enums.ResultEnum;
import lombok.Getter;

@Getter//get方法
public class SellException extends RuntimeException{
    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());//这个ResultEnum类本身就有一个message
        this.code=resultEnum.getCode();
    }

    public SellException(Integer code,String message) {
        super(message);
        this.code = code;
    }
}

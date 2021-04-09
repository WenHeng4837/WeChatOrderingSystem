package com.imooc.utils;

import com.imooc.enums.CodeEnum;

//枚举工具类
public class EnumUtil {
    public static <T extends CodeEnum> T getByCode(Integer code,Class<T> enumClass){
        for(T each:enumClass.getEnumConstants()){
            if(code.equals(each.getCode())){
                return each;
            }
        }
        //都不相等返回null
        return null;
    }
}

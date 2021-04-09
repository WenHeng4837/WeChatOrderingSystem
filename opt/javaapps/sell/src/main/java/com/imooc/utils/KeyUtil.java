package com.imooc.utils;

import java.util.Random;

/*
* 用于订单详情入库使用*/
public class KeyUtil {
    /*
    *生成唯一的主键
    * 格式：时间+随机数
    * 这里要加上关键字，因为时间精确到毫秒数还是有可能重复，加上关键字防止多线程*/
    public static synchronized String genUniqueKey(){
        Random random=new Random();
        Integer number=random.nextInt(900000)+100000;//随机生成的数是六位数
        return System.currentTimeMillis()+String.valueOf(number);
    }
}

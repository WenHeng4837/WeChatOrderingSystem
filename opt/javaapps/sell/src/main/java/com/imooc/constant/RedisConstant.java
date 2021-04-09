package com.imooc.constant;
//redis常量
public interface RedisConstant {
    //设置个前缀
    String TOKEN_PREFIX="token_%s";//到时候token存储的key是以token下划线为开头的
    Integer EXPIRE =7200;//过期时间两小时
}

package com.imooc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

//这里要配置一下mapper扫描路径
@MapperScan(basePackages = "com.imooc.dataobject.mapper")
@SpringBootApplication
@EnableCaching//使用缓存的注解
public class SellApplication {

    public static void main(String[] args) {
        SpringApplication.run(SellApplication.class, args);
    }

}

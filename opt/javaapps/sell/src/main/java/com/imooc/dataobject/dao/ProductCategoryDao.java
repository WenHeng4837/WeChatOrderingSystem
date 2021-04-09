package com.imooc.dataobject.dao;

import com.imooc.dataobject.mapper.ProductCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
//可以理解为是ProductCategoryMapper接口的实现类 ，然后可以直接在在需要用的service直接注入，也可以直接在需要用的service注入mapper
public class ProductCategoryDao {
    @Autowired
    ProductCategoryMapper mapper;
    public  int insertByMap(Map<String,Object> map){
        return mapper.insertByMap(map);
    }
}

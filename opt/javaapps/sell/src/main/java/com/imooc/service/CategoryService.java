package com.imooc.service;

import com.imooc.dataobject.ProductCategory;

import java.util.List;

/*
类目
 */
public interface CategoryService {
    //查找一个
    ProductCategory findOne(Integer categoryId);
    //查找多个，后台用的
    List<ProductCategory> findAll();
    //查找多个买家用的
    List<ProductCategory> findByCategoryTypeIn(List<Integer> CategoryTypeList);
    //保存更新
    ProductCategory save(ProductCategory ProductCategory);
    //删除暂时不用写
}

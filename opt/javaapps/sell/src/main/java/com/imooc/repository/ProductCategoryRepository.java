package com.imooc.repository;

import com.imooc.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//ProductCategory的dao对应接口
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer> {
    //按类目编号查询
    List<ProductCategory> findByCategoryTypeIn(List<Integer> CategoryTypeList);
}

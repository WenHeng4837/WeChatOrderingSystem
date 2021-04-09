package com.imooc.service.impl;

import com.imooc.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Arrays;
import java.util.List;
//类目业务层的测试
import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class CategoryServiceImplTest {
    //注入的是实现类，因为test的是实现类
    @Autowired
    private  CategoryServiceImpl categoryService;
    @Test
    void findOne() throws Exception{
        ProductCategory productCategory=categoryService.findOne(1);
        Assert.assertEquals(new Integer(1),productCategory.getCategoryId());
    }

    @Test
    void findAll() throws Exception{
        List<ProductCategory> productCategoryList=categoryService.findAll();
        Assert.assertNotEquals(0,productCategoryList.size());
    }

    @Test
    void findByCategoryTypeIn() throws Exception{
        List<ProductCategory> productCategoryList=categoryService.findByCategoryTypeIn(Arrays.asList(1,2,3,4));
        Assert.assertNotEquals(0,productCategoryList.size());
    }

    @Test
    void save() throws Exception{
        ProductCategory productCategory=new ProductCategory("男生专享",10);
        ProductCategory result =categoryService.save(productCategory);
        Assert.assertNotNull(result);
    }
}
package com.imooc.repository;

import com.imooc.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {
    @Autowired
    private ProductCategoryRepository repository;
    //按ID查找一个
    @Test
    public void findOneTest(){
        ProductCategory productCategory=repository.findById(1).orElse(null);
        System.out.println(productCategory.toString());
    }
    //保存、更新
    @Test
    @Transactional//测试完成功数据库不添加数据，这个注解在测视里完全回滚
    public void saveTest(){
       /* ProductCategory productCategory=repository.getOne(2);
        productCategory.setCategoryType(3);
        repository.save(productCategory);*/
        ProductCategory productCategory=new ProductCategory("男生最爱",4);
        ProductCategory result=repository.save(productCategory);
        Assert.assertNotNull(result);//断言判断
       // Assert.assertNotEquals(null,result);//前面是不期望的，后面的实际的
    }

    @Test
    public void findByCategoryTypeInTest(){
        List<Integer> list= Arrays.asList(2,3,4);
        List<ProductCategory> result= repository.findByCategoryTypeIn(list);
     /*   for(int i=0;i<result.size();i++){
      System.out.println(result.get(i).toString());
     }
           */
        Assert.assertNotEquals(0,result);
    }

}
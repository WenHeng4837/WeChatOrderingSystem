package com.imooc.dataobject.mapper;

import com.imooc.dataobject.ProductCategory;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

//优化开始使用mybatis
//对应商品ProductCategory
//@Mapper
//@Repository
public interface ProductCategoryMapper {
    //增加，使用注解方式，插入的时候注意要对应属性是什么类型的
    //通过map写入values的字段名值和map里的一一对应
    @Insert("insert into product_category(category_name,category_type) values(#{categoryName,jdbcType=VARCHAR},#{category_type,jdbcType=INTEGER})")
    int insertByMap(Map<String,Object> map);//这里values(#{category_name,jdbcType=VARCHAR},#{category_type,jdbcType=INTEGER})的category_name可以用categoryName，不过map添加时也得变成map.put("categoryName","师兄最不爱");，本来是category_name；也就是要一一对应

    //增加，通过对象写入，values里通过对象写入的字段就要和对象里的字段意义对应，不能像上面那样加下划线
    @Insert("insert into product_category(category_name,category_type) values(#{categoryName,jdbcType=VARCHAR},#{categoryType,jdbcType=INTEGER})")
    int insertByObject(ProductCategory productCategory);

    //查询,@Select要进行字段映射才行
    @Select("select * from product_category where category_type=#{categoryType}")
    @Results({
            @Result(column = "category_id",property ="categoryId" ),
            @Result(column = "category_name",property ="categoryName" ),
            @Result(column = "category_type",property ="categoryType" )
            //这里没有设置创建时间和修改时间的映射，所以是不会查出来的
    })
    ProductCategory findByCategoryType(Integer categoryType );

    //查询，通过名字查询
    //查询,@Select要进行字段映射才行
    //使用jpa按名字查询的话如果名字相同的只会返回第一个，但是mybatis会全部返回
    @Select("select * from product_category where category_name=#{categoryName}")
    @Results({
            @Result(column = "category_id",property ="categoryId" ),
            @Result(column = "category_name",property ="categoryName" ),
            @Result(column = "category_type",property ="categoryType" )
            //这里没有设置创建时间和修改时间的映射，所以是不会查出来的
    })
    List<ProductCategory> findByCategoryName(String categoryName );

    //更新，官方要求更新传参就要加@Param,然后括号里对应是传过来的参数的名字
    @Update("update product_category set category_name=#{categoryName} where category_type=#{categoryType}")
    int updateByCategoryType(@Param("categoryName") String categoryName,@Param("categoryType")Integer categoryType );

    //根据一个对象去更新
    @Update("update product_category set category_name=#{categoryName} where category_type=#{categoryType}")
    int updateByObject(ProductCategory productCategory);

    //删除
    @Delete("delete from product_category where category_type=#{categoryType}")
    int deleteByCategoryType(Integer categoryType);

    //使用xml文件的查查询
    ProductCategory selectByCategoryType(Integer categoryType);
}

package com.imooc.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Date;

/**
 * 类目
 * Created 吴丽慧
 * 2017-05-07 14:30
 */
@Entity
@DynamicUpdate//创建时间修改时间自动更新
@Proxy(lazy = false)//设置立即加载
@Data//自动getter,setter，tostring
public class ProductCategory {

    /** 类目id. */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)//主键id自增策略
    private Integer categoryId;

    /** 类目名字. */
    private String categoryName;

    /** 类目编号. */
    private Integer categoryType;

    private Date createTime;
    private Date updateTime;

    public ProductCategory() {

    }
    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }
}

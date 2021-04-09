package com.imooc.dataobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.imooc.enums.ProductStatusEnum;
import com.imooc.utils.EnumUtil;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/*
商品
* */
@Entity
@Data
@Proxy(lazy = false)//设置立即加载
@DynamicUpdate//创建时间修改时间自动更新
//测试缓存所以给它序列化了一下
public class ProductInfo implements Serializable {

    private static final long serialVersionUID = 6399186181668983148L;
    @Id
    private String productId;
    /*名字*/
    private String productName;
    /*单价*/
    private BigDecimal productPrice;
    /*库存*/
    private Integer productStock;
    /*描述*/
    private String productDescription;
    /*图片*/
    private String productIcon;
    /*状态,0正常1下架*/
    private Integer productStatus=ProductStatusEnum.UP.getCode();//给个默认值，默认在架
    /*类目编号*/
    private Integer categoryType;

    private Date createTime;
    private Date updateTime;
    //加个枚举用于后台卖家端的列表
    @JsonIgnore
    public ProductStatusEnum getProductStatusEnum() {
        return EnumUtil.getByCode(productStatus, ProductStatusEnum.class);
    }

}

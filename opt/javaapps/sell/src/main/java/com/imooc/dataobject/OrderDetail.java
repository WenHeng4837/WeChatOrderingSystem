package com.imooc.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/*订单详情*/
@Entity
@Data
@DynamicUpdate//创建时间修改时间自动更新
@Proxy(lazy = false)//设置立即加载
public class OrderDetail {
    @Id//订单id
    private String detailId;
    //订单id
    private String orderId;
    //商品id
    private String productId;
    //商品名字
    private String  productName;
    //单价
    private BigDecimal productPrice;
    //商品数量
    private Integer productQuantity;
    //商品小图
    private String productIcon;
    //创建时间
   // private Date createTime;
    //更新时间
    //private Date updateTime;
}

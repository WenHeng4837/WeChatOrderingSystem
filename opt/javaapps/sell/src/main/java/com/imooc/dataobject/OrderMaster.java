package com.imooc.dataobject;


import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/*订单主表*/
@Entity
@DynamicUpdate//创建时间修改时间自动更新
@Proxy(lazy = false)//设置立即加载
@Data//自动getter,setter，tostring
public class OrderMaster {
    @Id//订单id
    private  String orderId;
    //买家名字
    private String buyerName;
    //买家手机号
    private String buyerPhone;
    //买家地址
    private String buyerAddress;
    //买家微信Openid
    private String buyerOpenid;
    //订单总金额
    private BigDecimal orderAmount;
    //订单状态，默认为新下单
    private Integer orderStatus= OrderStatusEnum.NEW.getCode();
    //支付状态，默认为0未支付
    private Integer payStatus= PayStatusEnum.WAIT.getCode();
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
   // @Transient//这里需要OrderDetail里的内容但是直接在这里写肯定报错因为这个表对应数据库没有这个字段，所以加这个注解让它在对应数据库的时候忽略掉
    //private List<OrderDetail> orderDetailList;

}

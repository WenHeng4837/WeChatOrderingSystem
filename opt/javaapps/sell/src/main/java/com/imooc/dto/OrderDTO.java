package com.imooc.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.imooc.dataobject.OrderDetail;
import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.PayStatusEnum;
import com.imooc.utils.EnumUtil;
import com.imooc.utils.serializer.Date2LongSerializer;
import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
//用于dao层和service层传递数据的
@Data
//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)已废弃
//@JsonInclude(JsonInclude.Include.NON_NULL)
/*这里用于BuyerOrderController里那个订单列表那个函数返回给前端时的orderDetailList，
因为orderDetailList不需要所以返过去是null在这里加上这个注解表示null就不用返回过去了*/
public class OrderDTO {
    //@Id//订单id
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
    private Integer orderStatus;
    //支付状态，默认为0未支付
    private Integer payStatus;
    //创建时间
    @JsonSerialize(using= Date2LongSerializer.class)
    private Date createTime;
    //更新时间
    @JsonSerialize(using= Date2LongSerializer.class)
    private Date updateTime;
    private List<OrderDetail> orderDetailList;
    //增加两个方法用于卖家端后台直接得到支付状态和订单状态的值
    //加上这个注解以后对象返出去的时候转成json格式会忽略这两个方法也就是不用把这字段返回出去
    @JsonIgnore
    public OrderStatusEnum  getOrderStatusEnum(){
        return EnumUtil.getByCode(orderStatus, OrderStatusEnum.class);
    }
    @JsonIgnore
    public PayStatusEnum  getPayStatusEnum(){
        return EnumUtil.getByCode(payStatus, PayStatusEnum.class);
    }
}

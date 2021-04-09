package com.imooc.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/*创建订单时为了跟前端数据对应创建的*/
@Data
public class OrderForm {
    /*从Spring Boot 2.3 Release Notes中可以发现，原来从2.3版本开始，Spring Boot的starter包不再默认包
    含spring-boot-starter-validation库了，也就是说如果没有声明引入该库，将会提示javax.validation.*相
    关的类找不到，所以要继续使用@ Valid，@ NotEmpty等注解的话，我们需要引入该库。*/
    //加了@NotEmpty的String类、Collection、Map、数组，是不能为null或者长度为0的(String Collection Map的isEmpty()方法)
    //买家姓名
    @NotEmpty(message="姓名必填")
    private String name;
    //买家手机号
    @NotEmpty(message="手机号必填")
    private String phone;
    //买家地址
    @NotEmpty(message="地址必填")
    private String address;
    //买家微信openId
    @NotEmpty(message="openid必填")
    private String openid;
    //购物车
    @NotEmpty(message="购物车不能为空")
    private String items;
}

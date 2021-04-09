package com.imooc.service.impl;

import com.imooc.dto.OrderDTO;
import com.imooc.service.OrderService;
import com.imooc.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
//支付那边测试
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class PayServiceImplTest {
    @Autowired
    private PayService payService;
    @Autowired
    private OrderService orderService;
    //没有支付账号这里先注释，防止打包失败
 /*   @Test
    void create() throws  Exception{
        OrderDTO orderDTO=orderService.findOne("1615205954098140130");
        payService.create(orderDTO);
    }
    @Test
    public void refund(){
        OrderDTO orderDTO=orderService.findOne("1615602457613394676");
        payService.refund(orderDTO);
    }*/
}
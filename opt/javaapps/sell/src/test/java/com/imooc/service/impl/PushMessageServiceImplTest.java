package com.imooc.service.impl;

import com.imooc.dto.OrderDTO;
import com.imooc.service.OrderService;
import com.imooc.service.PushMessageService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
//测试消息推送
//在公众平台有个基本配置里面有个ip白名单，记得配置
@RunWith(SpringRunner.class)
@SpringBootTest
class PushMessageServiceImplTest {
    @Autowired
    private PushMessageServiceImpl pushMessageService;
    //利用orderService查询一个订单
    @Autowired
    private OrderService orderService;
    @Test
    void orderStatus() throws Exception{
        OrderDTO orderDTO=orderService.findOne("1615647760459237923");
        pushMessageService.orderStatus(orderDTO);
    }
}
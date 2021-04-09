package com.imooc.repository;


import com.imooc.dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {
    @Autowired
    OrderMasterRepository repository;
    //Opendid
    private final String OPENID="110110";
    //测试保存
    @Test
    public void saveTest() throws Exception{
        OrderMaster orderMaster=new OrderMaster();
        orderMaster.setOrderId("1234567");
        orderMaster.setBuyerName("师兄");
        orderMaster.setBuyerPhone("123456789123");
        orderMaster.setBuyerAddress("慕课网");
        orderMaster.setBuyerOpenid(OPENID);
        orderMaster.setOrderAmount(new BigDecimal(2.5));
        OrderMaster result=repository.save(orderMaster);
        Assert.assertNotNull(result);
    }
    @Test
    public void findByBuyerOpenid() throws Exception{
        PageRequest request=PageRequest.of(1,3);//第0页开始,每页两条
        Page<OrderMaster> result=repository.findByBuyerOpenid(OPENID,request);//这里用PageRequest不用Page，因为PageRequest 继承某个类，某个类实现Page,Page只是个接口
        //System.out.println(result.getTotalElements());
        Assert.assertNotEquals(0,result.getTotalElements());
    }
}
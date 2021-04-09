package com.imooc.repository;

import com.imooc.dataobject.SellerInfo;
import com.imooc.utils.KeyUtil;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
//卖家信息实体的dao的测试
@RunWith(SpringRunner.class)
@SpringBootTest
class SellerInfoRepositoryTest {
    @Autowired
    private SellerInfoRepository repository;
    //新增保存
    @Test
    public void save() throws Exception{
        SellerInfo sellerInfo=new SellerInfo();
        sellerInfo.setSellerId(KeyUtil.genUniqueKey());
        sellerInfo.setUsername("admin");
        sellerInfo.setPassword("admin");
        sellerInfo.setOpenid("abc");
        SellerInfo result=repository.save(sellerInfo);
        Assert.assertNotNull(result);

    }
    @Test//通过微信openid查询
    public void findByOpenid() throws Exception {
        SellerInfo result=repository.findByOpenid("abc");
        Assert.assertEquals("abc",result.getOpenid());
    }
}
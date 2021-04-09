package com.imooc.service.impl;

import com.imooc.dataobject.ProductInfo;
import com.imooc.enums.ProductStatusEnum;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProductServiceImplTest {
    @Autowired
    private ProductServiceImpl productService;
    @Test
    void findOne() throws  Exception {

        ProductInfo productInfo =productService.findOne("123459");
      /*  if(productInfo==null){
            System.out.println("找不到");
        }*/
        Assert.assertEquals("123456",productInfo.getProductId());
    }

    @Test
    void findUpAll() throws  Exception{
        List<ProductInfo>  productInfoList=productService.findUpAll();
        Assert.assertNotEquals(0,productInfoList.size());
    }

    @Test
    void findAll() throws  Exception{
        PageRequest request = PageRequest.of(0,2);//这里用PageRequest不用Page，因为PageRequest 继承某个类，某个类实现Page,Page只是个接口
        //这里过时了，不能在直接new
        Page<ProductInfo> productInfoPage=productService.findAll(request);
        // System.out.println(productInfoPage.getTotalElements());
        Assert.assertNotEquals(0,productInfoPage.getTotalElements());
    }

    @Test
    void save() throws  Exception{
        ProductInfo productInfo =new ProductInfo();
        productInfo.setProductId("123457");
        productInfo.setProductName("皮蛋粥");
        productInfo.setProductPrice(new BigDecimal(3.2));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("很好吃的虾");
        productInfo.setProductIcon("http://xxxxx.jpg");
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        productInfo.setCategoryType(2);
        ProductInfo request=productService.save(productInfo);
        Assert.assertNotNull(request);
    }
    @Test
    public void onSale(){
       ProductInfo result= productService.onSale("123456");
       Assert.assertEquals(ProductStatusEnum.UP,result.getProductStatusEnum());
    }
    @Test
    public void offSale(){
        ProductInfo result= productService.offSale("123456");
        Assert.assertEquals(ProductStatusEnum.DOWN,result.getProductStatusEnum());
    }
}
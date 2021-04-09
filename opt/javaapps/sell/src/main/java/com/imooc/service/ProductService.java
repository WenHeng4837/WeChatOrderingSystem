package com.imooc.service;

import com.imooc.dataobject.ProductInfo;
import com.imooc.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/*
商品
* */
public interface ProductService {
    ProductInfo findOne(String productId);
    /*
     * 查询所有所在商品列表
     */
    List<ProductInfo> findUpAll();
    /*
    *管理员查询所有商品，分页
    */
    Page<ProductInfo> findAll(Pageable pageable);
    /*增加商品*/
    ProductInfo save(ProductInfo productInfo);
    //加库存,不用返回数据。有错直接抛异常
    void increaseStock(List<CartDTO> cartDTOList);
    //减库存
    void decreaseStock(List<CartDTO> cartDTOList);
    //商品上架
    ProductInfo onSale(String productId);
    //商品下架
    ProductInfo offSale(String productId);
}

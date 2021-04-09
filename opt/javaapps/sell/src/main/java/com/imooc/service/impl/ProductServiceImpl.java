package com.imooc.service.impl;

import com.imooc.dataobject.ProductInfo;
import com.imooc.dto.CartDTO;
import com.imooc.enums.ProductStatusEnum;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import com.imooc.repository.ProductInfoRepository;
import com.imooc.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
//@CacheConfig(cacheNames = "product")//统一把cacheNames 写在这里，下面就不用写那么多遍
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductInfoRepository repository;
    //测试缓存，//product是文件名也相当于前缀，key就是正常的key,但是如果不填默认就是你传过来的参数
   // @Cacheable(cacheNames = "product",key = "123")//缓存注解,存的时候会进行序列化存进去ResultVO这个对象,Cacheable存了第一次后就不会在进入下面的方法，除非Redis里面缓存没了
  //  @Cacheable(key = "123")//缓存注解,存的时候会进行序列化存进去ResultVO这个对象,Cacheable存了第一次后就不会在进入下面的方法，除非Redis里面缓存没了
    @Override
    public ProductInfo findOne(String productId) {
        return repository.findById(productId).orElse(null);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    //测试缓存
   // @CachePut(cacheNames = "product",key = "123")//@CachePut每次都会执行下面方法的内容然后存进去缓存
   // @CachePut(key = "123")//@CachePut每次都会执行下面方法的内容然后存进去缓存
    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        for(CartDTO cartDTO:cartDTOList){
            ProductInfo productInfo=repository.findById(cartDTO.getProductId()).orElse(null);
            if(productInfo==null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result=productInfo.getProductStock()+cartDTO.getProductQuantity();
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }

    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for(CartDTO cartDTO:cartDTOList){
          ProductInfo productInfo=repository.findById(cartDTO.getProductId()).orElse(null);
          if(productInfo==null){
              throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
          }
          Integer result=productInfo.getProductStock()-cartDTO.getProductQuantity();
          if(result<0){
              throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
          }
          productInfo.setProductStock(result);
          repository.save(productInfo);
        }
    }
    //上架
    @Override
    public ProductInfo onSale(String productId) {
        ProductInfo productInfo=repository.findById(productId).orElse(null);
        if(productInfo==null){
            throw  new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if(productInfo.getProductStatusEnum()==ProductStatusEnum.UP){
            throw  new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        //更新
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        return repository.save(productInfo);
    }
    //下架
    @Override
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo=repository.findById(productId).orElse(null);
        if(productInfo==null){
            throw  new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if(productInfo.getProductStatusEnum()==ProductStatusEnum.DOWN){
            throw  new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        //更新
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        return repository.save(productInfo);
    }
}

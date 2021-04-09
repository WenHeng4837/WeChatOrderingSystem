package com.imooc.dto;

import lombok.Data;

/*
* 购物车*/
@Data
public class CartDTO {
    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }

    //商品id
    private String productId;
    //数量
    private Integer productQuantity;
}

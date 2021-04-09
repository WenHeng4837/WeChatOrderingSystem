package com.imooc.form;

import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;

//用于卖家后台商品新增或者修改后表单提交
@Data
public class ProductForm {

    private String productId;
    /*名字*/
    private String productName;
    /*单价*/
    private BigDecimal productPrice;
    /*库存*/
    private Integer productStock;
    /*描述*/
    private String productDescription;
    /*图片*/
    private String productIcon;
    /*类目编号*/
    private Integer categoryType;
}

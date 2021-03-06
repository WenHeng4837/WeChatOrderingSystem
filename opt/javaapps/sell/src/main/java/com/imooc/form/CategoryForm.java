package com.imooc.form;

import lombok.Data;

//用于类目的的新增或者修改用
@Data
public class CategoryForm {
    private Integer categoryId;

    /** 类目名字. */
    private String categoryName;

    /** 类目编号. */
    private Integer categoryType;
}

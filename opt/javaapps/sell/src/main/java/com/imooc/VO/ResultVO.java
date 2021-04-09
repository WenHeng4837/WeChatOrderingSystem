package com.imooc.VO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/*
* http请求返回的最外层对象*/
@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
/*这里用于BuyerOrderController里那个订单列表那个函数返回给前端时的orderDetailList，
因为orderDetailList不需要所以返过去是null在这里加上这个注解表示null就不用返回过去了*/
public class ResultVO<T> implements Serializable {
    //快捷键shit+i
    private static final long serialVersionUID = 3068837394742385883L;
    /*错误码*/
    private Integer code;
    /*提示信息*/
    private  String msg;
    /*具体内容*/
    private  T data;
}

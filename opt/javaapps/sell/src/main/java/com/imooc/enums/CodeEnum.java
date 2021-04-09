package com.imooc.enums;
//用于支付状态和订单状态的卖家端后台显示不重复写代码
//代码没那么大没做到那么通用都是Integer型就返回Integer，如果很大就自己去设定为T型
public interface CodeEnum<Integer> {
    Integer getCode();
}

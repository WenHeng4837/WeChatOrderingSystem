package com.imooc.service;

import com.imooc.dataobject.SellerInfo;

//卖家端service
public interface SellerService {
    //通过openid去查询卖家信息
    SellerInfo findSellerInfoByOpenid(String openid);
}

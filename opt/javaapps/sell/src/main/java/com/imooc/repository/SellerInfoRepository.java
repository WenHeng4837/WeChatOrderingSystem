package com.imooc.repository;

import com.imooc.dataobject.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
//卖家信息表的dao
public interface SellerInfoRepository extends JpaRepository<SellerInfo,String> {
    //通过openid去查询它的信息
    SellerInfo findByOpenid(String openid);
}

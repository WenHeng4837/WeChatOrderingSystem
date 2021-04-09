package com.imooc.repository;

import com.imooc.dataobject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,String> {
    //按照订单Id查询，一个订单Id对应多个订单详情Id
    List<OrderDetail> findByOrderId(String orderId);
}


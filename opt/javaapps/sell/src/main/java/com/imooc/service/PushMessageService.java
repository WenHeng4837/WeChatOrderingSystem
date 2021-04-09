package com.imooc.service;

import com.imooc.dto.OrderDTO;

//专门用于消息推送模板
public interface PushMessageService {
    //订单状态变更消息
    void orderStatus(OrderDTO orderDTO);
}

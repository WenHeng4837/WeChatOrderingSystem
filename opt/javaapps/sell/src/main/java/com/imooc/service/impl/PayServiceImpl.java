package com.imooc.service.impl;

import com.imooc.dto.OrderDTO;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import com.imooc.service.OrderService;
import com.imooc.service.PayService;
import com.imooc.utils.JsonUtil;
import com.imooc.utils.MathUtil;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class PayServiceImpl implements PayService {
    private static final String ORDER_NAME="微信点餐订单" ;
    @Autowired
    private  BestPayServiceImpl bestPayService;
    @Autowired
    private OrderService orderService;
    @Override
    public PayResponse create(OrderDTO orderDTO) {
        //支付sdk里的类
        //这里发起支付需要传openId和支付金额
        PayRequest payRequest=new PayRequest();
        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        //这里的orderDTO.getOrderAmount()是BigDecimal，需要转成double型
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        //选择支付方式，公众号支付
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("[微信支付] 发起支付，request={}", JsonUtil.toJson(payRequest));
        PayResponse payResponse=bestPayService.pay(payRequest);
        log.info("[微信支付] 发起支付，response={}",JsonUtil.toJson(payResponse));
        return payResponse;
    }

    @Override
    public PayResponse notify(String notifyData) {
        //1验证签名（验证是否是微信请求过来的参数）
        //2支付状态（异步通知消息内容不一定是百分百告诉你支付成功）{前两步sdk做了}
        //3支付的金额（有可能因为某些原因是一分钱到微信是一块钱微信回调给你的也是一块钱，这个时候异步通知就要校验订单金额和异步通知金额是否一样）
        //4 支付人（下单人==支付人）(这里不需要去用到所以不用去判断)
        PayResponse payResponse=bestPayService.asyncNotify(notifyData);
        log.info("[微信支付] 异步通知，payResponse={}",JsonUtil.toJson(payResponse));
        //查询订单
        OrderDTO orderDTO=orderService.findOne(payResponse.getOrderId());
        //判断订单是否存在
        if(orderDTO==null){
            log.error("[微信支付] 异步通知,订单不存在，orderId={}",payResponse.getOrderId());
            throw  new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //判断金额是否一致(0.10 0.1)
        //这里不能直接equals,因为类型不一样，然后及时一样double类型和duoble不能直接比较，要会报错得用不等于0说明不相等
        if(!MathUtil.equals(payResponse.getOrderAmount(),orderDTO.getOrderAmount().doubleValue())){
            log.error("[微信支付] 异步通知,订单金额不一致，orderId={}，微信通知金额={}，系统金额={}",
                    payResponse.getOrderId(),payResponse.getOrderAmount(),orderDTO.getOrderAmount());
            throw new  SellException(ResultEnum.WXPAY_NOTIFY_MONEY_VERIFY_ERROR);
        }
        //修改订单的支付状态
        orderService.paid(orderDTO);
        return payResponse;
    }
    // 退款
    @Override
    public RefundResponse refund(OrderDTO orderDTO) {
        RefundRequest refundRequest=new RefundRequest();
        refundRequest.setOrderId(orderDTO.getOrderId());
        refundRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("[微信退款] request={}",JsonUtil.toJson(refundRequest));
        RefundResponse refundResponse=bestPayService.refund(refundRequest);
        log.info("[微信退款] response={}",JsonUtil.toJson(refundResponse));
        return refundResponse;
    }
}

package com.imooc.controller;

import com.imooc.dto.OrderDTO;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import com.imooc.service.OrderService;
import com.imooc.service.PayService;
import com.lly835.bestpay.model.PayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/pay")
public class PayController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private PayService payService;
    //支付
    @GetMapping("/create")
    public ModelAndView create (@RequestParam("orderId") String orderId, @RequestParam("returnUrl") String returnUrl, Map<String,Object> map){
        //1.查询订单
        OrderDTO orderDTO=orderService.findOne(orderId);
        //log.info("[订单详情]  orderDTO={}",orderDTO);
        if(orderDTO==null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //2.发起支付（把逻辑写到service里）
        PayResponse payResponse=payService.create(orderDTO);
        map.put("payResponse",payResponse);
        //跳到订单详情页吧
        /*
        * 跳转的地址其实就是returnUrl，如果要跳到订单详情页http://sell.com/#/order/123456
          因含有特殊字符#, GET参数会被截断，要想传递完整参数，必须先将其urlEncode再拼接，编码后如下
          http%3a%2f%2fsell.com%2f%23%2forder%2f123456
          PayController接收到参数后，解码后渲染模版，才能跳转
        try{
            map.put("returnUrl", returnUrl.startsWith("http://") ? returnUrl : 	URLEncoder.encode(returnUrl, "utf-8"));
        }catch (UnsupportedEncodingException e){
                e.printStackTrace();
        }*/
        map.put("returnUrl",returnUrl)  ;
        return new ModelAndView("pay/create",map);
    }
    //微信异步通知
    @PostMapping("/notify")//notifyData是微信支付的时候微信传过来的流水号
    public ModelAndView notify(@RequestBody String notifyData){
        payService.notify(notifyData);
        //返回给微信处理结果
        return new ModelAndView("pay/success");
    }
}

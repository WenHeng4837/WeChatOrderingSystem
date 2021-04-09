package com.imooc.controller;

import com.imooc.dto.OrderDTO;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import com.imooc.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/*卖家端订单*/
@Controller//卖家端后端部分展示的前端页面这里用Controller，然后用的framemaker组件
@RequestMapping("/seller/order")
@Slf4j
public class SellerOrderController {
    @Autowired
    private OrderService orderService;

    /*订单列表
    * page 第几页，从第一页开始
    * size 一页有多少条数据*/
    //因为模板渲染所以返回类型用ModelAndView
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value = "size",defaultValue = "10")Integer size, Map<String,Object> map){
        //我定义的接口是从第一页开始查，而PageRequest从第0页开始的，所以这里要减1
        PageRequest request= PageRequest.of(page-1,size);
        Page<OrderDTO> orderDTOPage=orderService.findList(request);
        map.put("orderDTOPage",orderDTOPage);
        map.put("currentPage",page);//当前页
        map.put("size",size);//每页条数
        //第一个参数是模板名字
        return new ModelAndView("order/list",map);
    }
    /**
     * 取消订单
     */
    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam("orderId") String orderId,Map<String,Object> map){
        try{
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.cancel(orderDTO);
        }catch(SellException e){
            log.error("[卖家端取消订单] 发生异常={}",e);
            //错误的时候跳转的信息和提示的url
            map.put("msg", e.getMessage());
            map.put("url","/sell/seller/order/list");//如果发生错误跳到订单列表页
            //这里不用抛出异常直接跳转到一个错误页面
            return new ModelAndView("common/error",map);
        }
        //成功的时候
        map.put("msg", ResultEnum.ORDER_CANCEL_SUCCESS.getMessage());
        map.put("url","/sell/seller/order/list");//如果发生错误跳到订单列表页
        return new ModelAndView("common/success");
    }
    /*
    * 订单详情*/
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId") String orderId,Map<String,Object> map){
        OrderDTO orderDTO=new OrderDTO();
        try{
            orderDTO = orderService.findOne(orderId);
        }catch (SellException e){
            log.error("[卖家端查询订单详情] 发生异常={}",e);
            //错误的时候跳转的信息和提示的url
            map.put("msg", e.getMessage());
            map.put("url","/sell/seller/order/list");//如果发生错误跳到订单列表页
            //这里不用抛出异常直接跳转到一个错误页面
            return new ModelAndView("common/error",map);
        }
        map.put("orderDTO",orderDTO);
        return new ModelAndView("order/detail",map);
    }
    /*
    * 完结订单*/
    @GetMapping("/finish")
    public ModelAndView finished(@RequestParam("orderId") String orderId,Map<String,Object> map){
        try{
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.finish(orderDTO);
        }catch (SellException e){
            log.error("[卖家端完结订单] 发生异常={}",e);
            //错误的时候跳转的信息和提示的url
            map.put("msg", e.getMessage());
            map.put("url","/sell/seller/order/list");//如果发生错误跳到订单列表页
            //这里不用抛出异常直接跳转到一个错误页面
            return new ModelAndView("common/error",map);
        }
        //成功的时候
        map.put("msg", ResultEnum.ORDER_FINISH_SUCCESS.getMessage());
        map.put("url","/sell/seller/order/list");
        return new ModelAndView("common/success");
    }
}

package com.imooc.controller;

import com.imooc.VO.ResultVO;
import com.imooc.converter.OrderForm2OrderDTOConverter;
import com.imooc.dto.OrderDTO;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import com.imooc.form.OrderForm;
import com.imooc.service.BuyerService;
import com.imooc.service.OrderService;
import com.imooc.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private BuyerService buyerService;
    //创建订单
    //BindingResult用在实体类校验信息返回结果绑定。实体类常用的校验注解有：@NotEmpty
    //@Valid
    //用于验证注解是否符合要求，直接加在变量orderForm之前，在变量中添加验证信息的要求，当不符合要求时就会在方法中返回message 的错误提示信息。
    @PostMapping("/create")
    public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("[创建订单] 参数不正确，orderForm={}",orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        //要用转换器而不用BeanUtils因为属性名字不一致
        OrderDTO orderDTO= OrderForm2OrderDTOConverter.convert(orderForm);
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("[创建订单] 购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        OrderDTO createResult=orderService.create(orderDTO);
        Map<String,String> map=new HashMap<>();
        map.put("orderId",createResult.getOrderId());
        return ResultVOUtil.success(map);
    }
    //订单列表，这里的传过来的参数不会太多所以不用特地写个类直接在这里写
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value = "page",defaultValue="0") Integer page,
                                         @RequestParam(value = "size",defaultValue = "10") Integer size){
        if(StringUtils.isEmpty(openid)){
            log.error("[查询订单列表] openid为空");
            throw new SellException((ResultEnum.PARAM_ERROR));
        }
        PageRequest request=PageRequest.of(page,size);
        Page<OrderDTO> orderDTOPage=orderService.findList(openid,request);
        return ResultVOUtil.success(orderDTOPage.getContent());
        //return ResultVOUtil.success();
        /*ResultVO resultVO=new ResultVO();
        resultVO.setCode(0);
        return resultVO;*/
    }
    //订单详情
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,@RequestParam("orderId") String orderId){

        //OrderDTO orderDTO=orderService.findOne(orderId);
        OrderDTO orderDTO=buyerService.findOrderOne(openid,orderId);
        return ResultVOUtil.success(orderDTO);
    }
    //取消订单
    @PostMapping("/cancel")
    public ResultVO cancel (@RequestParam("openid") String openid,@RequestParam("orderId") String orderId){

        //OrderDTO orderDTO=orderService.findOne(orderId);
        //orderService.cancel(orderDTO);
        buyerService.cancelOrder(openid,orderId);
        return ResultVOUtil.success();
    }
}

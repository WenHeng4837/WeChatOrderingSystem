package com.imooc.service.impl;

import com.imooc.config.WechatAccountConfig;
import com.imooc.dto.OrderDTO;
import com.imooc.service.PushMessageService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class PushMessageServiceImpl implements PushMessageService {
    // 注入公众平台那service
    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private WechatAccountConfig accountConfig;//这里主要用于从配置文件引入推送消息模板ID

    @Override//在订单完结情况下推送
    public void orderStatus(OrderDTO orderDTO) {
        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
       /* 测试号测试的模板ID，写死在这里
       templateMessage.setTemplateId("kzGP8ON3gAhEiozA77kWBOlPnqGhobHVuCu_VaMTR0Q");*/
        //正式需要写入模板id,配置到配置文件里
        templateMessage.setTemplateId(accountConfig.getTemplateId().get("orderStatus"));
        /*测试：设置openid，也就是推送给谁，这个openid是相对于关注了公众号的用户的openid,这里先写死
        templateMessage.setToUser("ofPfE6O6IITc8wYHSMbY72LN9lJk");*/
        //从orderDTO.getOrderId()来动态获取买家Openid
        templateMessage.setToUser(orderDTO.getBuyerOpenid());
        //设置数据，里面是个list
        List<WxMpTemplateData> data= Arrays.asList(new WxMpTemplateData("first","亲，请记得收货。"),
                new WxMpTemplateData("keyword1","微信点餐"),
                new WxMpTemplateData("keyword2","18420384837"),
                new WxMpTemplateData("keyword3",orderDTO.getOrderId()),
                new WxMpTemplateData("keyword4",orderDTO.getOrderStatusEnum().getMessage()),
                //这里总价不是String类型，可是只能传String，所以价格人民币符号
                new WxMpTemplateData("keyword5","￥"+orderDTO.getOrderAmount()),
                new WxMpTemplateData("remark","欢迎再次光临!")
                );
        templateMessage.setData(data);
        //log.info("[微信模板消息] 发送失败");
        //跟消息相关的都在这个getTemplateMsgService()里操作,sendTemplateMsg()是发生消息
        //会报异常所以需要捕捉
        try{
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        }catch (WxErrorException e){
            //这里只是捕捉而已不抛异常，因为相对于订单完结提交来说，推送消息不是很重要，万一OrderServiceImpl那里订单成功可是只是推送消息如果抛异常会全部回滚，得不偿失
            log.error("[微信模板消息] 发送失败，{}",e);
        }
    }
}

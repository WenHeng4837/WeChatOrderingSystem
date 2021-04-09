package com.imooc.controller;

import com.imooc.config.ProjectUrlConfig;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.net.URLEncoder;

//微信开发平台，公众平台授权登录需要用到账号
@Controller//RestController返回的是json不会跳转，而我们下面为了重定向改用Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController {
    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private WxMpService wxOpenService;
    @Autowired
    private ProjectUrlConfig projectUrlConfig;
    //买家
    @GetMapping("/authorize")
    public String  authorize(@RequestParam("returnUrl") String returnUrl){
        String url=projectUrlConfig.getWechatMpAuthorize()+"/sell/wechat/userInfo";
        //1.配置，在配置文件里配置了
        //2.调用方法
        //首先构造网页授权url，然后构成超链接让用户点击：
        String redirectUrl=wxMpService.oauth2buildAuthorizationUrl(url,WxConsts.OAUTH2_SCOPE_BASE, URLEncoder.encode(returnUrl));
        log.info("[微信网页授权] 获取code,result={}",redirectUrl);//要重定向的url
        return "redirect:"+redirectUrl;
    }
    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code,@RequestParam("state") String returnUrl){
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try{
            wxMpOAuth2AccessToken=wxMpService.oauth2getAccessToken(code);
        }catch(WxErrorException e){
            log.error("[微信网页授权]{}",e);
            throw  new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(),e.getError().getErrorMsg());
        }
        String openId=wxMpOAuth2AccessToken.getOpenId();
        //这个?openid我一开始写成了openId结果成定向后获取不到openid一直重复登录，他妈毁了我好多温柔
        String returnUrl1=returnUrl+"?openid=" + openId;
        log.info("[微信网页授权重定向] 获取openId,result={}",returnUrl1);
        return "redirect:"+returnUrl1;
    }
    //后台卖家
    //扫码登录整个地址：http://sell9.mynatapp.cc/sell/wechat/qrAuthorize?returnUrl=http://sell9.mynatapp.cc/sell/seller/login
    //微信开发平台扫码授权登录
    @GetMapping("/qrAuthorize")
    public String qrAuthorize(@RequestParam("returnUrl") String returnUrl){
        String url=projectUrlConfig.getWechatOpenAuthorize()+"/sell/wechat/qrUserInfo";
        log.info("url,{}",url);
        String redirectUrl=wxOpenService.buildQrConnectUrl(url,WxConsts.QRCONNECT_SCOPE_SNSAPI_LOGIN,URLEncoder.encode(returnUrl));
        return "redirect:"+redirectUrl;
    }
    //微信开发平台扫码授权登录重定向方法
    @GetMapping("/qrUserInfo")
    public String qrUserInfo(@RequestParam("code") String code,@RequestParam("state") String returnUrl){
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try{
            wxMpOAuth2AccessToken=wxOpenService.oauth2getAccessToken(code);
        }catch(WxErrorException e){
            log.error("[微信网页授权]{}",e);
            throw  new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(),e.getError().getErrorMsg());
        }
        log.info("wxMpOAuth2AccessToken={}",wxMpOAuth2AccessToken);
        String openId=wxMpOAuth2AccessToken.getOpenId();
        //这个?openid我一开始写成了openId结果成定向后获取不到openid一直重复登录，他妈毁了我好多温柔
        //公众测试号的openID要加入数据库才能实现后面的登录
        return "redirect:" + returnUrl+"?openid="+openId;
    }
}

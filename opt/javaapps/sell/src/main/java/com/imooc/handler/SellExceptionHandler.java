package com.imooc.handler;

import com.imooc.VO.ResultVO;
import com.imooc.config.ProjectUrlConfig;
import com.imooc.exception.ResponseBankException;
import com.imooc.exception.SellException;
import com.imooc.exception.SellerAuthorizeException;
import com.imooc.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/*
* @ControllerAdvice，是Spring3.2提供的新注解,它是一个Controller增强器,
* 可对controller中被 @RequestMapping注解的方法加一些逻辑处理。最常用的就是异常处理*/

@ControllerAdvice
@Slf4j
public class SellExceptionHandler {
    @Autowired
    private ProjectUrlConfig projectUrlConfig;
    //拦截登录异常，拦截之后做个跳转
    @ExceptionHandler(value = SellerAuthorizeException.class)//拦截异常的名字
   // @ResponseStatus(HttpStatus.FORBIDDEN)
    public  ModelAndView  handlerAuthorizeException(){
        //concat:拼接函数
        //http://sell9.mynatapp.cc/sell/wechat/qrAuthorize?returnUrl=http://sell9.mynatapp.cc/sell/seller/login
        log.info("首先到这里了");
        return new ModelAndView("redirect:"
                .concat(projectUrlConfig.getWechatOpenAuthorize())//微信授权地址,但时候授权地址可能不是http://sell9.mynatapp.cc
                .concat("/sell/wechat/qrAuthorize")
                .concat("?returnUrl=")
                .concat(projectUrlConfig.getSell())
                .concat("/sell/seller/login"));

    }

    //由于上面不是@RestController所以这里加个@ResponseBody
    //异常同意处理，会返回给前端对应状态码和消息，比如10，商品不存在
    @ExceptionHandler(value = SellException.class)
    @ResponseBody
    public ResultVO handlerSellerException(SellException e){//获取到SellException异常
        return ResultVOUtil.error(e.getCode(),e.getMessage());
    }

    //银行异常，举列子如何返回所需状态码不再让他是200用的，项目不需要用到它
    @ExceptionHandler(value = ResponseBankException.class )
    @ResponseStatus(HttpStatus.FORBIDDEN)//假如要返回的是403这个错误状态码,HttpStatus.FORBIDDEN对应@ResponseStatus这个借口里的403
    public void handlerResponseBankException(){
    }
}

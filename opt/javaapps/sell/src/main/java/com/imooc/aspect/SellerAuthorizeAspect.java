package com.imooc.aspect;

import com.imooc.constant.CookieConstant;
import com.imooc.constant.RedisConstant;
import com.imooc.exception.SellerAuthorizeException;
import com.imooc.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
//AOP登录身份验证切面
@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {
    @Autowired
    private StringRedisTemplate redisTemplate;
    //验证方法，验证登录
    //定义切入点,com.imooc.controller下面的以Seller开头的controller里面的方法，但是不拦截登录登出方法所以这里得排掉，因为登录登出方法也在Seller的controller里
    @Pointcut("execution(public * com.imooc.controller.Seller*.*(..))"+
            "&& !execution(public * com.imooc.controller.SellerUserController.*(..))")
    public void verify(){

    }
    @Before("verify()")//在切入点之前做这个操作
    public void doVerify(){
        ServletRequestAttributes attributes=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request=attributes.getRequest();//获取到request后就可以拿cookie
        Cookie cookie= CookieUtil.get(request, CookieConstant.TOKEN);
        //cookie==null就是还没登录
        if(cookie==null){
            log.warn("[登录校验] Cookie中查不到token");
            //抛出异常在对异常捕捉处理那里就跳到扫码登录界面
            throw new SellerAuthorizeException();
        }
        //去redis里查询
        String tokenValue =redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
        //如果是空的
        if(StringUtils.isEmpty(tokenValue)){
            log.warn("[登录校验] Redis中查不到token");
            //抛出异常在对异常捕捉处理那里就跳到扫码登录界面
            throw new SellerAuthorizeException();
        }
    }
}

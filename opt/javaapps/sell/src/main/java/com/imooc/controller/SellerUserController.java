package com.imooc.controller;

import com.imooc.config.ProjectUrlConfig;
import com.imooc.constant.CookieConstant;
import com.imooc.constant.RedisConstant;
import com.imooc.dataobject.SellerInfo;
import com.imooc.enums.ResultEnum;
import com.imooc.service.SellerService;
import com.imooc.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

//卖家用户，这里也是用Controller因为会涉及页面跳转啥的
@Controller
@RequestMapping("/seller")
public class SellerUserController {
    @Autowired
    private SellerService sellerService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private ProjectUrlConfig projectUrlConfig;
    //登录
    @GetMapping("/login")//为了把token设置到cookie所以需要有个参HttpServletResponse response
    public ModelAndView login(@RequestParam("openid") String openid, HttpServletResponse response, Map<String,Object> map) {
        //1.openid去和数据库的数据匹配
        SellerInfo sellerInfo=sellerService.findSellerInfoByOpenid(openid);
        if(sellerInfo==null){
            map.put("msg", ResultEnum.LOGIN_FAIL.getMessage());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("common/error");
        }
        //2.设置token至redis
        //用UUID生成一个token
        String token = UUID.randomUUID().toString();
        //再设置一个过期时间
        Integer expire= RedisConstant.EXPIRE;
        //opsForValue就是说要操作某些Value
        //redisTemplate.opsForValue().set("abc","feweddwedw");
        //String.format(RedisConstant.TOKEN_PREFIX,token):格式化了一下存储的是以token开头的一个key
        //openid为value值，expire为过期时间，然后TimeUnit.SECONDS是时间格式是秒
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX,token),openid,expire, TimeUnit.SECONDS);
        //3.设置token至cookie，往cookie里填name和value。CookieConstant.TOKEN:cookie名字,token:值
        CookieUtil.set(response, CookieConstant.TOKEN,token,expire);
        //登录成功后跳到订单列表界面，让它跳到订单列表就得做个跳转重定向，而不是加什么map模板渲染
        //注意：这里跳转的时候用完整的http地址，不要用相对地址
        return new ModelAndView("redirect:"+projectUrlConfig.getSell()+"/sell/seller/order/list");

    }
    //登出,登出功能其实就清除cookie
    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,HttpServletResponse response,Map<String,Object> map){
        //1.从cookie里查询
        Cookie cookie=CookieUtil.get(request,CookieConstant.TOKEN);
        if(cookie!=null){
            //2.清除Redis
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
            //3.cookie,时间设置为0就清除了
            CookieUtil.set(response,CookieConstant.TOKEN,null,0);
        }
        map.put("msg",ResultEnum.LOGOUT_SUCCESS.getMessage());
        //登出完后跳到列表页去
        map.put("url","/sell/seller/order/list");
        return new ModelAndView("common/success",map);

    }
}

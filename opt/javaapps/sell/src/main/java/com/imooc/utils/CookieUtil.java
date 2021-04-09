package com.imooc.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

//用于扫码登录的cookie工具类
public class CookieUtil {
    //设置
    public static void set(HttpServletResponse response,String name,String value,int maxAge){
        Cookie cookie=new Cookie(name,value);
        cookie.setPath("/");//path一般不会去修改,所以这里直接写死
        cookie.setMaxAge(maxAge);//cookie的过期时间
        response.addCookie(cookie);
    }
    //获取cookie
    public static Cookie get(HttpServletRequest request,String name){
        //这里从request里得到cookie是个数组，需要遍历
        Map<String,Cookie> cookieMap=readCookieMap(request);
        //判断一下这里是否有这个name值的cookie
        if(cookieMap.containsKey(name)){
            //包含的话直接return回去，否则return null
            return cookieMap.get(name);
        }else{
            return null;
        }

    }
    //这个静态方法把原来的Cookie数组转成map形式
    private static Map<String,Cookie> readCookieMap(HttpServletRequest request){
        Map<String,Cookie> cookieMap=new HashMap<>();
        Cookie[] cookies=request.getCookies();
        if(cookies != null){
            for(Cookie cookie:cookies){
                cookieMap.put(cookie.getName(),cookie);
            }
        }
        return cookieMap;
    }
}

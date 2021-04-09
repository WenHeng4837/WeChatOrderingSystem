package com.imooc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

//微信公众号以及开发平台扫码登录重定向配置
@Data
@ConfigurationProperties(prefix = "projecturl")//这里命名不用大写，一律小写或者加下划线。因为yml配置文件不能用大写。
@Component
public class ProjectUrlConfig {
    //微信公众平台授权url
    public String wechatMpAuthorize;
    //微信开发平台授权url
    public String wechatOpenAuthorize;
    //点餐系统url
    public String sell;
}

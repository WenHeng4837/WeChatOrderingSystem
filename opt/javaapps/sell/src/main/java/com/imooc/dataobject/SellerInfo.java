package com.imooc.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

//卖家信息实体类
@Data
@Entity
@Proxy(lazy = false)//设置立即加载
@DynamicUpdate//创建时间修改时间自动更新
public class SellerInfo {
    @Id
    private String sellerId;
    private String username;
    private String password;
    private String openid;
    private Date createTime;
    private Date updateTime;
}

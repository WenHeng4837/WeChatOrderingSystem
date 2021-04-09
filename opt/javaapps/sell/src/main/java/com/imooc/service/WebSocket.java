package com.imooc.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;

//跟普通controller不一样，这里写在这里但是它又不是service
@Component
@ServerEndpoint("/webSocket")//url
@Slf4j
public class WebSocket {
    //定义一个session
    private Session session;
    //定义WebSocket的容器,这里不用普通set的原因是
    private static CopyOnWriteArraySet<WebSocket> webSocketSet=new CopyOnWriteArraySet<>();
    //下面下的是对应resources下面templates/order/list.ftl对应写的路径
    @OnOpen
    public void onOpen(Session session){
        this.session=session;
        webSocketSet.add(this);
        log.info("[websocket消息] 有新的连接，总数：{}",webSocketSet.size());
    }
    @OnClose
    public void onClose(){
        webSocketSet.remove(this);
        log.info("[websocket消息] 连接断开，总数：{}" ,webSocketSet.size());
    }
    //下面是收到消息，还有有一个发送消息但是list.ftl里面没有
    @OnMessage
    public void onMessage(String message) {

        log.info("【websocket消息】收到客户端发来的消息:{}", message);
    }

    public void sendMessage(String message) {
        for (WebSocket webSocket: webSocketSet) {
            log.info("【websocket消息】广播消息, message={}", message);
            //这里要捕捉不然有异常，但是不抛不要影响正常业务，因为它相对来说不是很重要
            try {
                webSocket.session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

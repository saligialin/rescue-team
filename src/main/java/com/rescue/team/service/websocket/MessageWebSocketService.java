package com.rescue.team.service.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rescue.team.bean.pojo.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@ServerEndpoint(value = "/websocket/message/{tid}")
public class MessageWebSocketService {

    private static ConcurrentHashMap<String, List<MessageWebSocketService>> webSocketClientMap= new ConcurrentHashMap<>();

    private Session session;


    @OnOpen
    public void onOpen(Session session, @PathParam("tid") String tid) {
        log.info("新的连接建立");
        this.session = session;
        List<MessageWebSocketService> list = webSocketClientMap.get("message" + tid);
        if(list==null) list = new ArrayList<>();
        list.add(this);
        webSocketClientMap.put("message"+tid,list);
    }

    @OnClose
    public void onClose(Session session, @PathParam("tid") String tid) {
        log.info("一连接关闭");
        List<MessageWebSocketService> list = webSocketClientMap.get("message" + tid);
        list.remove(this);
        webSocketClientMap.put("message"+tid,list);
    }

    @OnError
    public void onError(Session session,Throwable error) {
        log.info("发生错误");
        error.printStackTrace();
    }

    @OnMessage
    public void onMessage(String parameter) {
        log.info("接收到信息");
        JSONObject json = JSON.parseObject(parameter);
        Message message = new Message();
        message.setVid(json.getString("vid"));
        message.setTid(json.getString("tid"));
        message.setPicture(json.getString("picture"));
        message.setTime(json.getDate("time"));
        message.setMessage(json.getString("message"));

        List<MessageWebSocketService> list = webSocketClientMap.get("message" + message.getTid());
        if (list!=null) {
            for (MessageWebSocketService service : list) {
                service.sendMessage(message);
            }
        }
    }

    public void sendMessage(Message message) {
        try {
            this.session.getBasicRemote().sendText(JSON.toJSONString(message));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

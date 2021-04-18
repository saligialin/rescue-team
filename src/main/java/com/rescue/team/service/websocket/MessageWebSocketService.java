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

    private static ConcurrentHashMap<String, List<Message>> historyMessageMap = new ConcurrentHashMap<>();

    private Session session;


    @OnOpen
    public void onOpen(Session session, @PathParam("tid") String tid) {
        log.info("新的连接建立");

        //建立连接
        this.session = session;
        List<MessageWebSocketService> list = webSocketClientMap.get("message" + tid);
        if(list==null) list = new ArrayList<>();
        list.add(this);
        webSocketClientMap.put("message"+tid,list);

        //为新连接的用户推送历史记录
        List<Message> messageList = historyMessageMap.get("message" + tid);
        if(messageList==null) {
            List<Message> mList = new ArrayList<>();
            historyMessageMap.put("message" + tid, mList);
        } else {
            for (MessageWebSocketService service : list) {
                service.sendAllMessage(messageList);
            }
        }
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

        //解析消息字符串
        JSONObject json = JSON.parseObject(parameter);
        Message message = new Message();
        message.setVid(json.getString("vid"));
        message.setTid(json.getString("tid"));
        message.setName(json.getString("name"));
        message.setPicture(json.getString("picture"));
        message.setTime(json.getDate("time"));
        message.setMessage(json.getString("message"));

        //将消息存储到历史消息list中
        List<Message> messageList = historyMessageMap.get("message" + message.getTid());
        messageList.add(message);
        historyMessageMap.put("message" + message.getTid(), messageList);

        //为所有用户推送新消息
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

    public void sendAllMessage(List<Message> list) {
        try {
            this.session.getBasicRemote().sendText(JSON.toJSONString(list));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

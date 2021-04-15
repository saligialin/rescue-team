package com.rescue.team.service.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rescue.team.bean.pojo.Location;
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
@ServerEndpoint(value = "/websocket/location")
public class LocationWebSocketService {

    private static ConcurrentHashMap<String, List<LocationWebSocketService>> webSocketClientMap= new ConcurrentHashMap<>();

    private Session session;

    @OnOpen
    public void onOpen(Session session, @PathParam("tid") String tid) {
        log.info("新的连接建立");
        this.session = session;
        List<LocationWebSocketService> list = webSocketClientMap.get("location" + tid);
        if(list==null) list = new ArrayList<>();
        list.add(this);
        webSocketClientMap.put("location"+tid,list);
    }

    @OnClose
    public void onClose(Session session, @PathParam("tid") String tid) {
        log.info("一连接关闭");
        List<LocationWebSocketService> list = webSocketClientMap.get("location" + tid);
        list.remove(this);
        webSocketClientMap.put("location"+tid,list);
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
        Location location = new Location();
        location.setVid(json.getString("vid"));
        location.setTid(json.getString("tid"));
        location.setPicture(json.getString("picture"));
        location.setName(json.getString("name"));
        location.setLongitude(json.getDouble("longitude"));
        location.setLatitude(json.getDouble("latitude"));

        List<LocationWebSocketService> list = webSocketClientMap.get("location" + location.getTid());
        for (LocationWebSocketService service : list) {
            service.sendMessage(location);
        }
    }

    public void sendMessage(Location location) {
        try {
            this.session.getBasicRemote().sendText(JSON.toJSONString(location));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
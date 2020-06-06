package com.soil.service;

import com.soil.pojo.SoilWater;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * webSocket 和 web 客户端通信
 */
@Component
@ServerEndpoint("/webSocket")
public class WebSocket {

    private Session session;

    public static SoilWater soilWater = null;
    public static String soilMessage = null;

    private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);
        System.out.println("【websocket消息】有新的连接, 总数:"+ webSocketSet.size());
    }

    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        System.out.println("【websocket消息】连接断开, 总数:{}"+ webSocketSet.size());
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("【websocket消息】收到客户端发来的消息:{}"+ message);
        sendMessage("收到了" );
    }

    public static void sendMessage(String message) {
        for (WebSocket webSocket: webSocketSet) {
            System.out.println("【websocket消息】广播消息, message={}"+ message);
            try {
                webSocket.session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

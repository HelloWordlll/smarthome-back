package com.hmall.smarthome.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.hmall.smarthome.common.BaseResponse;
import com.hmall.smarthome.entry.vo.TopVO;
import com.hmall.smarthome.server.IotService;
import com.hmall.smarthome.server.impl.IotServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@ServerEndpoint("/ws/update") // WebSocket 连接端点
public class WebSocketServer {

    private final IotService iotService = SpringApplicationContext.getBean(IotService.class);;

    private static final CopyOnWriteArraySet<Session> sessions = new CopyOnWriteArraySet<>();
    private static final Map<Session, String> userSessions = new ConcurrentHashMap<>();

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
        log.info("New connection, session id: " + session.getId());
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
        userSessions.remove(session); // 移除用户会话
        log.info("Connection closed, session id: " + session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) throws JsonProcessingException {

        if ("ping".equals(message)) {
            // 心跳消息的响应
            try {
                BaseResponse<Object> objectBaseResponse = new BaseResponse<>();
                objectBaseResponse.setData(null);
                objectBaseResponse.setCode(200);
                objectBaseResponse.setMessage("pong");

                String s = objectMapper.writeValueAsString(objectBaseResponse);

                session.getBasicRemote().sendText(s);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        log.info("Message received from client: " + message);
        // 其他消息处理逻辑
        String[] parts = message.split(":", 2);
        if (parts[0].trim().equals("room") && parts.length == 2) {
            String room = parts[1].trim();
            userSessions.put(session, room);
            List<TopVO> top = iotService.getTop(room);

            BaseResponse<List<TopVO>> objectBaseResponse = BaseResponse.success(top);
            objectBaseResponse.setMessage("set");

            String json = objectMapper.writeValueAsString(objectBaseResponse);

            sendMessageToRoom(room, json);
            log.info("room: " + room);
            log.info("json: " + json);
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    // 向特定用户发送消息
    public static void sendMessageToRoom(String room, String message) {
        for (Map.Entry<Session, String> entry : userSessions.entrySet()) {
            if (entry.getValue().equals(room)) { // 找到匹配的用户 ID
                Session session = entry.getKey();
                if (session.isOpen()) {
                    try {
                        session.getBasicRemote().sendText(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    // 处理收到的消息
    private void handleMessage(String room, String userMessage) {
        // 根据用户消息做相应处理，并可以使用 sendMessageToUser 向特定用户返回消息
        String responseMessage = "Received your message: " + userMessage;
        sendMessageToRoom(room, responseMessage);
    }

    @Scheduled(fixedRate = 1000 * 5) // 每5秒执行一次
    public void scheduledTask() {
//        log.info("定时任务执行中...");
        // 这里编写你的定时处理逻辑
        for (Map.Entry<Session, String> entry : userSessions.entrySet()) {
            String room = entry.getValue();
            List<TopVO> top = iotService.getTop(room);
            Session session = entry.getKey();
            if (session.isOpen()) {
                try {
                    BaseResponse<List<TopVO>> objectBaseResponse = BaseResponse.success(top);
                    objectBaseResponse.setMessage("set");
                    String s = objectMapper.writeValueAsString(objectBaseResponse);

                    session.getBasicRemote().sendText(s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

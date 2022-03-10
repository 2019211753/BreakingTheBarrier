package com.lrm.util;

import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketUtils {
    public static final Map<Long, Session> sessionMap = new ConcurrentHashMap<>();

    public static void sendMessageAll(String message) {
        sessionMap.forEach(((aLong, session) -> sendMessage(session, message)));
    }

    public static void sendMessage(Session session, String message) {
        if (session == null) {
            return;
        }
        RemoteEndpoint.Basic basic = session.getBasicRemote();
        if (basic == null) {
            return;
        }
        try {
            basic.sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

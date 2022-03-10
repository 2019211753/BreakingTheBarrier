package com.lrm.web;

import com.lrm.util.UserHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import static com.lrm.util.WebSocketUtils.*;

@RestController
@ServerEndpoint("/teams/chatroom")
public class WebsocketController {
    private static final Logger log = LoggerFactory.getLogger(WebsocketController.class);
    @OnOpen
    public void openSession(Session session){
        sessionMap.put(UserHolder.getLocalUser().getId(), session);
        String message = "欢迎用户" + UserHolder.getLocalUser().getNickname() + "来到组队聊天室";
        sendMessageAll(message);
    }


    @OnMessage
    public void onMessage(String message){
        sendMessageAll("用户["+ UserHolder.getLocalUser().getId() +"]:" + message);
    }

    @OnClose
    public void onClose(@PathParam("username") String username, Session session){
        //移除session
        sessionMap.remove(UserHolder.getLocalUser().getId());
        //通知他人
        sendMessageAll("用户["+ UserHolder.getLocalUser().getNickname() +"]已经离开聊天室");
        try {
            session.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @OnError
    public void onError(Session session, Throwable throwable){
        try {
            session.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        throwable.printStackTrace();
    }

    @PostMapping("/send")
    public void sendAll(@RequestParam("message") String message){
        sendMessageAll(message);
    }
}

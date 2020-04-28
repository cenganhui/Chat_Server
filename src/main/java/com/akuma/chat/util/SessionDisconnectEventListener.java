package com.akuma.chat.util;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 * websocket断开连接事件
 * @author Akuma
 * @date 2020/4/27 19:07
 */
@Component
public class SessionDisconnectEventListener extends BaseSessionEventListener<SessionDisconnectEvent> {


    public void onApplicationEvent(SessionDisconnectEvent event) {
        using(event,(user,session) -> {
            log.info("{}<===>{},disconnect",user,session);
            SocketSessionRegistry.removeSessionId(session);
        });
    }
}

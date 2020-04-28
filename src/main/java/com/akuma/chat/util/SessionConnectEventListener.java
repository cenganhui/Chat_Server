package com.akuma.chat.util;

import com.louislivi.fastdep.shirojwt.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;

/**
 * websocket建立连接事件
 * @author Akuma
 * @date 2020/4/27 19:05
 */
@Component
public class SessionConnectEventListener extends BaseSessionEventListener<SessionConnectEvent> {

    @Autowired
    private JwtUtil jwtUtil;

    public void onApplicationEvent(SessionConnectEvent event) {
        using(event,(user,session) -> {
            //如果当前用户没有登录（没有认证信息），就添加到游客里面
            if (user == null || "".equals(user) || "undefined".equals(user) || "null".equals(user)) {
                log.info("user is null");
            }
            String userId = String.valueOf(jwtUtil.getUserId(user));
            log.info("{}<===>{},connect",user,session);
            //存入uid和session
            SocketSessionRegistry.registerSessionId(userId,session);
        });
    }
}

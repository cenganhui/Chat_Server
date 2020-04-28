package com.akuma.chat.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 用户session记录类
 * @author Akuma
 * @date 2020/4/27 19:14
 */
public class SocketSessionRegistry {



    private static final Logger logger = LoggerFactory.getLogger(SocketSessionRegistry.class);

    /**
     * 集合存储用户名和simpSessionId
     */
    private static final ConcurrentHashMap<String,String> userSession = new ConcurrentHashMap<>();

    /**
     * 根据用户获取simpSessionId
     * @param userId 用户id
     * @return 用户的simpSession
     */
    public static String getSessionId(String userId) {
        return SocketSessionRegistry.userSession.get(userId);
    }

    /**
     * 用户id记录sessionId
     * @param user 用户id
     * @param sessionId simpSessionId
     */
    public static void registerSessionId(String user,String sessionId) {
        if(user != null && !user.isEmpty() && sessionId != null && !sessionId.isEmpty()) {
            System.out.println("user + sessionId:" + user + " " + sessionId);
            SocketSessionRegistry.userSession.put(user,sessionId);
        } else {
            SocketSessionRegistry.logger.warn("register session fail ===> user or sessionId is null");
        }
    }

    /**
     * 根据session删除用户记录
     * @param sessionId
     */
    public static void removeSessionId(String sessionId) {
        if(sessionId != null && !sessionId.isEmpty()) {
            SocketSessionRegistry.userSession.entrySet().stream()
                    .filter(entry -> entry.getValue().equals(sessionId))
                    .forEach(entry -> {
                        SocketSessionRegistry.userSession.remove(entry.getKey());
                    });
        } else {
            SocketSessionRegistry.logger.warn("remove session fail ===> sessionId is null");
        }
    }

    /**
     * 获取记录
     * @return
     */
    public static ConcurrentHashMap<String,String> getUserSession() {
        return SocketSessionRegistry.userSession;
    }

}
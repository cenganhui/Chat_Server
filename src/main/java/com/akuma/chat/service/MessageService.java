package com.akuma.chat.service;

import com.akuma.chat.dao.MessageDao;
import com.akuma.chat.dao.UserDao;
import com.akuma.chat.model.entity.Message;
import com.akuma.chat.model.entity.User;
import com.akuma.chat.model.entity.websocket.InMessage;
import com.akuma.chat.util.SocketSessionRegistry;
import com.louislivi.fastdep.shirojwt.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Akuma
 * @date 2020/4/27 20:14
 */

@Service
public class MessageService {

    @Autowired
    private MessageDao messageDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 处理消息
     * @param inMessage
     * @return
     */
    public List<Message> handlerMessage(InMessage inMessage){
        // 通过token转换成用户id
        String fromId = String.valueOf(jwtUtil.getUserId(inMessage.getToken()));
        // 查询发送方用户信息
        User fromUser = userDao.queryUserById(fromId);
        //User fromUser = userDao.queryUserByName(fromId);
        // 查询接受方用户信息
        //User toUser = userDao.queryUserById(inMessage.getTo());
        //System.out.println("inMessage****" + inMessage.getTo());
        User toUser = userDao.queryUserByName(inMessage.getTo());
        //System.out.println("fromUser:" + fromUser.toString());
        //System.out.println("toUser:" + toUser.toString());
        // 根据用户id获取用户的在线情况
        String toPath = SocketSessionRegistry.getSessionId(toUser.getUId());
        //System.out.println("toPath:" + toPath);
        // 创建一个消息
        Message message=new Message();
        message.setSenderId(fromUser.getUId());
        message.setReceiverId(toUser.getUId());
        message.setContent(inMessage.getContent());
        Long time = new Date().getTime();
        message.setSendTime(time);

        List<Message> messageList=null;
        if(toPath == null) {
             //接收方用户不在线
            message.setStatus(1);
        } else {
            message.setStatus(1);
            messageList=new ArrayList<>();
            messageList.add(message);
        }
         //添加消息进数据库
        messageDao.insertMessage(message);
        return messageList;
    }

    /**
     * 查询消息好友列表
     * @param uId
     * @return
     */
    public List<User> getMessageUserList(String uId) {
        return messageDao.getMessageUserList(uId);
    }

    /**
     * 获取消息列表
     * @param uId   本人id
     * @param uName 对方uName
     * @return
     */
    public List<Message> getMessageList(String uId,String uName){
        String receiverId = userDao.queryUserByName(uName).getUId();
        List<Message> messageList = messageDao.getMessage(uId,receiverId);
        return messageList;
    }

}

package com.akuma.chat.controller;

import com.akuma.chat.model.ReturnModel;
import com.akuma.chat.model.entity.Message;
import com.akuma.chat.model.entity.User;
import com.akuma.chat.model.entity.websocket.InMessage;
import com.akuma.chat.model.req.message.GetMessageListReq;
import com.akuma.chat.model.res.message.GetMessageListRes;
import com.akuma.chat.service.MessageService;
import com.akuma.chat.util.SocketSessionRegistry;
import com.louislivi.fastdep.shirojwt.jwt.JwtUtil;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 消息控制层
 * @author Akuma
 * @date 2020/4/27 18:58
 */
@RestController
@RequestMapping("/api/v1")
public class MessageController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MessageService messageService;

    @GetMapping("/messageUser")
    @ApiOperation(value = "查询消息好友列表")
    public ReturnModel getMessageUserList(){
        String uId = String.valueOf(jwtUtil.getUserId());
        List<User> list = messageService.getMessageUserList(uId);

        return null;
    }

    /**
     * 查询与某人的消息列表
     * @param req
     * @return
     */
    @GetMapping("/message")
    public ReturnModel getMessage(@Validated GetMessageListReq req){
        String uId = String.valueOf(jwtUtil.getUserId());
        List<Message> messageList = messageService.getMessageList(uId,req.getUName());
        GetMessageListRes getMessageListRes = new GetMessageListRes();
        getMessageListRes.setMessageList(messageList);
        ReturnModel returnModel = new ReturnModel();
        returnModel.setCode(200);
        returnModel.setMsg("ok");
        returnModel.setData(getMessageListRes);
        return returnModel;
    }


    /**
     * 广播推送
     * @param inMessage 接受消息体
     */
    @MessageMapping("/chat")
    public void singleChat(InMessage inMessage){
        String toUser = inMessage.getTo();
        List<Message> messageList = messageService.handlerMessage(inMessage);
        if(messageList != null) {
            // 消息推送给订阅了 /queue/chat/{toUser} 的用户
            messagingTemplate.convertAndSend("/queue/chat/" + toUser, messageList);
        }
    }

}

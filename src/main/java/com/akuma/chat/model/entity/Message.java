package com.akuma.chat.model.entity;

import lombok.Data;

/**
 * 消息类
 * @author Akuma
 * @date 2020/4/27 19:54
 */
@Data
public class Message {
    private Integer mid;    //消息id
    private String senderId;    //发送者
    private String receiverId;  //接收者
    private String content; //内容
    private Long sendTime;  //时间
    private Integer status; //状态
}

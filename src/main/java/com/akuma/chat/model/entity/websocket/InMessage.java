package com.akuma.chat.model.entity.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 消息传送类
 * @author Akuma
 * @date 2020/4/27 19:58
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InMessage {

    private String token; //发送者token

    private String to; //接收者uName

    private String content; //内容
}

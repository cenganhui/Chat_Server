package com.akuma.chat.model.entity.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Akuma
 * @date 2020/4/27 19:58
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutMessage {

    private String from;

    private String content;

    private Long time;
}

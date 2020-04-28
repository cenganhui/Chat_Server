package com.akuma.chat.model.res.message;

import com.akuma.chat.model.entity.Message;
import lombok.Data;

import java.util.List;

/**
 * @author Akuma
 * @date 2020/4/28 16:26
 */
@Data
public class GetMessageListRes {
    List<Message> messageList;
}

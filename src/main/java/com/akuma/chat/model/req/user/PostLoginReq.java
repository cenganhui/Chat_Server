package com.akuma.chat.model.req.user;

import lombok.Data;


/**
 * @author Akuma
 * @date 2020/4/27 21:04
 */
@Data
public class PostLoginReq {
    private String uId;
    private String uPasswd;
}
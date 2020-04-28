package com.akuma.chat.model.entity;

import lombok.Data;

/**
 * @author Akuma
 * @date 2020/4/27 22:23
 */
@Data
public class TokenInfo {
    // 头部信息
    private String sub;
    // 接收端
    private String aud;
    // 签发端
    private String iss;
    // 名称
    private String name;
    // 用户id
    private String id;
    // 起始时间
    private Long iat;
    // 过期时间
    private Long exp;
    // 管理员标识
    private boolean admin;
}

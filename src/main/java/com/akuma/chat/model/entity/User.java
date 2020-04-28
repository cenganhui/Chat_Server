package com.akuma.chat.model.entity;

import lombok.Data;

/**
 * 用户类
 * @author Akuma
 * @date 2020/4/27 19:50
 */
@Data
public class User {
    private String uId;
    private String uPasswd;
    private String uName;
    private Integer uGender;
    private String uAvatar;
    private String uBrief;
    private Integer uCampus;
    private String uPhone;
    private Integer uPermission;
}

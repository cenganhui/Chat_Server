package com.akuma.chat.model;

import lombok.Data;

/**
 * 后端数据返回类
 * @author Akuma
 * @date 2020/4/27 21:05
 */
@Data
public class ReturnModel {
    private Integer code;
    private String msg;
    private Object data;
}
package com.akuma.chat.controller;

import com.louislivi.fastdep.shirojwt.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Akuma
 * @date 2020/4/27 18:50
 */
@RestController
public class HelloController {

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/hello")
    public String doHello() {
        return "hello world!";
    }
/*
    @PostMapping("/hello/login")
    public String login(@RequestBody User user) {
        System.out.println(user.toString());
        // 签名认证,返回token
        return jwtUtil.sign(user.getUsername());
    }*/

    @GetMapping("/profile")
    public String doProfile() {
        System.out.println(jwtUtil.getUserId());
        return "profile info!";
    }
}

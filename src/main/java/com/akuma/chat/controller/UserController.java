package com.akuma.chat.controller;


import com.akuma.chat.model.ReturnModel;
import com.akuma.chat.model.entity.User;
import com.akuma.chat.model.req.user.PostConnectReq;
import com.akuma.chat.model.req.user.PostLoginReq;
import com.akuma.chat.model.res.user.GetUserListRes;
import com.akuma.chat.model.res.user.GetUserRes;
import com.akuma.chat.model.res.user.PostConnectRes;
import com.akuma.chat.model.res.user.PostLoginRes;
import com.akuma.chat.service.UserService;
import com.akuma.chat.util.AESUtil;
import com.akuma.chat.util.ParseSystemUtil;
import com.louislivi.fastdep.shirojwt.jwt.JwtUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.List;


/**
 * 用户控制层
 * @author Akuma
 * @date 2020/4/27 20:52
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 用户登录
     * @param req
     * @return
     */
    @PostMapping("/front/login")
    public ReturnModel postLogin(@RequestBody PostLoginReq req){
        System.out.println("user:" + req.toString());
        User user = new User();
        BeanUtils.copyProperties(req,user);
        // 检验账号密码登录
        User loginUser = userService.loginUser(user);
        //String token = JwtToken.getToken(loginUser.getUId(),loginUser.getUName(),false);
        String token = jwtUtil.sign(String.valueOf(loginUser.getUId()));
        PostLoginRes res = new PostLoginRes(loginUser,token);
        ReturnModel returnModel = new ReturnModel();
        returnModel.setCode(200);
        returnModel.setMsg("ok");
        returnModel.setData(res);
        return returnModel;
    }

    /**
     * 这是两个服务器之间的接口
     * @param req
     * @return
     */
    @PostMapping("/front/connect")
    public ReturnModel postConnect(@RequestBody PostConnectReq req){
        //调用解密方法解密获得uId
        byte[] bytes = ParseSystemUtil.parseHexStr2Byte(req.getUId());
        byte[] decrypt = AESUtil.decrypt(bytes);
        String uId = null;
        try {
            uId = new String(decrypt,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(uId);
        User user = userService.getUser(uId);

        ReturnModel returnModel = new ReturnModel();
        PostConnectRes res = new PostConnectRes();
        if(user == null){
            returnModel.setCode(201);
            returnModel.setMsg("user not exit");
            //returnModel.setData(res);
        }
        else{
            String token = jwtUtil.sign(String.valueOf(uId));
            returnModel.setCode(200);
            returnModel.setMsg("ok");
            res.setToken(token);
        }
        returnModel.setData(res);
        return returnModel;
    }

    /**
     * 获取用户个人信息
     * @return
     */
    @GetMapping("/api/v1/user")
    public ReturnModel getUser(){
        //获取用户信息
        User user = userService.getUser(String.valueOf(jwtUtil.getUserId()));
        GetUserRes getUserRes = new GetUserRes();
        getUserRes.setUser(user);
        ReturnModel returnModel = new ReturnModel();
        returnModel.setCode(200);
        returnModel.setMsg("ok");
        returnModel.setData(getUserRes);
        return returnModel;
    }

    /**
     * 获取除自己外所有用户
     * @return
     */
    @GetMapping("/api/v1/all_user")
    public ReturnModel getAllUser(){
        List<User> userList= userService.queryAllUser(String.valueOf(jwtUtil.getUserId()));
        GetUserListRes getUserListRes = new GetUserListRes();
        getUserListRes.setUserList(userList);
        ReturnModel returnModel = new ReturnModel();
        returnModel.setCode(200);
        returnModel.setMsg("ok");
        returnModel.setData(getUserListRes);
        return returnModel;
    }

}

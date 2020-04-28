package com.akuma.chat.service;

import com.akuma.chat.dao.UserDao;
import com.akuma.chat.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;

/**
 * @author Akuma
 * @date 2020/4/27 20:14
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    /***
     * 用户登录
     * @param user
     * @return
     */
    public User loginUser(User user){
        // 验证用户名和密码
        System.out.println("service" + user.toString());
        User loginUser=userDao.checkUserLogin(user.getUId(),user.getUPasswd());
        if(loginUser == null){
            System.out.println("用户密码错误");
        }else{
            // 用户名密码正确，获取管理员权限
            return loginUser;
        }
        return null;
    }

    /**
     * 查询用户信息
     * @param uId
     * @return
     */
    public User getUser(String uId){
        User user = userDao.queryUserById(uId);
        return user;
    }

    /**
     * 查询用户信息
     * @param uName
     * @return
     */
    public User getUserByName(String uName){
        User user = userDao.queryUserByName(uName);
        return user;
    }

    /**
     * 查询除自己外的所有用户
     * @param uId
     * @return
     */
    public List<User> queryAllUser(String uId){
        List<User> userList = userDao.queryAllUser(uId);
        return userList;
    }

}

package com.akuma.chat.dao;

import com.akuma.chat.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户接口
 * @author Akuma
 * @date 2020/4/27 20:14
 */
@Mapper
@Repository
public interface UserDao {

    /**
     * 查询除自己外所有用户
     * @return
     */
    List<User> queryAllUser(@Param("uId") String uId);


    /***
     * 查询单个用户信息
     * @param uName
     * @return
     */
    User queryUserByName(@Param("uName") String uName);

    /**
     * 查询用户信息
     * @param uId
     * @return
     */
    User queryUserById(@Param("uId") String uId);

    /***
     * 查询用户名和密码
     * @param uId
     * @param uPasswd
     * @return
     */
    User checkUserLogin(@Param("uId") String uId, @Param("uPasswd") String uPasswd);
}

package com.akuma.chat.dao;

import com.akuma.chat.model.entity.Message;
import com.akuma.chat.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 消息接口
 * @author Akuma
 * @date 2020/4/27 20:14
 */
@Mapper
@Repository
public interface MessageDao {

    /***
     * 根据uid和接受者id查询消息
     * @param receiverId
     * @return
     */
    List<Message> getMessage(@Param("uId") String uId, @Param("receiverId") String receiverId);

    /***
     * 发送消息
     * @param message
     * @return
     */
    int insertMessage(Message message);

    /**
     * 修改消息接受状态
     * @param receiverId
     */
    void updateMessage(@Param("receiverId") Integer receiverId);

    /**
     * 根据用户id查询好友列表
     * @param uId
     * @return
     */
    List<User> getMessageUserList(@Param("uId") String uId);

}

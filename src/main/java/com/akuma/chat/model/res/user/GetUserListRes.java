package com.akuma.chat.model.res.user;

import com.akuma.chat.model.entity.User;
import lombok.Data;

import java.util.List;

/**
 * @author Akuma
 * @date 2020/4/28 13:08
 */
@Data
public class GetUserListRes {
    private List<User> userList;
}

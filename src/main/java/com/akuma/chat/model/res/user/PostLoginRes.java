package com.akuma.chat.model.res.user;

import com.akuma.chat.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Akuma
 * @date 2020/4/27 21:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostLoginRes {

    private User user;
    private String token;
}
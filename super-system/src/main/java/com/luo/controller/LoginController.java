package com.luo.controller;

import com.luo.common.UserUtils;
import com.luo.entity.User;
import com.luo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 临时登录使用类
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserUtils userUtils;

    @RequestMapping("/user")
    public User findByUser(String username,String password) throws Exception {
        User user = userMapper.loadUserByUsername(username);
        if(user == null){
            throw new Exception("用户不存在");
        }
        userUtils.setUser(user);
        return user;
    }
}

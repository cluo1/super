package com.luo.controller;

import com.luo.common.UserUtils;
import com.luo.entity.RespBean;
import com.luo.entity.User;
import com.luo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 临时登录使用类
 */
@RestController
public class LoginController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserUtils userUtils;

    @RequestMapping("/login")
    public RespBean findByUser(String username,String password) throws Exception {
        User user = userMapper.loadUserByUsername(username);
        if(user == null){
            return RespBean.ok("用户不存在！", user);
        }

        userUtils.setUser(user);
        return RespBean.ok("登录成功！", user);
    }

    @RequestMapping("/logout")
    public RespBean logout() throws Exception {
        return RespBean.ok("注销成功！");
    }
}

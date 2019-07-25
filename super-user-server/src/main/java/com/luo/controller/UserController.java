package com.luo.controller;

import com.luo.entity.User;
import com.luo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/selectUser")
    public Object selectUser(Model m){
        List<User> users = userService.selectUser();
        m.addAttribute("users",users);
        return "user";
    }

    @RequestMapping(value = "/selectByPrimaryKey")
    public User selectByPrimaryKey(Integer userId){
        return userService.selectByPrimaryKey(userId);
    }
}

package com.luo.controller;

import com.luo.common.UserUtils;
import com.luo.entity.Menu;
import com.luo.entity.User;
import com.luo.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 这是一个只要登录就能访问的Controller
 * 主要用来获取一些配置信息
 */
@RestController
@RequestMapping("/menu")
public class ConfigController {
    @Autowired
    MenuService menuService;
    @RequestMapping("/sysmenu")
    public List<Menu> sysmenu() {
        return menuService.getMenusByUserId();
    }

    @RequestMapping("/user")
    public User currentUser() {
        return UserUtils.getCurrentUser();
    }
}

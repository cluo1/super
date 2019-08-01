package com.luo.service;

import com.luo.entity.Menu;

import java.util.List;

public interface MenuService {

    public List<Menu> getAllMenu();

    public List<Menu> getMenusByUserId();
}

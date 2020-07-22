package com.luo.service;

import com.luo.common.UserUtils;
import com.luo.entity.Menu;
import com.luo.mapper.MenuMapper;
import com.luo.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {

    @Autowired
    MenuMapper menuMapper;

    @Override
    public List<Menu> getAllMenu() {
        return menuMapper.getAllMenu();
    }

    @Override
    public List<Menu> getMenusByUserId() {
        return menuMapper.getMenusByUserId(UserUtils.getCurrentUser().getId());
    }
}

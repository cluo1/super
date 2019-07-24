package com.luo.mapper;


import com.luo.entity.User;

import java.util.List;

public interface UserMapper {

    public List<User> selectUsers();
    public User selectUser(Integer id);

    public void save();
}

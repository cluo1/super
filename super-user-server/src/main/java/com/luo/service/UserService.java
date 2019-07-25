package com.luo.service;

import com.luo.entity.User;

import java.util.List;

public interface UserService {

    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    List<User> selectUser();

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}

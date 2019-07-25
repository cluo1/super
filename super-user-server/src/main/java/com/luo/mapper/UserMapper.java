package com.luo.mapper;

import com.luo.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    List<User> selectUser();

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}
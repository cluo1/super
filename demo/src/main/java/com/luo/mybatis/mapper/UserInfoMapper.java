package com.luo.mybatis.mapper;

import com.luo.mybatis.pojo.UserInfo;

public interface UserInfoMapper {
    int deleteByPrimaryKey(Long userid);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Long userid);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);
}
package com.cluo.mybatis.mapper;

import com.cluo.mybatis.pojo.User;
import org.apache.ibatis.annotations.*;
import org.springframework.transaction.annotation.Transactional;


public interface UserMapper {
    @Results({
            @Result(property = "str",column = "desc")
    })
    @Select("select * from user where userId = #{id}")
    public User selectUser(Integer id);


    @Insert("insert into user(userId)values(11)")
    public void save();
}

package com.luo.mybatis.mapper;

import com.luo.mybatis.pojo.User;
import org.apache.ibatis.annotations.*;


public interface UserMapper {
    @Results({
            @Result(property = "str",column = "desc")
    })
    @Select("select * from user where userId = #{id}")
    public User selectUser(Integer id);


    @Insert("insert into user(userId)values(11)")
    public void save();
}

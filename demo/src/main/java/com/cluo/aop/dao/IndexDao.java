package com.cluo.aop.dao;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Slf4j
@Component
public class IndexDao implements Dao{

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void save() {
        namedParameterJdbcTemplate.update("insert into user(userId)values(7)",new HashMap(){});
        System.out.println(1/0);
    }

    public void query(){
        System.out.println("=====query=====");
    }
}

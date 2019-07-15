package com.cluo.springMybatis;

import com.luo.mybatis.mapper.UserMapper;
import com.luo.mybatis.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/springMybatis/spring-mybatis.xml"})
@Slf4j
public class SpringMybatisTest {
    @Autowired
    UserMapper userMapper;
    @Test
    public void test() throws IOException {
        User user = userMapper.selectUser(1);
//        log.info("user:{}",user);
    }

    @Test
    public void test1(){
        userMapper.save();

    }

}

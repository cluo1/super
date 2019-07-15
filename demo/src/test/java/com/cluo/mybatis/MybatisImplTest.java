package com.cluo.mybatis;


import com.cluo.mybatis.mapper.UserMapper;
import com.cluo.mybatis.session.Configuration;
import com.cluo.mybatis.session.SqlSession;
import com.cluo.mybatis.session.SqlSessionFactory;
import com.cluo.mybatis.session.SqlSessionFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;

@Slf4j
public class MybatisImplTest {
    @Test
    public void test() throws IOException {
        Configuration configuration = new Configuration();
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        SqlSession session = sqlSessionFactory.openSession(configuration);
        UserMapper mapper = session.getMapper(UserMapper.class);
        mapper.selectUser(1);
    }
}

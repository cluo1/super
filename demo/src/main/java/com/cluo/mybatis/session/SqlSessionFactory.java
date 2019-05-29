package com.cluo.mybatis.session;

import com.cluo.mybatis.executor.SimpleExecutor;

public class SqlSessionFactory {
    public SqlSession openSession(Configuration configuration){
        return new DefaultSqlSession(configuration,new SimpleExecutor(configuration));
    }
}

package com.luo.mybatis.session;

import com.luo.mybatis.executor.SimpleExecutor;

public class SqlSessionFactory {
    public SqlSession openSession(Configuration configuration){
        return new DefaultSqlSession(configuration,new SimpleExecutor(configuration));
    }
}

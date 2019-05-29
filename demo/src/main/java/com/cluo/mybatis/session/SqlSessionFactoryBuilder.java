package com.cluo.mybatis.session;


public class SqlSessionFactoryBuilder {
    public SqlSessionFactory build(Configuration configuration) {
        configuration.loadXMLConfigrurations();
        return new SqlSessionFactory();
    }
}

package com.cluo.mybatis.session;


import com.cluo.mybatis.binding.MapperMethod;
import com.cluo.mybatis.executor.Executor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Proxy;

@Slf4j
public class DefaultSqlSession implements SqlSession {
    private Configuration configuration;
    private Executor executor;

    public DefaultSqlSession(Configuration configuration, Executor executor) {
        this.configuration = configuration;
        this.executor = executor;
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class[] { type }, new MapperProxy(this, type));
    }

    @Override
    public <T> T selectOne(MapperMethod mapperMethod, Object[] args) {
        return (T) executor.query(mapperMethod,args);
    }
}

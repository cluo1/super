package com.cluo.mybatis.executor;

import com.cluo.mybatis.binding.MapperMethod;
import com.cluo.mybatis.session.Configuration;

import java.util.List;

public class SimpleExecutor implements Executor {

    private Configuration configuration;

    public SimpleExecutor(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <T> List<T> query(MapperMethod mapperMethod, Object[] args) {
        System.out.println("----query()-----------");
        return null;
    }
}

package com.cluo.mybatis.executor;

import com.cluo.mybatis.binding.MapperMethod;

import java.util.List;

public interface Executor {
    <T> List<T> query(MapperMethod mapperMethod, Object[] args);
}

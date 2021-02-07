package com.luo.mybatis.executor;

import com.luo.mybatis.binding.MapperMethod;

import java.util.List;

public interface Executor {
    <T> List<T> query(MapperMethod mapperMethod, Object[] args);
}

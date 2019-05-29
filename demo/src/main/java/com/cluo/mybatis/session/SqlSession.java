package com.cluo.mybatis.session;

import com.cluo.mybatis.binding.MapperMethod;

public interface SqlSession {
    <T> T selectOne(MapperMethod statement, Object[] args);
    <T> T getMapper(Class<T> type);
}

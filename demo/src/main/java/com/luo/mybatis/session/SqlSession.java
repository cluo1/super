package com.luo.mybatis.session;

import com.luo.mybatis.binding.MapperMethod;

public interface SqlSession {
    <T> T selectOne(MapperMethod statement, Object[] args);
    <T> T getMapper(Class<T> type);
}

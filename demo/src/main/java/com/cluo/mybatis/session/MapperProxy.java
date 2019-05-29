package com.cluo.mybatis.session;

import com.cluo.mybatis.binding.MapperMethod;
import com.cluo.mybatis.binding.MapperRegistry;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MapperProxy<T> implements InvocationHandler {
    private final SqlSession sqlSession;
    private final Class<T> mapperInterface;

    public MapperProxy(SqlSession sqlSession, Class<T> mapperInterface) {
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(mapperInterface .equals(method.getDeclaringClass())  ){
            MapperMethod mapperMethod = new MapperRegistry().getKnownMappers().get(method.getDeclaringClass().getName() + "." + method.getName());
           return sqlSession.selectOne(mapperMethod,args);
        }else {
            return method.invoke(mapperInterface.newInstance(),args);
        }
    }
}

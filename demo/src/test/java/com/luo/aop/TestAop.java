package com.luo.aop;

import com.luo.aop.config.AopConfig;
import com.luo.aop.dao.Dao;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestAop {
    @Test
    public void test(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AopConfig.class);
//        applicationContext.start();
        Dao dao = applicationContext.getBean(Dao.class);
        dao.query();
//        dao.save();

        /*dao = (Dao) Proxy.newProxyInstance(Dao.class.getClassLoader(), new Class[]{Dao.class}, new TestInvocationHandler(new IndexDao()));
        dao.query();*/
    }
}

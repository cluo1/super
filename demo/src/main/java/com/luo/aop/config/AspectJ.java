package com.luo.aop.config;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectJ {
    @Pointcut("execution(* com.luo.aop..*(..))")
    public void pointCut(){ }

    @After("pointCut()")
    public void after(){
        System.out.println("after()");
    }

    @Before("pointCut()")
    public void before(){
        System.out.println("before");
    }
}

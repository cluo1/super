package com.luo.aop.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class AspectJ {
    @Pointcut("execution(* com.luo.aop..*(..))")
    public void pointCut(){ }

    @After("pointCut()")
    public void after(){
        log.info("after()");
    }

    @Before("pointCut()")
    public void before(){
        log.info("before()");
    }
}

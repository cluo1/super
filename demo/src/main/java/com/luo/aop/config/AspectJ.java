package com.luo.aop.config;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectJ {
    Logger log = LogManager.getLogger(AspectJ.class);
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

package com.luo.aop.config;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class AspectJ {
    private static final Logger log = LoggerFactory.getLogger(AspectJ.class);
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

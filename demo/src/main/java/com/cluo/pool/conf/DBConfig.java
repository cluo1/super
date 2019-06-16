package com.cluo.pool.conf;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

@Target({TYPE,METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DBConfig {
     String ip();
     String database();
     int port() default  3306;
     String user();
     String pwd();
     int initSize() default 5;
     int step() default 5;
     int maxSize() default 20;
}

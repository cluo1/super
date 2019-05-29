package com.cluo.redis;

import com.cluo.DemoApplication;
import com.cluo.redis.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class redisTest {

    private static final Logger logger = LoggerFactory.getLogger(RedisUtil.class);
    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void test() {
        redisUtil.set("super", "super");
        redisUtil.setExpire("super", 1);
        logger.info("super:{}", redisUtil.get("super"));
        logger.info("time:{}", redisUtil.getExpire("super"));

        redisUtil.hmSet("super1", new HashMap<String, Object>() {{
            put("01", "01");
            put("02", 01);
        }});

        logger.info("super1:" + redisUtil.hmGet("super1"));

        logger.info("super2:" + redisUtil.incr("super2", 2));
    }

}

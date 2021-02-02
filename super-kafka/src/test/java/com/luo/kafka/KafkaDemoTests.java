package com.luo.kafka;

import com.luo.KafkaApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {KafkaApplication.class})
public class KafkaDemoTests {

    @Autowired
    private KafkaProducer kafkaProducer;
    @Test
    public void send(){
        kafkaProducer.send();
    }
}

package com.luo.kafka;

import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "topic1")
    public void consumer(ConsumerRecord consumerRecord){
        Object value = consumerRecord.value();
        String jsonString = JSONObject.toJSONString(value);
        log.info("jsonStr==========={}",jsonString);
    }
}

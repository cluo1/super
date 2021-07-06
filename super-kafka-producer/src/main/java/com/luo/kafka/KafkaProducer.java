package com.luo.kafka;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.HashMap;
import java.util.Map;

@Component
public class KafkaProducer {
    private static final Logger log = LoggerFactory.getLogger(KafkaProducer.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send1(){
        Map<String,Object> map = new HashMap();
        map.put("super0","super0");
        kafkaTemplate.send("indexMsgTopic", JSONObject.toJSONString(map));
//
//        Map<String,Object> map1 = new HashMap();
//        map1.put("super1","super1");
//        kafkaTemplate.send("topic1",1,"key2", JSONObject.toJSONString(map1));

//        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send("topic1",i,"key1", JSONObject.toJSONString(map));
//        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
//            @Override
//            public void onFailure(Throwable throwable) {
//                log.error("kafka sendMessage error, throwable = {}, topic = {}, data = {}", throwable, "topic1", JSONObject.toJSONString(map));
//            }
//            @Override
//            public void onSuccess(SendResult<String, String> stringDotaHeroSendResult) {
//                log.info("kafka sendMessage success topic = {}, data = {}","topic1",  JSONObject.toJSONString(map));
//            }
//        });
        log.info("kafka sendMessage end");
    }
}

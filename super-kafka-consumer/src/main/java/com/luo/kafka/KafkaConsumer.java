package com.luo.kafka;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@Component
public class KafkaConsumer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "indexMsgTopic"
            ,group = "g1"
            ,containerFactory = "kafkaListenerContainerFactory1"
    )
    public void consumer1(ConsumerRecord consumerRecord){
        Object value = consumerRecord.value();

        LinkedHashMap infoList = JSONObject.parseObject(value+"",new TypeReference<LinkedHashMap>() {});
        logger.info("接收任务索引消息数据开始voiceId:{},ruleId:{}",infoList.get("voiceId"),infoList.get("ruleId"));
    }


    //同一个分区同一个消费组中不同的消费者只能有一个消费者消费 保证顺序消费
    @KafkaListener(topics = "indexMsgTopic"
            ,group = "g1"
//           ,topicPartitions ={@TopicPartition(topic = "indexMsgTopic",partitions = {"0"})}
            ,containerFactory = "kafkaListenerContainerFactory1"
    )
    public void consumer2(ConsumerRecord consumerRecord){
        long offset = consumerRecord.offset();
        String val = JSONObject.toJSONString(consumerRecord.value());
        logger.info("consumer===========offset:{},val:{}",offset,val);
    }
}

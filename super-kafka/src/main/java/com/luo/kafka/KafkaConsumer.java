package com.luo.kafka;

import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topicPartitions ={@TopicPartition(topic = "topic1",partitions = {"0"})}
        ,containerFactory = "kafkaListenerContainerFactory1")
    public void consumer1(ConsumerRecord consumerRecord){
        Object value = consumerRecord.value();
        String jsonString = JSONObject.toJSONString(value);
        log.info("jsonStr1==========={}",jsonString);
    }

    @KafkaListener(topicPartitions ={@TopicPartition(topic = "topic1",partitions = {"1"}
//        ,partitionOffsets = @PartitionOffset(partition = "1", initialOffset = "4")
        )}
        ,containerFactory = "kafkaListenerContainerFactory2"
    )
    public void consumer2(ConsumerRecord consumerRecord){
        Object value = consumerRecord.value();
        String jsonString = JSONObject.toJSONString(value);
        log.info("jsonStr2==========={}",jsonString);
    }

}

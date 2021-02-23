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
        ,containerFactory = "kafkaListenerContainerFactory1"
//        ,groupId = "g1"
    )
    public void consumer1(ConsumerRecord consumerRecord){
        long offset = consumerRecord.offset();
        String val = JSONObject.toJSONString(consumerRecord.value());
        log.info("consumer1===========offset:{},val:{}",offset,val);
    }

    @KafkaListener(topicPartitions ={@TopicPartition(topic = "topic1",partitions = {"0","1"}
//        ,partitionOffsets = @PartitionOffset(partition = "1", initialOffset = "4")
        )}
        ,containerFactory = "kafkaListenerContainerFactory1"
//        ,groupId = "g2"
    )
    public void consumer2(ConsumerRecord consumerRecord){
        long offset = consumerRecord.offset();
        String val = JSONObject.toJSONString(consumerRecord.value());
        log.info("consumer2===========offset:{},val:{}",offset,val);
    }

}

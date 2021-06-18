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
import java.util.List;
import java.util.Map;

@Component
public class KafkaConsumer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "indexMsgTopic")
    public void consumer1(ConsumerRecord consumerRecord){
        long offset = consumerRecord.offset();
        Object value = consumerRecord.value();

        LinkedHashMap infoList = JSONObject.parseObject(value+"",new TypeReference<LinkedHashMap>() {});
        logger.info("接收任务索引消息数据开始voiceId:{},ruleId:{}",infoList.get("voiceId"),infoList.get("ruleId"));

//        logger.info("consumer1===========offset:{},val:{}",offset,infoList);
    }


 /*   @KafkaListener(topicPartitions ={@TopicPartition(topic = "topic1",partitions = {"0","1"}
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
*/
}

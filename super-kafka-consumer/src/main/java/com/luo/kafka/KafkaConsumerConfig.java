package com.luo.kafka;


import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

//@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String brokers;

    private String group1="g1";
    private String group2="g2";

    //创建TopicName为topic1的Topic并设置分区数为8以及副本数为1
//    @Bean//通过bean创建(bean的名字为initialTopic)
//    public NewTopic initialTopic() {
//        return new NewTopic("topic1",8, (short) 1 );
//    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory1() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<String, String>();
        factory.setConsumerFactory(consumerFactory1());
        factory.setConcurrency(4);
        factory.getContainerProperties().setPollTimeout(4000);
        return factory;
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory2() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<String, String>();
        factory.setConsumerFactory(consumerFactory2());
        factory.setConcurrency(4);
        factory.getContainerProperties().setPollTimeout(4000);
        return factory;
    }

        public Map<String, Object> getCommonPropertis() {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");
        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        return properties;
    }
    public ConsumerFactory<String, String> consumerFactory1() {
        Map<String, Object> properties = getCommonPropertis();
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, group1);
        return new DefaultKafkaConsumerFactory<String, String>(properties);
    }

    public ConsumerFactory<String, String> consumerFactory2() {
        Map<String, Object> properties = getCommonPropertis();
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, group2);
        return new DefaultKafkaConsumerFactory<String, String>(properties);
    }
}

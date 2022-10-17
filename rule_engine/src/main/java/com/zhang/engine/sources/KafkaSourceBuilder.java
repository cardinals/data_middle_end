package com.zhang.engine.sources;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.zhang.engine.utils.ConfigConstant;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import java.util.Properties;

public class KafkaSourceBuilder {
    Config config;

    public  KafkaSourceBuilder(){
        config = ConfigFactory.load();
    }

    public  FlinkKafkaConsumer<String> build(String topic){

        Properties props = new Properties();
        props.setProperty(ConfigConstant.BOOTSTRAP_SERVERS,config.getString(ConfigConstant.KAFKA_BOOTSTRAP_SERVERS));
        props.setProperty(ConfigConstant.AUTO_OFFSET_RESET,config.getString(ConfigConstant.KAFKA_AUTO_OFFSET_RESET));
        FlinkKafkaConsumer<String> kafkaConsumer = new FlinkKafkaConsumer<>(topic,new SimpleStringSchema(),props);

        return kafkaConsumer;
    }
}

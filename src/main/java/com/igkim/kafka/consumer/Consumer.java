package com.igkim.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.igkim.kafka.consumer.model.Hdfs;
import com.igkim.kafka.consumer.model.Test;
import com.igkim.kafka.consumer.repository.HdfsRepository;
import com.igkim.kafka.consumer.repository.TestRepository;
import com.igkim.kafka.consumer.utils.yaml.HadoopProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.*;

@Component
@Data
public class Consumer {
    private final HadoopProperties hadoopProperties;
    private final DataSource dataSource;
    @Autowired
    public HdfsRepository hdfsRepository;
    @Autowired
    public TestRepository testRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void topicSubscribe() {
        Logger logger = LoggerFactory.getLogger(Consumer.class);
        KafkaConsumer kafkaConsumer = new KafkaConsumer(kafkaProp());
        List topicList = new ArrayList();
        topicList.add(hadoopProperties.getKafka().getTopic());

        kafkaConsumer.subscribe(topicList);
        logger.info("구독 시작");

        try {
            while (true) {
                ConsumerRecords records = kafkaConsumer.poll(1000);
                for (Object record : records) {
                    logger.info("Data Offset = {}", record);

                    String strRecord = record.toString();
                    String parsedValue = strRecord.substring(strRecord.indexOf("value = ") + 8, strRecord.length() - 1);

                    ObjectMapper objectMapper = new ObjectMapper();
                    Hdfs hdfs = objectMapper.readValue(parsedValue, Hdfs.class);
                    logger.info("Hdfs Object Converting..\n{}", hdfs);
                    try {
                        hdfsRepository.save(hdfs);
                        logger.info("HDFS Status Data Collection Complete");
                    } catch (Exception e) {
                        logger.error(e.toString());
                    }
                }

                kafkaConsumer.commitAsync(new OffsetCommitCallback() {
                    @Override
                    public void onComplete(Map<TopicPartition, OffsetAndMetadata> map, Exception e) {

                    }
                });
            }
        } catch (Exception e) {
            logger.info(e.toString());
        } finally {
            try{
                kafkaConsumer.commitAsync();
            } finally {
                kafkaConsumer.close();
            }
        }

    }


    public Properties kafkaProp() {
        Properties properties = new Properties();
        String kafkaPort = hadoopProperties.getKafka().getPort();
        String bootstrapServer = null;
        List<String> hostList = hadoopProperties.getKafka().getHostname();
        for (String s : hostList) {
            if (!(bootstrapServer == null)) {
                bootstrapServer = bootstrapServer + "," + s + ":" + kafkaPort;
            } else bootstrapServer = s + ":" + kafkaPort;
        }

        properties.put("bootstrap.servers", bootstrapServer);
        properties.put("group.id", hadoopProperties.getKafka().getConsumerGroup());
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        return properties;
    }
}

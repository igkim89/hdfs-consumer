package com.igkim.kafka.consumer;

import com.igkim.kafka.consumer.utils.yaml.HadoopProperties;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@EnableScheduling
@SpringBootApplication
public class ConsumerApplication {


    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

}

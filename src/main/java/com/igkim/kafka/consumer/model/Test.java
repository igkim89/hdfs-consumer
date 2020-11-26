package com.igkim.kafka.consumer.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity(name = "test02")
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long index;

    private String name;

    private Timestamp time;
}

package com.igkim.kafka.consumer.repository;

import com.igkim.kafka.consumer.model.Hdfs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HdfsRepository extends JpaRepository<Hdfs, Integer> {
}

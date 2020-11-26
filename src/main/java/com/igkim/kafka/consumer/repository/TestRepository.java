package com.igkim.kafka.consumer.repository;

import com.igkim.kafka.consumer.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<Test, String> {
}

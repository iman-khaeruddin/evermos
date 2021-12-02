package com.evermos.onlinestore.repository;

import com.evermos.onlinestore.model.entity.Bucket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BucketRepository extends JpaRepository<Bucket, Integer> {
    Bucket findByUserIdAndTransactionIdNull(int userId);
}

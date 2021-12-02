package com.evermos.onlinestore.repository;

import com.evermos.onlinestore.model.entity.BucketItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BucketItemRepository extends JpaRepository<BucketItem, Integer> {
    BucketItem findByBucketItemIdAndBucketUserId(int bucketItemId, int userId);

    BucketItem findByBucketUserIdAndBucketTransactionIdNull(int userId);
}

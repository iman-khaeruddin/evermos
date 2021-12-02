package com.evermos.onlinestore.service;

import com.evermos.onlinestore.model.enums.UpdateQuantityType;
import com.evermos.onlinestore.model.exception.RestClientException;
import com.evermos.onlinestore.model.request.SaveIBucketItemRequest;
import com.evermos.onlinestore.model.response.BucketItemResponse;

public interface BucketService {
    void saveBucketItem(SaveIBucketItemRequest saveIBucketItemRequest) throws RestClientException;

    void removeBucketItem(int userId, int bucketItemId) throws RestClientException;

    void updateQuantity(int userId, int bucketItemId, int quantity, UpdateQuantityType updateQuantityType) throws RestClientException;

    BucketItemResponse getBucketItem(int userId);
}

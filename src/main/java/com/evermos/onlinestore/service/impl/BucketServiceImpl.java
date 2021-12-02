package com.evermos.onlinestore.service.impl;

import com.evermos.onlinestore.model.entity.Bucket;
import com.evermos.onlinestore.model.entity.BucketItem;
import com.evermos.onlinestore.model.entity.Item;
import com.evermos.onlinestore.model.enums.UpdateQuantityType;
import com.evermos.onlinestore.model.enums.YesNoFlag;
import com.evermos.onlinestore.model.exception.RestClientException;
import com.evermos.onlinestore.model.mapper.BucketMapper;
import com.evermos.onlinestore.model.request.SaveIBucketItemRequest;
import com.evermos.onlinestore.model.response.BucketItemResponse;
import com.evermos.onlinestore.model.response.ItemResponse;
import com.evermos.onlinestore.repository.BucketItemRepository;
import com.evermos.onlinestore.repository.BucketRepository;
import com.evermos.onlinestore.repository.ItemRepository;
import com.evermos.onlinestore.repository.elastic.ElasticItemRepository;
import com.evermos.onlinestore.service.BucketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class BucketServiceImpl implements BucketService {

    public static final Logger logger = LoggerFactory.getLogger(BucketServiceImpl.class);

    @Autowired
    private BucketRepository bucketRepository;

    @Autowired
    private BucketItemRepository bucketItemRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ElasticItemRepository elasticItemRepository;

    @Override
    @Transactional
    public void saveBucketItem(SaveIBucketItemRequest saveIBucketItemRequest) throws RestClientException {
        Item item = itemRepository.findById(saveIBucketItemRequest.getItemId()).get();

        if (item.getStock() == BigInteger.ZERO.intValue()) throw new RestClientException(HttpStatus.BAD_REQUEST.value(), "out if stock");

        //insert into bucket and bucket item
        Bucket bucket = bucketRepository.findByUserIdAndTransactionIdNull(saveIBucketItemRequest.getUserId());
        if (bucket == null) bucket = new Bucket();
        bucket.setUserId(saveIBucketItemRequest.getUserId());
        bucket = bucketRepository.save(bucket);

        BucketItem bucketItem = new BucketItem();
        bucketItem.setBucketId(bucket.getBucketId());
        bucketItem.setItemId(saveIBucketItemRequest.getItemId());
        bucketItem.setQuantity(saveIBucketItemRequest.getQuantity());
        bucketItemRepository.save(bucketItem);
    }

    @Override
    @Transactional
    public void removeBucketItem(int userId, int bucketItemId) throws RestClientException {
        BucketItem bucketItem = bucketItemRepository.findByBucketItemIdAndBucketUserId(bucketItemId, userId);
        if (bucketItem == null) throw new RestClientException(HttpStatus.BAD_REQUEST.value(), "bucket item not found");

        //delete bucket item
        bucketItem.setFlagActive(YesNoFlag.n);
        bucketItemRepository.save(bucketItem);
    }

    @Override
    @Transactional
    public void updateQuantity(int userId, int bucketItemId, int quantity, UpdateQuantityType updateQuantityType) throws RestClientException {
        BucketItem bucketItem = bucketItemRepository.findByBucketItemIdAndBucketUserId(bucketItemId, userId);
        if (bucketItem == null) throw new RestClientException(HttpStatus.BAD_REQUEST.value(), "bucket item not found");

        Item item = bucketItem.getItem();

        if (updateQuantityType == UpdateQuantityType.INCREASE){
            if (item.getStock() < (bucketItem.getQuantity() + quantity)) throw new RestClientException(HttpStatus.BAD_REQUEST.value(), "out of stock");
            bucketItem.setQuantity(bucketItem.getQuantity() + quantity);
        } else {
            bucketItem.setQuantity(bucketItem.getQuantity() - quantity);
        }
        bucketItemRepository.save(bucketItem);
    }

    @Override
    public BucketItemResponse getBucketItem(int userId) {
        Bucket bucket = bucketRepository.findByUserIdAndTransactionIdNull(userId);
        if (bucket == null) return new BucketItemResponse();
        BucketItemResponse bucketItemResponse = new BucketItemResponse();
        bucketItemResponse.setBucketId(bucket.getBucketId());
        List<ItemResponse> itemResponses = new ArrayList<>();
        bucket.getBucketItems().stream().forEach(bucketItem -> {
            ItemResponse itemResponse = BucketMapper.BUCKET_MAPPER.toDto(bucketItem.getItem());
            itemResponse.setStock(null);
            itemResponse.setBucketItemId(bucketItem.getBucketItemId());
            itemResponse.setQty(bucketItem.getQuantity());
            itemResponses.add(itemResponse);
        });
        bucketItemResponse.setItems(itemResponses);
        return bucketItemResponse;
    }
}

package com.evermos.onlinestore.service.impl;

import com.evermos.onlinestore.model.constant.RedisKey;
import com.evermos.onlinestore.model.entity.Bucket;
import com.evermos.onlinestore.model.entity.BucketItem;
import com.evermos.onlinestore.model.entity.Item;
import com.evermos.onlinestore.model.entity.Transaction;
import com.evermos.onlinestore.model.exception.RestClientException;
import com.evermos.onlinestore.model.request.PurchaseRequest;
import com.evermos.onlinestore.repository.BucketRepository;
import com.evermos.onlinestore.repository.ItemRepository;
import com.evermos.onlinestore.repository.TransactionRepository;
import com.evermos.onlinestore.repository.elastic.ElasticItemRepository;
import com.evermos.onlinestore.service.PurchaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    public static final Logger logger = LoggerFactory.getLogger(PurchaseServiceImpl.class);

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BucketRepository bucketRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ElasticItemRepository elasticItemRepository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    @Transactional
    public void purchase(PurchaseRequest purchaseRequest) throws RestClientException {
        Bucket bucket = bucketRepository.findById(purchaseRequest.getBucketId()).get();
        for (BucketItem bucketItem : bucket.getBucketItems()){
            Long stock = redisTemplate.opsForValue().decrement(RedisKey.ITEM + bucketItem.getItemId(), bucketItem.getQuantity());
            if (stock == null) throw new RestClientException(HttpStatus.BAD_REQUEST.value(), "out of stock");
            if (stock < 0){
                redisTemplate.opsForValue().increment(RedisKey.ITEM + bucketItem.getItemId(), bucketItem.getQuantity());
                throw new RestClientException(HttpStatus.BAD_REQUEST.value(), "not enough stock");
            }

            //update stock in DB
            Item item = bucketItem.getItem();
            item.setStock(stock.intValue());
            itemRepository.save(item);

            //update stock in elastic
            com.evermos.onlinestore.model.document.Item item1 = elasticItemRepository.findByItemId(bucketItem.getItemId());
            item1.setStock(stock.intValue());
            elasticItemRepository.save(item1);
        }

        Transaction transaction = new Transaction();
        transaction.setTransactionCode(UUID.randomUUID().toString());
        transaction.setUserId(purchaseRequest.getUserId());
        transaction.setPaymentMethod(purchaseRequest.getPaymentMethod());
        transaction = transactionRepository.save(transaction);

        bucket.setTransactionId(transaction.getTransactionId());
        bucketRepository.save(bucket);
    }
}

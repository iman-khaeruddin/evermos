package com.evermos.onlinestore.config;

import com.evermos.onlinestore.model.constant.RedisKey;
import com.evermos.onlinestore.model.entity.Item;
import com.evermos.onlinestore.repository.ItemRepository;
import com.evermos.onlinestore.repository.elastic.ElasticItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class Sync {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ElasticItemRepository elasticItemRepository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @PostConstruct
    void sync(){
        List<Item> items = itemRepository.findAll();
        items.parallelStream().forEach(item -> {
            com.evermos.onlinestore.model.document.Item itm = elasticItemRepository.findByItemId(item.getItemId());
            if (itm == null){
                itm = new com.evermos.onlinestore.model.document.Item();
                itm.setItemId(item.getItemId());
            }
            itm.setStock(item.getStock());
            itm.setDescription(item.getDescription());
            itm.setItemName(item.getItemName());
            itm.setCategoryId(item.getCategoryId());
            itm.setPrice(item.getPrice());
            elasticItemRepository.save(itm);
            redisTemplate.opsForValue().set(RedisKey.ITEM + item.getItemId(), String.valueOf(item.getStock()));
        });
    }
}

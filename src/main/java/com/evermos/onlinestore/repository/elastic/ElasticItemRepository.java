package com.evermos.onlinestore.repository.elastic;

import com.evermos.onlinestore.model.document.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ElasticItemRepository extends ElasticsearchRepository<Item, Integer> {
    Item findByItemId(int itemId);

    Page<Item> findByItemNameLike(String key, Pageable pageable);
}

package com.evermos.onlinestore.service.impl;

import com.evermos.onlinestore.model.constant.RedisKey;
import com.evermos.onlinestore.model.entity.Item;
import com.evermos.onlinestore.model.mapper.ItemMapper;
import com.evermos.onlinestore.model.request.GetAllRequest;
import com.evermos.onlinestore.model.request.ItemRequest;
import com.evermos.onlinestore.model.response.ItemResponse;
import com.evermos.onlinestore.model.response.RestResult;
import com.evermos.onlinestore.repository.CategoriesRepository;
import com.evermos.onlinestore.repository.ItemRepository;
import com.evermos.onlinestore.repository.elastic.ElasticItemRepository;
import com.evermos.onlinestore.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.NoSuchIndexException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    public static final Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private ElasticItemRepository elasticItemRepository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    @Transactional
    public ItemRequest saveItem(ItemRequest itemRequest) {
        //save into database
        Item item = ItemMapper.ITEM_MAPPER.toEntity(itemRequest);
        item = itemRepository.save(item);

        //save into elastic
        itemRequest = ItemMapper.ITEM_MAPPER.toDto(item);
        com.evermos.onlinestore.model.document.Item itm = ItemMapper.ITEM_MAPPER.toDocumentItem(item);
        elasticItemRepository.save(itm);

        //save into redis
        redisTemplate.opsForValue().set(RedisKey.ITEM + item.getItemId(), String.valueOf(itemRequest.getStock()));

        return itemRequest;
    }

    @Override
    public RestResult<List<ItemResponse>> getAll(GetAllRequest getAllRequest) {
        RestResult<List<ItemResponse>> result = new RestResult<>();
        Map<String, Object> metaData = new HashMap<>();
        Sort sort = null;
        if (getAllRequest.getSort() == com.evermos.onlinestore.model.enums.Sort.ASC) sort = Sort.by(getAllRequest.getSortBy().name()).ascending();
        if (getAllRequest.getSort() == com.evermos.onlinestore.model.enums.Sort.DESC) sort = Sort.by(getAllRequest.getSortBy().name()).descending();
        Pageable pageable = PageRequest.of(getAllRequest.getOffset(), getAllRequest.getLimit(), sort);
        Page<com.evermos.onlinestore.model.document.Item> item = null;
        try {
            if (getAllRequest.getKey() != null) item = elasticItemRepository.findByItemNameLike(getAllRequest.getKey(), pageable);
            else item = elasticItemRepository.findAll(pageable);
            result.setData(ItemMapper.ITEM_MAPPER.toDtos(item.get().collect(Collectors.toList())));
            metaData.put("offset", getAllRequest.getOffset());
            metaData.put("limit", getAllRequest.getLimit());
            metaData.put("totalElement", item.getTotalElements());
            metaData.put("totalPage", item.getTotalPages());
            result.setMetaData(metaData);
        }catch (NoSuchIndexException e){
            metaData.put("offset", getAllRequest.getOffset());
            metaData.put("limit", getAllRequest.getLimit());
            metaData.put("totalElement", 0);
            metaData.put("totalPage", 0);
            result.setMetaData(metaData);
        }
        return result;
    }

    @Override
    public void deleteAll() {
        elasticItemRepository.deleteAll();
    }

    @Override
    public ItemResponse findById(int id) {
        com.evermos.onlinestore.model.document.Item item = elasticItemRepository.findByItemId(id);
        return ItemMapper.ITEM_MAPPER.toDto(item);
    }

}

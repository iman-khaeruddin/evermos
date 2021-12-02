package com.evermos.onlinestore.service;

import com.evermos.onlinestore.model.request.GetAllRequest;
import com.evermos.onlinestore.model.request.ItemRequest;
import com.evermos.onlinestore.model.request.SaveIBucketItemRequest;
import com.evermos.onlinestore.model.response.ItemResponse;
import com.evermos.onlinestore.model.response.RestResult;

import java.util.List;

public interface ItemService {
    ItemRequest saveItem(ItemRequest itemRequest);

    RestResult<List<ItemResponse>> getAll(GetAllRequest getAllRequest);

    void deleteAll();

    ItemResponse findById(int id);
}

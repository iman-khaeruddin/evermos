package com.evermos.onlinestore.model.response;

import com.evermos.onlinestore.model.request.ItemRequest;
import lombok.Data;

import java.util.List;

@Data
public class BucketItemResponse {
    private int bucketId;
    private List<ItemResponse> items;
}

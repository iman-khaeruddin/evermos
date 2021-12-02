package com.evermos.onlinestore.model.request;

import lombok.Data;

@Data
public class ItemRequest {
    private Integer itemId;
    private Integer categoryId;
    private String itemName;
    private Integer price;
    private int stock;
    private String description;
}

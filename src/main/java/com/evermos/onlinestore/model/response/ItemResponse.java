package com.evermos.onlinestore.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class ItemResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer bucketItemId;
    private Integer itemId;
    private String itemName;
    private Integer price;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer stock;
    private String description;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer qty;
}

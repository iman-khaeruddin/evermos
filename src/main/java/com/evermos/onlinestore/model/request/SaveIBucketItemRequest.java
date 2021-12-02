package com.evermos.onlinestore.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class SaveIBucketItemRequest {
    private Integer itemId;
    private int quantity;
    @JsonIgnore
    private int userId;
}

package com.evermos.onlinestore.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class PurchaseRequest {
    private Integer bucketId;
    private String paymentMethod;
    @JsonIgnore
    private int userId;
}

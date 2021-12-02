package com.evermos.onlinestore.service;

import com.evermos.onlinestore.model.exception.RestClientException;
import com.evermos.onlinestore.model.request.PurchaseRequest;

public interface PurchaseService {
    void purchase(PurchaseRequest purchaseRequest) throws RestClientException;
}

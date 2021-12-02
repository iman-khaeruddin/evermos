package com.evermos.onlinestore.controller;

import com.evermos.onlinestore.model.enums.UpdateQuantityType;
import com.evermos.onlinestore.model.exception.RestClientException;
import com.evermos.onlinestore.model.request.ItemRequest;
import com.evermos.onlinestore.model.request.SaveIBucketItemRequest;
import com.evermos.onlinestore.model.response.BucketItemResponse;
import com.evermos.onlinestore.model.response.RestResult;
import com.evermos.onlinestore.service.BucketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bucket")
@Api(value = "bucket")
public class BucketController extends AbstractRestController{

    public static final Logger logger = LoggerFactory.getLogger(BucketController.class);

    @Autowired
    private BucketService bucketService;

    @PostMapping("/add")
    @ApiOperation(value = "add item into bucket", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResult insertItem(@RequestHeader("user_id")int userId, @RequestBody SaveIBucketItemRequest saveIBucketItemRequest) throws RestClientException {
        saveIBucketItemRequest.setUserId(userId);
        bucketService.saveBucketItem(saveIBucketItemRequest);
        RestResult<ItemRequest> result = new RestResult<>();
        result.ok("success");
        return result;
    }

    @PostMapping("/remove")
    @ApiOperation(value = "remove item from bucket", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResult removeItem(@RequestHeader("user_id")int userId, @RequestParam("bucketItemId") int bucketItemId) throws RestClientException {
        bucketService.removeBucketItem(userId, bucketItemId);
        RestResult<ItemRequest> result = new RestResult<>();
        result.ok("success");
        return result;
    }

    @PostMapping("/update")
    @ApiOperation(value = "increase/decrease quantity item in bucket", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResult updateQtyInBucket(@RequestHeader("user_id")int userId, @RequestParam("bucketItemId") int bucketItemId, @RequestParam("qty") int quantity, @RequestParam("type") UpdateQuantityType updateQuantityType) throws RestClientException {
        bucketService.updateQuantity(userId, bucketItemId, quantity, updateQuantityType);
        RestResult<ItemRequest> result = new RestResult<>();
        result.ok("success");
        return result;
    }

    @GetMapping
    @ApiOperation(value = "get bucket item list", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResult<BucketItemResponse> getBucketItem(@RequestHeader("user_id")int userId) throws RestClientException {
        BucketItemResponse bucketItemResponse = bucketService.getBucketItem(userId);
        RestResult<BucketItemResponse> result = new RestResult<>();
        result.setData(bucketItemResponse);
        return result;
    }
}

package com.evermos.onlinestore.controller;

import com.evermos.onlinestore.model.exception.RestClientException;
import com.evermos.onlinestore.model.request.PurchaseRequest;
import com.evermos.onlinestore.model.response.RestResult;
import com.evermos.onlinestore.service.PurchaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/purchase")
@Api(value = "purchase")
public class PurchaseController extends AbstractRestController{
  
  public static final Logger logger = LoggerFactory.getLogger(PurchaseController.class);

  @Autowired
  private PurchaseService purchaseService;
  
  @PostMapping
  @ApiOperation(value = "checkout bucket item list", produces = MediaType.APPLICATION_JSON_VALUE)
  public RestResult purchase(@RequestHeader("user_id")int userId, @RequestBody PurchaseRequest purchaseRequest) throws RestClientException {
    RestResult result = new RestResult();
    purchaseRequest.setUserId(userId);
    purchaseService.purchase(purchaseRequest);
    return result;
  }
  
}

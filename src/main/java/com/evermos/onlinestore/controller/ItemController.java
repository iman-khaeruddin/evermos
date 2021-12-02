package com.evermos.onlinestore.controller;

import com.evermos.onlinestore.model.request.GetAllRequest;
import com.evermos.onlinestore.model.request.ItemRequest;
import com.evermos.onlinestore.model.response.ItemResponse;
import com.evermos.onlinestore.model.response.RestResult;
import com.evermos.onlinestore.service.ItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item")
@Api(value = "item")
public class ItemController extends AbstractRestController{

    public static final Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private ItemService itemService;

    @PostMapping
    @ApiOperation(value = "save item", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResult<ItemRequest> insertItem(@RequestBody ItemRequest itemRequest) {
        itemRequest = itemService.saveItem(itemRequest);
        RestResult<ItemRequest> result = new RestResult<>();
        result.setData(itemRequest);
        return result;
    }

    @GetMapping
    @ApiOperation(value = "get all", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResult<List<ItemResponse>> getAll(GetAllRequest getAllRequest) {
        RestResult<List<ItemResponse>> result = itemService.getAll(getAllRequest);
        return result;
    }

    @GetMapping("/detail/{id}")
    @ApiOperation(value = "find by id", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResult<ItemResponse> findById(@PathVariable("id")int id) {
        ItemResponse itemResponses = itemService.findById(id);
        RestResult<ItemResponse> result = new RestResult<>();
        result.setData(itemResponses);
        return result;
    }

    @DeleteMapping
    @ApiOperation(value = "delete all item", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResult<Void> deleteAllItem() {
        itemService.deleteAll();
        return new RestResult<>();
    }
}

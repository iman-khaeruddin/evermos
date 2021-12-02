package com.evermos.onlinestore.model.request;

import com.evermos.onlinestore.model.enums.GetAllSortBy;
import lombok.Data;

@Data
public class GetAllRequest extends SearchRequest{
    private GetAllSortBy sortBy = GetAllSortBy.itemId;
}

package com.evermos.onlinestore.model.request;

import com.evermos.onlinestore.model.enums.Sort;
import lombok.Data;

@Data
public class SearchRequest {
    private int offset = 0;
    private int limit = 10;
    private String key;
    private Sort sort = Sort.DESC;
}

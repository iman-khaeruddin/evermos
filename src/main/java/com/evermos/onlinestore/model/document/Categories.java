package com.evermos.onlinestore.model.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "categories")
public class Categories {

    @Id
    private Integer categoryId;

    private String categoryName;
}

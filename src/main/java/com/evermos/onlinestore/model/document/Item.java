package com.evermos.onlinestore.model.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "item")
@Data
public class Item {

    @Id
    private String id;

    private Integer itemId;

    private int categoryId;

    @Field(type = FieldType.Nested, includeInParent = true)
    private Categories categories;

    private String itemName;

    private Integer price;

    private int stock;

    private String description;
}

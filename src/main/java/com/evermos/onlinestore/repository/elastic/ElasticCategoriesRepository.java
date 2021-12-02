package com.evermos.onlinestore.repository.elastic;

import com.evermos.onlinestore.model.document.Categories;
import com.evermos.onlinestore.model.document.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ElasticCategoriesRepository extends ElasticsearchRepository<Categories, Integer> {
}

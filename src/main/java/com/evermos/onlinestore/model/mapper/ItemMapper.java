package com.evermos.onlinestore.model.mapper;

import com.evermos.onlinestore.model.document.Categories;
import com.evermos.onlinestore.model.entity.Item;
import com.evermos.onlinestore.model.request.ItemRequest;
import com.evermos.onlinestore.model.response.ItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ItemMapper {
    ItemMapper ITEM_MAPPER = Mappers.getMapper(ItemMapper.class);

    Item toEntity(ItemRequest itemRequest);
    ItemRequest toDto(Item item);
    List<ItemResponse> toDtos(List<com.evermos.onlinestore.model.document.Item> item);
    ItemResponse toDto(com.evermos.onlinestore.model.document.Item item);
    com.evermos.onlinestore.model.document.Item toDocumentItem(Item item);
    List<com.evermos.onlinestore.model.document.Item> toListDocumentItem(List<Item> item);
    Categories toDocumentCategories(com.evermos.onlinestore.model.entity.Categories categories);
}

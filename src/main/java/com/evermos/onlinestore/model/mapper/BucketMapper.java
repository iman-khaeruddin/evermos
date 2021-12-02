package com.evermos.onlinestore.model.mapper;

import com.evermos.onlinestore.model.entity.Item;
import com.evermos.onlinestore.model.response.ItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BucketMapper {
    BucketMapper BUCKET_MAPPER = Mappers.getMapper(BucketMapper.class);

    @Mapping(target = "stock", ignore = true)
    ItemResponse toDto(Item item);
}

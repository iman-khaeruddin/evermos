package com.evermos.onlinestore.repository;

import com.evermos.onlinestore.model.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
}

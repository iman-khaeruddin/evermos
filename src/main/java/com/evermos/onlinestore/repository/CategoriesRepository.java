package com.evermos.onlinestore.repository;

import com.evermos.onlinestore.model.entity.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepository extends JpaRepository<Categories, Integer> {
}

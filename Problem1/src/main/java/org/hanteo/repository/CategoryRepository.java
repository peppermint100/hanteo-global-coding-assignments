package org.hanteo.repository;

import org.hanteo.domain.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    void save(Category category);
    Optional<Category> findByCategoryId(long categoryId);
    List<Category> findByCategoryName(String categoryName);
}

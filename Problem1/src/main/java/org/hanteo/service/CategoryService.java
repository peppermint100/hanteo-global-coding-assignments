package org.hanteo.service;

import org.hanteo.domain.Category;

import java.util.List;

public interface CategoryService {
    void addNewCategory(Category category);

    Category getByCategoryId(long categoryId);
    List<Category> getByCategoryName(String categoryName);
}

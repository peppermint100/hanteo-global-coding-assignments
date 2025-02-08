package org.hanteo.repository;

import org.hanteo.domain.Category;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryCategoryRepository implements CategoryRepository {

    private final Map<Long, Category> records = new HashMap<>();

    @Override
    public void save(Category category) {
        records.put(category.getId(), category);
    }

    @Override
    public Optional<Category> findByCategoryId(long categoryId) {
        return Optional.ofNullable(records.get(categoryId));
    }

    // categoryName을 포함한 모든 Category를 가져온다.
    @Override
    public List<Category> findByCategoryName(String categoryName) {
        return records.values()
                .stream()
                .filter(category -> category.getName().contains(categoryName))
                .toList();
    }
}

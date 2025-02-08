package org.hanteo.repository;

import org.hanteo.domain.CategoryRelation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryCategoryRelationRepository implements CategoryRelationRepository {

    private final Map<Long, List<Long>> records = new HashMap<>();

    @Override
    public void save(CategoryRelation categoryRelation) {
        // records에 parentId로 키가 없으면 빈 리스트로 설정하고 childId를 추가한다.
        records.computeIfAbsent(categoryRelation.getParentId(), valueIfAbsent -> new ArrayList<>())
                .add(categoryRelation.getChildId());
    }

    @Override
    public List<CategoryRelation> findByParentId(long parentId) {
        return records.getOrDefault(parentId, List.of())
                .stream()
                .map(childId -> CategoryRelation.of(parentId, childId))
                .toList();
    }
}

package org.hanteo.repository;

import org.hanteo.domain.CategoryRelation;

import java.util.List;

public interface CategoryRelationRepository {
    void save(CategoryRelation categoryRelation);
    List<CategoryRelation> findByParentId(long parentId);
}

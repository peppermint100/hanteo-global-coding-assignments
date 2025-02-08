package org.hanteo.domain;


public class CategoryRelation {
    private Long parentId;
    private Long childId;

    private CategoryRelation(long childId, long parentId) {
        this.childId = childId;
        this.parentId = parentId;
    }

    public long getChildId() { return childId; }
    public long getParentId() { return parentId; }

    public static CategoryRelation of(long parentId, long childId) {
        return new CategoryRelation(parentId, childId);
    }
}

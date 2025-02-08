package org.hanteo.service;

import org.hanteo.domain.Category;
import org.hanteo.domain.CategoryRelation;
import org.hanteo.exception.CategoryException;
import org.hanteo.repository.CategoryRelationRepository;
import org.hanteo.repository.CategoryRepository;

import java.util.*;

public class InMemoryCategoryService implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryRelationRepository categoryRelationRepository;

    public InMemoryCategoryService(
            CategoryRepository categoryRepository,
            CategoryRelationRepository categoryRelationRepository) {
        this.categoryRepository = categoryRepository;
        this.categoryRelationRepository = categoryRelationRepository;
    }

    @Override
    public void addNewCategory(Category category) {
        Stack<Category> stack = new Stack<>();
        stack.push(category);

        // Stack을 활용하여 DFS 방식으로 하위 카테고리를 전부 추가한다.
        while (!stack.isEmpty()) {
            Category current = stack.pop();
            categoryRepository.save(current);

            for (Category child : current.getChildren()) {
                CategoryRelation categoryRelation = CategoryRelation.of(current.getId(), child.getId());
                categoryRelationRepository.save(categoryRelation);
                stack.push(child);
            }
        }
    }

    @Override
    public Category getByCategoryId(long categoryId) {
        Category category = categoryRepository.findByCategoryId(categoryId)
                .orElseThrow(() -> new CategoryException("존재하지 않는 카테고리입니다."));

        buildCategory(category);

        return category;
    }

    @Override
    public List<Category> getByCategoryName(String categoryName) {
        List<Category> categories = categoryRepository.findByCategoryName(categoryName);
        for (Category category : categories) {
            buildCategory(category);
        }
        return categories;
    }

    // Stack을 활용하여 DFS 방식으로 하위 카테고리를 전부 추가한다.
    private void buildCategory(Category root) {
        Stack<Category> stack = new Stack<>();
        Map<Long, Category> categoryMap = new HashMap<>();

        // 최상위 카테고리 설정
        stack.push(root);
        categoryMap.put(root.getId(), root);

        while (!stack.isEmpty()) {
            Category current = stack.pop();
            Set<Long> visited = categoryMap.keySet();

            // 익명 게시판은 중복된 카테고리이므로 무한 루프에 빠지지 않도록 categoryMap의 키를 통해서 걸러준다.
            if (visited.contains(current.getId())) {
                continue;
            }

            List<CategoryRelation> categoryRelations = categoryRelationRepository.findByParentId(current.getId());

            // 하위 카테고리들을 전부 스택에 추가한다.
            for (CategoryRelation relation : categoryRelations) {
                Optional<Category> childOptional = categoryRepository.findByCategoryId(relation.getChildId());
                if (childOptional.isPresent()) {
                    Category child = childOptional.get();
                    stack.push(child);
                    current.addChild(child);
                }
            }
        }
    }
}

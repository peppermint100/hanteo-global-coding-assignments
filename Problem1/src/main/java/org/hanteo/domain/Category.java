package org.hanteo.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private Long id;
    private String name;
    private List<Category> children;

    private Category(Long id, String name) {
        this.id = id;
        this.name = name;
        this.children = new ArrayList<>();
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public List<Category> getChildren() { return children; }

    public static Category of(long id, String name) {
        return new Category(id, name);
    }

    public void addChild(Category category) {
        children.add(category);
    }

    public String toJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

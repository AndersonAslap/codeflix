package com.codeflix.admin.catalogo.domain.category;

import com.codeflix.admin.catalogo.domain.AggregateRoot;
import java.time.Instant;

public class Category extends AggregateRoot<CategoryId> {

    private String name;
    private String description;
    private boolean isActive;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    private Category(
            CategoryId id,
            String name,
            String description,
            boolean isActive,
            Instant createdAt,
            Instant updatedAt,
            Instant deletedAt
    ) {
        super(id);
        this.name = name;
        this.description = description;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static Category newCategory(
            final String name,
            final String description,
            final boolean isActive
    ) {
        return  new Category(
                CategoryId.unique(),
                name,
                description,
                isActive,
                Instant.now(),
                Instant.now(),
                null
        );
    }

    public CategoryId getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public boolean isActive() {
        return isActive;
    }
}

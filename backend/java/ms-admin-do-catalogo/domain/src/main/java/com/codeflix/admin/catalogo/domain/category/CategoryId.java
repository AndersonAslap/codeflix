package com.codeflix.admin.catalogo.domain.category;

import com.codeflix.admin.catalogo.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class CategoryId extends Identifier {

    private final String id;

    private CategoryId(final String id) {
        Objects.requireNonNull(id, "'id' should not be null");
        this.id = id;
    }

    public static CategoryId unique() {
        return CategoryId.from(UUID.randomUUID());
    }

    public static CategoryId from(final UUID id) {
        return CategoryId.from(id.toString());
    }

    public static CategoryId from(final String id) {
        return new CategoryId(id.toLowerCase());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CategoryId that = (CategoryId) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

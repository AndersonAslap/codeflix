package com.codeflix.admin.catalogo.application.category.create;

import com.codeflix.admin.catalogo.domain.category.Category;

public record CreateCategoryOutput(String categoryId) {

    public static CreateCategoryOutput from(final String categoryId) {
        return new CreateCategoryOutput(categoryId);
    }

    public static CreateCategoryOutput from(final Category category) {
        return new CreateCategoryOutput(category.getId().getValue());
    }
}

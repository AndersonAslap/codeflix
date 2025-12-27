package com.codeflix.admin.catalogo.application.category.retrieve.get;

import com.codeflix.admin.catalogo.domain.category.Category;
import com.codeflix.admin.catalogo.domain.category.CategoryId;

public record GetCategoryByIdOutput(
        CategoryId categoryId,
        String name,
        String description,
        boolean isActive
) {

    public static GetCategoryByIdOutput from(final Category category) {
        return new GetCategoryByIdOutput(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.isActive()
        );
    }
}

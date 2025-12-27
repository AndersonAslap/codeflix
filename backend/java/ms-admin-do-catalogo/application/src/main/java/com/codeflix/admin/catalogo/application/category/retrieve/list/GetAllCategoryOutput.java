package com.codeflix.admin.catalogo.application.category.retrieve.list;

import com.codeflix.admin.catalogo.domain.category.Category;
import com.codeflix.admin.catalogo.domain.category.CategoryId;

public record GetAllCategoryOutput(
        CategoryId categoryId,
        String name,
        String description
) {

    public static GetAllCategoryOutput from(Category category) {
        return new GetAllCategoryOutput(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }
}

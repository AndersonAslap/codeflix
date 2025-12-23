package com.codeflix.admin.catalogo.application.category.create;

import com.codeflix.admin.catalogo.domain.category.Category;
import com.codeflix.admin.catalogo.domain.category.CategoryId;

public record CreateCategoryOutput(CategoryId categoryId) {

    public static CreateCategoryOutput from(final Category category) {
        return new CreateCategoryOutput(category.getId());
    }
}

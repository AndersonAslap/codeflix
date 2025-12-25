package com.codeflix.admin.catalogo.application.category.update;

public record UpdateCategoryCommand(
        String categoryId,
        String name,
        String description,
        boolean isActive
) {

    public static UpdateCategoryCommand with(
            final String categoryId,
            final String name,
            final String description,
            final boolean isActive
    ) {
        return new UpdateCategoryCommand(categoryId, name, description, isActive);
    }
}

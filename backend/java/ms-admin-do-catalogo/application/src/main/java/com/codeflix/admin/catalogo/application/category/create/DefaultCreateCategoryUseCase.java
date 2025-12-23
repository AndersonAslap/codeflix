package com.codeflix.admin.catalogo.application.category.create;

import com.codeflix.admin.catalogo.domain.category.Category;
import com.codeflix.admin.catalogo.domain.category.CategoryGateway;
import com.codeflix.admin.catalogo.domain.validation.handler.ThrowsValidationHandler;

public class DefaultCreateCategoryUseCase extends CreateCategoryUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultCreateCategoryUseCase(CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Override
    public CreateCategoryOutput execute(CreateCategoryCommand command) {
        Category category = Category.newCategory(
                command.name(),
                command.description(),
                command.isActive()
        );
        category.validate(new ThrowsValidationHandler());
        this.categoryGateway.create(category);
        return CreateCategoryOutput.from(category);
    }
}

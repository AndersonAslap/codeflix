package com.codeflix.admin.catalogo.application.category.create;

import com.codeflix.admin.catalogo.domain.category.Category;
import com.codeflix.admin.catalogo.domain.category.CategoryGateway;
import com.codeflix.admin.catalogo.domain.validation.handler.NotificationValidationHandler;
import io.vavr.control.Either;

import static io.vavr.API.Left;
import static io.vavr.API.Try;

public class DefaultCreateCategoryUseCase extends CreateCategoryUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultCreateCategoryUseCase(CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Override
    public Either<NotificationValidationHandler, CreateCategoryOutput> execute(CreateCategoryCommand command) {
        Category category = Category.newCategory(
                command.name(),
                command.description(),
                command.isActive()
        );
        NotificationValidationHandler notification = NotificationValidationHandler.create();
        category.validate(notification);
        return notification.hasErrors() ? Left(notification) : this.create(category);
    }

    private Either<NotificationValidationHandler, CreateCategoryOutput> create(Category category) {
        return Try(() -> this.categoryGateway.create(category))
                .toEither()
                .bimap(NotificationValidationHandler::create, CreateCategoryOutput::from);
    }
}

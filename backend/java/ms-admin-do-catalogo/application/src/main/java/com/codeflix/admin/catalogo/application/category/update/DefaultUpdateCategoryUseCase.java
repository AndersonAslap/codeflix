package com.codeflix.admin.catalogo.application.category.update;

import com.codeflix.admin.catalogo.domain.category.Category;
import com.codeflix.admin.catalogo.domain.category.CategoryGateway;
import com.codeflix.admin.catalogo.domain.category.CategoryId;
import com.codeflix.admin.catalogo.domain.exceptions.DomainException;
import com.codeflix.admin.catalogo.domain.validation.Error;
import com.codeflix.admin.catalogo.domain.validation.handler.NotificationValidationHandler;
import io.vavr.control.Either;

import java.util.Objects;
import java.util.function.Supplier;

import static io.vavr.API.Left;
import static io.vavr.API.Try;

public class DefaultUpdateCategoryUseCase extends UpdateCategoryUseCase {

    private CategoryGateway categoryGateway;

    public DefaultUpdateCategoryUseCase(CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Either<NotificationValidationHandler, UpdateCategoryOutput> execute(UpdateCategoryCommand command) {
        final var categoryId = CategoryId.from(command.categoryId());

        final var category = this.categoryGateway.findById(categoryId)
                .orElseThrow(notFound(categoryId));

        final var notification = NotificationValidationHandler.create();
        category
                .update(command.name(), command.description(), command.isActive())
                .validate(notification);

        return notification.hasErrors() ? Left(notification) : this.update(category);
    }

    private Either<NotificationValidationHandler, UpdateCategoryOutput> update(Category category) {
        return Try(() -> this.categoryGateway.update(category))
                .toEither()
                .bimap(NotificationValidationHandler::create, UpdateCategoryOutput::from);
    }

    private Supplier<DomainException> notFound(final CategoryId categoryId) {
        return () -> DomainException.with(
                new Error("Category with ID %s was not found".formatted(categoryId.getValue()))
        );
    }
}
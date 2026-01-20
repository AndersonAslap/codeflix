package com.codeflix.admin.catalogo.infrastructure.api.controllers;

import com.codeflix.admin.catalogo.application.category.create.CreateCategoryCommand;
import com.codeflix.admin.catalogo.application.category.create.CreateCategoryOutput;
import com.codeflix.admin.catalogo.application.category.create.CreateCategoryUseCase;
import com.codeflix.admin.catalogo.domain.pagination.Pagination;
import com.codeflix.admin.catalogo.domain.validation.handler.NotificationValidationHandler;
import com.codeflix.admin.catalogo.infrastructure.api.CategoryAPI;
import com.codeflix.admin.catalogo.infrastructure.category.models.CreateCategoryApiInput;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.function.Function;

@RestController
public class CategoryController implements CategoryAPI {

    private final CreateCategoryUseCase createCategoryUseCase;

    public CategoryController(final CreateCategoryUseCase createCategoryUseCase) {
        this.createCategoryUseCase = createCategoryUseCase;
    }

    @Override
    public ResponseEntity<?> createCategory(final CreateCategoryApiInput input) {
        final var command = CreateCategoryCommand.with(
                input.name(),
                input.description(),
                input.isActive() != null ? input.isActive() : true
        );

        final Function<NotificationValidationHandler, ResponseEntity<?>> onError =
                notification -> ResponseEntity.unprocessableEntity().body(notification);

        final Function<CreateCategoryOutput, ResponseEntity<?>> onSuccess =
                output -> ResponseEntity.created(URI.create("/api/categories/" + output.categoryId())).build();

        return this.createCategoryUseCase.execute(command)
                .fold(onError, onSuccess);
    }

    @Override
    public Pagination<?> listCategories(String search, int page, int perPage, String sort, String direction) {
        return null;
    }
}

package com.codeflix.admin.catalogo.application.category.update;

import com.codeflix.admin.catalogo.application.UseCase;
import com.codeflix.admin.catalogo.domain.validation.handler.NotificationValidationHandler;
import io.vavr.control.Either;

public abstract class UpdateCategoryUseCase
        extends UseCase<UpdateCategoryCommand, Either<NotificationValidationHandler, UpdateCategoryOutput>> {
}

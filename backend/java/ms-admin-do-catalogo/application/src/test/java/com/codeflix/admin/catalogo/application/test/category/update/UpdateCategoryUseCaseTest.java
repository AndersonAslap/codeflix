package com.codeflix.admin.catalogo.application.test.category.update;

import com.codeflix.admin.catalogo.application.category.update.DefaultUpdateCategoryUseCase;
import com.codeflix.admin.catalogo.application.category.update.UpdateCategoryCommand;
import com.codeflix.admin.catalogo.domain.category.Category;
import com.codeflix.admin.catalogo.domain.category.CategoryGateway;
import com.codeflix.admin.catalogo.domain.category.CategoryId;
import com.codeflix.admin.catalogo.domain.exceptions.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateCategoryUseCaseTest {

    @InjectMocks
    private DefaultUpdateCategoryUseCase updateCategoryUseCase;

    @Mock
    private CategoryGateway categoryGateway;

    // 1. Teste do caminho feliz
    // 2. Teste passando uma propriedade inválida (name)
    // 3. Teste criando uma categoria inativa
    // 4. Teste simulando um erro generico vindo do gateway
    // 5. Teste atualizar categoria passando ID inválido

    @Test
    public void givenAValidCommand_whenCallsUpdateCategory_shouldReturnCategoryId() {
        final var category = Category.newCategory("Filme", null, true);

        final var expectedName = "Documentário";
        final var expectedDescription = "A categoria menos assistida";
        final var expectedIsActive = true;
        final var expectedId = category.getId();

        final var commandUpdateCategory = UpdateCategoryCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        when(this.categoryGateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(Category.with(category)));

        when(this.categoryGateway.update(any()))
            .thenAnswer(returnsFirstArg());

        final var categoryUpdated = this.updateCategoryUseCase.execute(commandUpdateCategory).get();

        Assertions.assertNotNull(categoryUpdated);
        Assertions.assertNotNull(categoryUpdated.categoryId());

        Mockito.verify(this.categoryGateway, times(1)).findById(eq(expectedId));

        Mockito.verify(categoryGateway, times(1)).update(argThat(
                aCategoryUpdated ->
                        Objects.equals(expectedName, aCategoryUpdated.getName())
                                && Objects.equals(expectedDescription, aCategoryUpdated.getDescription())
                                && Objects.equals(expectedIsActive, aCategoryUpdated.isActive())
                                && Objects.equals(expectedId, aCategoryUpdated.getId())
                                && Objects.equals(category.getCreatedAt(), aCategoryUpdated.getCreatedAt())
                                && category.getUpdatedAt().isBefore(aCategoryUpdated.getUpdatedAt())
                                && Objects.isNull(aCategoryUpdated.getDeletedAt())
        ));
    }

    @Test
    public void givenAInvalidName_whenCallUpdateCategory_shouldReturnError() {
        final var category = Category.newCategory("Filme", null, true);

        final String expectedName = null;
        final var expectedDescription = "A categoria menos assistida";
        final var expectedIsActive = true;
        final var expectedId = category.getId();
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        final var commandUpdateCategory = UpdateCategoryCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        when(this.categoryGateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(Category.with(category)));

        final var notification = this.updateCategoryUseCase.execute(commandUpdateCategory).getLeft();

        Assertions.assertNotNull(notification);
        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());

        Mockito.verify(this.categoryGateway, times(1)).findById(eq(expectedId));
    }

    @Test
    public void givenAValidCommandWithInactiveCategory_whenCallsUpdateCategory_shouldReturnInactiveCategoryId() {
        final var category = Category.newCategory("Documentário", null, true);
        final var expectedId = category.getId();
        final var expectedIsActive = false;
        final var commandUpdateCategory = UpdateCategoryCommand.with(
                expectedId.getValue(),
                category.getName(),
                category.getDescription(),
                expectedIsActive);

        when(this.categoryGateway.findById(eq(expectedId)))
            .thenReturn(Optional.of(Category.with(category.clone())));

        when(this.categoryGateway.update(any()))
            .thenAnswer(returnsFirstArg());

        final var categoryUpdated = this.updateCategoryUseCase.execute(commandUpdateCategory).get();

        Assertions.assertNotNull(categoryUpdated);
        Assertions.assertEquals(expectedId.getValue(), categoryUpdated.categoryId());

        Mockito.verify(this.categoryGateway, times(1))
                .findById(eq(expectedId));

        Mockito.verify(this.categoryGateway, times(1))
                .update(argThat(
                        aCategoryUpdated ->
                                Objects.equals(category.getName(), aCategoryUpdated.getName())
                                        && Objects.equals(category.getDescription(), aCategoryUpdated.getDescription())
                                        && Objects.equals(false, aCategoryUpdated.isActive())
                                        && Objects.equals(category.getCreatedAt(), aCategoryUpdated.getCreatedAt())
                                        && category.getUpdatedAt().isBefore(aCategoryUpdated.getUpdatedAt())
                                        && Objects.nonNull(aCategoryUpdated.getDeletedAt())
                ));
    }

    @Test
    public void givenAValidCommand_whenGatewayThrowsRandomException_shouldReturnAException() {
        final var aCategory =
                Category.newCategory("Film", null, true);

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedId = aCategory.getId();
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "Gateway error";

        final var aCommand = UpdateCategoryCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        when(categoryGateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(Category.with(aCategory.clone())));

        when(categoryGateway.update(any()))
                .thenThrow(new IllegalStateException(expectedErrorMessage));

        final var notification = this.updateCategoryUseCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());

        Mockito.verify(categoryGateway, times(1)).update(argThat(
                aUpdatedCategory ->
                        Objects.equals(expectedName, aUpdatedCategory.getName())
                                && Objects.equals(expectedDescription, aUpdatedCategory.getDescription())
                                && Objects.equals(expectedIsActive, aUpdatedCategory.isActive())
                                && Objects.equals(expectedId, aUpdatedCategory.getId())
                                && Objects.equals(aCategory.getCreatedAt(), aUpdatedCategory.getCreatedAt())
                                && aCategory.getUpdatedAt().isBefore(aUpdatedCategory.getUpdatedAt())
                                && Objects.isNull(aUpdatedCategory.getDeletedAt())
        ));
    }

    @Test
    public void givenACommandWithInvalidID_whenCallsUpdateCategory_shouldReturnNotFoundException() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = false;
        final var expectedId = "123";
        final var expectedErrorMessage = "Category with ID 123 was not found";

        final var aCommand = UpdateCategoryCommand.with(
                expectedId,
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        when(categoryGateway.findById(eq(CategoryId.from(expectedId))))
                .thenReturn(Optional.empty());

        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> this.updateCategoryUseCase.execute(aCommand));

        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        Mockito.verify(categoryGateway, times(1)).findById(eq(CategoryId.from(expectedId)));

        Mockito.verify(categoryGateway, times(0)).update(any());
    }
}

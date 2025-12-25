package com.codeflix.admin.catalogo.application.test.category.update;

import com.codeflix.admin.catalogo.application.category.update.DefaultUpdateCategoryUseCase;
import com.codeflix.admin.catalogo.application.category.update.UpdateCategoryCommand;
import com.codeflix.admin.catalogo.domain.category.Category;
import com.codeflix.admin.catalogo.domain.category.CategoryGateway;
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
                .thenReturn(Category.with(category));

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

    }

    @Test
    public void givenAValidCommandWithInactiveCategory_whenCallsUpdateCategory_shouldReturnInactiveCategoryId() {

    }

    @Test
    public void givenAValidCommand_whenGatewayThrowsRandomException_shouldReturnAException() {

    }
}

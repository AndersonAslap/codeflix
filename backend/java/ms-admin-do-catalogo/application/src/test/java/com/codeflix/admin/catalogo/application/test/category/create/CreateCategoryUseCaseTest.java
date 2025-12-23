package com.codeflix.admin.catalogo.application.test.category.create;

import com.codeflix.admin.catalogo.application.category.create.CreateCategoryCommand;
import com.codeflix.admin.catalogo.application.category.create.DefaultCreateCategoryUseCase;
import com.codeflix.admin.catalogo.domain.category.CategoryGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;

import java.util.Objects;

public class CreateCategoryUseCaseTest {
    // 1. Teste do caminho feliz
    // 2. Teste simulando um erro com nome inválido
    // 3. Teste criando uma categoria inativa
    // 4. Teste simulando um erro genérico vindo do gateway

    @Test
    public void givenAValidCommand_whenCallsCreateCategory_shouldReturnCategoryId() {
        final var expectedName = "filmes";
        final var expectedDescription = "description";
        final var expectedIsActive = true;

        final var command = CreateCategoryCommand.with(
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        final CategoryGateway categoryGateway = Mockito.mock(CategoryGateway.class);

        Mockito.when(categoryGateway.create(Mockito.any()))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());

        final var useCase = new DefaultCreateCategoryUseCase(categoryGateway);
        final var output = useCase.execute(command);

        Assertions.assertNotNull(output);

        Mockito.verify(categoryGateway, Mockito.times(1))
                .create(Mockito.argThat(category -> {
                    return Objects.nonNull(category.getId())
                            && Objects.equals(category.getName(), expectedName)
                            && Objects.equals(category.getDescription(), expectedDescription)
                            && Objects.equals(category.isActive(), expectedIsActive)
                            && Objects.nonNull(category.getCreatedAt())
                            && Objects.nonNull(category.getUpdatedAt())
                            && Objects.isNull(category.getDeletedAt());
                }));
    }
}

package com.codeflix.admin.catalogo.application.test.category.create;

import com.codeflix.admin.catalogo.application.category.create.CreateCategoryCommand;
import com.codeflix.admin.catalogo.application.category.create.DefaultCreateCategoryUseCase;
import com.codeflix.admin.catalogo.domain.category.CategoryGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateCategoryUseCaseTest {
    @InjectMocks
    private DefaultCreateCategoryUseCase createCategoryUseCase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    public void cleanUp() {
        Mockito.reset(categoryGateway);
    }

    // 1. Teste do caminho feliz
    // 2. Teste passando uma propriedade inválida (name)
    // 3. Teste criando uma categoria inativa
    // 4. Teste simulando um erro generico vindo do gateway

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

        when(this.categoryGateway.create(any()))
                .thenAnswer(returnsFirstArg());

        final var output = this.createCategoryUseCase.execute(command).get();

        Assertions.assertNotNull(output);

        Mockito.verify(this.categoryGateway, times(1))
                .create(argThat(category -> {
                    return Objects.nonNull(category.getId())
                            && Objects.equals(category.getName(), expectedName)
                            && Objects.equals(category.getDescription(), expectedDescription)
                            && Objects.equals(category.isActive(), expectedIsActive)
                            && Objects.nonNull(category.getCreatedAt())
                            && Objects.nonNull(category.getUpdatedAt())
                            && Objects.isNull(category.getDeletedAt());
                }));
    }

    @Test
    public void givenAInvalidName_whenCallsCreateCategory_thenShouldReturnDomainException() {
        final String expectedName = null;
        final var expectedDescription = "description";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        final var command =
                CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        final var notification = this.createCategoryUseCase.execute(command).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());

        Mockito.verify(this.categoryGateway, times(0)).create(any());
    }

    @Test
    public void givenAValidCommandWithInactiveCategory_whenCallsCreateCategory_shouldReturnInactiveCategoryId() {
        final var expectedName = "filmes";
        final var expectedDescription = "description";
        final var expectedIsActive = false;

        final var command = CreateCategoryCommand.with(
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        when(this.categoryGateway.create(any()))
                .thenAnswer(returnsFirstArg());

        final var output = this.createCategoryUseCase.execute(command).get();

        Assertions.assertNotNull(output);

        Mockito.verify(this.categoryGateway, times(1))
                .create(argThat(category -> {
                    return Objects.nonNull(category.getId())
                            && Objects.equals(category.getName(), expectedName)
                            && Objects.equals(category.getDescription(), expectedDescription)
                            && Objects.equals(category.isActive(), expectedIsActive)
                            && Objects.nonNull(category.getCreatedAt())
                            && Objects.nonNull(category.getUpdatedAt())
                            && Objects.nonNull(category.getDeletedAt());
                }));
    }

    @Test
    public void givenAValidCommand_whenGatewayThrowsRandomException_shouldReturnAException() {
        final var expectedName = "filmes";
        final var expectedDescription = "description";
        final var expectedIsActive = true;
        final var exceptionErrorMessage = "Gateway error";
        final var expectedErrorCount = 1;

        final var command = CreateCategoryCommand.with(
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        when(this.categoryGateway.create(any()))
                .thenThrow(new IllegalStateException(exceptionErrorMessage));

        final var notification = this.createCategoryUseCase.execute(command).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(exceptionErrorMessage, notification.firstError().message());

        Mockito.verify(this.categoryGateway, times(1))
                .create(argThat(category -> {
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

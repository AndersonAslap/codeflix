package com.codeflix.admin.catalogo.application.test.category.retrieve.get;

import com.codeflix.admin.catalogo.application.category.retrieve.get.DefaultGetCategoryByIdUseCase;
import com.codeflix.admin.catalogo.domain.category.Category;
import com.codeflix.admin.catalogo.domain.category.CategoryGateway;
import com.codeflix.admin.catalogo.domain.category.CategoryId;
import com.codeflix.admin.catalogo.domain.exceptions.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetCategoryByIdUseCaseTest {

    @InjectMocks
    private DefaultGetCategoryByIdUseCase getCategoryByIdUseCase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    public void cleanUp(){
        Mockito.reset(categoryGateway);
    }

    @Test
    public void givenAValidId_whenCallsGetCategory_shouldReturnCategory() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var category =
                Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var expectedId = category.getId();

        when(this.categoryGateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(category.clone()));

        final var categoryFind = this.getCategoryByIdUseCase.execute(expectedId.getValue());

        Mockito.verify(this.categoryGateway, times(1)).findById(eq(expectedId));
    }

    @Test
    public void givenAInvalidId_whenCallsGetCategory_shouldReturnNotFound() {
        final var expectedId = CategoryId.from("123");
        final var expectedErrorMessage = "";

        when(this.categoryGateway.findById(eq(expectedId)))
                .thenReturn(Optional.empty());

        final var notification = Assertions.assertThrows(
                DomainException.class,
                () -> this.getCategoryByIdUseCase.execute(expectedId.getValue())
        );

        Assertions.assertEquals(expectedErrorMessage, notification.getMessage());
    }

    @Test
    public void givenAValidId_whenGatewayThrowsException_shouldReturnException() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedMessageError = "Gateway error";

        final var category =
                Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var expectedId = category.getId();

        when(this.categoryGateway.findById(eq(expectedId)))
                .thenThrow(new IllegalStateException(expectedMessageError));

        final var notification = Assertions.assertThrows(
                IllegalStateException.class,
                () -> this.getCategoryByIdUseCase.execute(expectedId.getValue())
        );

        Assertions.assertEquals(expectedMessageError, notification.getMessage());
    }
}

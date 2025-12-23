package com.codeflix.admin.catalogo.domain.category;

import com.codeflix.admin.catalogo.domain.exceptions.DomainException;
import com.codeflix.admin.catalogo.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CategoryTest {

    @Test
    public void givenAValidParams_whenCallNewCategory_thenInstantiatecategory() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var category =
                Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertNotNull(category);
        Assertions.assertNotNull(category.getId());
        Assertions.assertEquals(expectedName, category.getName());
        Assertions.assertEquals(expectedDescription, category.getDescription());
        Assertions.assertEquals(expectedIsActive, category.isActive());
        Assertions.assertNotNull(category.getCreatedAt());
        Assertions.assertNotNull(category.getUpdatedAt());
        Assertions.assertNull(category.getDeletedAt());
    }

    @Test
    public void givenAnInvalidNullName_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final String expectedName = null;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var category =
                Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var exception =
                Assertions.assertThrows(DomainException.class, () -> category.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, exception.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, exception.getErrors().get(0).message());
    }

    @Test
    public void givenAnInvalidEmptyName_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final var expectedName = "  ";
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be empty";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var category =
                Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var exception =
                Assertions.assertThrows(DomainException.class, () -> category.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, exception.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, exception.getErrors().get(0).message());
    }

    @Test
    public void givenAnInvalidNameLengthLessThan3_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final var expectedName = "Fi ";
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' must be between 3 and 255 characters";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var category =
                Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var exception =
                Assertions.assertThrows(DomainException.class, () -> category.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, exception.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, exception.getErrors().get(0).message());
    }

    @Test
    public void givenAnInvalidNameLengthMoreThan255_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final var expectedName = """
                Gostaria de enfatizar que o consenso sobre a necessidade de qualificação auxilia a preparação e a
                composição das posturas dos órgãos dirigentes com relação às suas atribuições.
                Do mesmo modo, a estrutura atual da organização apresenta tendências no sentido de aprovar a
                manutenção das novas proposições.
                """;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' must be between 3 and 255 characters";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var category =
                Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var exception =
                Assertions.assertThrows(DomainException.class, () -> category.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, exception.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, exception.getErrors().get(0).message());
    }

    @Test
    public void givenAValidEmptyDescription_whenCallNewCategoryAndValidate_thenShouldReceiveOK() {
        final var expectedName = "Filmes";
        final var expectedDescription = "  ";
        final var expectedIsActive = true;

        final var category =
                Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertDoesNotThrow(() -> category.validate(new ThrowsValidationHandler()));

        Assertions.assertNotNull(category);
        Assertions.assertNotNull(category.getId());
        Assertions.assertEquals(expectedName, category.getName());
        Assertions.assertEquals(expectedDescription, category.getDescription());
        Assertions.assertEquals(expectedIsActive, category.isActive());
        Assertions.assertNotNull(category.getCreatedAt());
        Assertions.assertNotNull(category.getUpdatedAt());
        Assertions.assertNull(category.getDeletedAt());
    }

    @Test
    public void givenAValidFalseIsActive_whenCallNewCategoryAndValidate_thenShouldReceiveOK() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = false;

        final var category =
                Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertDoesNotThrow(() -> category.validate(new ThrowsValidationHandler()));

        Assertions.assertNotNull(category);
        Assertions.assertNotNull(category.getId());
        Assertions.assertEquals(expectedName, category.getName());
        Assertions.assertEquals(expectedDescription, category.getDescription());
        Assertions.assertEquals(expectedIsActive, category.isActive());
        Assertions.assertNotNull(category.getCreatedAt());
        Assertions.assertNotNull(category.getUpdatedAt());
        Assertions.assertNotNull(category.getDeletedAt());
    }

    @Test
    public void givenAValidActiveCategory_whenCallDeactivate_thenReturnCategoryInactivated() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = false;

        final var category =
                Category.newCategory(expectedName, expectedDescription, true);

        Assertions.assertDoesNotThrow(() -> category.validate(new ThrowsValidationHandler()));

        final var createdAt = category.getCreatedAt();
        final var updatedAt = category.getUpdatedAt();

        Assertions.assertTrue(category.isActive());
        Assertions.assertNull(category.getDeletedAt());

        final var category = category.deactivate();

        Assertions.assertDoesNotThrow(() -> category.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(category.getId(), category.getId());
        Assertions.assertEquals(expectedName, category.getName());
        Assertions.assertEquals(expectedDescription, category.getDescription());
        Assertions.assertEquals(expectedIsActive, category.isActive());
        Assertions.assertEquals(createdAt, category.getCreatedAt());
        Assertions.assertTrue(category.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNotNull(category.getDeletedAt());
    }

    @Test
    public void givenAValidInactiveCategory_whenCallActivate_thenReturnCategoryActivated() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var category1 =
                Category.newCategory(expectedName, expectedDescription, false);

        Assertions.assertDoesNotThrow(() -> category1.validate(new ThrowsValidationHandler()));

        final var createdAt = category1.getCreatedAt();
        final var updatedAt = category1.getUpdatedAt();

        Assertions.assertFalse(category1.isActive());
        Assertions.assertNotNull(category1.getDeletedAt());

        final var category = category1.activate();

        Assertions.assertDoesNotThrow(() -> category.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(category.getId(), category.getId());
        Assertions.assertEquals(expectedName, category.getName());
        Assertions.assertEquals(expectedDescription, category.getDescription());
        Assertions.assertEquals(expectedIsActive, category.isActive());
        Assertions.assertEquals(createdAt, category.getCreatedAt());
        Assertions.assertTrue(category.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNull(category.getDeletedAt());
    }

    @Test
    public void givenAValidCategory_whenCallUpdate_thenReturnCategoryUpdated() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var category =
                Category.newCategory("Film", "A categoria", expectedIsActive);

        Assertions.assertDoesNotThrow(() -> category.validate(new ThrowsValidationHandler()));

        final var createdAt = category.getCreatedAt();
        final var updatedAt = category.getUpdatedAt();

        final var categoryUpdated = category.update(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertDoesNotThrow(() -> categoryUpdated.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(category.getId(), categoryUpdated.getId());
        Assertions.assertEquals(expectedName, categoryUpdated.getName());
        Assertions.assertEquals(expectedDescription, categoryUpdated.getDescription());
        Assertions.assertEquals(expectedIsActive, categoryUpdated.isActive());
        Assertions.assertEquals(createdAt, categoryUpdated.getCreatedAt());
        Assertions.assertTrue(categoryUpdated.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNull(categoryUpdated.getDeletedAt());
    }

    @Test
    public void givenAValidCategory_whenCallUpdateToInactive_thenReturnCategoryUpdated() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = false;

        final var category =
                Category.newCategory("Film", "A categoria", true);

        Assertions.assertDoesNotThrow(() -> category.validate(new ThrowsValidationHandler()));
        Assertions.assertTrue(category.isActive());
        Assertions.assertNull(category.getDeletedAt());

        final var createdAt = category.getCreatedAt();
        final var updatedAt = category.getUpdatedAt();

        final var categoryUpdated = category.update(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertDoesNotThrow(() -> categoryUpdated.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(category.getId(), categoryUpdated.getId());
        Assertions.assertEquals(expectedName, categoryUpdated.getName());
        Assertions.assertEquals(expectedDescription, categoryUpdated.getDescription());
        Assertions.assertFalse(category.isActive());
        Assertions.assertEquals(createdAt, categoryUpdated.getCreatedAt());
        Assertions.assertTrue(categoryUpdated.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNotNull(category.getDeletedAt());
    }

    @Test
    public void givenAValidCategory_whenCallUpdateWithInvalidParams_thenReturnCategoryUpdated() {
        final String expectedName = null;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var category =
                Category.newCategory("Filmes", "A categoria", expectedIsActive);

        Assertions.assertDoesNotThrow(() -> category.validate(new ThrowsValidationHandler()));

        final var createdAt = category.getCreatedAt();
        final var updatedAt = category.getUpdatedAt();

        final var categoryUpdated = category.update(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertEquals(category.getId(), categoryUpdated.getId());
        Assertions.assertEquals(expectedName, categoryUpdated.getName());
        Assertions.assertEquals(expectedDescription, categoryUpdated.getDescription());
        Assertions.assertTrue(category.isActive());
        Assertions.assertEquals(createdAt, categoryUpdated.getCreatedAt());
        Assertions.assertTrue(categoryUpdated.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNull(category.getDeletedAt());
    }
}

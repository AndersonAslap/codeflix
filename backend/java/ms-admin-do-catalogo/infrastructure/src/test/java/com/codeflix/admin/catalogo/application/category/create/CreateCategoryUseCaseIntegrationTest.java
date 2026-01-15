package com.codeflix.admin.catalogo.application.category.create;

import com.codeflix.admin.catalogo.IntegrationTest;
import com.codeflix.admin.catalogo.infrastructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
public class CreateCategoryUseCaseIntegrationTest {

    @Autowired
    private CreateCategoryUseCase createCategoryUseCase;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void givenAValidCommand_whenCallsCreateCategoryUseCase_shouldReturnCategoryId() {
        final var expectedName = "filmes";
        final var expectedDescription = "description";
        final var expectedIsActive = true;

        Assertions.assertEquals(0, this.categoryRepository.count());

        final var command = CreateCategoryCommand.with(
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        final var output = createCategoryUseCase.execute(command).get();

        Assertions.assertEquals(1, this.categoryRepository.count());
        Assertions.assertNotNull(output);
        Assertions.assertNotNull(output.categoryId());

        final var categoryRegistered = this.categoryRepository.findById(output.categoryId().getValue()).get();

        Assertions.assertNotNull(categoryRegistered);
        Assertions.assertEquals(expectedName, categoryRegistered.getName());
        Assertions.assertEquals(expectedDescription, categoryRegistered.getDescription());
        Assertions.assertEquals(expectedIsActive, categoryRegistered.isActive());
        Assertions.assertNotNull(categoryRegistered.getCreatedAt());
        Assertions.assertNotNull(categoryRegistered.getUpdatedAt());
        Assertions.assertNull(categoryRegistered.getDeletedAt());
    }
}

package com.codeflix.admin.catalogo.application.category.update;

import com.codeflix.admin.catalogo.IntegrationTest;
import com.codeflix.admin.catalogo.domain.category.Category;
import com.codeflix.admin.catalogo.infrastructure.category.persistence.CategoryJpaEntity;
import com.codeflix.admin.catalogo.infrastructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
public class UpdateCategoryUseCaseIntegrationTest {

    @Autowired
    private UpdateCategoryUseCase updateCategoryUseCase;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void givenAValidCommand_whenCallsUpdateCategory_shouldReturnCategoryId() {
        var category = Category.newCategory("Filmes", "", true);
        final var expectedCategoryId = category.getId();
        
        this.categoryRepository.save(CategoryJpaEntity.from(category));

        Assertions.assertEquals(1, this.categoryRepository.count());

        final var expectedUpdateName = "Séries";
        final var expectedUpdateDescription = "A categoria mais assistida";

        final var command = UpdateCategoryCommand.with(
                expectedCategoryId.getValue(),
                expectedUpdateName,
                expectedUpdateDescription,
                false
        );

        this.updateCategoryUseCase.execute(command);

        Assertions.assertEquals(1, this.categoryRepository.count());

        final var categoryUpdated =
                this.categoryRepository.findById(expectedCategoryId.getValue()).get().toAggregate();

        Assertions.assertEquals(category.getId(), categoryUpdated.getId());
        Assertions.assertEquals(expectedUpdateName, categoryUpdated.getName());
        Assertions.assertEquals(expectedUpdateDescription, categoryUpdated.getDescription());
        Assertions.assertFalse(categoryUpdated.isActive());
        Assertions.assertNotNull(categoryUpdated.getCreatedAt());
        Assertions.assertNotNull(categoryUpdated.getUpdatedAt());
        Assertions.assertNotNull(categoryUpdated.getDeletedAt());
    }
}

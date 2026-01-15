package com.codeflix.admin.catalogo.application.category.retrieve.get;

import com.codeflix.admin.catalogo.IntegrationTest;
import com.codeflix.admin.catalogo.domain.category.Category;
import com.codeflix.admin.catalogo.infrastructure.category.persistence.CategoryJpaEntity;
import com.codeflix.admin.catalogo.infrastructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
public class GetCategoryByIdUseCaseIntegrationTest {

    @Autowired
    private GetCategoryByIdUseCase getCategoryByIdUseCase;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void givenAValidId_whenCallsGetCategoryByIdUseCase_shouldReturnACategory() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        Assertions.assertEquals(0,  this.categoryRepository.count());

        final var category = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var expectedId = category.getId();

        this.categoryRepository.save(CategoryJpaEntity.from(category));

        Assertions.assertEquals(1,  this.categoryRepository.count());

        final var output = this.getCategoryByIdUseCase.execute(expectedId.getValue());

        Assertions.assertNotNull(output);
        Assertions.assertEquals(expectedId.getValue(), output.categoryId().getValue());
        Assertions.assertEquals(expectedName, output.name());
        Assertions.assertEquals(expectedDescription, output.description());
        Assertions.assertEquals(expectedIsActive, output.isActive());
    }
}

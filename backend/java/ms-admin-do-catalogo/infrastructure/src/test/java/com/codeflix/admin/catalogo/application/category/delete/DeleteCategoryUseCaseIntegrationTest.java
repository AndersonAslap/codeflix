package com.codeflix.admin.catalogo.application.category.delete;

import com.codeflix.admin.catalogo.IntegrationTest;
import com.codeflix.admin.catalogo.domain.category.Category;
import com.codeflix.admin.catalogo.infrastructure.category.persistence.CategoryJpaEntity;
import com.codeflix.admin.catalogo.infrastructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
public class DeleteCategoryUseCaseIntegrationTest {

    @Autowired
    private DeleteCategoryUseCase deleteCategoryUseCase;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void givenAValidId_whenCallsDeleteCategory_shouldBeOK() {
        final var category = Category.newCategory("Filme", "", true);
        final var expectedId = category.getId();

        this.categoryRepository.save(CategoryJpaEntity.from(category));

        Assertions.assertEquals(1, this.categoryRepository.count());

        this.deleteCategoryUseCase.execute(expectedId.getValue());

        Assertions.assertEquals(0, this.categoryRepository.count());
    }
}

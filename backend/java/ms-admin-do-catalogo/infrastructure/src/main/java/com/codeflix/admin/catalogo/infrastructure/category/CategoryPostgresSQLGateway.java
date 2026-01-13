package com.codeflix.admin.catalogo.infrastructure.category;

import com.codeflix.admin.catalogo.domain.category.Category;
import com.codeflix.admin.catalogo.domain.category.CategoryGateway;
import com.codeflix.admin.catalogo.domain.category.CategoryId;
import com.codeflix.admin.catalogo.domain.category.CategorySearchQuery;
import com.codeflix.admin.catalogo.domain.pagination.Pagination;
import com.codeflix.admin.catalogo.infrastructure.category.persistence.CategoryJpaEntity;
import com.codeflix.admin.catalogo.infrastructure.category.persistence.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryPostgresSQLGateway implements CategoryGateway {

    private CategoryRepository categoryRepository;

    public CategoryPostgresSQLGateway(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category create(final Category category) {
        return save(category);
    }

    @Override
    public Category update(final Category category) {
        return save(category);
    }

    @Override
    public void deleteById(final CategoryId categoryId) {
        final String id = categoryId.getValue();
        if (this.categoryRepository.existsById(id)) {
            this.categoryRepository.deleteById(id);
        }
    }

    @Override
    public Optional<Category> findById(final CategoryId categoryId) {
        return this.categoryRepository.findById(categoryId.getValue())
                .map(CategoryJpaEntity::toAggregate);
    }

    @Override
    public Pagination<Category> findAll(final CategorySearchQuery query) {
        return null;
    }

    private Category save(final Category category) {
        return this.categoryRepository.save(CategoryJpaEntity.from(category)).toAggregate();
    }
}

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
    public Category create(Category category) {
        this.categoryRepository.save(
                CategoryJpaEntity.from(category)
        );
        return category;
    }

    @Override
    public Category update(Category category) {
        return null;
    }

    @Override
    public void deleteById(CategoryId categoryId) {

    }

    @Override
    public Optional<Category> findById(CategoryId categoryId) {
        return Optional.empty();
    }

    @Override
    public Pagination<Category> findAll(CategorySearchQuery query) {
        return null;
    }
}

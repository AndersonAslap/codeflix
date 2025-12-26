package com.codeflix.admin.catalogo.domain.category;

import com.codeflix.admin.catalogo.domain.pagination.Pagination;

import java.util.Optional;

public interface CategoryGateway {

    Category create(Category category);

    Category update(Category category);

    void deleteById(CategoryId categoryId);

    Optional<Category> findById(CategoryId categoryId);

    Pagination<Category> findAll(CategorySearchQuery query);
}

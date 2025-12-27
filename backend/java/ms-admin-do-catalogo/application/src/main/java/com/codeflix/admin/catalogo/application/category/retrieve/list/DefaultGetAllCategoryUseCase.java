package com.codeflix.admin.catalogo.application.category.retrieve.list;

import com.codeflix.admin.catalogo.domain.category.CategoryGateway;
import com.codeflix.admin.catalogo.domain.category.CategorySearchQuery;
import com.codeflix.admin.catalogo.domain.pagination.Pagination;

import java.util.Objects;

public class DefaultGetAllCategoryUseCase extends GetAllCategoryUseCase {

    private CategoryGateway categoryGateway;

    public DefaultGetAllCategoryUseCase(CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Pagination<GetAllCategoryOutput> execute(CategorySearchQuery query) {
        return this.categoryGateway.findAll(query)
                .map(GetAllCategoryOutput::from);
    }
}

package com.codeflix.admin.catalogo.infrastructure.configuration.usecases;

import com.codeflix.admin.catalogo.application.category.create.CreateCategoryUseCase;
import com.codeflix.admin.catalogo.application.category.create.DefaultCreateCategoryUseCase;
import com.codeflix.admin.catalogo.application.category.delete.DefaultDeleteCategoryUseCase;
import com.codeflix.admin.catalogo.application.category.delete.DeleteCategoryUseCase;
import com.codeflix.admin.catalogo.application.category.retrieve.get.DefaultGetCategoryByIdUseCase;
import com.codeflix.admin.catalogo.application.category.retrieve.get.GetCategoryByIdUseCase;
import com.codeflix.admin.catalogo.application.category.retrieve.list.DefaultGetAllCategoryUseCase;
import com.codeflix.admin.catalogo.application.category.retrieve.list.GetAllCategoryUseCase;
import com.codeflix.admin.catalogo.application.category.update.DefaultUpdateCategoryUseCase;
import com.codeflix.admin.catalogo.application.category.update.UpdateCategoryUseCase;
import com.codeflix.admin.catalogo.domain.category.CategoryGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryUseCaseConfig {

    private final CategoryGateway categoryGateway;

    public CategoryUseCaseConfig(CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Bean
    public GetCategoryByIdUseCase getCategoryByIdUseCase() {
        return new DefaultGetCategoryByIdUseCase(categoryGateway);
    }

    @Bean
    public GetAllCategoryUseCase getAllCategoryUseCase() {
        return new DefaultGetAllCategoryUseCase(categoryGateway);
    }

    @Bean
    public CreateCategoryUseCase createCategoryUseCase() {
        return new DefaultCreateCategoryUseCase(categoryGateway);
    }

    @Bean
    public UpdateCategoryUseCase updateCategoryUseCase() {
        return new DefaultUpdateCategoryUseCase(categoryGateway);
    }

    @Bean
    public DeleteCategoryUseCase deleteCategoryUseCase() {
        return new DefaultDeleteCategoryUseCase(categoryGateway);
    }
}

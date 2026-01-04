package com.codeflix.admin.catalogo.infrastructure;

import com.codeflix.admin.catalogo.domain.category.Category;
import com.codeflix.admin.catalogo.infrastructure.category.persistence.CategoryJpaEntity;
import com.codeflix.admin.catalogo.infrastructure.category.persistence.CategoryRepository;
import com.codeflix.admin.catalogo.infrastructure.configuration.WebServerConfig;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.AbstractEnvironment;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        System.setProperty(AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME, "development");
        SpringApplication.run(WebServerConfig.class, args);
    }


    /*
    @Bean
    public ApplicationRunner run(CategoryRepository categoryRepository) {
        return args -> {
          Category filme = Category.newCategory("Filmes", "", true);
          categoryRepository.save(CategoryJpaEntity.from(filme));
          categoryRepository.deleteAll();
        };
    }
    */
}

package com.codeflix.admin.catalogo.infrastructure.category.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

/*
* Quem realiza a implementação por debaixo dos panos é o
* SimpleJpaRepository, ele que faz a implementação dinamicamente
* */

public interface CategoryRepository extends JpaRepository<CategoryJpaEntity, String> {
}

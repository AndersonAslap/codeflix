package com.codeflix.admin.catalogo.infrastructure.category;

import com.codeflix.admin.catalogo.infrastructure.PostgresSQLGatewayTest;
import com.codeflix.admin.catalogo.infrastructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@PostgresSQLGatewayTest
public class CategoryPostgresSQLGatewayTest {

    @Autowired
    private CategoryPostgresSQLGateway categoryPostgresSQLGateway;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void test() {
        Assertions.assertNotNull(categoryPostgresSQLGateway);
        Assertions.assertNotNull(categoryRepository);
    }
}

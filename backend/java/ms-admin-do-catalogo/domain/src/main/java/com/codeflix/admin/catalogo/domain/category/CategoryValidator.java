package com.codeflix.admin.catalogo.domain.category;

import com.codeflix.admin.catalogo.domain.validation.Error;
import com.codeflix.admin.catalogo.domain.validation.ValidationHandler;
import com.codeflix.admin.catalogo.domain.validation.Validator;

public class CategoryValidator extends Validator {

    private final Category category;

    public CategoryValidator(Category category,  ValidationHandler validationHandler) {
        super(validationHandler);
        this.category = category;
    }

    @Override
    public void validate() {
        if (category.getName() == null) {
            this.getValidationHandler().append(new Error("'name' should not be null"));
        }
    }
}

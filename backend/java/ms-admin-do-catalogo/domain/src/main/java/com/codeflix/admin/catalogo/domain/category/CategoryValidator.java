package com.codeflix.admin.catalogo.domain.category;

import com.codeflix.admin.catalogo.domain.validation.Error;
import com.codeflix.admin.catalogo.domain.validation.ValidationHandler;
import com.codeflix.admin.catalogo.domain.validation.Validator;

public class CategoryValidator extends Validator {
    private final int NAME_MIN_LENGTH = 3;
    private final int NAME_MAX_LENGTH = 255;
    private final Category category;

    public CategoryValidator(Category category,  ValidationHandler validationHandler) {
        super(validationHandler);
        this.category = category;
    }

    @Override
    public void validate() {
        this.checkNameConstraint();
    }

    private void checkNameConstraint() {
        var name = category.getName();
        if (name == null) {
            this.getValidationHandler().append(new Error("'name' should not be null"));
            return;
        }
        if (name.isBlank()) {
            this.getValidationHandler().append(new Error("'name' should not be empty"));
            return;
        }
        final int length = name.trim().length();
        if (length > NAME_MAX_LENGTH && length < NAME_MIN_LENGTH) {
            this.getValidationHandler().append(new Error("'name' must be between 3 and 255 characters"));
        }
    }
}

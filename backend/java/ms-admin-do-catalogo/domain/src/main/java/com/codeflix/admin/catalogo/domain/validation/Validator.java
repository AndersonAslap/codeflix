package com.codeflix.admin.catalogo.domain.validation;

public abstract class Validator {

    private final ValidationHandler handler;

    protected Validator(ValidationHandler validationHandler) {
        this.handler = validationHandler;
    }

    public abstract void validate();

    protected ValidationHandler getValidationHandler() {
        return this.handler;
    }
}

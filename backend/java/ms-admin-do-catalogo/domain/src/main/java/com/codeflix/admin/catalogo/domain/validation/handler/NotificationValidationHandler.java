package com.codeflix.admin.catalogo.domain.validation.handler;

import com.codeflix.admin.catalogo.domain.exceptions.DomainException;
import com.codeflix.admin.catalogo.domain.validation.Error;
import com.codeflix.admin.catalogo.domain.validation.Validation;
import com.codeflix.admin.catalogo.domain.validation.ValidationHandler;

import java.util.ArrayList;
import java.util.List;

public class NotificationValidationHandler implements ValidationHandler {

    private final List<Error> errors;

    private NotificationValidationHandler(final List<Error> errors) {
        this.errors = errors;
    }

    public static NotificationValidationHandler create() {
        return new NotificationValidationHandler(new ArrayList<>());
    }

    public static NotificationValidationHandler create(final Error error) {
        return new NotificationValidationHandler(new ArrayList<>()).append(error);
    }

    @Override
    public NotificationValidationHandler append(Error error) {
        this.errors.add(error);
        return this;
    }

    @Override
    public NotificationValidationHandler append(ValidationHandler validationHandler) {
        this.errors.addAll(validationHandler.getErrors());
        return this;
    }

    @Override
    public NotificationValidationHandler validate(Validation validation) {
        try {
            validation.validate();
        } catch (DomainException e) {
            this.errors.addAll(e.getErrors());
        } catch (Throwable e) {
            this.errors.add(new Error(e.getMessage()));
        }
        return this;
    }

    @Override
    public List<Error> getErrors() {
        return this.errors;
    }
}

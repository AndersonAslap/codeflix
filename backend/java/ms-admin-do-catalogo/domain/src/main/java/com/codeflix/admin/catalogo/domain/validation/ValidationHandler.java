package com.codeflix.admin.catalogo.domain.validation;

import java.util.List;

public interface ValidationHandler {

    ValidationHandler append(Error error);

    ValidationHandler append(ValidationHandler validationHandler);

    ValidationHandler validate(Validation validation);

    List<Error> getErrors();

    default boolean hasErrors() {
        return (getErrors() != null && !getErrors().isEmpty());
    }

    default Error firstError() {
        if (getErrors() != null && !getErrors().isEmpty()) {
            return getErrors().getFirst();
        } else {
            return null;
        }
    }
}

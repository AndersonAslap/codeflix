package com.codeflix.admin.catalogo.application;

public abstract class UnitUseCase<INPUT> {
    public abstract void execute(INPUT input);
}

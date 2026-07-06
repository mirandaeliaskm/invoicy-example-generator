package br.com.migrate.invoicy.examples.domain.exception;

import br.com.migrate.invoicy.examples.domain.validation.ValidationResult;

public class FiscalValidationException extends RuntimeException {

    private final transient ValidationResult validationResult;

    public FiscalValidationException(ValidationResult validationResult) {
        super("O exemplo fiscal gerado não passou nas validações configuradas.");
        this.validationResult = validationResult;
    }

    public ValidationResult validationResult() {
        return validationResult;
    }
}

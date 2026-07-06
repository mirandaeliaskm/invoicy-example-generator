package br.com.migrate.invoicy.examples.domain.validation;

public record ValidationMessage(
        Severity severity,
        String ruleId,
        String path,
        String message
) {
}

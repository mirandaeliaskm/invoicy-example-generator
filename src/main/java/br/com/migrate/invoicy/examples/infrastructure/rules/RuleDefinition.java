package br.com.migrate.invoicy.examples.infrastructure.rules;

import java.util.List;

public record RuleDefinition(
        String id,
        String path,
        Boolean required,
        String fixedValue,
        List<String> allowedValues,
        Boolean greaterThanZero,
        String message
) {
    public boolean isRequired() {
        return Boolean.TRUE.equals(required);
    }

    public boolean isGreaterThanZero() {
        return Boolean.TRUE.equals(greaterThanZero);
    }
}

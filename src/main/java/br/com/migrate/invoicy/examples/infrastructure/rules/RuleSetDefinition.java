package br.com.migrate.invoicy.examples.infrastructure.rules;

import java.util.List;

public record RuleSetDefinition(
        String id,
        String description,
        List<RuleDefinition> rules
) {
}

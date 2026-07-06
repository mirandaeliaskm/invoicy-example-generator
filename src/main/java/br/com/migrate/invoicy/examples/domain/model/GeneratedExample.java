package br.com.migrate.invoicy.examples.domain.model;

import br.com.migrate.invoicy.examples.domain.validation.ValidationResult;

import java.time.OffsetDateTime;
import java.util.Map;

public record GeneratedExample(
        String generationId,
        OffsetDateTime generatedAt,
        UseCaseDefinition useCase,
        Map<OutputFormat, String> files,
        ValidationResult validationResult
) {
}

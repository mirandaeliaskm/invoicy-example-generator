package br.com.migrate.invoicy.examples.api.dto;

import br.com.migrate.invoicy.examples.domain.model.GeneratedExample;
import br.com.migrate.invoicy.examples.domain.model.OutputFormat;
import br.com.migrate.invoicy.examples.domain.validation.ValidationMessage;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

public record GeneratedExampleResponse(
        String generationId,
        OffsetDateTime generatedAt,
        String useCaseId,
        String useCaseName,
        Map<OutputFormat, String> files,
        boolean valid,
        List<ValidationMessage> validationMessages
) {
    public static GeneratedExampleResponse from(GeneratedExample example) {
        return new GeneratedExampleResponse(
                example.generationId(),
                example.generatedAt(),
                example.useCase().id(),
                example.useCase().name(),
                example.files(),
                example.validationResult().isValid(),
                example.validationResult().messages()
        );
    }
}

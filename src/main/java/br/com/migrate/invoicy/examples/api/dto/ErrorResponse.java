package br.com.migrate.invoicy.examples.api.dto;

import br.com.migrate.invoicy.examples.domain.validation.ValidationMessage;

import java.time.OffsetDateTime;
import java.util.List;

public record ErrorResponse(
        OffsetDateTime timestamp,
        String message,
        List<ValidationMessage> validationMessages
) {
}

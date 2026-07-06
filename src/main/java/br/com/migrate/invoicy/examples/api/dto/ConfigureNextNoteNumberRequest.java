package br.com.migrate.invoicy.examples.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ConfigureNextNoteNumberRequest(
        @NotNull @Min(1) Long nextNoteNumber
) {
}

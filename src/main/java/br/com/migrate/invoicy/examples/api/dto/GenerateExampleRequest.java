package br.com.migrate.invoicy.examples.api.dto;

import br.com.migrate.invoicy.examples.domain.model.OutputFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.Map;
import java.util.Set;

public record GenerateExampleRequest(
        @NotBlank String useCaseId,
        @NotEmpty Set<OutputFormat> outputFormats,
        Map<String, Object> overrides
) {
}

package br.com.migrate.invoicy.examples.domain.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.Map;
import java.util.Set;

public record GenerateExampleCommand(
        @NotBlank String useCaseId,
        @NotEmpty Set<OutputFormat> outputFormats,
        Map<String, Object> overrides
) {
    public Map<String, Object> safeOverrides() {
        return overrides == null ? Map.of() : overrides;
    }
}

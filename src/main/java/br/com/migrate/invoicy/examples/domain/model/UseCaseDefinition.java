package br.com.migrate.invoicy.examples.domain.model;

import java.util.List;

public record UseCaseDefinition(
        String id,
        FiscalModule module,
        String name,
        String description,
        String model,
        String layoutVersion,
        List<OutputFormat> outputs,
        List<String> tags,
        TemplateDefinition template,
        String sampleData,
        List<String> ruleSets
) {
}

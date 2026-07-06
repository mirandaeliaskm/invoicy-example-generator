package br.com.migrate.invoicy.examples.domain.layout;

public record FieldDefinition(
        String path,
        String name,
        String description,
        String type,
        String minLength,
        String maxLength,
        FieldRequirement requirement,
        String condition,
        String layoutVersion
) {
}

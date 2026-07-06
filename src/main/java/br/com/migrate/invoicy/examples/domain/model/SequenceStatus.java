package br.com.migrate.invoicy.examples.domain.model;

import java.time.OffsetDateTime;

public record SequenceStatus(
        long lastNoteNumber,
        OffsetDateTime lastEmissionDateTime,
        String stateFile
) {
}

package br.com.migrate.invoicy.examples.domain.model;

import java.time.OffsetDateTime;

public record SequenceAllocation(
        long noteNumber,
        String formattedNoteNumber,
        OffsetDateTime emissionDateTime
) {
}

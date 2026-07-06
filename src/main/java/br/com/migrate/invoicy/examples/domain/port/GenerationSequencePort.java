package br.com.migrate.invoicy.examples.domain.port;

import br.com.migrate.invoicy.examples.domain.model.SequenceAllocation;
import br.com.migrate.invoicy.examples.domain.model.SequenceStatus;

public interface GenerationSequencePort {

    SequenceAllocation next();

    SequenceStatus status();

    SequenceStatus configureNextNoteNumber(long nextNoteNumber);
}

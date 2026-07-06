package br.com.migrate.invoicy.examples.infrastructure.sequence;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class FileGenerationSequenceTest {

    @Test
    void shouldPersistAndIncrementSequence() throws Exception {
        Path stateFile = Path.of("target/test-sequence-unit.properties");
        Files.deleteIfExists(stateFile);

        FileGenerationSequence sequence = new FileGenerationSequence(stateFile.toString(), 1);

        assertThat(sequence.next().noteNumber()).isEqualTo(1);
        assertThat(sequence.next().noteNumber()).isEqualTo(2);

        FileGenerationSequence reloadedSequence = new FileGenerationSequence(stateFile.toString(), 1);
        assertThat(reloadedSequence.next().noteNumber()).isEqualTo(3);
    }

    @Test
    void shouldConfigureNextNoteNumber() throws Exception {
        Path stateFile = Path.of("target/test-sequence-configure.properties");
        Files.deleteIfExists(stateFile);

        FileGenerationSequence sequence = new FileGenerationSequence(stateFile.toString(), 1);
        sequence.configureNextNoteNumber(11);

        assertThat(sequence.status().lastNoteNumber()).isEqualTo(10);
        assertThat(sequence.next().noteNumber()).isEqualTo(11);
    }
}

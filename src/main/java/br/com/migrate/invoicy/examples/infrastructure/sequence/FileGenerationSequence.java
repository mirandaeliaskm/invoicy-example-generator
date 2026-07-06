package br.com.migrate.invoicy.examples.infrastructure.sequence;

import br.com.migrate.invoicy.examples.domain.model.SequenceAllocation;
import br.com.migrate.invoicy.examples.domain.model.SequenceStatus;
import br.com.migrate.invoicy.examples.domain.port.GenerationSequencePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Properties;

@Component
public class FileGenerationSequence implements GenerationSequencePort {

    private static final ZoneId BRAZIL_ZONE = ZoneId.of("America/Sao_Paulo");
    private static final String LAST_NOTE_NUMBER = "lastNoteNumber";
    private static final String LAST_EMISSION_DATE_TIME = "lastEmissionDateTime";

    private final Path stateFile;
    private final long initialNoteNumber;

    public FileGenerationSequence(
            @Value("${invoicy.examples.sequence.state-file:data/generator-sequence.properties}") String stateFile,
            @Value("${invoicy.examples.sequence.initial-note-number:1}") long initialNoteNumber
    ) {
        this.stateFile = Path.of(stateFile);
        this.initialNoteNumber = initialNoteNumber;
    }

    @Override
    public synchronized SequenceAllocation next() {
        Properties properties = loadProperties();

        long lastNoteNumber = parseLong(properties.getProperty(LAST_NOTE_NUMBER), initialNoteNumber - 1);
        OffsetDateTime lastEmissionDateTime = parseOffsetDateTime(properties.getProperty(LAST_EMISSION_DATE_TIME));

        long nextNoteNumber = lastNoteNumber + 1;
        OffsetDateTime nextEmissionDateTime = nextEmissionDateTime(lastEmissionDateTime);

        properties.setProperty(LAST_NOTE_NUMBER, Long.toString(nextNoteNumber));
        properties.setProperty(LAST_EMISSION_DATE_TIME, nextEmissionDateTime.toString());
        storeProperties(properties);

        return new SequenceAllocation(
                nextNoteNumber,
                Long.toString(nextNoteNumber),
                nextEmissionDateTime
        );
    }

    @Override
    public synchronized SequenceStatus status() {
        return toStatus(loadProperties());
    }

    @Override
    public synchronized SequenceStatus configureNextNoteNumber(long nextNoteNumber) {
        if (nextNoteNumber < 1) {
            throw new IllegalArgumentException("O proximo numero da NF-e deve ser maior ou igual a 1.");
        }

        Properties properties = loadProperties();
        properties.setProperty(LAST_NOTE_NUMBER, Long.toString(nextNoteNumber - 1));
        properties.putIfAbsent(LAST_EMISSION_DATE_TIME, OffsetDateTime.now(BRAZIL_ZONE).minusSeconds(1).truncatedTo(ChronoUnit.SECONDS).toString());
        storeProperties(properties);
        return toStatus(properties);
    }

    private SequenceStatus toStatus(Properties properties) {
        long lastNoteNumber = parseLong(properties.getProperty(LAST_NOTE_NUMBER), initialNoteNumber - 1);
        OffsetDateTime lastEmissionDateTime = parseOffsetDateTime(properties.getProperty(LAST_EMISSION_DATE_TIME));
        return new SequenceStatus(lastNoteNumber, lastEmissionDateTime, stateFile.toAbsolutePath().toString());
    }

    private OffsetDateTime nextEmissionDateTime(OffsetDateTime lastEmissionDateTime) {
        OffsetDateTime now = OffsetDateTime.now(BRAZIL_ZONE).truncatedTo(ChronoUnit.SECONDS);

        if (lastEmissionDateTime == null) {
            return now;
        }

        OffsetDateTime minimumNext = lastEmissionDateTime.plusSeconds(1);
        return now.isAfter(minimumNext) ? now : minimumNext;
    }

    private Properties loadProperties() {
        Properties properties = new Properties();
        if (!Files.exists(stateFile)) {
            return properties;
        }

        try (InputStream inputStream = Files.newInputStream(stateFile)) {
            properties.load(inputStream);
            return properties;
        } catch (IOException exception) {
            throw new IllegalStateException("Nao foi possivel ler o arquivo de sequencia: " + stateFile, exception);
        }
    }

    private void storeProperties(Properties properties) {
        try {
            Path parent = stateFile.toAbsolutePath().getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
            try (OutputStream outputStream = Files.newOutputStream(stateFile)) {
                properties.store(outputStream, "InvoiCy Example Generator sequence state");
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Nao foi possivel gravar o arquivo de sequencia: " + stateFile, exception);
        }
    }

    private long parseLong(String value, long defaultValue) {
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        return Long.parseLong(value.trim());
    }

    private OffsetDateTime parseOffsetDateTime(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return OffsetDateTime.parse(value.trim());
    }
}

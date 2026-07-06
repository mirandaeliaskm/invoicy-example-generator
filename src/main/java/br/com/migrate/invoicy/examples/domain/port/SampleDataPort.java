package br.com.migrate.invoicy.examples.domain.port;

import java.util.Map;

public interface SampleDataPort {
    Map<String, Object> load(String samplePath);
}

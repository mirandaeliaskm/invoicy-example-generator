package br.com.migrate.invoicy.examples.infrastructure.sample;

import br.com.migrate.invoicy.examples.domain.port.SampleDataPort;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class ResourceSampleData implements SampleDataPort {

    private final ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory()).findAndRegisterModules();

    @Override
    public Map<String, Object> load(String samplePath) {
        try (InputStream inputStream = new ClassPathResource(samplePath).getInputStream()) {
            return yamlMapper.readValue(inputStream, new TypeReference<LinkedHashMap<String, Object>>() {});
        } catch (IOException exception) {
            throw new IllegalStateException("Não foi possível carregar o sample: " + samplePath, exception);
        }
    }
}

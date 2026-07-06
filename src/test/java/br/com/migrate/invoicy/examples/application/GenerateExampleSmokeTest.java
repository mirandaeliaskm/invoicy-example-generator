package br.com.migrate.invoicy.examples.application;

import br.com.migrate.invoicy.examples.domain.model.GenerateExampleCommand;
import br.com.migrate.invoicy.examples.domain.model.OutputFormat;
import br.com.migrate.invoicy.examples.domain.model.GeneratedExample;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GenerateExampleSmokeTest {

    @Autowired
    private GenerateExampleService service;

    @Test
    void shouldGenerateSimpleNfeExample() {
        GeneratedExample example = service.generate(new GenerateExampleCommand(
                "NFE_VENDA_SIMPLES_NACIONAL",
                Set.of(OutputFormat.XML, OutputFormat.JSON, OutputFormat.README),
                Map.of("document.ide.numero", "9999")
        ));

        assertThat(example.validationResult().isValid()).isTrue();
        assertThat(example.files().get(OutputFormat.XML)).contains("<Envio>");
        assertThat(example.files().get(OutputFormat.XML)).contains("<ModeloDocumento>NFe</ModeloDocumento>");
        assertThat(example.files().get(OutputFormat.XML)).contains("<nNF>9999</nNF>");
        assertThat(example.files().get(OutputFormat.JSON)).contains("\"nNF\": \"9999\"");
    }
}

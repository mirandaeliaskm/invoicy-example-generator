package br.com.migrate.invoicy.examples.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        "invoicy.examples.sequence.state-file=target/test-generator-sequence-smoke.properties",
        "invoicy.examples.sequence.initial-note-number=1"
})
class GenerateExampleSmokeTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void cleanSequenceState() throws Exception {
        Files.deleteIfExists(Path.of("target/test-generator-sequence-smoke.properties"));
    }

    @Test
    void shouldGenerateSimpleNfeExampleThroughApiUsingGlobalSequence() throws Exception {
        String requestBody = """
                {
                  "useCaseId": "NFE_VENDA_SIMPLES_NACIONAL",
                  "outputFormats": ["XML", "JSON", "README"],
                  "overrides": {
                    "document.ide.numero": "9999"
                  }
                }
                """;

        mockMvc.perform(post("/api/v1/examples/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valid").value(true))
                .andExpect(content().string(containsString("<Envio>")))
                .andExpect(content().string(containsString("<ModeloDocumento>NFe</ModeloDocumento>")))
                .andExpect(content().string(containsString("<nNF>1</nNF>")))
                .andExpect(content().string(not(containsString("<nNF>9999</nNF>"))))
                .andExpect(content().string(containsString("<xNome_dest>NF-E EMITIDA EM AMBIENTE DE HOMOLOGACAO - SEM VALOR FISCAL</xNome_dest>")))
                .andExpect(content().string(containsString("<CNPJ_dest>11444777000161</CNPJ_dest>")))
                .andExpect(content().string(not(containsString("<IE_dest>"))))
                .andExpect(content().string(containsString("<pagItem>")))
                .andExpect(content().string(containsString("<tPag>01</tPag>")))
                .andExpect(content().string(containsString("\\\"Documento\\\"")))
                .andExpect(content().string(containsString("\\\"det\\\": [")))
                .andExpect(content().string(containsString("\\\"pag\\\": [")))
                .andExpect(content().string(containsString("\\\"nNF\\\": 1")))
                .andExpect(content().string(not(containsString("\\\"Envio\\\""))));
    }

    @Test
    void shouldIncrementNoteNumberAcrossDifferentUseCases() throws Exception {
        String firstRequest = """
                {
                  "useCaseId": "NFE_VENDA_SIMPLES_NACIONAL",
                  "outputFormats": ["XML"],
                  "overrides": {}
                }
                """;

        String secondRequest = """
                {
                  "useCaseId": "NFE_VENDA_REGIME_NORMAL",
                  "outputFormats": ["XML"],
                  "overrides": {}
                }
                """;

        mockMvc.perform(post("/api/v1/examples/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(firstRequest))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<nNF>1</nNF>")));

        mockMvc.perform(post("/api/v1/examples/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(secondRequest))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<nNF>2</nNF>")));
    }
}

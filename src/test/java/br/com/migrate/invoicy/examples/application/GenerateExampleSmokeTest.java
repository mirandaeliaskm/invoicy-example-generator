package br.com.migrate.invoicy.examples.application;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GenerateExampleSmokeTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldGenerateSimpleNfeExampleThroughApi() throws Exception {
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
                .andExpect(content().string(org.hamcrest.Matchers.containsString("<Envio>")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("<ModeloDocumento>NFe</ModeloDocumento>")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("<nNF>9999</nNF>")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("<xNome_dest>NF-E EMITIDA EM AMBIENTE DE HOMOLOGACAO - SEM VALOR FISCAL</xNome_dest>")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("<CNPJ_dest>11444777000161</CNPJ_dest>")))
                .andExpect(content().string(org.hamcrest.Matchers.not(org.hamcrest.Matchers.containsString("<IE_dest>"))))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("<pagItem>")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("<tPag>01</tPag>")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("\\\"nNF\\\": \\\"9999\\\"")));
    }
}

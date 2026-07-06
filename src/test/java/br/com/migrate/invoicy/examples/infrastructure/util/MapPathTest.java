package br.com.migrate.invoicy.examples.infrastructure.util;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class MapPathTest {

    @Test
    void shouldReadWildcardValues() {
        Map<String, Object> context = Map.of(
                "document", Map.of(
                        "itens", List.of(
                                Map.of("produto", Map.of("cfop", "5102")),
                                Map.of("produto", Map.of("cfop", "5401"))
                        )
                )
        );

        assertThat(MapPath.getValues(context, "document.itens[*].produto.cfop"))
                .containsExactly("5102", "5401");
    }

    @Test
    void shouldApplyDotNotationSet() {
        java.util.Map<String, Object> context = new java.util.LinkedHashMap<>();
        MapPath.set(context, "document.ide.numero", "123");

        assertThat(MapPath.getFirst(context, "document.ide.numero"))
                .contains("123");
    }
}

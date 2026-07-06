package br.com.migrate.invoicy.examples.application;

import br.com.migrate.invoicy.examples.infrastructure.util.MapPath;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class FiscalCalculationServiceTest {

    private final FiscalCalculationService service = new FiscalCalculationService();

    @Test
    void shouldCalculateBasicTotals() {
        Map<String, Object> context = new LinkedHashMap<>();
        context.put("document", new LinkedHashMap<String, Object>());

        Map<String, Object> product = new LinkedHashMap<>();
        product.put("quantidade", new BigDecimal("2"));
        product.put("valorUnitario", new BigDecimal("50.00"));

        Map<String, Object> item = new LinkedHashMap<>();
        item.put("produto", product);

        MapPath.set(context, "document.itens", List.of(item));
        MapPath.set(context, "document.totais.frete", BigDecimal.ZERO);
        MapPath.set(context, "document.totais.seguro", BigDecimal.ZERO);
        MapPath.set(context, "document.totais.desconto", BigDecimal.ZERO);
        MapPath.set(context, "document.totais.outrasDespesas", BigDecimal.ZERO);

        service.calculate(context);

        assertThat(MapPath.getFirst(context, "document.totais.valorProdutos")).contains(new BigDecimal("100.00"));
        assertThat(MapPath.getFirst(context, "document.totais.valorNota")).contains(new BigDecimal("100.00"));
    }
}

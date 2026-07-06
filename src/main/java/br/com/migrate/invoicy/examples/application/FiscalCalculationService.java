package br.com.migrate.invoicy.examples.application;

import br.com.migrate.invoicy.examples.infrastructure.util.MapPath;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

@Service
public class FiscalCalculationService {

    @SuppressWarnings("unchecked")
    public void calculate(Map<String, Object> context) {
        List<Map<String, Object>> itens = (List<Map<String, Object>>) MapPath.getFirst(context, "document.itens").orElse(List.of());

        BigDecimal valorProdutos = BigDecimal.ZERO;
        BigDecimal baseCalculoIcms = BigDecimal.ZERO;
        BigDecimal valorIcms = BigDecimal.ZERO;
        BigDecimal baseCalculoSt = BigDecimal.ZERO;
        BigDecimal valorIpi = BigDecimal.ZERO;
        BigDecimal valorSt = BigDecimal.ZERO;
        BigDecimal valorPis = BigDecimal.ZERO;
        BigDecimal valorCofins = BigDecimal.ZERO;

        for (Map<String, Object> item : itens) {
            BigDecimal quantidade = valueOf(MapPath.getFirst(item, "produto.quantidade").orElse(BigDecimal.ZERO));
            BigDecimal valorUnitario = valueOf(MapPath.getFirst(item, "produto.valorUnitario").orElse(BigDecimal.ZERO));
            BigDecimal valorProduto = quantidade.multiply(valorUnitario).setScale(2, RoundingMode.HALF_UP);
            MapPath.set(item, "produto.valorProduto", valorProduto);
            valorProdutos = valorProdutos.add(valorProduto);

            baseCalculoIcms = baseCalculoIcms.add(valueOf(MapPath.getFirst(item, "impostos.icms.baseCalculo").orElse(BigDecimal.ZERO)));
            valorIcms = valorIcms.add(valueOf(MapPath.getFirst(item, "impostos.icms.valor").orElse(BigDecimal.ZERO)));

            baseCalculoSt = baseCalculoSt.add(valueOf(MapPath.getFirst(item, "impostos.icmsSt.baseCalculoST").orElse(BigDecimal.ZERO)));
            valorSt = valorSt.add(valueOf(MapPath.getFirst(item, "impostos.icmsSt.valorICMSST").orElse(BigDecimal.ZERO)));

            valorIpi = valorIpi.add(valueOf(MapPath.getFirst(item, "impostos.ipi.valorIpi").orElse(BigDecimal.ZERO)));
            valorPis = valorPis.add(valueOf(MapPath.getFirst(item, "impostos.pis.valor").orElse(BigDecimal.ZERO)));
            valorCofins = valorCofins.add(valueOf(MapPath.getFirst(item, "impostos.cofins.valor").orElse(BigDecimal.ZERO)));
        }

        BigDecimal frete = valueOf(MapPath.getFirst(context, "document.totais.frete").orElse(BigDecimal.ZERO));
        BigDecimal seguro = valueOf(MapPath.getFirst(context, "document.totais.seguro").orElse(BigDecimal.ZERO));
        BigDecimal desconto = valueOf(MapPath.getFirst(context, "document.totais.desconto").orElse(BigDecimal.ZERO));
        BigDecimal outrasDespesas = valueOf(MapPath.getFirst(context, "document.totais.outrasDespesas").orElse(BigDecimal.ZERO));

        BigDecimal valorNota = valorProdutos
                .add(frete)
                .add(seguro)
                .add(outrasDespesas)
                .add(valorIpi)
                .add(valorSt)
                .subtract(desconto)
                .setScale(2, RoundingMode.HALF_UP);

        MapPath.set(context, "document.totais.valorProdutos", scaled(valorProdutos));
        MapPath.set(context, "document.totais.baseCalculoIcms", scaled(baseCalculoIcms));
        MapPath.set(context, "document.totais.valorIcms", scaled(valorIcms));
        MapPath.set(context, "document.totais.baseCalculoST", scaled(baseCalculoSt));
        MapPath.set(context, "document.totais.valorIpi", scaled(valorIpi));
        MapPath.set(context, "document.totais.valorICMSST", scaled(valorSt));
        MapPath.set(context, "document.totais.valorPis", scaled(valorPis));
        MapPath.set(context, "document.totais.valorCofins", scaled(valorCofins));
        MapPath.set(context, "document.totais.valorNota", valorNota);
    }

    private BigDecimal scaled(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal valueOf(Object value) {
        if (value == null) {
            return BigDecimal.ZERO;
        }
        if (value instanceof BigDecimal bigDecimal) {
            return bigDecimal;
        }
        if (value instanceof Number number) {
            return BigDecimal.valueOf(number.doubleValue());
        }
        return new BigDecimal(value.toString());
    }
}

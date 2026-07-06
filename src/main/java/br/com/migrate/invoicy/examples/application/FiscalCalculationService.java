package br.com.migrate.invoicy.examples.application;

import br.com.migrate.invoicy.examples.infrastructure.util.MapPath;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

@Service
public class FiscalCalculationService {

    private static final ZoneId BRAZIL_ZONE = ZoneId.of("America/Sao_Paulo");
    private static final String HOMOLOGATION_DESTINATION_NAME =
            "NF-E EMITIDA EM AMBIENTE DE HOMOLOGACAO - SEM VALOR FISCAL";
    private static final String DEFAULT_VALID_DESTINATION_CNPJ = "11444777000161";

    @SuppressWarnings("unchecked")
    public void calculate(Map<String, Object> context) {
        applyDynamicEmissionDate(context);
        applyHomologationDefaults(context);

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

        applyDefaultPaymentAmount(context, valorNota);
    }

    private void applyDynamicEmissionDate(Map<String, Object> context) {
        String currentEmissionDate = MapPath.getFirst(context, "document.ide.dataEmissao")
                .map(Object::toString)
                .orElse("");

        if (currentEmissionDate.isBlank() || isDynamicDateMarker(currentEmissionDate) || isOutdatedOrFuture(currentEmissionDate)) {
            MapPath.set(context, "document.ide.dataEmissao", OffsetDateTime.now(BRAZIL_ZONE).truncatedTo(ChronoUnit.SECONDS).toString());
            MapPath.set(context, "document.ide.fusoHorario", "-03:00");
        }
    }

    private boolean isDynamicDateMarker(String value) {
        return "NOW".equalsIgnoreCase(value)
                || "CURRENT_TIMESTAMP".equalsIgnoreCase(value)
                || "${NOW}".equalsIgnoreCase(value);
    }

    private boolean isOutdatedOrFuture(String value) {
        try {
            OffsetDateTime emissionDate = OffsetDateTime.parse(value);
            OffsetDateTime now = OffsetDateTime.now(BRAZIL_ZONE);
            return emissionDate.isBefore(now.minusHours(24)) || emissionDate.isAfter(now.plusMinutes(5));
        } catch (Exception ignored) {
            return true;
        }
    }

    @SuppressWarnings("unchecked")
    private void applyHomologationDefaults(Map<String, Object> context) {
        String ambiente = MapPath.getFirst(context, "document.ide.tipoAmbiente")
                .map(Object::toString)
                .orElse("");

        if (!"2".equals(ambiente)) {
            return;
        }

        MapPath.set(context, "document.destinatario.razaoSocial", HOMOLOGATION_DESTINATION_NAME);
        MapPath.set(context, "document.destinatario.indicadorIE", "9");

        Object destinatarioObject = MapPath.getFirst(context, "document.destinatario").orElse(null);
        if (destinatarioObject instanceof Map<?, ?> destinatario) {
            ((Map<String, Object>) destinatario).remove("inscricaoEstadual");
        }

        boolean hasCpf = MapPath.getFirst(context, "document.destinatario.cpf").isPresent();
        boolean hasForeignId = MapPath.getFirst(context, "document.destinatario.idEstrangeiro").isPresent();
        String cnpj = MapPath.getFirst(context, "document.destinatario.cnpj")
                .map(Object::toString)
                .orElse("");

        if (!hasCpf && !hasForeignId && !isValidCnpj(cnpj)) {
            MapPath.set(context, "document.destinatario.cnpj", DEFAULT_VALID_DESTINATION_CNPJ);
        }
    }

    @SuppressWarnings("unchecked")
    private void applyDefaultPaymentAmount(Map<String, Object> context, BigDecimal valorNota) {
        Object pagamentoObject = MapPath.getFirst(context, "document.pagamento.itens[0]").orElse(null);
        if (pagamentoObject instanceof Map<?, ?> pagamento) {
            Map<String, Object> pagamentoMap = (Map<String, Object>) pagamento;
            pagamentoMap.putIfAbsent("tipoPagamento", "01");
            pagamentoMap.put("valor", scaled(valueOf(pagamentoMap.getOrDefault("valor", valorNota))));
            return;
        }

        MapPath.set(context, "document.pagamento.indicadorFormaPagamento", "0");
        MapPath.set(context, "document.pagamento.itens", List.of(Map.of(
                "tipoPagamento", "01",
                "valor", scaled(valorNota)
        )));
    }

    private boolean isValidCnpj(String cnpj) {
        if (cnpj == null || !cnpj.matches("\\d{14}") || cnpj.chars().distinct().count() == 1) {
            return false;
        }

        int firstDigit = calculateCnpjDigit(cnpj.substring(0, 12), new int[]{5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2});
        int secondDigit = calculateCnpjDigit(cnpj.substring(0, 12) + firstDigit, new int[]{6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2});

        return cnpj.equals(cnpj.substring(0, 12) + firstDigit + secondDigit);
    }

    private int calculateCnpjDigit(String base, int[] weights) {
        int sum = 0;
        for (int i = 0; i < weights.length; i++) {
            sum += Character.getNumericValue(base.charAt(i)) * weights[i];
        }
        int digit = 11 - (sum % 11);
        return digit >= 10 ? 0 : digit;
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

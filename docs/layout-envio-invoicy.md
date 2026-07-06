# Layout de Envio InvoiCy NF-e

Esta versão do gerador foi ajustada para produzir exemplos com base no **Layout de Envio InvoiCy** da planilha `Layout 4.0 NFe_NFCe.xlsx`.

## Decisão técnica

O motor do projeto mantém um modelo interno simples nos arquivos de sample, por exemplo:

```yaml
document:
  ide:
    codigoUF: "43"
    naturezaOperacao: Venda de mercadoria
```

O template é responsável por renderizar esse modelo interno para as tags efetivas aceitas pelo InvoiCy:

```xml
<ide>
  <cUF>43</cUF>
  <natOp>Venda de mercadoria</natOp>
</ide>
```

Isso evita acoplar todos os use cases diretamente a nomes técnicos como `vProd_ttlnfe`, `CNPJ_emit`, `CNPJ_dest`, `vICMS_icms`, etc., mas preserva a saída final no contrato correto.

## Estrutura de envio esperada

```xml
<Envio>
  <ModeloDocumento>NFe</ModeloDocumento>
  <Versao>4.00</Versao>
  <ide>...</ide>
  <emit>...</emit>
  <dest>...</dest>
  <det>
    <detItem>
      <prod>...</prod>
      <imposto>...</imposto>
    </detItem>
  </det>
  <total>
    <ICMStot>...</ICMStot>
  </total>
  <transp>...</transp>
  <infAdic>...</infAdic>
</Envio>
```

## Metadados extraídos

O arquivo abaixo contém os campos extraídos da planilha anexada, filtrados para NF-e modelo 55:

```text
src/main/resources/layouts/nfe/layout-envio-invoicy-4.00.yml
```

Cada campo contém:

- `id`
- `path`
- `tag`
- `description`
- `elementKind`
- `parent`
- `type`
- `occurrence`
- `required`
- `version`
- `model`

## Endpoint de consulta

```http
GET /api/v1/layouts/NFE/fields?version=4.00
```

## Próximos ajustes recomendados

1. Criar validador estrutural do XML gerado contra os metadados do layout.
2. Marcar campos condicionais (`CE`, `CG`, `GE`) com regras específicas por cenário.
3. Evoluir samples para cobrir os principais grupos ainda não usados: cobrança, pagamento, exportação detalhada, importação, volumes, intermediador e informações adicionais estruturadas.
4. Versionar novas planilhas sem sobrescrever os metadados antigos.

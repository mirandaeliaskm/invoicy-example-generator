# Fontes e governança de atualização

## Fontes recomendadas

- Portal InvoiCyForDev Brasil.
- Repositório público de integração InvoiCy BR.
- Portal NF-e/SVRS.
- Manual de Orientação do Contribuinte e schemas oficiais.
- Notas Técnicas NF-e/NFC-e vigentes.

## Governança sugerida

- Toda mudança de layout deve gerar nova versão de catálogo/ruleset.
- Toda Nota Técnica relevante deve ser registrada com impacto previsto.
- Templates publicados no portal devem passar por revisão do time BR + Qualidade.
- Samples devem usar dados fictícios e ambiente de homologação.
- Casos com dúvida tributária devem ser marcados como `requires-tax-review` antes de publicação.

## Convenção de versão

```text
rules/nfe/{layout}/{vigencia}/{cenario}.yml
templates/nfe/{layout}/{tipo}/{cenario}.ftl
samples/nfe/{layout}/{cenario}.yml
```

# Arquitetura

O projeto segue uma separação inspirada em Clean Architecture:

- `domain`: modelos, portas e regras conceituais.
- `application`: casos de uso e orquestração de geração.
- `infrastructure`: carregamento de YAML, templates, regras e empacotamento.
- `api`: controladores REST e DTOs.

## Fluxo de geração

```text
Request REST
  -> GenerateExampleService
  -> Catalog carrega use case
  -> SampleData carrega dados fictícios
  -> Overrides são aplicados
  -> Totais são calculados
  -> Rulesets YAML validam coerência mínima
  -> Templates renderizam XML/JSON/README
  -> Retorno ou ZIP
```

## Estratégia fiscal

As regras fiscais não devem ficar espalhadas no código. O código oferece um motor genérico e os detalhes ficam em YAML. Isso permite que o time de Qualidade/BR atualize regras e exemplos com menor acoplamento técnico.

## Estratégia para layout Migrate

O importador do layout oficial deve ser uma extensão futura da infraestrutura:

```text
Layout XLSX Migrate
  -> LayoutMetadataImporter
  -> FieldDefinition
  -> Ruleset base
  -> Validador de obrigatoriedade/tamanho/tipo
```

## Pontos de evolução

- Validação por XSD.
- Importação do layout XLSX.
- Versionamento de rulesets por data de vigência.
- Geração de exemplos de evento NF-e.
- Publicação automática no InvoiCyForDev.

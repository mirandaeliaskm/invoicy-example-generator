# InvoiCy Example Generator

Gerador centralizado de exemplos fiscais em **XML** e **JSON** para apoiar o onboarding técnico no InvoiCyForDev.

O projeto nasce com foco em **NF-e modelo 55 / Layout de Envio InvoiCy 4.00**. A versão atual foi ajustada para gerar a estrutura que a aplicação InvoiCy aceita, e não o XML autorizado final da SEFAZ.

## Objetivo

Automatizar e centralizar a geração de exemplos de documentos fiscais eletrônicos e payloads de integração, reduzindo retrabalho operacional, padronizando materiais de onboarding e aumentando a cobertura dos use cases mais comuns.

## Correção importante desta versão

A primeira versão do MVP usava uma estrutura próxima do XML nacional da NF-e, por exemplo:

```xml
<NFe xmlns="http://www.portalfiscal.inf.br/nfe">
  <infNFe>
    ...
  </infNFe>
</NFe>
```

Essa estrutura representa o documento fiscal autorizado, mas **não é o contrato de entrada do InvoiCy**.

A versão atual passa a gerar o **Layout de Envio InvoiCy**, conforme a planilha `Layout 4.0 NFe_NFCe.xlsx`, com raiz:

```xml
<Envio>
  <ModeloDocumento>NFe</ModeloDocumento>
  <Versao>4.00</Versao>
  <ide>...</ide>
  <emit>...</emit>
  <dest>...</dest>
  <det>
    <detItem>...</detItem>
  </det>
  <total>
    <ICMStot>...</ICMStot>
  </total>
</Envio>
```

Exemplos de diferenças aplicadas:

| Ponto | Antes | Agora |
|---|---|---|
| Raiz XML | `NFe/infNFe` | `Envio` |
| Namespace | `http://www.portalfiscal.inf.br/nfe` | Sem namespace SEFAZ no envio |
| Modelo | `ide/mod` | `ModeloDocumento=NFe` + `ide/mod=55` |
| Item | `det nItem` | `det/detItem` |
| Emitente CNPJ | `CNPJ` | `CNPJ_emit` |
| Destinatário CNPJ | `CNPJ` | `CNPJ_dest` |
| Total ICMS | `ICMSTot` | `ICMStot` |
| Valor produtos total | `vProd` | `vProd_ttlnfe` |
| Valor ICMS item | `vICMS` | `vICMS_icms` |
| Produto unidade comercial | `uCom` | `uCOM` |
| Código município fato gerador | `cMunFG` | `cMunFg` |


## Estrutura JSON REST InvoiCy

A saída JSON não espelha exatamente a hierarquia do XML de envio. Para integração REST, o gerador usa a estrutura aceita pelo InvoiCy, com uma lista na raiz e o documento dentro da chave `Documento`:

```json
[
  {
    "Documento": {
      "ModeloDocumento": "NFe",
      "Versao": 4,
      "Parametros": {
        "ApelidoLogomarca": ""
      },
      "ide": {},
      "emit": {},
      "dest": {},
      "det": [],
      "total": {},
      "transp": {},
      "pag": [],
      "infAdic": {}
    }
  }
]
```

Diferenças principais em relação ao XML:

| Ponto | XML de envio | JSON REST |
|---|---|---|
| Raiz | `<Envio>` | Array com objeto `{ "Documento": ... }` |
| Versão | `"4.00"` | `4` |
| Parâmetros | Opcional/fora do XML básico | `Documento.Parametros` |
| Itens | `det/detItem` | `det: [ ... ]` |
| Pagamentos | `pag/pagItem` | `pag: [ ... ]` |

## O que este MVP entrega

- API REST para listar use cases fiscais.
- API REST para gerar exemplos em XML, JSON e README.
- Endpoint para baixar pacote `.zip` com os artefatos gerados.
- Catálogo versionado de use cases NF-e.
- Templates FreeMarker alinhados ao Layout de Envio InvoiCy.
- Regras fiscais parametrizadas em YAML.
- Dados fictícios válidos para cenários comuns.
- Cálculo automático de totais básicos.
- Validação de campos obrigatórios, valores permitidos e totais maiores que zero.
- Metadados extraídos da planilha de layout em `src/main/resources/layouts/nfe/layout-envio-invoicy-4.00.yml`.
- Endpoint para consultar os campos do layout versionado.

## Stack

- Java 21
- Spring Boot 3.5.x
- Maven
- FreeMarker
- Jackson YAML
- JUnit 5
- OpenAPI/Swagger UI

## Como executar localmente

```bash
mvn spring-boot:run
```

Se a porta 8080 estiver ocupada no PowerShell:

```powershell
mvn spring-boot:run "-Dspring-boot.run.arguments=--server.port=8081"
```

Acesse:

```text
http://localhost:8081/api/v1/health
```

## Como executar com Docker

```bash
docker compose up --build
```

## Endpoints principais

### Health check

```http
GET /api/v1/health
```

### Listar use cases

```http
GET /api/v1/use-cases
```

### Buscar um use case

```http
GET /api/v1/use-cases/NFE_VENDA_SIMPLES_NACIONAL
```

### Listar campos do Layout de Envio InvoiCy

```http
GET /api/v1/layouts/NFE/fields?version=4.00
```

### Gerar exemplo

```http
POST /api/v1/examples/generate
Content-Type: application/json
```

```json
{
  "useCaseId": "NFE_VENDA_SIMPLES_NACIONAL",
  "outputFormats": ["XML", "JSON", "README"],
  "overrides": {
    "document.emitente.razaoSocial": "EMPRESA EXEMPLO LTDA"
  }
}
```

> A partir da versão 0.2.3, o campo `document.ide.numero` é controlado automaticamente por sequência global. Mesmo que seja enviado em `overrides`, o valor final gerado pelo motor será o próximo número disponível.

### Baixar pacote ZIP

```http
POST /api/v1/examples/generate/package
Content-Type: application/json
```

Corpo igual ao endpoint de geração.


## Sequência automática de número e emissão

A aplicação mantém uma sequência global para todos os use cases de NF-e. Isso significa que, se o último exemplo gerado saiu com `nNF = 10`, o próximo exemplo sairá com `nNF = 11`, mesmo que o novo exemplo seja de outro use case.

A data/hora de emissão (`dhEmi`) também é definida automaticamente em `America/Sao_Paulo`. Se duas gerações ocorrerem no mesmo segundo, a aplicação incrementa a próxima emissão em 1 segundo para manter a ordem temporal.

O estado da sequência fica salvo em:

```text
data/generator-sequence.properties
```

Configuração padrão em `application.yml`:

```yaml
invoicy:
  examples:
    sequence:
      state-file: data/generator-sequence.properties
      initial-note-number: 1
```

Para consultar o último número gerado:

```http
GET /api/v1/sequence/status
```

Para informar que o próximo exemplo deve começar em um número específico, por exemplo `11`:

```http
POST /api/v1/sequence/configure-next
Content-Type: application/json

{
  "nextNoteNumber": 11
}
```

Esse endpoint grava internamente `lastNoteNumber = 10`, então a próxima geração usará `nNF = 11`.

Para reiniciar a sequência manualmente em ambiente local, pare a aplicação e edite ou remova o arquivo `data/generator-sequence.properties`. Em uso real de homologação, evite apagar esse arquivo para não repetir `nNF`.

## Estrutura

```text
src/main/java/br/com/migrate/invoicy/examples
├── api
├── application
├── domain
└── infrastructure

src/main/resources
├── catalog
├── layouts
│   └── nfe
│       └── layout-envio-invoicy-4.00.yml
├── rules
├── samples
├── templates
└── xsd
```

## Como adicionar um novo use case

1. Criar um arquivo de sample em `src/main/resources/samples/nfe`.
2. Usar os nomes de negócio internos do sample, mas garantir que o template renderize as tags conforme o Layout de Envio InvoiCy.
3. Criar/reutilizar rulesets em `src/main/resources/rules/nfe`.
4. Registrar o cenário em `src/main/resources/catalog/nfe-use-cases.yml`.
5. Criar teste automatizado para o novo cenário.
6. Conferir as tags geradas contra `src/main/resources/layouts/nfe/layout-envio-invoicy-4.00.yml`.

## Roadmap sugerido

1. Substituir a extração estática por importador versionado da planilha `Layout 4.0 NFe_NFCe.xlsx`.
2. Adicionar validador estrutural que compare o XML gerado com a hierarquia da planilha.
3. Adicionar regras condicionais do Layout InvoiCy, especialmente campos CE/CG/GE.
4. Geração de Postman Collection por cenário.
5. Mapeamento completo dos retornos/rejeições mais comuns.
6. Expansão para eventos NF-e: cancelamento, CC-e, inutilização e consulta.
7. Interface administrativa para curadoria dos exemplos.
8. Integração com pipeline de publicação do InvoiCyForDev.

## Aviso técnico

Este projeto entrega um núcleo funcional e extensível para onboarding. Os exemplos são artefatos técnicos para integração com o InvoiCy e não substituem homologação fiscal, análise tributária ou validação final contra ambiente autorizador.


## Correção 0.1.1 - Envio InvoiCy / SEFAZ

- Incluído suporte ao grupo `pag/pagItem` do Layout de Envio InvoiCy.
- Ajustados samples para gerar `tPag` e `vPag`, evitando que o XML nacional seja montado com `<pag/>` vazio.
- Adicionado `docs/troubleshooting-sefaz.md` com diagnóstico para erro de schema `TISTot`.


## Ajustes de homologação SEFAZ

A partir da versão 0.2.2, o gerador aplica automaticamente saneamentos para homologação NF-e:

- `dhEmi` dinâmico usando `America/Sao_Paulo`;
- razão social do destinatário em homologação como `NF-E EMITIDA EM AMBIENTE DE HOMOLOGACAO - SEM VALOR FISCAL`;
- validação/saneamento de CNPJ do destinatário;
- omissão de `IE_dest` quando `indIEDest=9`;
- grupo `pag/pagItem` gerado conforme o Layout de Envio InvoiCy.

Os XSDs anexados foram mantidos em `src/main/resources/xsd/invoicy` como referência de contrato do Layout de Envio. O erro `TISTot` não pertence ao Layout de Envio InvoiCy; ele ocorre no XML nacional NF-e após a conversão interna.


## Correção 0.2.3 - Sequência global

- Adicionado controle persistente de sequência global de `nNF`.
- `nNF` passa a ser gerado automaticamente para todos os use cases.
- `dhEmi` passa a ser gerado junto da sequência e incrementado em pelo menos 1 segundo quando necessário.
- Adicionado endpoint `GET /api/v1/sequence/status`.
- O arquivo `data/generator-sequence.properties` foi incluído no `.gitignore`.


## Correção 0.2.4 - JSON REST InvoiCy

- Alterado template JSON para raiz `[{ "Documento": { ... } }]`.
- Adicionado cabeçalho `Documento.Parametros`.
- Alterado `det` no JSON de objeto `detItem` para array direto.
- Alterado `pag` no JSON de objeto `pagItem` para array direto.
- Mantido XML de envio como `<Envio>`, pois este formato já foi validado e autorizado em homologação.

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
    "document.ide.numero": "12345",
    "document.emitente.razaoSocial": "EMPRESA EXEMPLO LTDA"
  }
}
```

### Baixar pacote ZIP

```http
POST /api/v1/examples/generate/package
Content-Type: application/json
```

Corpo igual ao endpoint de geração.

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

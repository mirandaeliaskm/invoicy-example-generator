# InvoiCy Example Generator

Gerador centralizado de exemplos fiscais em **XML** e **JSON** para apoiar o onboarding técnico no InvoiCyForDev.

O projeto nasce com foco em **NF-e modelo 55 / layout 4.00**, mas a estrutura foi desenhada para expansão para NFC-e, CT-e, MDF-e, NFS-e, NFCom e módulos internacionais.

## Objetivo

Automatizar e centralizar a geração de exemplos de documentos fiscais eletrônicos e payloads de integração, reduzindo retrabalho operacional, padronizando materiais de onboarding e aumentando a cobertura dos use cases mais comuns.

## O que este MVP entrega

- API REST para listar use cases fiscais.
- API REST para gerar exemplos em XML, JSON e README.
- Endpoint para baixar pacote `.zip` com os artefatos gerados.
- Catálogo versionado de use cases NF-e.
- Templates FreeMarker para XML, JSON e documentação.
- Regras fiscais parametrizadas em YAML.
- Dados fictícios válidos para cenários comuns.
- Cálculo automático de totais básicos.
- Validação de campos obrigatórios, valores permitidos e totais maiores que zero.
- Estrutura pronta para adicionar validação XSD e importação do layout oficial da Migrate.

## Referências funcionais

Este projeto foi modelado para trabalhar em conjunto com:

- Portal InvoiCyForDev Brasil.
- Layout de emissão NF-e/NFC-e publicado no GitHub da Migrate.
- Documentação nacional da NF-e e Notas Técnicas vigentes.

Os exemplos gerados são **artefatos técnicos de onboarding**. Eles não substituem homologação fiscal, análise tributária ou validação final contra SEFAZ/InvoiCy.

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

Acesse:

```text
http://localhost:8080/swagger-ui.html
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
├── rules
├── samples
├── templates
└── xsd
```

## Como adicionar um novo use case

1. Criar um arquivo de sample em `src/main/resources/samples/nfe`.
2. Criar ou reutilizar templates em `src/main/resources/templates/nfe`.
3. Criar/reutilizar rulesets em `src/main/resources/rules/nfe`.
4. Registrar o cenário em `src/main/resources/catalog/nfe-use-cases.yml`.
5. Criar teste automatizado para o novo cenário.

## Roadmap sugerido

1. Importador do `Layout 4.0 NFe_NFCe.xlsx` para gerar metadados de campos.
2. Validação por XSD oficial.
3. Geração de Postman Collection por cenário.
4. Mapeamento completo dos retornos/rejeições mais comuns.
5. Expansão para eventos NF-e: cancelamento, CC-e, inutilização e consulta.
6. Interface administrativa para curadoria dos exemplos.
7. Integração com pipeline de publicação do InvoiCyForDev.

## Aviso técnico

Este projeto entrega um núcleo funcional e extensível. Os templates iniciais são exemplos didáticos e devem ser conferidos com o layout de emissão oficial mais recente da Migrate antes de publicação externa em produção.

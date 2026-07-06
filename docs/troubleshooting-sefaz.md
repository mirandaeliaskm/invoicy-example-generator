# Troubleshooting SEFAZ - NF-e Homologação

## Rejeição: razão social do destinatário em homologação

Quando `tpAmb = 2`, a razão social do destinatário deve ser gerada como:

```text
NF-E EMITIDA EM AMBIENTE DE HOMOLOGACAO - SEM VALOR FISCAL
```

O gerador aplica essa regra automaticamente para evitar a rejeição de homologação.

## Rejeição: data de emissão muito atrasada

Os samples não devem usar `dhEmi` fixo. A versão atual aceita `dataEmissao: NOW` e substitui automaticamente por `OffsetDateTime.now(America/Sao_Paulo)`.

## Rejeição: CNPJ do destinatário inválido

O gerador valida o dígito verificador do CNPJ do destinatário. Em homologação, se o CNPJ informado for inválido e não houver CPF/idEstrangeiro, o gerador substitui por um CNPJ sintaticamente válido para testes.

## Erro: Type 'http://www.portalfiscal.inf.br/nfe:TISTot' is not declared

Esse erro ocorre depois que o InvoiCy converte o Layout de Envio para o XML nacional da NF-e. O XSD de entrada do InvoiCy não declara `TISTot`; portanto, esse erro normalmente indica inconsistência no pacote de schemas nacionais NF-e/NFC-e usado na validação/autorização, especialmente em cenários com NTs recentes da Reforma Tributária do Consumo.

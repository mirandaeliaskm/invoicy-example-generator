# Sequência automática de NF-e

A versão 0.2.3 adiciona um mecanismo persistente para controlar o número da NF-e (`nNF`) e a data/hora de emissão (`dhEmi`).

## Objetivo

Evitar que cada use case tenha um número fixo de nota e impedir repetição de `nNF` durante os testes em homologação.

## Regra implementada

- A sequência é global para todos os use cases.
- O próximo `nNF` sempre será o último número salvo + 1.
- O valor informado em `overrides.document.ide.numero` é ignorado pelo motor de sequência.
- A data/hora de emissão é gerada automaticamente em `America/Sao_Paulo`.
- Se duas gerações ocorrerem no mesmo segundo, a próxima emissão recebe +1 segundo.

Exemplo:

```text
Geração 1: useCase=NFE_VENDA_SIMPLES_NACIONAL -> nNF=10
Geração 2: useCase=NFE_VENDA_REGIME_NORMAL    -> nNF=11
Geração 3: useCase=NFE_DEVOLUCAO_MERCADORIA   -> nNF=12
```

## Arquivo de estado

Por padrão, a sequência é salva em:

```text
data/generator-sequence.properties
```

Conteúdo típico:

```properties
lastNoteNumber=11
lastEmissionDateTime=2026-05-17T20:30:12-03:00
```

## Configuração

```yaml
invoicy:
  examples:
    sequence:
      state-file: data/generator-sequence.properties
      initial-note-number: 1
```

## Consulta da sequência

```http
GET /api/v1/sequence/status
```

## Cuidados

Em homologação real, não remova o arquivo de sequência sem necessidade. Se ele for apagado, o sistema voltará para o número inicial configurado e poderá repetir `nNF`.

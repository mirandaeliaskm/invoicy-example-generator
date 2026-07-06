# JSON REST InvoiCy - NF-e/NFC-e 4.00

Esta versão diferencia a estrutura XML de envio da estrutura JSON usada na integração REST.

## Estrutura raiz

O JSON deve iniciar com uma lista, contendo um objeto com a chave `Documento`:

```json
[
  {
    "Documento": {
      "ModeloDocumento": "NFe",
      "Versao": 4,
      "Parametros": {
        "ApelidoLogomarca": ""
      }
    }
  }
]
```

## Diferenças mantidas no gerador

- XML continua usando `<Envio>`.
- JSON usa `Documento` dentro de uma lista.
- JSON usa `det` como array direto.
- JSON usa `pag` como array direto.
- `Versao` no JSON sai como número `4`; no XML permanece `4.00`.

## Exemplo resumido

```json
[
  {
    "Documento": {
      "ModeloDocumento": "NFe",
      "Versao": 4,
      "Parametros": { "ApelidoLogomarca": "" },
      "ide": { "nNF": 11 },
      "det": [ { "prod": {}, "imposto": {} } ],
      "pag": [ { "tPag": "01", "vPag": "100.00" } ]
    }
  }
]
```

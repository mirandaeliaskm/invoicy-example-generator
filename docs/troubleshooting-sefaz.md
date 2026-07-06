# Troubleshooting - Envio NF-e InvoiCy para SEFAZ

## Erro: `Type http://www.portalfiscal.inf.br/nfe:TISTot is not declared`

Esse erro não é uma rejeição fiscal comum por CST, CFOP, valor ou CNPJ. A mensagem indica falha de validação por XSD/schema: algum schema usado na validação referencia o tipo `TISTot`, mas o conjunto de schemas carregado não contém a declaração correspondente.

A causa mais provável é mistura de pacotes de schema da NF-e/NFC-e, especialmente após as versões da NT 2025.002 relacionadas à Reforma Tributária do Consumo. O pacote de schemas deve ser atualizado de forma completa e consistente, sem combinar arquivos novos com `tiposBasico` ou includes antigos.

## Pontos do XML de exemplo

- O Layout de Envio InvoiCy usa `Envio/total/ICMStot`.
- O XML nacional montado para a SEFAZ usa `NFe/infNFe/total/ICMSTot`.
- O erro cita `TISTot`, não `ICMStot` nem `ICMSTot`. Portanto, o problema não é apenas diferença de maiúscula/minúscula nesse grupo.
- A versão anterior do gerador não informava o grupo `pag/pagItem`, fazendo o XML nacional sair com `<pag/>`. Isso foi ajustado para gerar `pag/pagItem/tPag` e `pag/pagItem/vPag`.

## Ação recomendada

1. Validar se o ambiente InvoiCy usado no teste está com o pacote completo de schemas NF-e/NFC-e atualizado.
2. Confirmar se todos os XSDs pertencem ao mesmo pacote de liberação.
3. Reemitir o exemplo com o grupo `pag/pagItem` preenchido.
4. Conferir a série gerada no XML nacional. Para NF-e modelo 55, a série deve ser compatível com a regra fiscal e com a configuração do emitente no InvoiCy.

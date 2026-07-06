# ${document.identificador}

Exemplo fiscal gerado automaticamente para onboarding técnico InvoiCyForDev.

## Dados principais

| Campo | Valor |
|---|---|
| Modelo | ${document.ide.modelo} |
| Versão do layout | ${document.layoutVersion} |
| Natureza da operação | ${document.ide.naturezaOperacao} |
| Série | ${document.ide.serie} |
| Número | ${document.ide.numero} |
| Ambiente | ${document.ide.tipoAmbiente} |
| Finalidade | ${document.ide.finalidade} |
| CRT emitente | ${document.emitente.crt} |
| Valor dos produtos | ${document.totais.valorProdutos?string["0.00"]} |
| Valor da nota | ${document.totais.valorNota?string["0.00"]} |

## Quando usar

Use este exemplo como base de integração e validação de payloads durante o onboarding técnico.

## Observações

- Os dados de emitente, destinatário e produtos são fictícios.
- O ambiente padrão dos samples é homologação.
- A estrutura deve ser conferida com o layout de emissão NF-e/NFC-e mais recente da Migrate antes de publicação externa.
- Este exemplo não substitui validação fiscal, homologação junto à SEFAZ ou análise tributária do contribuinte.

## Próximos passos recomendados

1. Validar o XML contra o XSD oficial da NF-e.
2. Validar o JSON contra o layout REST vigente do InvoiCy.
3. Executar envio em ambiente de homologação.
4. Registrar rejeições ou ajustes no catálogo de use cases.

{
  "DocumentoFiscal": {
    "Modelo": "${document.ide.modelo}",
    "VersaoLayout": "${document.layoutVersion}",
    "IdentificadorExemplo": "${document.identificador}",
    "Ide": {
      "cUF": "${document.ide.codigoUF}",
      "natOp": "${document.ide.naturezaOperacao}",
      "mod": "${document.ide.modelo}",
      "serie": "${document.ide.serie}",
      "nNF": "${document.ide.numero}",
      "dhEmi": "${document.ide.dataEmissao}",
      "tpNF": "${document.ide.tipoOperacao}",
      "idDest": "${document.ide.identificadorDestino}",
      "cMunFG": "${document.ide.codigoMunicipioFatoGerador}",
      "tpAmb": "${document.ide.tipoAmbiente}",
      "finNFe": "${document.ide.finalidade}",
      "indFinal": "${document.ide.consumidorFinal}",
      "indPres": "${document.ide.indicadorPresenca}"
    },
    "Emitente": {
      "CNPJ": "${document.emitente.cnpj}",
      "xNome": "${document.emitente.razaoSocial}",
      "xFant": "${document.emitente.nomeFantasia!document.emitente.razaoSocial}",
      "IE": "${document.emitente.inscricaoEstadual}",
      "CRT": "${document.emitente.crt}"
    },
    "Destinatario": {
      <#if document.destinatario.cnpj??>"CNPJ": "${document.destinatario.cnpj}",</#if>
      <#if document.destinatario.idEstrangeiro??>"idEstrangeiro": "${document.destinatario.idEstrangeiro}",</#if>
      "xNome": "${document.destinatario.razaoSocial}",
      "indIEDest": "${document.destinatario.indicadorIE}"
    },
    "Itens": [
      <#list document.itens as item>
      {
        "nItem": ${item.numero},
        "Produto": {
          "cProd": "${item.produto.codigo}",
          "cEAN": "${item.produto.ean}",
          "xProd": "${item.produto.descricao}",
          "NCM": "${item.produto.ncm}",
          "CFOP": "${item.produto.cfop}",
          "uCom": "${item.produto.unidade}",
          "qCom": ${item.produto.quantidade?string["0.0000"]},
          "vUnCom": ${item.produto.valorUnitario?string["0.00"]},
          "vProd": ${item.produto.valorProduto?string["0.00"]}
        },
        "Impostos": {
          "ICMS": {
            "orig": "${item.impostos.icms.origem}"
            <#if item.impostos.icms.csosn??>, "CSOSN": "${item.impostos.icms.csosn}"</#if>
            <#if item.impostos.icms.cst??>, "CST": "${item.impostos.icms.cst}", "vBC": ${(item.impostos.icms.baseCalculo!0)?string["0.00"]}, "pICMS": ${(item.impostos.icms.aliquota!0)?string["0.00"]}, "vICMS": ${(item.impostos.icms.valor!0)?string["0.00"]}</#if>
          }
          <#if item.impostos.icmsSt??>,
          "ICMSST": {
            "vBCST": ${(item.impostos.icmsSt.baseCalculoST!0)?string["0.00"]},
            "pICMSST": ${(item.impostos.icmsSt.aliquotaST!0)?string["0.00"]},
            "vICMSST": ${(item.impostos.icmsSt.valorICMSST!0)?string["0.00"]}
          }
          </#if>
          <#if item.impostos.ipi??>,
          "IPI": {
            "CST": "${item.impostos.ipi.cst}",
            "cEnq": "${item.impostos.ipi.codigoEnquadramento!"999"}",
            "vBC": ${(item.impostos.ipi.baseCalculo!0)?string["0.00"]},
            "pIPI": ${(item.impostos.ipi.aliquota!0)?string["0.00"]},
            "vIPI": ${(item.impostos.ipi.valorIpi!0)?string["0.00"]}
          }
          </#if>
        }
      }<#if item_has_next>,</#if>
      </#list>
    ],
    "Totais": {
      "vProd": ${document.totais.valorProdutos?string["0.00"]},
      "vFrete": ${document.totais.frete?string["0.00"]},
      "vSeg": ${document.totais.seguro?string["0.00"]},
      "vDesc": ${document.totais.desconto?string["0.00"]},
      "vIPI": ${document.totais.valorIpi?string["0.00"]},
      "vST": ${document.totais.valorICMSST?string["0.00"]},
      "vOutro": ${document.totais.outrasDespesas?string["0.00"]},
      "vNF": ${document.totais.valorNota?string["0.00"]}
    },
    "Transporte": {
      "modFrete": "${document.transporte.modalidadeFrete}"
    },
    "InformacoesAdicionais": {
      "infCpl": "${document.informacoesAdicionais.complementares}"
    }
  }
}

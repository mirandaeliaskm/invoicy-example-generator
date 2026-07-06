{
  "Envio": {
    "ModeloDocumento": "${document.modeloDocumento!"NFe"}",
    "Versao": "${document.layoutVersion}",
    "ide": {
      "cUF": "${document.ide.codigoUF}",
      "natOp": "${document.ide.naturezaOperacao}",
      "mod": "${document.ide.modelo}",
      "serie": "${document.ide.serie}",
      "nNF": "${document.ide.numero}",
      "dhEmi": "${document.ide.dataEmissao}",
      "fusoHorario": "${document.ide.fusoHorario!"-03:00"}",
      "tpNf": "${document.ide.tipoOperacao}",
      "idDest": "${document.ide.identificadorDestino}",
      "indFinal": "${document.ide.consumidorFinal}",
      "indPres": "${document.ide.indicadorPresenca}",
      "cMunFg": "${document.ide.codigoMunicipioFatoGerador}",
      "tpImp": "${document.ide.tipoImpressao!"1"}",
      "tpEmis": "${document.ide.tipoEmissao!"1"}",
      "tpAmb": "${document.ide.tipoAmbiente}",
      "finNFe": "${document.ide.finalidade}"<#if document.documentosReferenciados?? && document.documentosReferenciados?size gt 0>,
      "NFRef": {
        "NFRefItem": [
          <#list document.documentosReferenciados as ref>
          { "refNFe": "${ref.chaveNFe}" }<#if ref_has_next>,</#if>
          </#list>
        ]
      }</#if>
    },
    "emit": {
      "CNPJ_emit": "${document.emitente.cnpj}",
      "xNome": "${document.emitente.razaoSocial}",
      "xFant": "${document.emitente.nomeFantasia!document.emitente.razaoSocial}",
      "IE": "${document.emitente.inscricaoEstadual}",
      "CRT": "${document.emitente.crt}",
      "enderEmit": {
        "xLgr": "${document.emitente.endereco.logradouro}",
        "nro": "${document.emitente.endereco.numero}",
        "xBairro": "${document.emitente.endereco.bairro}",
        "cMun": "${document.emitente.endereco.codigoMunicipio}",
        "xMun": "${document.emitente.endereco.municipio}",
        "UF": "${document.emitente.endereco.uf}",
        "CEP": "${document.emitente.endereco.cep}",
        "cPais": "${document.emitente.endereco.codigoPais}",
        "xPais": "${document.emitente.endereco.pais}"
      }
    },
    "dest": {
      <#if document.destinatario.cnpj??>"CNPJ_dest": "${document.destinatario.cnpj}",</#if>
      <#if document.destinatario.cpf??>"CPF_dest": "${document.destinatario.cpf}",</#if>
      <#if document.destinatario.idEstrangeiro??>"idEstrangeiro": "${document.destinatario.idEstrangeiro}",</#if>
      "xNome_dest": "${document.destinatario.razaoSocial}",
      "indIEDest": "${document.destinatario.indicadorIE}",
      "enderDest": {
        "nro_dest": "${document.destinatario.endereco.numero}",
        "xBairro_dest": "${document.destinatario.endereco.bairro}",
        "xLgr_dest": "${document.destinatario.endereco.logradouro}",
        "xPais_dest": "${document.destinatario.endereco.pais}",
        "cMun_dest": "${document.destinatario.endereco.codigoMunicipio}",
        "xMun_dest": "${document.destinatario.endereco.municipio}",
        "UF_dest": "${document.destinatario.endereco.uf}",
        "CEP_dest": "${document.destinatario.endereco.cep}",
        "cPais_dest": "${document.destinatario.endereco.codigoPais}"
      }
    },
    "det": {
      "detItem": [
        <#list document.itens as item>
        {
          "prod": {
            "cProd": "${item.produto.codigo}",
            "cEAN": "${item.produto.ean}",
            "xProd": "${item.produto.descricao}",
            "NCM": "${item.produto.ncm}",
            "CFOP": "${item.produto.cfop}",
            "uCOM": "${item.produto.unidade}",
            "qCOM": ${item.produto.quantidade?string["0.0000"]},
            "vUnCom": ${item.produto.valorUnitario?string["0.00"]},
            "vProd": ${item.produto.valorProduto?string["0.00"]},
            "cEANTrib": "${item.produto.eanTributavel!item.produto.ean}",
            "uTrib": "${item.produto.unidadeTributavel!item.produto.unidade}",
            "qTrib": ${(item.produto.quantidadeTributavel!item.produto.quantidade)?string["0.0000"]},
            "vUnTrib": ${(item.produto.valorUnitarioTributavel!item.produto.valorUnitario)?string["0.00"]},
            "indTot": "${item.produto.indicadorTotal!"1"}",
            "nTipoItem": "${item.produto.tipoItem!"0"}"
          },
          "imposto": {
            "ICMS": {
              "orig": "${item.impostos.icms.origem}",
              "CST": "${item.impostos.icms.cst!item.impostos.icms.csosn}"<#if item.impostos.icms.baseCalculo??>,
              "modBC": "${item.impostos.icms.modalidadeBaseCalculo!"3"}",
              "vBC": ${item.impostos.icms.baseCalculo?string["0.00"]},
              "pICMS": ${item.impostos.icms.aliquota?string["0.00"]},
              "vICMS_icms": ${item.impostos.icms.valor?string["0.00"]}</#if><#if item.impostos.icmsSt??>,
              "modBCST": "${item.impostos.icmsSt.modalidadeBaseCalculoST!"4"}",
              "pMVAST": ${(item.impostos.icmsSt.mvaST!0)?string["0.00"]},
              "vBCST": ${(item.impostos.icmsSt.baseCalculoST!0)?string["0.00"]},
              "pICMSST": ${(item.impostos.icmsSt.aliquotaST!0)?string["0.00"]},
              "vICMSST_icms": ${(item.impostos.icmsSt.valorICMSST!0)?string["0.00"]}</#if>
            }<#if item.impostos.ipi??>,
            "IPI": {
              "cEnq": "${item.impostos.ipi.codigoEnquadramento!"999"}",
              "CSTIPI": {
                "CST_IPI": "${item.impostos.ipi.cst}",
                "vBC_IPI": ${(item.impostos.ipi.baseCalculo!0)?string["0.00"]},
                "pIPI": ${(item.impostos.ipi.aliquota!0)?string["0.00"]},
                "vIPI": ${(item.impostos.ipi.valorIpi!0)?string["0.00"]}
              }
            }</#if><#if item.impostos.pis??>,
            "PIS": {
              "CST_pis": "${item.impostos.pis.cst}",
              "vBC_pis": ${(item.impostos.pis.baseCalculo!0)?string["0.00"]},
              "pPIS": ${(item.impostos.pis.aliquota!0)?string["0.00"]},
              "vPIS": ${(item.impostos.pis.valor!0)?string["0.00"]}
            }</#if><#if item.impostos.cofins??>,
            "COFINS": {
              "CST_cofins": "${item.impostos.cofins.cst}",
              "vBC_cofins": ${(item.impostos.cofins.baseCalculo!0)?string["0.00"]},
              "pCOFINS": ${(item.impostos.cofins.aliquota!0)?string["0.00"]},
              "vCOFINS": ${(item.impostos.cofins.valor!0)?string["0.00"]}
            }</#if>
          }
        }<#if item_has_next>,</#if>
        </#list>
      ]
    },
    "total": {
      "ICMStot": {
        "vBC_ttlnfe": ${(document.totais.baseCalculoIcms!0)?string["0.00"]},
        "vICMS_ttlnfe": ${(document.totais.valorIcms!0)?string["0.00"]},
        "vICMSDeson_ttlnfe": ${(document.totais.valorIcmsDesonerado!0)?string["0.00"]},
        "vBCST_ttlnfe": ${(document.totais.baseCalculoST!0)?string["0.00"]},
        "vST_ttlnfe": ${document.totais.valorICMSST?string["0.00"]},
        "vProd_ttlnfe": ${document.totais.valorProdutos?string["0.00"]},
        "vFrete_ttlnfe": ${document.totais.frete?string["0.00"]},
        "vSeg_ttlnfe": ${document.totais.seguro?string["0.00"]},
        "vDesc_ttlnfe": ${document.totais.desconto?string["0.00"]},
        "vII_ttlnfe": ${(document.totais.valorII!0)?string["0.00"]},
        "vIPI_ttlnfe": ${document.totais.valorIpi?string["0.00"]},
        "vPIS_ttlnfe": ${(document.totais.valorPis!0)?string["0.00"]},
        "vCOFINS_ttlnfe": ${(document.totais.valorCofins!0)?string["0.00"]},
        "vOutro": ${document.totais.outrasDespesas?string["0.00"]},
        "vNF": ${document.totais.valorNota?string["0.00"]}
      }
    },
    "transp": {
      "modFrete": "${document.transporte.modalidadeFrete}"
    },
    "infAdic": {
      "infCpl": "${document.informacoesAdicionais.complementares}"
    }
  }
}

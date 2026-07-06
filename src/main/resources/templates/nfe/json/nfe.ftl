[
  {
    "Documento": {
      "ModeloDocumento": "${document.modeloDocumento!"NFe"}",
      "Versao": 4,
      "Parametros": {
        "ApelidoLogomarca": "${((document.parametros.apelidoLogomarca)!"")}"
      },
      "ide": {
        <#if document.ide.codigoNumerico??>"cNF": ${document.ide.codigoNumerico},</#if>
        "cUF": ${document.ide.codigoUF},
        "natOp": "${document.ide.naturezaOperacao}",
        "mod": "${document.ide.modelo}",
        "serie": "${document.ide.serie}",
        "nNF": ${document.ide.numero},
        "dhEmi": "${document.ide.dataEmissao?replace("-03:00", "")}",
        "fusoHorario": "${document.ide.fusoHorario!"-03:00"}",
        <#if document.ide.dataSaidaEntrada??>"dhSaiEnt": "${document.ide.dataSaidaEntrada}",<#else>"dhSaiEnt": "0000-00-00T00:00:00",</#if>
        "tpNf": ${document.ide.tipoOperacao},
        "idDest": ${document.ide.identificadorDestino},
        "indFinal": ${document.ide.consumidorFinal},
        "indPres": ${document.ide.indicadorPresenca},
        "cMunFg": ${document.ide.codigoMunicipioFatoGerador},
        "tpImp": ${document.ide.tipoImpressao!"1"},
        "tpEmis": ${document.ide.tipoEmissao!"1"},
        "tpAmb": ${document.ide.tipoAmbiente},
        "xJust": "${document.ide.justificativaContingencia!""}",
        <#if document.ide.dataContingencia??>"dhCont": "${document.ide.dataContingencia}",<#else>"dhCont": "0000-00-00T00:00:00",</#if>
        "finNFe": ${document.ide.finalidade}<#if document.ide.emailArquivos??>,
        "EmailArquivos": "${document.ide.emailArquivos}"</#if><#if document.ide.numeroPedido??>,
        "NumeroPedido": "${document.ide.numeroPedido}"</#if><#if document.documentosReferenciados?? && document.documentosReferenciados?size gt 0>,
        "NFRef": [
          <#list document.documentosReferenciados as ref>
          { "refNFe": "${ref.chaveNFe}" }<#if ref_has_next>,</#if>
          </#list>
        ]</#if>
      },
      "emit": {
        "CNPJ_emit": "${document.emitente.cnpj}",
        "CPF_emit": "${document.emitente.cpf!""}",
        "xNome": "${document.emitente.razaoSocial}",
        "xFant": "${document.emitente.nomeFantasia!document.emitente.razaoSocial}",
        "IM": "${document.emitente.inscricaoMunicipal!""}",
        "CNAE": "${document.emitente.cnae!""}",
        "IE": "${document.emitente.inscricaoEstadual}",
        "IEST": "${document.emitente.ieSubstitutoTributario!""}",
        "CRT": ${document.emitente.crt},
        "enderEmit": {
          "xLgr": "${document.emitente.endereco.logradouro}",
          "nro": "${document.emitente.endereco.numero}",
          "xCpl": "${document.emitente.endereco.complemento!""}",
          "xBairro": "${document.emitente.endereco.bairro}",
          "cMun": ${document.emitente.endereco.codigoMunicipio},
          "xMun": "${document.emitente.endereco.municipio}",
          "UF": "${document.emitente.endereco.uf}",
          "CEP": ${document.emitente.endereco.cep},
          "cPais": ${document.emitente.endereco.codigoPais},
          "xPais": "${document.emitente.endereco.pais}",
          "fone": ${document.emitente.endereco.telefone!0},
          "Email": "${document.emitente.email!""}"
        }
      },
      "dest": {
        "CNPJ_dest": "${document.destinatario.cnpj!""}",
        "CPF_dest": "${document.destinatario.cpf!""}",
        "idEstrangeiro": "${document.destinatario.idEstrangeiro!""}",
        "xNome_dest": "${document.destinatario.razaoSocial}",
        "IE_dest": "${document.destinatario.inscricaoEstadual!""}",
        "indIEDest": ${document.destinatario.indicadorIE},
        "IM_dest": "${document.destinatario.inscricaoMunicipal!""}",
        "enderDest": {
          "nro_dest": "${document.destinatario.endereco.numero}",
          "xCpl_dest": "${document.destinatario.endereco.complemento!""}",
          "xBairro_dest": "${document.destinatario.endereco.bairro}",
          "xEmail_dest": "${document.destinatario.email!""}",
          "xLgr_dest": "${document.destinatario.endereco.logradouro}",
          "xPais_dest": "${document.destinatario.endereco.pais}",
          "cMun_dest": ${document.destinatario.endereco.codigoMunicipio},
          "xMun_dest": "${document.destinatario.endereco.municipio}",
          "UF_dest": "${document.destinatario.endereco.uf}",
          "CEP_dest": ${document.destinatario.endereco.cep},
          "cPais_dest": ${document.destinatario.endereco.codigoPais},
          "fone_dest": ${document.destinatario.endereco.telefone!0}
        }
      },
      "det": [
        <#list document.itens as item>
        {
          "infADProd": "${item.informacoesAdicionais!""}",
          "prod": {
            "cProd": "${item.produto.codigo}",
            "cEAN": "${item.produto.ean}",
            "xProd": "${item.produto.descricao}",
            "NCM": "${item.produto.ncm}",
            "CFOP": ${item.produto.cfop},
            "uCOM": "${item.produto.unidade}",
            "qCOM": "${item.produto.quantidade?string["0.0000"]}",
            "vUnCom": "${item.produto.valorUnitario?string["0.0000000000"]}",
            "vProd": "${item.produto.valorProduto?string["0.00"]}",
            "cEANTrib": "${item.produto.eanTributavel!item.produto.ean}",
            "uTrib": "${item.produto.unidadeTributavel!item.produto.unidade}",
            "qTrib": "${(item.produto.quantidadeTributavel!item.produto.quantidade)?string["0.0000"]}",
            "vUnTrib": "${(item.produto.valorUnitarioTributavel!item.produto.valorUnitario)?string["0.0000000000"]}",
            "vFrete": "${(item.produto.frete!0)?string["0.00"]}",
            "vSeg": "${(item.produto.seguro!0)?string["0.00"]}",
            "vDesc": "${(item.produto.desconto!0)?string["0.00"]}",
            "vOutro_item": "${(item.produto.outrasDespesas!0)?string["0.00"]}",
            "indTot": ${item.produto.indicadorTotal!"1"},
            "nTipoItem": ${item.produto.tipoItem!"0"},
            "CEST": "${item.produto.cest!""}",
            "cBenef": "${item.produto.codigoBeneficio!""}",
            "NVEs": [],
            "detDI": [],
            "detExport": [],
            "med": [],
            "arma": [],
            "Rastro": []
          },
          "imposto": {
            "ICMS": {
              "orig": ${item.impostos.icms.origem},
              "CST": "${item.impostos.icms.cst!item.impostos.icms.csosn}",
              "modBC": "${item.impostos.icms.modalidadeBaseCalculo!"0"}",
              "vBC": "${(item.impostos.icms.baseCalculo!0)?string["0.00"]}",
              "pICMS": ${(item.impostos.icms.aliquota!0)?string["0.##"]},
              "vICMS_icms": "${(item.impostos.icms.valor!0)?string["0.00"]}",
              "modBCST": ${((item.impostos.icmsSt.modalidadeBaseCalculoST)!0)?string["0"]},
              "pMVAST": ${((item.impostos.icmsSt.mvaST)!0)?string["0.##"]},
              "pRedBCST": ${((item.impostos.icmsSt.reducaoBaseCalculoST)!0)?string["0.##"]},
              "vBCST": "${((item.impostos.icmsSt.baseCalculoST)!0)?string["0.00"]}",
              "pICMSST": ${((item.impostos.icmsSt.aliquotaST)!0)?string["0.##"]},
              "vICMSST_icms": "${((item.impostos.icmsSt.valorICMSST)!0)?string["0.00"]}",
              "pRedBC": ${(item.impostos.icms.reducaoBaseCalculo!0)?string["0.##"]},
              "motDesICMS": ${(item.impostos.icms.motivoDesoneracao!0)?string["0"]},
              "vICMSDeson": "${(item.impostos.icms.valorDesonerado!0)?string["0.00"]}",
              "pCredSN": ${(item.impostos.icms.percentualCreditoSN!0)?string["0.##"]},
              "vCredICMSSN": "${(item.impostos.icms.valorCreditoSN!0)?string["0.00"]}",
              "pFCP": ${(item.impostos.icms.percentualFCP!0)?string["0.##"]},
              "vFCP": "${(item.impostos.icms.valorFCP!0)?string["0.00"]}"
            },
            "IPI": {
              "clEnq": "",
              "CNPJProd": "",
              "cSelo": "",
              "qSelo": 0,
              "cEnq": "${((item.impostos.ipi.codigoEnquadramento)!"")}",
              "CSTIPI": {
                "CST_IPI": "${((item.impostos.ipi.cst)!"")}",
                "vBC_IPI": "${((item.impostos.ipi.baseCalculo)!0)?string["0.00"]}",
                "qUnid_IPI": "0.0000",
                "vUnid_IPI": "0.0000",
                "pIPI": ${((item.impostos.ipi.aliquota)!0)?string["0.##"]},
                "vIPI": "${((item.impostos.ipi.valorIpi)!0)?string["0.00"]}"
              }
            },
            "II": {
              "vBC_imp": "${((item.impostos.ii.baseCalculo)!0)?string["0.00"]}",
              "vDespAdu": "${((item.impostos.ii.despesasAduaneiras)!0)?string["0.00"]}",
              "vII": "${((item.impostos.ii.valor)!0)?string["0.00"]}",
              "vIOF": "${((item.impostos.ii.valorIof)!0)?string["0.00"]}"
            },
            "PIS": {
              "CST_pis": "${item.impostos.pis.cst!""}",
              "vBC_pis": "${(item.impostos.pis.baseCalculo!0)?string["0.00"]}",
              "pPIS": ${(item.impostos.pis.aliquota!0)?string["0.##"]},
              "vPIS": "${(item.impostos.pis.valor!0)?string["0.00"]}",
              "qBCprod_pis": "${(item.impostos.pis.quantidadeBaseCalculo!0)?string["0.0000"]}",
              "vAliqProd_pis": "${(item.impostos.pis.valorAliquotaProduto!0)?string["0.0000"]}"
            },
            "COFINS": {
              "CST_cofins": "${item.impostos.cofins.cst!""}",
              "vBC_cofins": "${(item.impostos.cofins.baseCalculo!0)?string["0.00"]}",
              "pCOFINS": ${(item.impostos.cofins.aliquota!0)?string["0.##"]},
              "vCOFINS": "${(item.impostos.cofins.valor!0)?string["0.00"]}",
              "qBCProd_cofins": "${(item.impostos.cofins.quantidadeBaseCalculo!0)?string["0.0000"]}",
              "vAliqProd_cofins": "${(item.impostos.cofins.valorAliquotaProduto!0)?string["0.0000"]}"
            }
          },
          "impostoDevol": {
            "pDevol": ${((item.impostoDevol.percentualDevolucao)!0)?string["0.##"]},
            "IPIDevol": {
              "vIPIDevol": "${((item.impostoDevol.valorIpiDevolvido)!0)?string["0.00"]}"
            }
          }
        }<#if item_has_next>,</#if>
        </#list>
      ],
      "total": {
        "ICMStot": {
          "vBC_ttlnfe": "${(document.totais.baseCalculoIcms!0)?string["0.00"]}",
          "vICMS_ttlnfe": "${(document.totais.valorIcms!0)?string["0.00"]}",
          "vICMSDeson_ttlnfe": "${(document.totais.valorIcmsDesonerado!0)?string["0.00"]}",
          "vBCST_ttlnfe": "${(document.totais.baseCalculoST!0)?string["0.00"]}",
          "vST_ttlnfe": "${document.totais.valorICMSST?string["0.00"]}",
          "vProd_ttlnfe": "${document.totais.valorProdutos?string["0.00"]}",
          "vFrete_ttlnfe": "${document.totais.frete?string["0.00"]}",
          "vSeg_ttlnfe": "${document.totais.seguro?string["0.00"]}",
          "vDesc_ttlnfe": "${document.totais.desconto?string["0.00"]}",
          "vII_ttlnfe": "${(document.totais.valorII!0)?string["0.00"]}",
          "vIPI_ttlnfe": "${document.totais.valorIpi?string["0.00"]}",
          "vPIS_ttlnfe": "${(document.totais.valorPis!0)?string["0.00"]}",
          "vCOFINS_ttlnfe": "${(document.totais.valorCofins!0)?string["0.00"]}",
          "vOutro": "${document.totais.outrasDespesas?string["0.00"]}",
          "vNF": "${document.totais.valorNota?string["0.00"]}",
          "vTotTrib_ttlnfe": "${(document.totais.valorTotalTributos!"")}",
          "vFCPUFDest_ttlnfe": "${(document.totais.valorFCPUFDest!0)?string["0.00"]}",
          "vICMSUFDest_ttlnfe": "${(document.totais.valorICMSUFDest!0)?string["0.00"]}",
          "vICMSUFRemet_ttlnfe": "${(document.totais.valorICMSUFRemet!0)?string["0.00"]}",
          "vFCP_ttlnfe": "${(document.totais.valorFCP!0)?string["0.00"]}",
          "vFCPST_ttlnfe": "${(document.totais.valorFCPST!0)?string["0.00"]}",
          "vFCPSTRet_ttlnfe": "${(document.totais.valorFCPSTRet!0)?string["0.00"]}",
          "vIPIDevol_ttlnfe": "${(document.totais.valorIpiDevolvido!0)?string["0.00"]}",
          "vAFRMM_ttlnfe": "${(document.totais.valorAfrmm!0)?string["0.00"]}"
        },
        "ISSQNtot": {
          "vServ": "0.00",
          "vBC_ttlnfe_iss": "0.00",
          "vISS": "0.00",
          "vPIS_servttlnfe": "0.00",
          "vCOFINS_servttlnfe": "0.00",
          "dCompet": "0000-00-00",
          "vDeducao_servttlnfe": "0.00",
          "vOutro_servttlnfe": "0.00",
          "vDescIncond_servttlnfe": "0.00",
          "vDescCond_servttlnfe": "0.00",
          "vISSRet_servttlnfe": "0.00",
          "cRegTrib": 0
        },
        "retTrib": {
          "vRetPIS": "0.00",
          "vRetCOFINS_servttlnfe": "0.00",
          "vRetCSLL": "0.00",
          "vBCIRRF": "0.00",
          "vIRRF": "0.00",
          "vBCRetPrev": "0.00",
          "vRetPrev": "0.00"
        }
      },
      "transp": {
        "modFrete": ${document.transporte.modalidadeFrete},
        "balsa": "",
        "vagao": "",
        "transporta": {
          "CNPJ_transp": "",
          "CPF_transp": "",
          "xNome_transp": "",
          "IE_transp": "",
          "xEnder": "",
          "xMun_transp": "",
          "UF_transp": ""
        },
        "vol": []
      },
      "cobr": {
        "fat": {
          "nFat": "",
          "vOrig": "0.00",
          "vDesc_cob": "0.00",
          "vLiq": "0.00"
        },
        "dup": []
      },
      "pag": [
        <#if document.pagamento?? && document.pagamento.itens?? && document.pagamento.itens?size gt 0>
        <#list document.pagamento.itens as pagamento>
        {
          "indPag_pag": "${document.pagamento.indicadorFormaPagamento!""}",
          "tPag": "${pagamento.tipoPagamento!"01"}",
          <#if pagamento.descricao??>"xPag": "${pagamento.descricao}",</#if>
          "vPag": "${(pagamento.valor!document.totais.valorNota)?string["0.00"]}",
          <#if pagamento.dataPagamento??>"dPag": "${pagamento.dataPagamento}",</#if>
          <#if pagamento.cnpjPagamento??>"CNPJPag": "${pagamento.cnpjPagamento}",</#if>
          <#if pagamento.ufPagamento??>"UFPag": "${pagamento.ufPagamento}",</#if>
          "card": {
            "tipoIntegracao": 0,
            "CNPJ_card": "",
            "tBand": "",
            "cAut": ""
          }
        }<#if pagamento_has_next>,</#if>
        </#list>
        <#else>
        {
          "indPag_pag": "",
          "tPag": "01",
          "vPag": "${document.totais.valorNota?string["0.00"]}",
          "card": {
            "tipoIntegracao": 0,
            "CNPJ_card": "",
            "tBand": "",
            "cAut": ""
          }
        }
        </#if>
      ],
      "infAdic": {
        "infAdFisco": "${document.informacoesAdicionais.fisco!""}",
        "infCpl": "${document.informacoesAdicionais.complementares}",
        "obsCont": [],
        "procRef": []
      },
      "exporta": {
        "UFEmbarq": "${((document.exportacao.ufEmbarque)!"")}",
        "xLocEmbarq": "${((document.exportacao.localEmbarque)!"")}",
        "xLocDespacho": "${((document.exportacao.localDespacho)!"")}"
      },
      "compra": {
        "xNEmp": "",
        "xPed": "",
        "xCont": ""
      }
    }
  }
]

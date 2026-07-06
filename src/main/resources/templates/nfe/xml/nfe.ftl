<#ftl output_format="XML" auto_esc=true>
<?xml version="1.0" encoding="UTF-8"?>
<Envio>
  <ModeloDocumento>${document.modeloDocumento!"NFe"}</ModeloDocumento>
  <Versao>${document.layoutVersion}</Versao>
  <#if document.chaveParceiro??><ChaveParceiro>${document.chaveParceiro}</ChaveParceiro></#if>
  <#if document.chaveAcesso??><ChaveAcesso>${document.chaveAcesso}</ChaveAcesso></#if>

  <ide>
    <#if document.ide.codigoNumerico??><cNF>${document.ide.codigoNumerico}</cNF></#if>
    <cUF>${document.ide.codigoUF}</cUF>
    <natOp>${document.ide.naturezaOperacao}</natOp>
    <mod>${document.ide.modelo}</mod>
    <serie>${document.ide.serie}</serie>
    <#if document.ide.idUnico??><IDUnico>${document.ide.idUnico}</IDUnico></#if>
    <nNF>${document.ide.numero}</nNF>
    <dhEmi>${document.ide.dataEmissao}</dhEmi>
    <fusoHorario>${document.ide.fusoHorario!"-03:00"}</fusoHorario>
    <#if document.ide.dataSaidaEntrada??><dhSaiEnt>${document.ide.dataSaidaEntrada}</dhSaiEnt></#if>
    <tpNf>${document.ide.tipoOperacao}</tpNf>
    <idDest>${document.ide.identificadorDestino}</idDest>
    <indFinal>${document.ide.consumidorFinal}</indFinal>
    <indPres>${document.ide.indicadorPresenca}</indPres>
    <#if document.ide.indicadorIntermediador??><indIntermed>${document.ide.indicadorIntermediador}</indIntermed></#if>
    <cMunFg>${document.ide.codigoMunicipioFatoGerador}</cMunFg>
    <tpImp>${document.ide.tipoImpressao!"1"}</tpImp>
    <tpEmis>${document.ide.tipoEmissao!"1"}</tpEmis>
    <tpAmb>${document.ide.tipoAmbiente}</tpAmb>
    <#if document.ide.justificativaContingencia??><xJust>${document.ide.justificativaContingencia}</xJust></#if>
    <#if document.ide.dataContingencia??><dhCont>${document.ide.dataContingencia}</dhCont></#if>
    <finNFe>${document.ide.finalidade}</finNFe>
    <#if document.ide.emailArquivos??><EmailArquivos>${document.ide.emailArquivos}</EmailArquivos></#if>
    <#if document.ide.numeroPedido??><NumeroPedido>${document.ide.numeroPedido}</NumeroPedido></#if>

    <#if document.documentosReferenciados?? && document.documentosReferenciados?size gt 0>
    <NFRef>
      <#list document.documentosReferenciados as ref>
      <NFRefItem>
        <refNFe>${ref.chaveNFe}</refNFe>
      </NFRefItem>
      </#list>
    </NFRef>
    </#if>
  </ide>

  <emit>
    <CNPJ_emit>${document.emitente.cnpj}</CNPJ_emit>
    <xNome>${document.emitente.razaoSocial}</xNome>
    <#if document.emitente.nomeFantasia??><xFant>${document.emitente.nomeFantasia}</xFant></#if>
    <#if document.emitente.inscricaoMunicipal??><IM>${document.emitente.inscricaoMunicipal}</IM></#if>
    <IE>${document.emitente.inscricaoEstadual}</IE>
    <#if document.emitente.ieSubstitutoTributario??><IEST>${document.emitente.ieSubstitutoTributario}</IEST></#if>
    <CRT>${document.emitente.crt}</CRT>
    <enderEmit>
      <xLgr>${document.emitente.endereco.logradouro}</xLgr>
      <nro>${document.emitente.endereco.numero}</nro>
      <#if document.emitente.endereco.complemento??><xCpl>${document.emitente.endereco.complemento}</xCpl></#if>
      <xBairro>${document.emitente.endereco.bairro}</xBairro>
      <cMun>${document.emitente.endereco.codigoMunicipio}</cMun>
      <xMun>${document.emitente.endereco.municipio}</xMun>
      <UF>${document.emitente.endereco.uf}</UF>
      <CEP>${document.emitente.endereco.cep}</CEP>
      <cPais>${document.emitente.endereco.codigoPais}</cPais>
      <xPais>${document.emitente.endereco.pais}</xPais>
      <#if document.emitente.endereco.telefone??><fone>${document.emitente.endereco.telefone}</fone></#if>
      <#if document.emitente.email??><Email>${document.emitente.email}</Email></#if>
    </enderEmit>
  </emit>

  <dest>
    <#if document.destinatario.cnpj??><CNPJ_dest>${document.destinatario.cnpj}</CNPJ_dest></#if>
    <#if document.destinatario.cpf??><CPF_dest>${document.destinatario.cpf}</CPF_dest></#if>
    <#if document.destinatario.idEstrangeiro??><idEstrangeiro>${document.destinatario.idEstrangeiro}</idEstrangeiro></#if>
    <xNome_dest>${document.destinatario.razaoSocial}</xNome_dest>
    <#if document.destinatario.inscricaoEstadual?? && document.destinatario.inscricaoEstadual?has_content><IE_dest>${document.destinatario.inscricaoEstadual}</IE_dest></#if>
    <indIEDest>${document.destinatario.indicadorIE}</indIEDest>
    <#if document.destinatario.inscricaoMunicipal??><IM_dest>${document.destinatario.inscricaoMunicipal}</IM_dest></#if>
    <enderDest>
      <nro_dest>${document.destinatario.endereco.numero}</nro_dest>
      <#if document.destinatario.endereco.complemento??><xCpl_dest>${document.destinatario.endereco.complemento}</xCpl_dest></#if>
      <xBairro_dest>${document.destinatario.endereco.bairro}</xBairro_dest>
      <#if document.destinatario.email??><xEmail_dest>${document.destinatario.email}</xEmail_dest></#if>
      <xLgr_dest>${document.destinatario.endereco.logradouro}</xLgr_dest>
      <xPais_dest>${document.destinatario.endereco.pais}</xPais_dest>
      <cMun_dest>${document.destinatario.endereco.codigoMunicipio}</cMun_dest>
      <xMun_dest>${document.destinatario.endereco.municipio}</xMun_dest>
      <UF_dest>${document.destinatario.endereco.uf}</UF_dest>
      <CEP_dest>${document.destinatario.endereco.cep}</CEP_dest>
      <cPais_dest>${document.destinatario.endereco.codigoPais}</cPais_dest>
      <#if document.destinatario.endereco.telefone??><fone_dest>${document.destinatario.endereco.telefone}</fone_dest></#if>
    </enderDest>
  </dest>

  <det>
    <#list document.itens as item>
    <detItem>
      <#if item.informacoesAdicionais??><infADProd>${item.informacoesAdicionais}</infADProd></#if>
      <prod>
        <cProd>${item.produto.codigo}</cProd>
        <cEAN>${item.produto.ean}</cEAN>
        <xProd>${item.produto.descricao}</xProd>
        <NCM>${item.produto.ncm}</NCM>
        <CFOP>${item.produto.cfop}</CFOP>
        <uCOM>${item.produto.unidade}</uCOM>
        <qCOM>${item.produto.quantidade?string["0.0000"]}</qCOM>
        <vUnCom>${item.produto.valorUnitario?string["0.00"]}</vUnCom>
        <vProd>${item.produto.valorProduto?string["0.00"]}</vProd>
        <cEANTrib>${item.produto.eanTributavel!item.produto.ean}</cEANTrib>
        <uTrib>${item.produto.unidadeTributavel!item.produto.unidade}</uTrib>
        <qTrib>${(item.produto.quantidadeTributavel!item.produto.quantidade)?string["0.0000"]}</qTrib>
        <vUnTrib>${(item.produto.valorUnitarioTributavel!item.produto.valorUnitario)?string["0.00"]}</vUnTrib>
        <#if item.produto.frete??><vFrete>${item.produto.frete?string["0.00"]}</vFrete></#if>
        <#if item.produto.seguro??><vSeg>${item.produto.seguro?string["0.00"]}</vSeg></#if>
        <#if item.produto.desconto??><vDesc>${item.produto.desconto?string["0.00"]}</vDesc></#if>
        <#if item.produto.outrasDespesas??><vOutro_item>${item.produto.outrasDespesas?string["0.00"]}</vOutro_item></#if>
        <indTot>${item.produto.indicadorTotal!"1"}</indTot>
        <nTipoItem>${item.produto.tipoItem!"0"}</nTipoItem>
        <#if item.produto.cest??><CEST>${item.produto.cest}</CEST></#if>
      </prod>

      <imposto>
        <#if item.impostos.valorTotalTributos??><vTotTrib>${item.impostos.valorTotalTributos?string["0.00"]}</vTotTrib></#if>
        <ICMS>
          <orig>${item.impostos.icms.origem}</orig>
          <CST>${item.impostos.icms.cst!item.impostos.icms.csosn}</CST>
          <#if item.impostos.icms.modalidadeBaseCalculo??><modBC>${item.impostos.icms.modalidadeBaseCalculo}</modBC></#if>
          <#if item.impostos.icms.baseCalculo??><vBC>${item.impostos.icms.baseCalculo?string["0.00"]}</vBC></#if>
          <#if item.impostos.icms.aliquota??><pICMS>${item.impostos.icms.aliquota?string["0.00"]}</pICMS></#if>
          <#if item.impostos.icms.valor??><vICMS_icms>${item.impostos.icms.valor?string["0.00"]}</vICMS_icms></#if>
          <#if item.impostos.icmsSt??>
          <modBCST>${item.impostos.icmsSt.modalidadeBaseCalculoST!"4"}</modBCST>
          <pMVAST>${(item.impostos.icmsSt.mvaST!0)?string["0.00"]}</pMVAST>
          <vBCST>${(item.impostos.icmsSt.baseCalculoST!0)?string["0.00"]}</vBCST>
          <pICMSST>${(item.impostos.icmsSt.aliquotaST!0)?string["0.00"]}</pICMSST>
          <vICMSST_icms>${(item.impostos.icmsSt.valorICMSST!0)?string["0.00"]}</vICMSST_icms>
          </#if>
        </ICMS>

        <#if item.impostos.ipi??>
        <IPI>
          <cEnq>${item.impostos.ipi.codigoEnquadramento!"999"}</cEnq>
          <CSTIPI>
            <CST_IPI>${item.impostos.ipi.cst}</CST_IPI>
            <#if item.impostos.ipi.baseCalculo??><vBC_IPI>${item.impostos.ipi.baseCalculo?string["0.00"]}</vBC_IPI></#if>
            <#if item.impostos.ipi.aliquota??><pIPI>${item.impostos.ipi.aliquota?string["0.00"]}</pIPI></#if>
            <#if item.impostos.ipi.valorIpi??><vIPI>${item.impostos.ipi.valorIpi?string["0.00"]}</vIPI></#if>
          </CSTIPI>
        </IPI>
        </#if>

        <#if item.impostos.pis??>
        <PIS>
          <CST_pis>${item.impostos.pis.cst}</CST_pis>
          <vBC_pis>${(item.impostos.pis.baseCalculo!0)?string["0.00"]}</vBC_pis>
          <pPIS>${(item.impostos.pis.aliquota!0)?string["0.00"]}</pPIS>
          <vPIS>${(item.impostos.pis.valor!0)?string["0.00"]}</vPIS>
        </PIS>
        </#if>

        <#if item.impostos.cofins??>
        <COFINS>
          <CST_cofins>${item.impostos.cofins.cst}</CST_cofins>
          <vBC_cofins>${(item.impostos.cofins.baseCalculo!0)?string["0.00"]}</vBC_cofins>
          <pCOFINS>${(item.impostos.cofins.aliquota!0)?string["0.00"]}</pCOFINS>
          <vCOFINS>${(item.impostos.cofins.valor!0)?string["0.00"]}</vCOFINS>
        </COFINS>
        </#if>
      </imposto>
    </detItem>
    </#list>
  </det>

  <total>
    <ICMStot>
      <vBC_ttlnfe>${(document.totais.baseCalculoIcms!0)?string["0.00"]}</vBC_ttlnfe>
      <vICMS_ttlnfe>${(document.totais.valorIcms!0)?string["0.00"]}</vICMS_ttlnfe>
      <vICMSDeson_ttlnfe>${(document.totais.valorIcmsDesonerado!0)?string["0.00"]}</vICMSDeson_ttlnfe>
      <vBCST_ttlnfe>${(document.totais.baseCalculoST!0)?string["0.00"]}</vBCST_ttlnfe>
      <vST_ttlnfe>${document.totais.valorICMSST?string["0.00"]}</vST_ttlnfe>
      <vProd_ttlnfe>${document.totais.valorProdutos?string["0.00"]}</vProd_ttlnfe>
      <vFrete_ttlnfe>${document.totais.frete?string["0.00"]}</vFrete_ttlnfe>
      <vSeg_ttlnfe>${document.totais.seguro?string["0.00"]}</vSeg_ttlnfe>
      <vDesc_ttlnfe>${document.totais.desconto?string["0.00"]}</vDesc_ttlnfe>
      <vII_ttlnfe>${(document.totais.valorII!0)?string["0.00"]}</vII_ttlnfe>
      <vIPI_ttlnfe>${document.totais.valorIpi?string["0.00"]}</vIPI_ttlnfe>
      <vPIS_ttlnfe>${(document.totais.valorPis!0)?string["0.00"]}</vPIS_ttlnfe>
      <vCOFINS_ttlnfe>${(document.totais.valorCofins!0)?string["0.00"]}</vCOFINS_ttlnfe>
      <vOutro>${document.totais.outrasDespesas?string["0.00"]}</vOutro>
      <vNF>${document.totais.valorNota?string["0.00"]}</vNF>
      <#if document.totais.valorTotalTributos??><vTotTrib_ttlnfe>${document.totais.valorTotalTributos?string["0.00"]}</vTotTrib_ttlnfe></#if>
    </ICMStot>
  </total>

  <transp>
    <modFrete>${document.transporte.modalidadeFrete}</modFrete>
  </transp>

  <pag>
    <#if document.pagamento?? && document.pagamento.itens?? && document.pagamento.itens?size gt 0>
    <#list document.pagamento.itens as pagamento>
    <pagItem>
      <#if document.pagamento.indicadorFormaPagamento??><indPag_pag>${document.pagamento.indicadorFormaPagamento}</indPag_pag></#if>
      <tPag>${pagamento.tipoPagamento!"01"}</tPag>
      <#if pagamento.descricao??><xPag>${pagamento.descricao}</xPag></#if>
      <vPag>${(pagamento.valor!document.totais.valorNota)?string["0.00"]}</vPag>
      <#if pagamento.dataPagamento??><dPag>${pagamento.dataPagamento}</dPag></#if>
      <#if pagamento.cnpjPagamento??><CNPJPag>${pagamento.cnpjPagamento}</CNPJPag></#if>
      <#if pagamento.ufPagamento??><UFPag>${pagamento.ufPagamento}</UFPag></#if>
    </pagItem>
    </#list>
    <#else>
    <pagItem>
      <#if document.pagamento?? && document.pagamento.indicadorFormaPagamento??><indPag_pag>${document.pagamento.indicadorFormaPagamento}</indPag_pag></#if>
      <tPag>01</tPag>
      <vPag>${document.totais.valorNota?string["0.00"]}</vPag>
    </pagItem>
    </#if>
  </pag>

  <#if document.informacoesAdicionais??>
  <infAdic>
    <#if document.informacoesAdicionais.fisco??><infAdFisco>${document.informacoesAdicionais.fisco}</infAdFisco></#if>
    <#if document.informacoesAdicionais.complementares??><infCpl>${document.informacoesAdicionais.complementares}</infCpl></#if>
  </infAdic>
  </#if>
</Envio>

<#ftl output_format="XML" auto_esc=true>
<?xml version="1.0" encoding="UTF-8"?>
<NFe xmlns="http://www.portalfiscal.inf.br/nfe">
  <infNFe versao="${document.layoutVersion}">
    <ide>
      <cUF>${document.ide.codigoUF}</cUF>
      <natOp>${document.ide.naturezaOperacao}</natOp>
      <mod>${document.ide.modelo}</mod>
      <serie>${document.ide.serie}</serie>
      <nNF>${document.ide.numero}</nNF>
      <dhEmi>${document.ide.dataEmissao}</dhEmi>
      <tpNF>${document.ide.tipoOperacao}</tpNF>
      <idDest>${document.ide.identificadorDestino}</idDest>
      <cMunFG>${document.ide.codigoMunicipioFatoGerador}</cMunFG>
      <tpAmb>${document.ide.tipoAmbiente}</tpAmb>
      <finNFe>${document.ide.finalidade}</finNFe>
      <indFinal>${document.ide.consumidorFinal}</indFinal>
      <indPres>${document.ide.indicadorPresenca}</indPres>
    </ide>

    <#if document.documentosReferenciados??>
    <#list document.documentosReferenciados as ref>
    <NFref>
      <refNFe>${ref.chaveNFe}</refNFe>
    </NFref>
    </#list>
    </#if>

    <emit>
      <CNPJ>${document.emitente.cnpj}</CNPJ>
      <xNome>${document.emitente.razaoSocial}</xNome>
      <xFant>${document.emitente.nomeFantasia!document.emitente.razaoSocial}</xFant>
      <enderEmit>
        <xLgr>${document.emitente.endereco.logradouro}</xLgr>
        <nro>${document.emitente.endereco.numero}</nro>
        <xBairro>${document.emitente.endereco.bairro}</xBairro>
        <cMun>${document.emitente.endereco.codigoMunicipio}</cMun>
        <xMun>${document.emitente.endereco.municipio}</xMun>
        <UF>${document.emitente.endereco.uf}</UF>
        <CEP>${document.emitente.endereco.cep}</CEP>
        <cPais>${document.emitente.endereco.codigoPais}</cPais>
        <xPais>${document.emitente.endereco.pais}</xPais>
      </enderEmit>
      <IE>${document.emitente.inscricaoEstadual}</IE>
      <CRT>${document.emitente.crt}</CRT>
    </emit>

    <dest>
      <#if document.destinatario.cnpj??><CNPJ>${document.destinatario.cnpj}</CNPJ></#if>
      <#if document.destinatario.idEstrangeiro??><idEstrangeiro>${document.destinatario.idEstrangeiro}</idEstrangeiro></#if>
      <xNome>${document.destinatario.razaoSocial}</xNome>
      <enderDest>
        <xLgr>${document.destinatario.endereco.logradouro}</xLgr>
        <nro>${document.destinatario.endereco.numero}</nro>
        <xBairro>${document.destinatario.endereco.bairro}</xBairro>
        <cMun>${document.destinatario.endereco.codigoMunicipio}</cMun>
        <xMun>${document.destinatario.endereco.municipio}</xMun>
        <UF>${document.destinatario.endereco.uf}</UF>
        <CEP>${document.destinatario.endereco.cep}</CEP>
        <cPais>${document.destinatario.endereco.codigoPais}</cPais>
        <xPais>${document.destinatario.endereco.pais}</xPais>
      </enderDest>
      <indIEDest>${document.destinatario.indicadorIE}</indIEDest>
      <#if document.destinatario.inscricaoEstadual??><IE>${document.destinatario.inscricaoEstadual}</IE></#if>
    </dest>

    <#list document.itens as item>
    <det nItem="${item.numero}">
      <prod>
        <cProd>${item.produto.codigo}</cProd>
        <cEAN>${item.produto.ean}</cEAN>
        <xProd>${item.produto.descricao}</xProd>
        <NCM>${item.produto.ncm}</NCM>
        <CFOP>${item.produto.cfop}</CFOP>
        <uCom>${item.produto.unidade}</uCom>
        <qCom>${item.produto.quantidade}</qCom>
        <vUnCom>${item.produto.valorUnitario?string["0.00"]}</vUnCom>
        <vProd>${item.produto.valorProduto?string["0.00"]}</vProd>
        <cEANTrib>${item.produto.ean}</cEANTrib>
        <uTrib>${item.produto.unidade}</uTrib>
        <qTrib>${item.produto.quantidade}</qTrib>
        <vUnTrib>${item.produto.valorUnitario?string["0.00"]}</vUnTrib>
        <indTot>1</indTot>
      </prod>
      <imposto>
        <ICMS>
          <#if item.impostos.icms.csosn??>
          <ICMSSN102>
            <orig>${item.impostos.icms.origem}</orig>
            <CSOSN>${item.impostos.icms.csosn}</CSOSN>
          </ICMSSN102>
          <#elseif item.impostos.icmsSt??>
          <ICMS10>
            <orig>${item.impostos.icms.origem}</orig>
            <CST>${item.impostos.icms.cst}</CST>
            <modBC>${item.impostos.icms.modalidadeBaseCalculo!"3"}</modBC>
            <vBC>${(item.impostos.icms.baseCalculo!0)?string["0.00"]}</vBC>
            <pICMS>${(item.impostos.icms.aliquota!0)?string["0.00"]}</pICMS>
            <vICMS>${(item.impostos.icms.valor!0)?string["0.00"]}</vICMS>
            <modBCST>${item.impostos.icmsSt.modalidadeBaseCalculoST!"4"}</modBCST>
            <pMVAST>${(item.impostos.icmsSt.mvaST!0)?string["0.00"]}</pMVAST>
            <vBCST>${(item.impostos.icmsSt.baseCalculoST!0)?string["0.00"]}</vBCST>
            <pICMSST>${(item.impostos.icmsSt.aliquotaST!0)?string["0.00"]}</pICMSST>
            <vICMSST>${(item.impostos.icmsSt.valorICMSST!0)?string["0.00"]}</vICMSST>
          </ICMS10>
          <#else>
          <ICMS00>
            <orig>${item.impostos.icms.origem}</orig>
            <CST>${item.impostos.icms.cst}</CST>
            <modBC>${item.impostos.icms.modalidadeBaseCalculo!"3"}</modBC>
            <vBC>${(item.impostos.icms.baseCalculo!0)?string["0.00"]}</vBC>
            <pICMS>${(item.impostos.icms.aliquota!0)?string["0.00"]}</pICMS>
            <vICMS>${(item.impostos.icms.valor!0)?string["0.00"]}</vICMS>
          </ICMS00>
          </#if>
        </ICMS>
        <#if item.impostos.ipi??>
        <IPI>
          <cEnq>${item.impostos.ipi.codigoEnquadramento!"999"}</cEnq>
          <IPITrib>
            <CST>${item.impostos.ipi.cst}</CST>
            <vBC>${(item.impostos.ipi.baseCalculo!0)?string["0.00"]}</vBC>
            <pIPI>${(item.impostos.ipi.aliquota!0)?string["0.00"]}</pIPI>
            <vIPI>${(item.impostos.ipi.valorIpi!0)?string["0.00"]}</vIPI>
          </IPITrib>
        </IPI>
        </#if>
        <#if item.impostos.pis??>
        <PIS>
          <PISAliq>
            <CST>${item.impostos.pis.cst}</CST>
            <vBC>${(item.impostos.pis.baseCalculo!0)?string["0.00"]}</vBC>
            <pPIS>${(item.impostos.pis.aliquota!0)?string["0.00"]}</pPIS>
            <vPIS>${(item.impostos.pis.valor!0)?string["0.00"]}</vPIS>
          </PISAliq>
        </PIS>
        </#if>
        <#if item.impostos.cofins??>
        <COFINS>
          <COFINSAliq>
            <CST>${item.impostos.cofins.cst}</CST>
            <vBC>${(item.impostos.cofins.baseCalculo!0)?string["0.00"]}</vBC>
            <pCOFINS>${(item.impostos.cofins.aliquota!0)?string["0.00"]}</pCOFINS>
            <vCOFINS>${(item.impostos.cofins.valor!0)?string["0.00"]}</vCOFINS>
          </COFINSAliq>
        </COFINS>
        </#if>
      </imposto>
    </det>
    </#list>

    <total>
      <ICMSTot>
        <vProd>${document.totais.valorProdutos?string["0.00"]}</vProd>
        <vFrete>${document.totais.frete?string["0.00"]}</vFrete>
        <vSeg>${document.totais.seguro?string["0.00"]}</vSeg>
        <vDesc>${document.totais.desconto?string["0.00"]}</vDesc>
        <vII>0.00</vII>
        <vIPI>${document.totais.valorIpi?string["0.00"]}</vIPI>
        <vST>${document.totais.valorICMSST?string["0.00"]}</vST>
        <vOutro>${document.totais.outrasDespesas?string["0.00"]}</vOutro>
        <vNF>${document.totais.valorNota?string["0.00"]}</vNF>
      </ICMSTot>
    </total>

    <transp>
      <modFrete>${document.transporte.modalidadeFrete}</modFrete>
    </transp>

    <infAdic>
      <infCpl>${document.informacoesAdicionais.complementares}</infCpl>
    </infAdic>
  </infNFe>
</NFe>

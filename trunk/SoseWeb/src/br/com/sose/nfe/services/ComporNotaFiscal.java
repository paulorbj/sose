package br.com.sose.nfe.services;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;

import br.com.sose.entity.admistrativo.parceiros.Endereco;
import br.com.sose.entity.admistrativo.parceiros.Pessoa;
import br.com.sose.entity.expedicao.NotaFiscalRemessa;
import br.com.sose.entity.recebimento.ItemNotaFiscal;
import br.com.sose.entity.recebimento.OrdemServico;
import br.com.sose.nfe.producao.xsdclasses.ObjectFactory;
import br.com.sose.nfe.producao.xsdclasses.TEnderEmi;
import br.com.sose.nfe.producao.xsdclasses.TEndereco;
import br.com.sose.nfe.producao.xsdclasses.TNFe;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Dest;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Det;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Det.Imposto;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Det.Prod;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Det.Imposto.COFINS;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Det.Imposto.ICMS;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Det.Imposto.IPI;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Det.Imposto.PIS;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Det.Imposto.COFINS.COFINSNT;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Det.Imposto.ICMS.ICMSSN900;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Det.Imposto.IPI.IPINT;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Det.Imposto.PIS.PISNT;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Total.ICMSTot;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Emit;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Ide;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Total;
import br.com.sose.nfe.producao.xsdclasses.TUf;
import br.com.sose.nfe.producao.xsdclasses.TUfEmi;


public class ComporNotaFiscal {

//	JAXBContext jc = JAXBContext.newInstance("br.com.sose.nfe.producao.xsdclasses");			
	ObjectFactory obj = new ObjectFactory();
	
	private Logger logger = Logger.getLogger(this.getClass());

	public TNFe criarNotaFiscal(NotaFiscalRemessa notaFiscal){
		try{
			TNFe notaGerada = obj.createTNFe();
			notaGerada.setInfNFe(criarInformacaoNotaFiscal(notaFiscal));		
			return notaGerada;
		}catch(Exception e){
			e.printStackTrace(); logger.error(e);
			return null;
		}
		

	}
	
	public InfNFe criarInformacaoNotaFiscal(NotaFiscalRemessa notaFiscal){
		try{
			GeradorChaveAcessoNFe gerador = new GeradorChaveAcessoNFe();
			String chave = gerador.gerarChave("52", "0604", "33009911002506", "55", "012", "000000780", "0", "26730161");			
			
			InfNFe infoNFe = obj.createInfNFe();
			infoNFe.setVersao("2.00");
			infoNFe.setId("NFe"+chave);
			infoNFe.setIde(criarInformacoesIdentificacaoNotaFiscal(null));
			infoNFe.setEmit(criarEmitente(null));
			infoNFe.setDest(criarDestinatario(null));
			infoNFe = criarDetalhamentoProdutoServico(infoNFe,null);
			infoNFe.setTotal(criarTotal(null));
			return infoNFe;
		}catch(Exception e){
			e.printStackTrace(); logger.error(e);
			return null;
		}
	}
	
	public InfNFe criarDetalhamentoProdutoServico(InfNFe infNfe, NotaFiscalRemessa notaFiscal){
		
//		HashMap<ItemNotaFiscal, Integer> itensNF = new HashMap<ItemNotaFiscal,Integer>();
//		for(OrdemServico os :notaFiscal.getOrdemServicos()){
//			ItemNotaFiscal inf = os.getItemNotaFiscal();
//			if(itensNF.get(inf) == null){
//				itensNF.put(inf, 1);
//			}else{
//				itensNF.put(inf, itensNF.get(inf)+1);
//			}		
//		}
		
		int nItem = 1;
		Object inf =null;
//		for (ItemNotaFiscal inf : itensNF.keySet()){
		for(int i=0; i<2; i++) {
			Det det = obj.createDet();
			
			/**
			 Número do item (1-990)
			 */		
			det.setNItem(String.valueOf(nItem++));
			Prod produto = obj.createProd();
			
			/**
			 Código Fiscal de Operações e Prestações
			 */
			produto.setCFOP(getCfop(inf));
			
			/**
			 Código do produto ou serviço
			 */
			produto.setCProd(getCodigoProduto(inf));
			
			/**
			 Descrição do produto ou serviço (1-120)
			 */
			produto.setXProd(getDescricaoProduto(inf));
			
			/**
			 Código NCM com 8 dígitos ou 2 dígitos (gênero)
			 */
			produto.setNCM(getNcm(inf));
			
			/**
			 Unidade Comercial (1-6)
			 Informar a unidade de comercialização do produto.
			 */
			produto.setUCom(getUnidadeMedida(inf));
			
			/**
			 Quantidade Comercial (15)
			 Informar a quantidade de comercialização do produto
			 */
			produto.setQCom(getQuantidade(null));
			
			/**
			 Valor Unitário de Comercialização
			 Informar o valor unitário de comercialização do produto, campo meramente informativo,
			 o contribuinte pode utilizar a precisão desejada (0-10 decimais). Para efeitos de
			 cálculo, o valor unitário será obtido pela divisão do valor do produto pela quantidade
			 comercial.
			 */
			produto.setVUnCom(getValorUnitario(inf));
			
			/**
			 Unidade Tributável (1-6)
			 */
			produto.setUTrib(getUnidadeMedida(inf));
			
			/**
			 Valor Unitário de tributação (21)
			 Informar o valor unitário de tributação do produto, campo meramente informativo, o
			 contribuinte pode utilizar a precisão desejada (0-10 decimais). Para efeitos de
			 cálculo, o valor unitário será obtido pela divisão do valor do produto pela quantidade
			 tributável.
			 */
			produto.setVUnTrib(getValorUnitario(inf));
			
			/**
			 Valor Total Bruto dos Produtos ou Serviços
			 */
//			produto.setVProd(getValorTotal(inf.getValorUnitario(), itensNF.get(inf)));
			produto.setVProd(getValorTotal(null, null));
			
			/**
			 Indica se valor do Item (vProd) entra no valor total da NF-e (vProd)
			 Este campo deverá ser preenchido com:
				0 – o valor do item (vProd) não compõe o valor total da NF-e (vProd)
				1 – o valor do item (vProd) compõe o valor total da NF-e (vProd)
			 */
			produto.setIndTot(compoeTotal());
			det.setProd(produto);
		
			det.setImposto(createImposto());
			
			infNfe.getDet().add(det);
		}
		
		return infNfe;
	}
	

	private Imposto createImposto(){
		Imposto imposto = obj.createImposto();

		COFINS cofins = obj.createCofins();
		COFINSNT cofinsnt = obj.createCofinsnt();
		cofinsnt.setCST("07");
		cofins.setCOFINSNT(cofinsnt);
		imposto.setCOFINS(cofins);
		
		IPI ipi = obj.createIPI();
		ipi.setCEnq("0");
		IPINT ipiNt = obj.createIPINT();
		ipiNt.setCST("53");
		ipi.setIPINT(ipiNt);
		imposto.setIPI(ipi);
		
		PIS pis = obj.createPis();
		PISNT pisNt = obj.createPisnt();
		pisNt.setCST("07");
		pis.setPISNT(pisNt);
		imposto.setPIS(pis);
		
		ICMS icms = obj.createIcms();
		ICMSSN900 icmssn900 = obj.createICMSSN900();
		icmssn900.setCSOSN("900");
		icmssn900.setOrig("0");
		imposto.setICMS(icms);
		
		return imposto;
	}
	
	private Total criarTotal(NotaFiscalRemessa notaFiscal){
		Total total = obj.createTotal();
		ICMSTot icmsTotal = obj.createICMSTot();
		
		icmsTotal.setVBC("0.00");
		
		icmsTotal.setVICMS("0.00");
		
		icmsTotal.setVBCST("0.00");
		
		icmsTotal.setVST("0.00");
		
		icmsTotal.setVProd(calcularTotalNotaFiscal(notaFiscal));
		
		icmsTotal.setVFrete("0.00");
		
		icmsTotal.setVSeg("0.00");
		
		icmsTotal.setVDesc("0.00");
		
		icmsTotal.setVII("0.00");
		
		icmsTotal.setVIPI("0.00");
		
		icmsTotal.setVPIS("0.00");
		
		icmsTotal.setVCOFINS("0.00");
		
		icmsTotal.setVOutro("0.00");
		
		icmsTotal.setVNF(calcularTotalNotaFiscal(notaFiscal));
		
		total.setICMSTot(icmsTotal);
		
		
		return total;
	}
	

	
	private String calcularTotalNotaFiscal(NotaFiscalRemessa notaFiscal){
		if(notaFiscal != null){
			BigDecimal total = new BigDecimal(0);
			for(OrdemServico os : notaFiscal.getOrdensServico()){
				total = total.add(os.getItemNotaFiscal().getValorUnitario());
			}
			return total.toString();
		}else{
			return "400.0000";
		}

	}
	
	private String compoeTotal(){
		return "1";
	}
	
	private String getQuantidade(Integer quantidade){
		if(quantidade != null){
			return quantidade.toString();
		}else{
			return "1";
		}
	}
	
	private String getValorTotal(BigDecimal valorUnitario, Integer quantidade){
		if(valorUnitario != null){
			BigDecimal total = valorUnitario.multiply(new BigDecimal(quantidade));
			return total.toString();
		}else{
			return "400.0000";
		}

	}
	
	private String getValorUnitario(Object item){
		if(item != null){
			ItemNotaFiscal inf = (ItemNotaFiscal)item;
			return inf.getValorUnitario().toString();
		}else{
			return "400.0000";
		}

	}
	
	private String getDescricaoProduto(Object item){
		if(item != null){
			ItemNotaFiscal inf = (ItemNotaFiscal)item;
			return inf.getNome();
		}else{
			return "APARELHOS M-3904 PRETO";
		}

	}
	
	private String getCfop(Object item){
		if(item != null){
			ItemNotaFiscal inf = (ItemNotaFiscal)item;	
			return inf.getCfop();
		}else{
			return "5915";
		}

	}
	
	private String getNcm(Object item){
		if(item != null){
			ItemNotaFiscal inf = (ItemNotaFiscal)item;
			return inf.getNcm();
		}else{
			return "85170101";
		}

	}
	
	private String getUnidadeMedida(Object item){
		if(item != null){
			ItemNotaFiscal inf = (ItemNotaFiscal)item;
			return inf.getUnidadeMedida();
		}else{
			return "Pc";
		}

	}
	
	private String getCodigoProduto(Object item){
		if(item != null){
			ItemNotaFiscal inf = (ItemNotaFiscal)item;
			return inf.getCodigo();
		}else{
			return "0";
		}

	}
	
	public Dest criarDestinatario(Pessoa destinatario){
		try{
		
			Dest dest = obj.createDest();
			
			/**
			Informar o CNPJ do emitente. Em se tratando de emiss�o de NF-e avulsa pelo Fisco, as informa��es do remetente ser�o
			informadas neste grupo. O CNPJ ou CPF dever�o ser informados com os zeros não significativos.
			*/
			if(destinatario == null || destinatario.getTipoPessoa().equals(Pessoa.TIPO_PESSOA_JURIDICA)){
				dest.setCNPJ(getCnpj("06315767000196"));
//				emit.setCNPJ(destinatario.getCpfCnpj());
			}else if(destinatario.getTipoPessoa().equals(Pessoa.TIPO_PESSOA_FISICA)){
				dest.setCPF(getCpf("04736125000135"));
//				emit.setCPF(destinatario.getCpfCnpj());
			}

			/**
			 Raz�o Social ou Nome do emitente
			 */
			dest.setXNome(getNomeEmitente("DATAREPAIR COM. PRODUTOS DE INFORMAT.E SERV.LTDA"));
//			dest.setXNome(destinatario.getNomeRazaoSocial());
			/**
			 IE
			 Campo de informa��o obrigat�ria nos casos de emiss�o pr�pria (procEmi = 0, 2 ou 3).
			 A IE deve ser informada apenas com algarismos para destinat�rios contribuintes do
			 ICMS, sem caracteres de formata��o (ponto, barra, h�fen, etc.);
			 O literal �ISENTO� deve ser informado apenas para contribuintes do ICMS que s�o
			 isentos de inscri��o no cadastro de contribuintes do ICMS e estejam emitindo NF-e avulsa;			 
			 */
			dest.setIE(getIe("149902602113"));			
//			if(destinatario.getTipoPessoa().equals(Pessoa.TIPO_PESSOA_JURIDICA)){
//				dest.setIE(getIe(destinatario.getRgIe()));
//			}
			/**
			 Endereco emitente
			 */
//			Set<Endereco> enderecos = destinatario.getEnderecos();
//			for(Endereco e : enderecos){
//				if(e.getEnderecoComercial()){
//					emit.setEnderEmit(criarEnderecoDestinatario(e));
//					break;
//				}
//			}
			dest.setEnderDest(criarEnderecoDestinatario(null)); //Teste

			return dest;
		}catch(Exception e){
			e.printStackTrace(); logger.error(e);
			return null;
		}
		
	}
	
	public Emit criarEmitente(Pessoa emitente){
		try{
//			Pessoa cadastroEmpresa;
//			if(emitente == null){
//				cadastroEmpresa = pessoaService.buscarCadastroEmpresa();
//			}else{
//				cadastroEmpresa = emitente;
//			}
			Pessoa cadastroEmpresa = new Pessoa();
			
			Emit emit = obj.createEmit();
			
			/**
			Informar o CNPJ do emitente. Em se tratando de emiss�o de NF-e avulsa pelo Fisco, as informa��es do remetente ser�o
			informadas neste grupo. O CNPJ ou CPF dever�o ser informados com os zeros não significativos.
			*/
			

			if(emitente == null || cadastroEmpresa.getTipoPessoa().equals(Pessoa.TIPO_PESSOA_JURIDICA)){
				emit.setCNPJ(getCnpj("04736125000135"));
//				emit.setCNPJ(cadastroEmpresa.getCpfCnpj());
			}else if(cadastroEmpresa.getTipoPessoa().equals(Pessoa.TIPO_PESSOA_FISICA)){
				emit.setCPF(getCpf("04736125000135"));
//				emit.setCPF(cadastroEmpresa.getCpfCnpj());
			}

			/**
			 C�digo de Regime Tribut�rio
			 Este campo ser� obrigatoriamente preenchido com:
				1 � Simples Nacional;
				2 � Simples Nacional � excesso de sublimite de receita bruta;
				3 � Regime Normal. (v2.0).
			 */
			emit.setCRT(getRegimeTributario()); 
			
			/**
			 Raz�o Social ou Nome do emitente
			 */
			emit.setXNome(getNomeEmitente("Servilogi Telecomunica��es Com�rcio e Servi�os Ltda - Epp."));
			
			/**
			 Nome fantasia
			 */
			emit.setXFant(getNomeFantasiaEmitente("Servilogi Telecom"));
			
			/**
			 IE
			 Campo de informa��o obrigat�ria nos casos de emiss�o pr�pria (procEmi = 0, 2 ou 3).
			 A IE deve ser informada apenas com algarismos para destinat�rios contribuintes do
			 ICMS, sem caracteres de formata��o (ponto, barra, h�fen, etc.);
			 O literal �ISENTO� deve ser informado apenas para contribuintes do ICMS que s�o
			 isentos de inscri��o no cadastro de contribuintes do ICMS e estejam emitindo NF-e avulsa;			 
			 */
			emit.setIE(getIe("116281246116"));
			
			/**
			 Inscri��o Municipal
			 Este campo deve ser informado, quando ocorrer a emiss�o de NF-e conjugada, com presta��o 
			 de servi�os sujeitos ao ISSQN e fornecimento de pe�as sujeitos ao ICMS.
			 */
			emit.setIM(getIm("30807476"));
			
			/**
			 CNAE fiscal
			 Este campo deve ser informado quando o campo IM (C19) for informado.
			 */
			emit.setCNAE(getCnae("4752100"));

			/**
			 Endereco emitente
			 */
//			Set<Endereco> enderecos = cadastroEmpresa.getEnderecos();
//			for(Endereco e : enderecos){
//				if(e.getEnderecoComercial()){
//					emit.setEnderEmit(criarEnderecoEmitente(e));
//					break;
//				}
//			}
			emit.setEnderEmit(criarEnderecoEmitente(null)); //Teste

			return emit;
		}catch(Exception e){
			e.printStackTrace(); logger.error(e);
			return null;
		}
		
	}
	
	public TEnderEmi criarEnderecoEmitente(Endereco endereco){
		try{
			TEnderEmi tEnderEmi = obj.createTEnderEmi();
//			tEnderEmi.setXLgr(endereco.getLogradouro());
//			tEnderEmi.setNro(endereco.getNumero());
//			tEnderEmi.setXBairro(endereco.getBairro());
//			tEnderEmi.setCEP(endereco.getCep());
//			tEnderEmi.setUF(getUfEmitente(endereco.getEstado()));
//			tEnderEmi.setCMun(getCodigoMunicipio());
//			tEnderEmi.setXMun("3550308");
//			tEnderEmi.setCPais(getCodigoPais("Brasil"));
//			tEnderEmi.setXPais(endereco.getPais());
//			tEnderEmi.setFone(endereco.getTelefone());
			tEnderEmi.setCEP("04284080");
			tEnderEmi.setCMun("3550308");
			tEnderEmi.setCPais("1058");
			tEnderEmi.setFone("1120684588");
			tEnderEmi.setNro("66");
			tEnderEmi.setUF(TUfEmi.SP);
			tEnderEmi.setXBairro("Ipiranga");
			tEnderEmi.setXLgr("Rua: Abauna");
			tEnderEmi.setXMun("Sao Paulo");
			tEnderEmi.setXPais("BRASIL");	
			return tEnderEmi;
		}catch(Exception e){
			e.printStackTrace(); logger.error(e);
			return null;
		}
	}
	
	public TEndereco criarEnderecoDestinatario(Endereco endereco){
		try{
			TEndereco eDest = obj.createEndereco();
//			eDest.setXLgr(endereco.getLogradouro());
//			eDest.setNro(endereco.getNumero());
//			eDest.setXBairro(endereco.getBairro());
//			eDest.setCEP(endereco.getCep());
//			eDest.setUF(getUfEmitente(endereco.getEstado()));
//			eDest.setCMun(getCodigoMunicipio());
//			eDest.setXMun("3550308");
//			eDest.setCPais(getCodigoPais("Brasil"));
//			eDest.setXPais(endereco.getPais());
//			eDest.setFone(endereco.getTelefone());
			eDest.setCEP("04132000");
			eDest.setCMun("3550308");
			eDest.setCPais("1058");
			eDest.setFone("1150630207");
			eDest.setNro("140");
			eDest.setUF(TUf.SP);
			eDest.setXBairro("Vila Gumercindo");
			eDest.setXLgr("Avenida do Cursino");
			eDest.setXMun("Sao Paulo");
			eDest.setXPais("BRASIL");
			eDest.setXCpl("Conj. 11 / 12");
			return eDest;
		}catch(Exception e){
			e.printStackTrace(); logger.error(e);
			return null;
		}
	}
	
	/**
	 

	 */
	
	public TUfEmi getUfEmitente(String uf){
		
		return TUfEmi.SP;
	}
	
	public String getCodigoPais(String pais){
		
		return pais;
	}
	
	public String getCnpj(String cnpj){
		
		return cnpj;
	}
	
	public String getCpf(String cpf){
		
		return cpf;
	}
	
	public String getRegimeTributario(){
		
		return "1";
	}
	
	public String getNomeEmitente(String nome){
		
		return nome;
	}
	
	public String getNomeFantasiaEmitente(String nome){
		
		return nome;
	}
	
	public String getIe(String ie){
		
		return ie;
	}
	
	public String getIm(String im){
		
		return im;
	}
	
	public String getCnae(String cnae){
		
		return cnae;
	}
	
	/**
			



	*/
	
	public Ide criarInformacoesIdentificacaoNotaFiscal(NotaFiscalRemessa notaFiscal){
		try{
			Ide ide = obj.createIde();
			ide.setCUF(getCodigoUF()); //C�digo da UF do emitente do Documento Fiscal. Utilizar a Tabela do IBGE de c�digo de unidades da federa��o
			ide.setCNF(gerarCodigoNotaFiscal()); //C�digo num�rico que comp�e a	Chave de Acesso. N�mero aleat�rio gerado pelo emitente para cada NF-e para evitar acessos indevidos da NF-e. (v2.0)
			ide.setNatOp(getNaturezaOperacao()); //Informar a natureza da opera��o de que decorrer a sa�da ou a entrada, tais como: venda, compra, transfer�ncia, devolu��o, importa��o, consigna��o, remessa
			ide.setIndPag(getFormaPagamento()); // 0 � pagamento � vista; 1 � pagamento � prazo; 2 - outros.
			ide.setMod("55"); // Utilizar o c�digo 55 para identifica��o da NF-e, emitida em substitui��o ao modelo 1 ou 1A
			ide.setSerie(getSerie()); //S�rie do Documento Fiscal, preencher com zeros na hip�tese de a NF-e não possuir s�rie. (v2.0) S�rie 890-899 de uso exclusivo para emiss�o de NF-e avulsa, pelo contribuinte com seu certificado digital, atrav�s do site do Fisco (procEmi=2). (v2.0) Serie 900-999 � uso exclusivo de NF-e emitidas no SCAN.
			ide.setNNF(gerarNumeroNotaFiscal()); // N�mero do Documento Fiscal.
			ide.setDEmi(formatarData(new Date())); // Data de emiss�o do Documento Fiscal. Formato AAAA-MM-DD
			ide.setDSaiEnt(formatarData(new Date())); //Data de Sa�da ou da Entrada da Mercadoria/Produto. Formato AAAA-MM-DD
			ide.setTpNF("1"); // Tipo de Opera��o 0-entrada / 1-sa�da
			ide.setCMunFG(getCodigoMunicipio()); // C�digo do Munic�pio de Ocorr�ncia do Fato Gerador. Informar o munic�pio de ocorr�ncia do fato gerador do ICMS
			ide.setTpImp("1"); // Formato de Impress�o do DANFE. 1-Retrato/ 2-Paisagem
			ide.setTpEmis("1"); // Tipo de Emiss�o da NF-e. 1 � Normal � emiss�o normal; 2 � Conting�ncia FS � emiss�o em conting�ncia com impress�o do DANFE em Formul�rio de Seguran�a; 3 � Conting�ncia SCAN �	emiss�o em conting�ncia no Sistema de Conting�ncia do Ambiente Nacional � SCAN; 4 � Conting�ncia DPEC - emiss�o em conting�ncia com envio da Declara��o Pr�via de Emiss�o em Conting�ncia � DPEC; 5 � Conting�ncia FS-DA - emiss�o em conting�ncia com impress�o do DANFE em Formul�rio de Seguran�a para Impress�o de Documento Auxiliar de Documento Fiscal Eletr�nico (FS-DA).
			ide.setCDV(getDigitoVerificador()); // D�gito Verificador da Chave de Acesso da NF-e
			ide.setTpAmb("2"); // Identifica��o do Ambiente. 1-Produ��o/ 2-Homologa��o 
			ide.setFinNFe("1"); // Finalidade de emiss�o da NFe. 1- NF-e normal/ 2-NF-e complementar / 3 � NF-e de ajuste
			ide.setProcEmi("0"); // Processo de emiss�o da NF-e. Identificador do processo de emiss�o da NF-e: 0 - emiss�o de NF-e com aplicativo do contribuinte; 1 - emiss�o de NF-e avulsa pelo Fisco; 2 - emiss�o de NF-e avulsa,	pelo contribuinte com seu certificado digital, atrav�s do site do Fisco; 3- emiss�o NF-e pelo contribuinte com aplicativo fornecido pelo Fisco. 
			ide.setVerProc("2.0"); // Identificador da vers�o do processo de emiss�o (informar a vers�o do aplicativo emissor de NF-e).
			return ide;
		}catch(Exception e){
			e.printStackTrace(); logger.error(e);
			return null;
		}	
	}
	
	public String formatarData(Date data){
		SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");
		return formatador.format(data);
	}
	
	public String getCodigoMunicipio(){
		
		return "3550308";
	}
	
	public String getDigitoVerificador(){
		
		return "5";
	}
	
	public String getCodigoUF(){
		
		return "35";
	}
	
	public String gerarCodigoNotaFiscal(){
		
		return "00080019";
	}
	
	public String gerarNumeroNotaFiscal(){
		
		return "1021";
	}
	
	public String getNaturezaOperacao(){
		
		return "Remessa de Mercadoria ou bem para conserto / reparo";
	}
	
	public String getFormaPagamento(){
		
		return "2";
	}
	
	public String getSerie(){
		
		return "1";
	}
	
	public static void main(String[] args){
		ComporNotaFiscal cnf = new ComporNotaFiscal();
		TNFe notaEletronica = cnf.criarNotaFiscal(null);
		
		
	}
	
	
}

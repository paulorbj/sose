package br.com.sose.nfe.services;

import br.com.sose.nfe.producao.xsdclasses.KeyInfoType;
import br.com.sose.nfe.producao.xsdclasses.ReferenceType;
import br.com.sose.nfe.producao.xsdclasses.SignatureType;
import br.com.sose.nfe.producao.xsdclasses.SignatureValueType;
import br.com.sose.nfe.producao.xsdclasses.SignedInfoType;
import br.com.sose.nfe.producao.xsdclasses.TEnderEmi;
import br.com.sose.nfe.producao.xsdclasses.TEndereco;
import br.com.sose.nfe.producao.xsdclasses.TLocal;
import br.com.sose.nfe.producao.xsdclasses.TNFe;
import br.com.sose.nfe.producao.xsdclasses.TUf;
import br.com.sose.nfe.producao.xsdclasses.TUfEmi;
import br.com.sose.nfe.producao.xsdclasses.TVeiculo;
import br.com.sose.nfe.producao.xsdclasses.TransformsType;
import br.com.sose.nfe.producao.xsdclasses.X509DataType;
import br.com.sose.nfe.producao.xsdclasses.ReferenceType.DigestMethod;
import br.com.sose.nfe.producao.xsdclasses.SignedInfoType.CanonicalizationMethod;
import br.com.sose.nfe.producao.xsdclasses.SignedInfoType.SignatureMethod;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Avulsa;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Cana;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Cobr;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Compra;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Dest;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Det;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Emit;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Exporta;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Ide;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.InfAdic;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Total;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Transp;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Cobr.Fat;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Det.Imposto;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Det.Prod;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Det.Imposto.COFINS;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Det.Imposto.COFINSST;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Det.Imposto.ICMS;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Det.Imposto.II;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Det.Imposto.IPI;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Det.Imposto.ISSQN;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Det.Imposto.PIS;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Det.Imposto.PISST;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Det.Prod.Comb;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Det.Prod.VeicProd;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Det.Prod.Comb.CIDE;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Transp.RetTransp;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Transp.Transporta;

public class TestService {

	public void teste(){
		TNFe notaFiscal = new TNFe();
		TNFe.InfNFe informacaoNotaFiscal = new TNFe.InfNFe();
		SignatureType tipoAssinatura = new SignatureType();
		
		informacaoNotaFiscal.setVersao("");
		
		
		
		Avulsa avulsa = new Avulsa();
		avulsa.setCNPJ("");
		avulsa.setDEmi("");
		avulsa.setDPag("");
		avulsa.setFone("");
		avulsa.setMatr("");
		avulsa.setNDAR("");
		avulsa.setRepEmi("");
		avulsa.setUF(TUfEmi.SP);
		avulsa.setVDAR("");
		avulsa.setXAgente("");
		avulsa.setXOrgao("");
		informacaoNotaFiscal.setAvulsa(avulsa);

		Cana cana = new Cana();
		cana.setQTotAnt("");
		cana.setQTotGer("");
		cana.setQTotMes("");
		cana.setRef("");
		cana.setSafra("");
		cana.setVFor("");
		cana.setVLiqFor("");
		cana.setVTotDed("");
		informacaoNotaFiscal.setCana(cana);
		
		Cobr cobr = new Cobr();
		Fat fat = new Fat();
		fat.setNFat("");
		fat.setVDesc("");
		fat.setVLiq("");
		fat.setVOrig("");
		
		cobr.setFat(fat);
		cobr.getDup();
		informacaoNotaFiscal.setCobr(cobr);
		
		Compra compra = new Compra();
		compra.setXCont("");
		compra.setXNEmp("");
		compra.setXPed("");
		informacaoNotaFiscal.setCompra(compra);
		
		Dest dest = new Dest();
		dest.setCNPJ("");
		dest.setCPF("");
		dest.setEmail("");
		
		TEndereco tEndereco = new TEndereco();
		tEndereco.setCEP("");
		tEndereco.setCMun("");
		tEndereco.setCPais("");
		tEndereco.setFone("");
		tEndereco.setNro("");
		tEndereco.setUF(TUf.SP);
		tEndereco.setXBairro("");
		tEndereco.setXCpl("");
		tEndereco.setXLgr("");
		tEndereco.setXMun("");
		tEndereco.setXPais("");	
		dest.setEnderDest(tEndereco);
		
		dest.setIE("");
		dest.setISUF("");
		dest.setXNome("");
		informacaoNotaFiscal.setDest(dest);
		
		Det det = new Det();
		det.setInfAdProd("");
		det.setNItem("");
		
		Imposto imposto = new Imposto();
		COFINS cofins = new COFINS();
		imposto.setCOFINS(cofins);
		
		COFINSST cofinsst = new COFINSST();
		imposto.setCOFINSST(cofinsst);
		
		ICMS icms = new ICMS();
		imposto.setICMS(icms);
		
		II ii = new II();
		imposto.setII(ii);
		
		IPI ipi = new IPI();
		imposto.setIPI(ipi);
		
		ISSQN issqn = new ISSQN();
		imposto.setISSQN(issqn);
		
		PIS pis = new PIS();
		imposto.setPIS(pis);
		
		PISST pisst = new PISST();
		imposto.setPISST(pisst);
		
		det.setImposto(imposto);
		
		Prod produto = new Prod();
		produto.setCEAN("");
		produto.setCEANTrib("");
		produto.setCFOP("");
		
		Comb comb = new Comb();
		CIDE cide = new CIDE();
		comb.setCIDE(cide);
		produto.setComb(comb);
		
		produto.setCProd("");
		produto.setEXTIPI("");
		produto.setIndTot("");
		produto.setNCM("");
		produto.setNItemPed("");
		produto.setQCom("");
		produto.setQTrib("");
		produto.setUCom("");
		produto.setUTrib("");
		produto.setVDesc("");
		
		VeicProd veicProd = new VeicProd();
		produto.setVeicProd(veicProd);
		
		produto.setVFrete("");
		produto.setVOutro("");
		produto.setVProd("");
		produto.setVSeg("");
		produto.setVUnCom("");
		produto.setVUnTrib("");
		det.setProd(produto);
		informacaoNotaFiscal.getDet().add(det);

		Emit emit = new Emit(); 
		emit.setCNAE("");
		emit.setCNPJ("");
		emit.setCPF("");
		emit.setCRT("");
		
		TEnderEmi tEnderEmi = new TEnderEmi();
		tEnderEmi.setCEP("");
		tEnderEmi.setCMun("");
		tEnderEmi.setCPais("");
		tEnderEmi.setFone("");
		tEnderEmi.setNro("");
		tEnderEmi.setUF(TUfEmi.SP);
		tEnderEmi.setXBairro("");
		tEnderEmi.setXCpl("");
		tEnderEmi.setXLgr("");
		tEnderEmi.setXMun("");
		tEnderEmi.setXPais("");	
		emit.setEnderEmit(tEnderEmi);
		informacaoNotaFiscal.setEmit(emit);
		
		TLocal tLocal = new TLocal();
		tLocal.setCMun("");
		tLocal.setCNPJ("");
		tLocal.setCPF("");
		tLocal.setNro("");
		tLocal.setUF(TUf.SP);
		tLocal.setXBairro("");
		tLocal.setXCpl("");
		tLocal.setXLgr("");
		tLocal.setXMun("");
		informacaoNotaFiscal.setEntrega(tLocal);
		
		Exporta exporta = new Exporta();
		exporta.setUFEmbarq(TUf.SP);
		exporta.setXLocEmbarq("");
		informacaoNotaFiscal.setExporta(exporta);
		
		informacaoNotaFiscal.setId("");
		
		/**
		 * Identificação da Nota Fiscal eletrônica
		 */
		Ide iDe = new Ide();
		iDe.setCNF("");
		iDe.setCDV("");
		iDe.setCMunFG("");
		iDe.setCUF(""); //Código da UF do emitente do Documento Fiscal
		iDe.setDEmi("");
		iDe.setDhCont("");
		iDe.setDSaiEnt("");
		iDe.setFinNFe("");
		iDe.setHSaiEnt("");
		iDe.setIndPag("");
		iDe.setMod("");
		iDe.setNatOp("");
		iDe.setNNF("");
		iDe.setProcEmi("");
		iDe.setSerie("");
		iDe.setTpAmb("");
		iDe.setTpEmis("");
		iDe.setTpImp("");
		iDe.setTpNF("");
		iDe.setVerProc("");
		iDe.setXJust("");
		informacaoNotaFiscal.setIde(iDe);
		/***********************************/
		
		InfAdic infAdic = new InfAdic();
		infAdic.setInfAdFisco("");
		infAdic.setInfCpl("");
		informacaoNotaFiscal.setInfAdic(infAdic);
		
		
		informacaoNotaFiscal.setRetirada(tLocal);
		
		
		Total.ICMSTot icmsTot = new Total.ICMSTot();
		icmsTot.setVBC("");
		icmsTot.setVBCST("");
		icmsTot.setVCOFINS("");
		icmsTot.setVDesc("");
		icmsTot.setVFrete("");
		icmsTot.setVICMS("");
		icmsTot.setVII("");
		icmsTot.setVIPI("");
		icmsTot.setVNF("");
		icmsTot.setVOutro("");
		icmsTot.setVPIS("");
		icmsTot.setVProd("");
		icmsTot.setVSeg("");
		icmsTot.setVST("");
//		informacaoNotaFiscal.setTotal(Total);

		Transp transp = new Transp();
		transp.setBalsa("");
		transp.setModFrete("");
		
		RetTransp retTransp = new RetTransp();
		retTransp.setCFOP("");
		retTransp.setCMunFG("");
		retTransp.setPICMSRet("");
		retTransp.setVBCRet("");
		retTransp.setVICMSRet("");
		retTransp.setVServ("");
		transp.setRetTransp(retTransp);
		
		Transporta transporta = new Transporta();
		transporta.setCNPJ("");
		transporta.setCPF("");
		transporta.setIE("");
		transporta.setUF(TUf.SP);
		transporta.setXEnder("");
		transporta.setXMun("");
		transporta.setXNome("");
		transp.setTransporta(transporta);
		
		transp.setVagao("");
		
		TVeiculo tVeiculo = new TVeiculo();
		tVeiculo.setPlaca("");
		tVeiculo.setRNTC("");
		tVeiculo.setUF(TUf.SP);
		transp.setVeicTransp(tVeiculo);
		
		informacaoNotaFiscal.setTransp(transp);
		
		
		notaFiscal.setInfNFe(informacaoNotaFiscal);
		
		tipoAssinatura.setId("");
		
		KeyInfoType keyInfoType = new KeyInfoType();
		keyInfoType.setId("");
		
		X509DataType x509DataType = new X509DataType();
		x509DataType.setX509Certificate(null);
		keyInfoType.setX509Data(x509DataType);
		tipoAssinatura.setKeyInfo(keyInfoType);
		
		SignatureValueType signatureValueType = new SignatureValueType();
		signatureValueType.setId("");
		signatureValueType.setValue(null);
		tipoAssinatura.setSignatureValue(signatureValueType);
		
		SignedInfoType signedInfoType = new SignedInfoType();
		CanonicalizationMethod canonicalizationMethod = new CanonicalizationMethod();
		signedInfoType.setCanonicalizationMethod(canonicalizationMethod);
		signedInfoType.setId("");
		ReferenceType referenceType = new ReferenceType();
		
		DigestMethod digestMethod = new DigestMethod();
		digestMethod.setAlgorithm("");
		referenceType.setDigestMethod(digestMethod);
		referenceType.setDigestValue(null);
		referenceType.setId("");
		TransformsType transformsType = new TransformsType();

		referenceType.setType("");
		referenceType.setURI("");
		referenceType.setTransforms(transformsType);
		signedInfoType.setReference(referenceType);
		
		SignatureMethod signatureMethod = new SignatureMethod();
		signatureMethod.setAlgorithm("");
		signedInfoType.setSignatureMethod(signatureMethod);
		tipoAssinatura.setSignedInfo(signedInfoType);
		
		notaFiscal.setSignature(tipoAssinatura);
		

	}
}

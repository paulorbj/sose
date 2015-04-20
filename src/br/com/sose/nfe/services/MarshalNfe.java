package br.com.sose.nfe.services;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import br.com.sose.nfe.producao.xsdclasses.ObjectFactory;
import br.com.sose.nfe.producao.xsdclasses.SignatureType;
import br.com.sose.nfe.producao.xsdclasses.TEnderEmi;
import br.com.sose.nfe.producao.xsdclasses.TEndereco;
import br.com.sose.nfe.producao.xsdclasses.TEnviNFe;
import br.com.sose.nfe.producao.xsdclasses.TNFe;
import br.com.sose.nfe.producao.xsdclasses.TUf;
import br.com.sose.nfe.producao.xsdclasses.TUfEmi;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Dest;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Det;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Emit;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Ide;
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
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Det.Imposto.COFINS.COFINSNT;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Det.Imposto.ICMS.ICMSSN900;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Det.Imposto.IPI.IPINT;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Det.Imposto.PIS.PISNT;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Det.Prod.Comb;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Det.Prod.VeicProd;
import br.com.sose.nfe.producao.xsdclasses.TNFe.InfNFe.Det.Prod.Comb.CIDE;

public class MarshalNfe {

	public static void main(String[] args){
		try{
			JAXBContext jc = JAXBContext.newInstance("br.com.sose.nfe.producao.xsdclasses");			
			ObjectFactory obj = new ObjectFactory();
			
			TEnviNFe enviNfe = obj.createTEnviNFe();
			
			TNFe.InfNFe informacaoNotaFiscal = obj.createInfNFe();
			
			informacaoNotaFiscal.setVersao("2.00");
			informacaoNotaFiscal.setId("id da informacao da nf");
			
			enviNfe.setIdLote("teste");
			enviNfe.setVersao("2.00");
			
			TNFe nota = obj.createTNFe();
			
			/**
			 * Identifica��o da Nota Fiscal eletr�nica
			 */
			Ide iDe = new Ide();
			iDe.setCNF("00080019");
			iDe.setCDV("3");
			iDe.setCMunFG("3550308");
			iDe.setCUF("35"); //C�digo da UF do emitente do Documento Fiscal
			iDe.setDEmi("2011-07-14");
//			iDe.setDhCont("");
//			iDe.setDSaiEnt("");
			iDe.setFinNFe("1");
//			iDe.setHSaiEnt("");
			iDe.setIndPag("2");
			iDe.setMod("55");
			iDe.setNatOp("Remessa de Mercadoria ou bem para conserto / reparo");
			iDe.setNNF("1021");
			iDe.setProcEmi("3");
			iDe.setSerie("1");
			iDe.setTpAmb("1");
			iDe.setTpEmis("1");
			iDe.setTpImp("1");
			iDe.setTpNF("1");
			iDe.setVerProc("2.0.9");
//			iDe.setXJust("");
			informacaoNotaFiscal.setIde(iDe);
			
			Emit emit = new Emit(); 
//			emit.setCNAE("");
			emit.setCNPJ("04736125000135");
//			emit.setCPF("");
			emit.setCRT("1");
			emit.setXNome("Servilogi Telecomunicações Comércio e Serviços Ltda - Epp.");
			emit.setXFant("Servilogi Telecom");
			emit.setIE("116281246116");
			emit.setIM("30807476");
			emit.setCNAE("4752100");
			
			TEnderEmi tEnderEmi = new TEnderEmi();
			tEnderEmi.setCEP("04284080");
			tEnderEmi.setCMun("3550308");
			tEnderEmi.setCPais("1058");
			tEnderEmi.setFone("1120684588");
			tEnderEmi.setNro("66");
			tEnderEmi.setUF(TUfEmi.SP);
			tEnderEmi.setXBairro("Ipiranga");
//			tEnderEmi.setXCpl("");
			tEnderEmi.setXLgr("Rua: Abauna");
			tEnderEmi.setXMun("3550308");
			tEnderEmi.setXPais("BRASIL");	
			emit.setEnderEmit(tEnderEmi);
			informacaoNotaFiscal.setEmit(emit);
			
			
			Dest dest = new Dest();
			dest.setCNPJ("06315767000196");
//			dest.setCPF("");
//			dest.setEmail("");
			
			TEndereco tEndereco = new TEndereco();
			tEndereco.setCEP("04132000");
			tEndereco.setCMun("3550308");
			tEndereco.setCPais("1058");
			tEndereco.setFone("1150630207");
			tEndereco.setNro("140");
			tEndereco.setUF(TUf.SP);
			tEndereco.setXBairro("Vila Gumercindo");
			tEndereco.setXCpl("Conj. 11 / 12");
			tEndereco.setXLgr("Avenida do Cursino");
			tEndereco.setXMun("Sao Paulo");
			tEndereco.setXPais("BRASIL");	
			dest.setEnderDest(tEndereco);
			
			dest.setIE("149902602113");
//			dest.setISUF("");
			dest.setXNome("DATAREPAIR COM. PRODUTOS DE INFORMAT.E SERV.LTDA");
			informacaoNotaFiscal.setDest(dest);
			
			
			/**
			 * Produto 1
			 */
			Det det = obj.createDet();
			det.setInfAdProd("");
			det.setNItem("1");
			
			Imposto imposto = new Imposto();
			COFINS cofins = new COFINS();
			COFINSNT cofinsnt = new COFINSNT();
			cofinsnt.setCST("07");
			cofins.setCOFINSNT(cofinsnt);
			imposto.setCOFINS(cofins);
			
			IPI ipi = new IPI();
			ipi.setCEnq("0");
			IPINT ipiNt = new IPINT();
			ipiNt.setCST("53");
			ipi.setIPINT(ipiNt);
			imposto.setIPI(ipi);
			
			PIS pis = new PIS();
			PISNT pisNt = new PISNT();
			pisNt.setCST("07");
			pis.setPISNT(pisNt);
			imposto.setPIS(pis);
			
			ICMS icms = new ICMS();
			ICMSSN900 icmssn900 = new ICMSSN900();
			icmssn900.setCSOSN("900");
			icmssn900.setOrig("0");
			imposto.setICMS(icms);
			
			det.setImposto(imposto);
			
			Prod produto = obj.createProd();
			produto.setCEAN("");
			produto.setCEANTrib("");
			produto.setCFOP("5915");
			
			produto.setCProd("0");
			produto.setXProd("APARELHOS M-3903 PRETO");
			produto.setIndTot("1");
			produto.setNCM("85170101");
			produto.setQCom("2.0000");
			produto.setQTrib("2.0000");
			produto.setUCom("P�");
			produto.setUTrib("P�");

			
			produto.setVFrete("120.00");
			produto.setVOutro("130.00");
			produto.setVProd("800.00");
			produto.setVSeg("150.00");
			produto.setVUnCom("400.0000000000");
			produto.setVUnTrib("400.0000000000");
			det.setProd(produto);
			informacaoNotaFiscal.getDet().add(det);
			
			//############################################
			
			/**
			 * Produto 2
			 */
			Det det2 = obj.createDet();
			det2.setInfAdProd("");
			det2.setNItem("1");
			
			Imposto imposto2 = new Imposto();
			COFINS cofins2 = new COFINS();
			COFINSNT cofinsnt2 = new COFINSNT();
			cofinsnt2.setCST("07");
			cofins2.setCOFINSNT(cofinsnt2);
			imposto2.setCOFINS(cofins2);
			
			IPI ipi2 = new IPI();
			ipi2.setCEnq("0");
			IPINT ipiNt2 = new IPINT();
			ipiNt2.setCST("53");
			ipi2.setIPINT(ipiNt2);
			imposto2.setIPI(ipi2);
			
			PIS pis2 = new PIS();
			PISNT pisNt2 = new PISNT();
			pisNt2.setCST("07");
			pis2.setPISNT(pisNt2);
			imposto2.setPIS(pis2);
			
			ICMS icms2 = new ICMS();
			ICMSSN900 icmssn9002 = new ICMSSN900();
			icmssn9002.setCSOSN("900");
			icmssn9002.setOrig("0");
			imposto2.setICMS(icms2);
			
			det2.setImposto(imposto2);
			
			Prod produto2 = obj.createProd();
			produto2.setCEAN("");
			produto2.setCEANTrib("");
			produto2.setCFOP("5915");
			
			produto2.setCProd("0");
			produto2.setXProd("APARELHOS M-3903 PRETO");
			produto2.setIndTot("1");
			produto2.setNCM("85170101");
			produto2.setQCom("2.0000");
			produto2.setQTrib("2.0000");
			produto2.setUCom("P�");
			produto2.setUTrib("P�");

			
			produto2.setVFrete("120.00");
			produto2.setVOutro("130.00");
			produto2.setVProd("800.00");
			produto2.setVSeg("150.00");
			produto2.setVUnCom("400.0000000000");
			produto2.setVUnTrib("400.0000000000");
			det2.setProd(produto2);
			informacaoNotaFiscal.getDet().add(det2);
			
			//################################
			
			nota.setInfNFe(informacaoNotaFiscal);
			enviNfe.getNFe().add(nota);
			
			ComporNotaFiscal cnf = new ComporNotaFiscal();
			nota = cnf.criarNotaFiscal(null);
			String nomeArquivo = nota.getInfNFe().getId();
			nomeArquivo = nomeArquivo.substring(3);
			File xmlFile = new File(nomeArquivo + "-nfe2.xml");
			Marshaller marshal = jc.createMarshaller();

			marshal.marshal(nota, xmlFile);
			marshal.marshal(enviNfe, System.out);
		}catch(Exception e){
			e.printStackTrace(); 
		}
		
	}
}

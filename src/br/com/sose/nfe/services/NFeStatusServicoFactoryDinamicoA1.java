package br.com.sose.nfe.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.AXIOMUtil;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.log4j.Logger;

import br.com.sose.nfe.producao.xsdclasses.ObjectFactory;
import br.com.sose.nfe.producao.xsdclasses.TRetConsReciNFe;
import br.com.sose.nfe.producao.xsdclasses.TRetConsStatServ;
import br.com.sose.nfe.producao.xsdclasses.TRetEnviNFe;
import br.inf.portalfiscal.www.nfe.wsdl.nferecepcao2.NfeRecepcao2Stub;
import br.inf.portalfiscal.www.nfe.wsdl.nferetrecepcao2.NfeRetRecepcao2Stub;
import br.inf.portalfiscal.www.nfe.wsdl.nfestatusservico2.NfeStatusServico2Stub;

public class NFeStatusServicoFactoryDinamicoA1 {
	private static final int SSL_PORT = 443;

	public static TRetEnviNFe enviarNotaFiscal(String nota){
        try {
            String codigoDoEstado = "35";
            URL url = new URL("https://homologacao.nfe.fazenda.sp.gov.br/nfeweb/services/NfeRecepcao2.asmx ");
            
            /**
             * Informacoes do Certificado Digital A3.
             */
            String caminhoDoCertificadoDoCliente = "D:/Paulinho_projetos/paulo/certificados_exportados/clienteteste.pfx";
            String senhaDoCertificado = "teste12345";
            String arquivoCacertsGeradoTodosOsEstados = "NFeCacerts";

    		InputStream entrada = new FileInputStream(caminhoDoCertificadoDoCliente);
    		KeyStore ks = KeyStore.getInstance("pkcs12");
    		try {
    			ks.load(entrada, senhaDoCertificado.toCharArray());
    		} catch (IOException e) {
    			throw new Exception("Senha do Certificado Digital esta incorreta ou Certificado inv�lido.");
    		}

            String alias = "";  
            Enumeration<String> aliasesEnum = ks.aliases();  
            while (aliasesEnum.hasMoreElements()) {  
                alias = (String) aliasesEnum.nextElement();  
                if (ks.isKeyEntry(alias)) break;  
            }
      		X509Certificate certificate = (X509Certificate) ks.getCertificate(alias);
    		PrivateKey privateKey = (PrivateKey) ks.getKey(alias, senhaDoCertificado.toCharArray());
    		SocketFactoryDinamico socketFactoryDinamico = new SocketFactoryDinamico(certificate, privateKey);
    		socketFactoryDinamico.setFileCacerts(arquivoCacertsGeradoTodosOsEstados);

            Protocol protocol = new Protocol("https", socketFactoryDinamico, SSL_PORT);  
            Protocol.registerProtocol("https", protocol);  

            OMElement ome = AXIOMUtil.stringToOM(nota);
            
            NfeRecepcao2Stub.NfeDadosMsg dadosEnvio = new NfeRecepcao2Stub.NfeDadosMsg();
            dadosEnvio.setExtraElement(ome);
            
            NfeRecepcao2Stub.NfeCabecMsg nfeCabecMsg = new NfeRecepcao2Stub.NfeCabecMsg();
            nfeCabecMsg.setCUF(codigoDoEstado);
            nfeCabecMsg.setVersaoDados("2.00");

            
            NfeRecepcao2Stub.NfeCabecMsgE nfeCabecMsgE = new NfeRecepcao2Stub.NfeCabecMsgE();
            nfeCabecMsgE.setNfeCabecMsg(nfeCabecMsg);
            
           
            NfeRecepcao2Stub stub = new NfeRecepcao2Stub(url.toString());
            
            NfeRecepcao2Stub.NfeRecepcaoLote2Result result2 = stub.nfeRecepcaoLote2(dadosEnvio, nfeCabecMsgE);

            //Teste de parser do retorno
			JAXBContext jc = JAXBContext.newInstance("br.com.sose.nfe.producao.xsdclasses");			
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			
			TRetEnviNFe retornoEnvio = (TRetEnviNFe)unmarshaller.unmarshal(result2.getExtraElement().getXMLStreamReader());
            info(result2.getExtraElement().toString());
            return retornoEnvio;
        }catch(Exception e){
        	e.printStackTrace();
        }
		
		return null;
	}
	
	
	public static TRetConsReciNFe consultarNotaFiscal(String nota){
        try {
            String codigoDoEstado = "35";
            URL url = new URL("https://homologacao.nfe.fazenda.sp.gov.br/nfeweb/services/NfeRetRecepcao2.asmx");
            
            /**
             * Informacoes do Certificado Digital A3.
             */
            String caminhoDoCertificadoDoCliente = "D:/Paulinho_projetos/paulo/certificados_exportados/clienteteste.pfx";
            String senhaDoCertificado = "teste12345";
            String arquivoCacertsGeradoTodosOsEstados = "NFeCacerts";

    		InputStream entrada = new FileInputStream(caminhoDoCertificadoDoCliente);
    		KeyStore ks = KeyStore.getInstance("pkcs12");
    		try {
    			ks.load(entrada, senhaDoCertificado.toCharArray());
    		} catch (IOException e) {
    			throw new Exception("Senha do Certificado Digital esta incorreta ou Certificado inv�lido.");
    		}

            String alias = "";  
            Enumeration<String> aliasesEnum = ks.aliases();  
            while (aliasesEnum.hasMoreElements()) {  
                alias = (String) aliasesEnum.nextElement();  
                if (ks.isKeyEntry(alias)) break;  
            }
      		X509Certificate certificate = (X509Certificate) ks.getCertificate(alias);
    		PrivateKey privateKey = (PrivateKey) ks.getKey(alias, senhaDoCertificado.toCharArray());
    		SocketFactoryDinamico socketFactoryDinamico = new SocketFactoryDinamico(certificate, privateKey);
    		socketFactoryDinamico.setFileCacerts(arquivoCacertsGeradoTodosOsEstados);

            Protocol protocol = new Protocol("https", socketFactoryDinamico, SSL_PORT);  
            Protocol.registerProtocol("https", protocol);  

            OMElement ome = AXIOMUtil.stringToOM(nota);
            
            NfeRetRecepcao2Stub.NfeDadosMsg dadosEnvio = new NfeRetRecepcao2Stub.NfeDadosMsg();
            dadosEnvio.setExtraElement(ome);
            
            NfeRetRecepcao2Stub.NfeCabecMsg nfeCabecMsg = new NfeRetRecepcao2Stub.NfeCabecMsg();
            nfeCabecMsg.setCUF(codigoDoEstado);
            nfeCabecMsg.setVersaoDados("2.00");
           
            
            NfeRetRecepcao2Stub.NfeCabecMsgE nfeCabecMsgE = new NfeRetRecepcao2Stub.NfeCabecMsgE();
            nfeCabecMsgE.setNfeCabecMsg(nfeCabecMsg);
           
            NfeRetRecepcao2Stub stub = new NfeRetRecepcao2Stub(url.toString());
            
            NfeRetRecepcao2Stub.NfeRetRecepcao2Result result2 = stub.nfeRetRecepcao2(dadosEnvio, nfeCabecMsgE);

            //Teste de parser do retorno
			JAXBContext jc = JAXBContext.newInstance("br.com.sose.nfe.producao.xsdclasses");			
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			
			TRetConsReciNFe retornoEnvio = (TRetConsReciNFe)unmarshaller.unmarshal(result2.getExtraElement().getXMLStreamReader());
            info(result2.getExtraElement().toString());
            return retornoEnvio;
        }catch(Exception e){
        	e.printStackTrace();
        }
		
		return null;
	}
	
	public static void main(String[] args) {
        try {
            String codigoDoEstado = "35";
//            URL url = new URL("https://homologacao.nfe.fazenda.sp.gov.br/nfeweb/services/NfeStatusServico2.asmx");
            URL url2 = new URL("https://homologacao.nfe.fazenda.sp.gov.br/nfeweb/services/NfeRecepcao2.asmx ");

            
            /**
             * Informa��es do Certificado Digital A3.
             */
            String caminhoDoCertificadoDoCliente = "D:/Paulinho_projetos/paulo/certificados_exportados/clienteteste.pfx";
            String senhaDoCertificado = "teste12345";
            String arquivoCacertsGeradoTodosOsEstados = "NFeCacerts";

    		InputStream entrada = new FileInputStream(caminhoDoCertificadoDoCliente);
    		KeyStore ks = KeyStore.getInstance("pkcs12");
    		try {
    			ks.load(entrada, senhaDoCertificado.toCharArray());
    		} catch (IOException e) {
    			throw new Exception("Senha do Certificado Digital esta incorreta ou Certificado inv�lido.");
    		}

    		/**
    		 * Resolve o problema do 403.7 Forbidden para Certificados A3 e A1 
    		 * e elimina o uso das cofigura��es:
    		 * - System.setProperty("javax.net.ssl.keyStore", "NONE");
    		 * - System.setProperty("javax.net.ssl.keyStoreType", "PKCS11");
    		 * - System.setProperty("javax.net.ssl.keyStoreProvider", "SunPKCS11-SmartCard");
    		 * - System.setProperty("javax.net.ssl.trustStoreType", "JKS");
    		 * - System.setProperty("javax.net.ssl.trustStore", arquivoCacertsGeradoTodosOsEstados);
    		 */
            String alias = "";  
            Enumeration<String> aliasesEnum = ks.aliases();  
            while (aliasesEnum.hasMoreElements()) {  
                alias = (String) aliasesEnum.nextElement();  
                if (ks.isKeyEntry(alias)) break;  
            }
      		X509Certificate certificate = (X509Certificate) ks.getCertificate(alias);
    		PrivateKey privateKey = (PrivateKey) ks.getKey(alias, senhaDoCertificado.toCharArray());
    		SocketFactoryDinamico socketFactoryDinamico = new SocketFactoryDinamico(certificate, privateKey);
    		socketFactoryDinamico.setFileCacerts(arquivoCacertsGeradoTodosOsEstados);

            Protocol protocol = new Protocol("https", socketFactoryDinamico, SSL_PORT);  
            Protocol.registerProtocol("https", protocol);    		
    		
    		/**
             * Xml de Consulta.
             */
            StringBuilder xml = new StringBuilder();
            xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
                .append("<consStatServ versao=\"2.00\" xmlns=\"http://www.portalfiscal.inf.br/nfe\">")
                .append("<tpAmb>2</tpAmb>")
                .append("<cUF>")
                .append(codigoDoEstado)
                .append("</cUF>")
                .append("<xServ>STATUS</xServ>")
                .append("</consStatServ>");

            OMElement ome = AXIOMUtil.stringToOM(xml.toString());
//            NfeStatusServico2Stub.NfeDadosMsg dadosMsg = new NfeStatusServico2Stub.NfeDadosMsg();
            NfeRecepcao2Stub.NfeDadosMsg dadosEnvio = new NfeRecepcao2Stub.NfeDadosMsg();
            dadosEnvio.setExtraElement(ome);
//            dadosMsg.setExtraElement(ome);

//            NfeStatusServico2Stub.NfeCabecMsg nfeCabecMsg = new NfeStatusServico2Stub.NfeCabecMsg();
            NfeRecepcao2Stub.NfeCabecMsg nfeCabecMsg2 = new NfeRecepcao2Stub.NfeCabecMsg();
            nfeCabecMsg2.setCUF(codigoDoEstado);
            nfeCabecMsg2.setVersaoDados("2.00");
            /**
             * C�digo do Estado.
             */
//            nfeCabecMsg.setCUF(codigoDoEstado);

            /**
             * Versao do XML
             */
//            nfeCabecMsg.setVersaoDados("2.00");
//            NfeStatusServico2Stub.NfeCabecMsgE nfeCabecMsgE = new NfeStatusServico2Stub.NfeCabecMsgE();
//            nfeCabecMsgE.setNfeCabecMsg(nfeCabecMsg);
            
            NfeRecepcao2Stub.NfeCabecMsgE nfeCabecMsgE2 = new NfeRecepcao2Stub.NfeCabecMsgE();
            nfeCabecMsgE2.setNfeCabecMsg(nfeCabecMsg2);
            
            NfeRecepcao2Stub stub2 = new NfeRecepcao2Stub(url2.toString());
//            NfeStatusServico2Stub stub = new NfeStatusServico2Stub(url.toString());
            
            NfeRecepcao2Stub.NfeRecepcaoLote2Result result2 = stub2.nfeRecepcaoLote2(dadosEnvio, nfeCabecMsgE2);
//            NfeStatusServico2Stub.NfeStatusServicoNF2Result result = stub.nfeStatusServicoNF2(dadosMsg, nfeCabecMsgE);

            //Teste de parser do retorno
			JAXBContext jc = JAXBContext.newInstance("br.com.sose.nfe.producao.xsdclasses");			
			ObjectFactory obj = new ObjectFactory();
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			
			File xmlFile = new File("35110704736125000135550010000010211000800193-procNfe.xml");	
			TRetEnviNFe retornoEnvio = (TRetEnviNFe)unmarshaller.unmarshal(result2.getExtraElement().getXMLStreamReader());
//			TRetConsStatServ tnp = (TRetConsStatServ)unmarshaller.unmarshal(result.getExtraElement().getXMLStreamReader());
			
			 

            info(result2.getExtraElement().toString());
        } catch (Exception e) {
            error(e.toString());
        }
	}

	/**
	 * Log Info.
	 * @param log
	 */
	private static void info(String log) {
		System.out.println("INFO: " + log);
	}

	/**
	 * Log Error.
	 * @param log
	 */
	private static void error(String log) {
		System.out.println("ERROR: " + log);
	}
	
}


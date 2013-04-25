package br.com.sose.nfe.services;

import java.io.File; 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.security.KeyException;
import java.security.KeyStore;
import java.security.Provider;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.xml.crypto.AlgorithmMethod;
import javax.xml.crypto.KeySelector;
import javax.xml.crypto.KeySelectorException;
import javax.xml.crypto.KeySelectorResult;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class GerarNotaFiscal {

	
	private static final String C14N_TRANSFORM_METHOD = "http://www.w3.org/TR/2001/REC-xml-c14n-20010315"; 
	private static final String PROVIDER_CLASS_NAME = "org.jcp.xml.dsig.internal.dom.XMLDSigRI"; 
	private static final String PROVIDER_NAME = "jsr105Provider"; 

	public void assinar(String caminhoXml, String caminhoCertificado, String senha, String caminhoXmlNovo, String tipo) throws Exception { 

	/* 
	* Opera��o 
	* '1' - NFE 
	* '2' - CANCELAMENTO 
	* '3' - INUTILIZA��O 
	*/ 

	String tag = ""; 
	if (tipo.equals("1")) { 
	tag = "infNFe"; 
	} else if (tipo.equals("2")) { 
	tag = "infCanc"; 
	} else if (tipo.equals("3")) { 
	tag = "infInut"; 
	} 



	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); 
	factory.setNamespaceAware(false); 
	DocumentBuilder builder = factory.newDocumentBuilder(); 
	Document docs = builder.parse(new File(caminhoXml)); 
	NodeList elements = docs.getElementsByTagName(tag); 
	Element el = (Element) elements.item(0); 
	String id = el.getAttribute("Id"); 


	// Cria um DOM do Tipo XMLSignatureFactory que ser� utilizado 
	// para gerar a assinatura envelopada (eneveloped signature) 
	String providerName = System.getProperty(PROVIDER_NAME, PROVIDER_CLASS_NAME); 
	XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM", (Provider) Class.forName(providerName).newInstance()); 

	// Define os Algoritimos de Transforma��o 
	ArrayList transformList = new ArrayList(); 
	TransformParameterSpec tps = null; 
	Transform envelopedTransform = fac.newTransform(Transform.ENVELOPED, tps); 
	Transform c14NTransform = fac.newTransform(C14N_TRANSFORM_METHOD, tps); 
	transformList.add(envelopedTransform); 
	transformList.add(c14NTransform); 

	// Cria objeto Reference 
	Reference ref = fac.newReference("#" + id, fac.newDigestMethod(DigestMethod.SHA1, null), transformList, null, null); 

	// Cria o elemento SignedInfo 
	SignedInfo si = fac.newSignedInfo(fac.newCanonicalizationMethod( 
	CanonicalizationMethod.INCLUSIVE, 
	(C14NMethodParameterSpec) null), fac.newSignatureMethod(SignatureMethod.RSA_SHA1, null), 
	Collections.singletonList(ref)); 


	// Carrega o keyStore e obtem a chave do certificado 
	KeyStore ks = KeyStore.getInstance("PKCS12"); 
	ks.load(new FileInputStream(caminhoCertificado), senha.toCharArray()); 
	Enumeration aliasesEnum = ks.aliases(); 
	String alias = ""; 
	while (aliasesEnum.hasMoreElements()) { 
	alias = (String) aliasesEnum.nextElement(); 

	if (ks.isKeyEntry(alias)) { 

	break; 
	} 
	} 


	KeyStore.PrivateKeyEntry keyEntry = (KeyStore.PrivateKeyEntry) ks.getEntry(alias, new KeyStore.PasswordProtection(senha.toCharArray())); 

	// Instancia um certificado do tipo X509 
	X509Certificate cert = (X509Certificate) keyEntry.getCertificate(); 

	// Cria o elemento KeyInfo contendo a X509Data; 
	KeyInfoFactory kif = fac.getKeyInfoFactory(); 
	List x509Content = new ArrayList(); 
	x509Content.add(cert); 
	X509Data xd = kif.newX509Data(x509Content); 
	KeyInfo ki = kif.newKeyInfo(Collections.singletonList(xd)); 

	// Instancia o documento que ser� assinado 
	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); 
	dbf.setNamespaceAware(true); 
	Document doc = dbf.newDocumentBuilder().parse(new FileInputStream(caminhoXml)); 

	// Cria o DOMSignContext especificando a chave e o n� pai 
	DOMSignContext dsc = new DOMSignContext(keyEntry.getPrivateKey(), doc.getDocumentElement()); 

	// Cria a XMLSignature, mas não assina ainda 
	XMLSignature signature = fac.newXMLSignature(si, ki); 

	// Empacota (marshal), gera e assina 
	signature.sign(dsc); 

	// Arquivo novo Assinado 
	OutputStream os = new FileOutputStream(caminhoXmlNovo); 
	TransformerFactory tf = TransformerFactory.newInstance(); 
	Transformer trans = tf.newTransformer(); 
	trans.transform(new DOMSource(doc), new StreamResult(os)); 

	// Encontra o elemente Signature 
	NodeList nl = doc.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature"); 

	if (nl.getLength() == 0) { 
	throw new Exception("Cannot find Signature element"); 
	} 

	// Cria um DOMValidateContext 
//	DOMValidateContext valContext = new DOMValidateContext(new X509CRLSelector(ks), nl.item(0)); 
//	DOMValidateContext valContext = new DOMValidateContext(new X509CRLSelector(ks),nl.item(0));
//	DOMValidateContext valContext = new DOMValidateContext(
	// Desempacota (unmarshal) a XMLsignature 
//	XMLSignature signatures = fac.unmarshalXMLSignature(valContext); 
//
//	// Validate the XMLSignature. 
//	boolean coreValidity = signatures.validate(valContext); 
//
//	//Checa o status da Valida��o 
//	if (coreValidity == false) { 
//	System.err.println("Falha na Assinatura!"); 
//	} else { 
//	System.out.println("Assinatura Correta!"); 
//	} 
	}
	
	public static void main(String[] args) throws Exception { 

		/* if (args.length != 5) { 
		//JOptionPane.showMessageDialog(null, "S�o esperados 5 par�metros!", "Aten��o", JOptionPane.INFORMATION_MESSAGE); 
		System.out.println("Sao esperados 5 parametros!"); 
		return; 
		}*/ 
		String caminhoXml = "D:\\Projetos\\Sose_v2\\SoseWeb\\35110704736125000135550010000010211000800193-nfe.xml"; 
		String caminhoCertificado = "C:/Users/Negaum/Desktop/paulinho/paulo/certificados_exportados/clienteteste.pfx"; 
		String senha = "teste12345"; 
		String arquivoXmlNovo = "D:\\notaassinado.xml"; 
		String tipo = "1"; 


		File file = new File(caminhoXml); 
		if (!file.exists()) { 
		//JOptionPane.showMessageDialog(null, "Arquivo " + caminhoXml + " não encontrado!", "Aten��o", JOptionPane.INFORMATION_MESSAGE); 
		System.out.println("Arquivo " + caminhoXml + " não encontrado!"); 
		return; 
		} 
		file = new File(caminhoCertificado); 
		if (!file.exists()) { 
		//JOptionPane.showMessageDialog(null, "Arquivo " + caminhoCertificado + " não encontrado!", "Aten��o", JOptionPane.INFORMATION_MESSAGE); 
		System.out.println("Arquivo " + caminhoCertificado + " não encontrado!"); 
		return; 
		} 
		try { 
		GerarNotaFiscal t = new GerarNotaFiscal(); 
		t.assinar(caminhoXml, caminhoCertificado, senha, arquivoXmlNovo, tipo); 
		//JOptionPane.showMessageDialog(null, "Arquivo xml assinado com sucesso!", "Aten��o", JOptionPane.INFORMATION_MESSAGE); 
		System.out.println("Arquivo xml assinado com sucesso" + caminhoXml + "!"); 
		} catch (Exception e) { 
		//JOptionPane.showMessageDialog(null, "Erro ao tentar assinar arquivo xml! \n\n" + e.toString(), "Aten��o", JOptionPane.INFORMATION_MESSAGE); 
		System.out.println("Erro ao tentar assinar arquivo xml! \n\n" + e.toString()); 
		} 
		} 
	
//	private static class KeyValueKeySelector extends KeySelector {
//
//		  public KeySelectorResult select(KeyInfo keyInfo,
//		      KeySelector.Purpose purpose,
//		      AlgorithmMethod method,
//		      XMLCryptoContext context)
//		    throws KeySelectorException {
//
//		    if (keyInfo == null) {
//		      throw new KeySelectorException("Null KeyInfo object!");
//		    }
//		    SignatureMethod sm = (SignatureMethod) method;
//		    List list = keyInfo.getContent();
//
//		    for (int i = 0; i < list.size(); i++) {
//		      XMLStructure xmlStructure = (XMLStructure) list.get(i);
//		      if (xmlStructure instanceof KeyValue) {
//		        PublicKey pk = null;
//		        try {
//		          pk = ((KeyValue)xmlStructure).getPublicKey();
//		        } catch (KeyException ke) {
//		          throw new KeySelectorException(ke);
//		        }
//		        // make sure algorithm is compatible with method
//		        if (algEquals(sm.getAlgorithm(), 
//		            pk.getAlgorithm())) {
//		          return new SimpleKeySelectorResult(pk);
//		        }
//		      }
//		    }
//		    throw new KeySelectorException("No KeyValue element found!");
//		  }
//
//		  static boolean algEquals(String algURI, String algName) {
//		    if (algName.equalsIgnoreCase("DSA") &&
//		        algURI.equalsIgnoreCase(SignatureMethod.DSA_SHA1)) {
//		      return true;
//		    } else if (algName.equalsIgnoreCase("RSA") &&
//		        algURI.equalsIgnoreCase(SignatureMethod.RSA_SHA1)) {
//		      return true;
//		    } else {
//		      return false;
//		    }
//		  }
//		} 
}

package br.com.sose.nfe.services;

import java.io.File;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.log4j.Logger;

import br.com.sose.entity.expedicao.NotaFiscalRemessa;
import br.com.sose.nfe.producao.xsdclasses.ObjectFactory;
import br.com.sose.nfe.producao.xsdclasses.TConsReciNFe;
import br.com.sose.nfe.producao.xsdclasses.TEnviNFe;
import br.com.sose.nfe.producao.xsdclasses.TNFe;
import br.com.sose.nfe.producao.xsdclasses.TRetConsReciNFe;
import br.com.sose.nfe.producao.xsdclasses.TRetEnviNFe;
import br.com.sose.utils.StringOutputStream;

public class NFEServices {

	private Logger logger = Logger.getLogger(this.getClass());

	public void enviarLoteNotaFiscal(Set<NotaFiscalRemessa> notasFiscais){
		
//		ComporNotaFiscal cnf = new ComporNotaFiscal();
//		nota = cnf.criarNotaFiscal(null);
//		String nomeArquivo = nota.getInfNFe().getId();
//		nomeArquivo = nomeArquivo.substring(3);
//		File xmlFile = new File(nomeArquivo + "-nfe2.xml");
//		Marshaller marshal = jc.createMarshaller();
//
//		marshal.marshal(nota, xmlFile);
//		marshal.marshal(nota, System.out);
		
	}
	
	public void enviarNotaFiscal(NotaFiscalRemessa notasFiscal){
		try{
			JAXBContext jc = JAXBContext.newInstance("br.com.sose.nfe.producao.xsdclasses");			
			ObjectFactory obj = new ObjectFactory();

			ComporNotaFiscal cnf = new ComporNotaFiscal();
			TNFe nota = cnf.criarNotaFiscal(null);
			String nomeArquivo = nota.getInfNFe().getId();
			nomeArquivo = nomeArquivo.substring(3);
			Marshaller marshal = jc.createMarshaller();

			StringOutputStream notaString = new StringOutputStream();
			
			TEnviNFe enviNfe = obj.createTEnviNFe();
			
	
			enviNfe.setIdLote("123456");
			enviNfe.setVersao("2.00");
			enviNfe.getNFe().add(nota);
			
			marshal.marshal(enviNfe, notaString);
			
			TRetEnviNFe retorno = NFeStatusServicoFactoryDinamicoA1.enviarNotaFiscal(notaString.toString());
			
			System.out.println("passou");
			
			consultarLoteNotaFiscal(retorno.getInfRec().getNRec());
			
		}catch(Exception e){
			e.printStackTrace(); logger.error(e);
		}

		
	}
	
	public void consultarLoteNotaFiscal(String nLote){
		try{
			JAXBContext jc = JAXBContext.newInstance("br.com.sose.nfe.producao.xsdclasses");			
			ObjectFactory obj = new ObjectFactory();

			TConsReciNFe consulta = obj.createConsReciNFe();
			consulta.setNRec(nLote);
			consulta.setVersao("2.00");
			consulta.setTpAmb("2");

			Marshaller marshal = jc.createMarshaller();

			StringOutputStream consultaString = new StringOutputStream();
			marshal.marshal(consulta, consultaString);
			
			TRetConsReciNFe retorno = NFeStatusServicoFactoryDinamicoA1.consultarNotaFiscal(consultaString.toString());
			
			System.out.println("passou");
			
		}catch(Exception e){
			e.printStackTrace(); logger.error(e);
		}
		
	}
	
	public static void main(String[] args){
		NFEServices nServices = new NFEServices();
		nServices.enviarNotaFiscal(null);
		
	}
	
}

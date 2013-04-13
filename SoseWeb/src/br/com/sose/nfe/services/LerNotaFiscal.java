package br.com.sose.nfe.services;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import br.com.sose.nfe.producao.xsdclasses.ObjectFactory;
import br.com.sose.nfe.producao.xsdclasses.TNFe;
import br.com.sose.nfe.producao.xsdclasses.TNfeProc;

public class LerNotaFiscal {

	public static void main(String[] args){
		try{		
			JAXBContext jc = JAXBContext.newInstance("br.com.sose.nfe.producao.xsdclasses");			
			ObjectFactory obj = new ObjectFactory();
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			
			File xmlFile = new File("35110704736125000135550010000010211000800193-procNfe.xml");	
			TNfeProc tnp = (TNfeProc)unmarshaller.unmarshal(xmlFile);
			tnp.getNFe();
			
//			File xmlFile = new File("35110704736125000135550010000010211000800193-nfe.xml");	
//			TNFe nota = (TNFe)unmarshaller.unmarshal(xmlFile);
//			nota.getInfNFe();
			

		}catch(Exception e){
			e.printStackTrace();
		}


	}
}

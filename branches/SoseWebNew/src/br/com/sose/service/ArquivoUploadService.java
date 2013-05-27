package br.com.sose.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.daoImpl.ArquivoUploadDao;
import br.com.sose.utils.ArquivoUpload;

@Service(value="arquivoUploadService")
@RemotingDestination(value="arquivoUploadService")
public class ArquivoUploadService {
	
	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private ArquivoUploadDao arquivoUploadDao;
	
	public ArquivoUploadService() {
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ArquivoUpload salvarArquivoUpload(ArquivoUpload arquivoUpload) throws Exception {
		ArquivoUpload arquivoUploadSalvo;
		try {
			if(arquivoUpload.getId() == null || arquivoUpload.getId().equals(new Long(0))){
				arquivoUploadSalvo =(ArquivoUpload) arquivoUploadDao.save(arquivoUpload);	
			}else{
				arquivoUploadSalvo =(ArquivoUpload) arquivoUploadDao.update(arquivoUpload);	
			}
		} catch (Exception e) {
			logger.error("Erro ao salvar componente: " + arquivoUpload.getNome());;
			throw e;
		}
		return arquivoUploadSalvo;
	}
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public List<ArquivoUpload> listarPorEntidadePorIdentificador(String entidade, Long idEntidade) {
		return arquivoUploadDao.listarPorEntidadePorIdentificador(entidade, idEntidade);
	}

}

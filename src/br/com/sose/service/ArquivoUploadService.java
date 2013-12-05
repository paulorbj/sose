package br.com.sose.service;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.daoImpl.ArquivoUploadDao;
import br.com.sose.entity.admistrativo.Componente;
import br.com.sose.utils.ArquivoUpload;

@Service(value="arquivoUploadService")
@RemotingDestination(value="arquivoUploadService")
public class ArquivoUploadService {

	private Logger logger = Logger.getLogger(this.getClass());

	private static String caminho = "C:\\arquivo_servilogi\\";


	@Autowired
	private ArquivoUploadDao arquivoUploadDao;

	public ArquivoUploadService() {
	}

	@RemotingInclude
	@Transactional(readOnly = true)
	public ArquivoUpload buscarArquivoUploadLpu(Long idEntidade) {
		return arquivoUploadDao.buscarArquivoUploadLpu(idEntidade);
	}

	@RemotingInclude
	@Transactional(readOnly = true)
	public ArquivoUpload buscarImagemPrincipalComponente(Componente componente) {
		try {
			List<ArquivoUpload> listaRetorno = arquivoUploadDao.listarImagemPorEntidadePorIdentificador("COMPONENTE", componente.getId());
			if(listaRetorno != null){
				return listaRetorno.get(0);
			}else{
				return null;
			}
		}catch(Exception e){
			return null;
		}
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
		List<ArquivoUpload> listaRetorno = arquivoUploadDao.listarPorEntidadePorIdentificador(entidade, idEntidade);
		return listaRetorno;
	}

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<ArquivoUpload> listarImagemPorEntidadePorIdentificador(String entidade, Long idEntidade) {
		List<ArquivoUpload> listaRetorno = arquivoUploadDao.listarImagemPorEntidadePorIdentificador(entidade, idEntidade);
		return listaRetorno;
	}



	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ArquivoUpload excluirArquivoUpload(ArquivoUpload arquivoUpload) throws Exception {
		try {
			arquivoUploadDao.remover(arquivoUpload);	
			logger.info("Arquivo com o nome: "+arquivoUpload.getNome()+" foi removido do sistema");

			String caminhoParaSalvar = caminho + arquivoUpload.getTipoEntidade() + "\\" + arquivoUpload.getId() + "\\" +  arquivoUpload.getTipoArquivo() + "\\" + arquivoUpload.getNome();
			File localParaDeletar = new File(caminhoParaSalvar);
			localParaDeletar.delete();

		} catch (ConstraintViolationException e) {
			logger.error(e);
			throw e;
		}catch (Exception e) {
			logger.error(e);
			throw e;
		}
		return arquivoUpload;
	}

}

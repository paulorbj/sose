package br.com.sose.service.externo;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.daoImpl.administrativo.parceiros.PessoaDao;
import br.com.sose.entity.admistrativo.parceiros.Pessoa;
import br.com.sose.service.administrativo.parceiros.ContatoService;
import br.com.sose.service.administrativo.parceiros.EnderecoService;
import br.com.sose.utils.ConstantesAplicacao;

@Service(value="externoService")
@RemotingDestination(value="externoService")
public class ExternoService  {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	public PessoaDao pessoaDao;
	
	@Autowired
	public ContatoService contatoService;
	
	@Autowired
	public EnderecoService enderecoService;


	
	/* (non-Javadoc)
	 * @see br.com.sose.service.administrativo.parceiros.IPessoaService#listarPrestadoresServico()
	 */
	@RemotingInclude
	@Transactional(readOnly = true)
	public List<Pessoa> listarPrestadoresServico() throws Exception {
		List<Pessoa> pessoas;
		try {
			pessoas =(List<Pessoa>) pessoaDao.listarPessoaPorTipo(ConstantesAplicacao.TIPO_PESSOA_PRESTADOR_SERVICO.getId());	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return pessoas;
	}
	
		
	/********************** Metodos de listagem *********************/
	
	/* (non-Javadoc)
	 * @see br.com.sose.service.administrativo.parceiros.IPessoaService#buscarPorId(java.lang.Long)
	 */
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public Pessoa buscarPorId(Long id) throws Exception {
		Pessoa pessoaSalva;
		try {
			pessoaSalva = pessoaDao.buscarPorId(id);
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return pessoaSalva;
	}

	
	/*********************** Metodos de busca ***********************/

	
	
}

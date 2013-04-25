package br.com.sose.service.administrativo.parceiros;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.daoImpl.administrativo.parceiros.ContatoDao;
import br.com.sose.daoImpl.administrativo.parceiros.EnderecoDao;
import br.com.sose.daoImpl.administrativo.parceiros.PessoaDao;
import br.com.sose.entity.admistrativo.parceiros.Contato;
import br.com.sose.entity.admistrativo.parceiros.Endereco;
import br.com.sose.entity.admistrativo.parceiros.Pessoa;
import br.com.sose.utils.ConstantesAplicacao;

@Service(value="pessoaService")
@RemotingDestination(value="pessoaService")
public class PessoaService  {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	public PessoaDao pessoaDao;
	
	@Autowired
	public ContatoService contatoService;
	
	@Autowired
	public EnderecoService enderecoService;

	/* (non-Javadoc)
	 * @see br.com.sose.service.administrativo.parceiros.IPessoaService#listarPessoas()
	 */
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public List<Pessoa> listarPessoas() throws Exception {
		List<Pessoa> pessoas;
		try {
			pessoas =(List<Pessoa>) pessoaDao.listarPessoas();	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return pessoas;
	}
	
	/* (non-Javadoc)
	 * @see br.com.sose.service.administrativo.parceiros.IPessoaService#listarClientes()
	 */
	@RemotingInclude
	@Transactional(readOnly = true)
	public List<Pessoa> listarClientes() throws Exception {
		List<Pessoa> pessoas;
		try {
			System.out.println("listarClientes - INICIO");
			pessoas =(List<Pessoa>) pessoaDao.listarPessoaPorTipo(ConstantesAplicacao.TIPO_PESSOA_CLIENTE.getId());
			System.out.println("listarClientes - FIM");
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return pessoas;
	}
	
	/* (non-Javadoc)
	 * @see br.com.sose.service.administrativo.parceiros.IPessoaService#listarClientes(java.lang.Boolean)
	 */
	@RemotingInclude
	@Transactional(readOnly = true)
	public List<Pessoa> listarClientes(Boolean possuiContrato) throws Exception {
		List<Pessoa> pessoas;
		List<Pessoa> pessoasRetorno = new ArrayList<Pessoa>();
		try {
			pessoas =(List<Pessoa>) pessoaDao.listarPessoaPorTipo(ConstantesAplicacao.TIPO_PESSOA_CLIENTE.getId());
			for (Pessoa p : pessoas){
				if(p.getPossuiContrato()){
					pessoasRetorno.add(p);
				}
			}
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return pessoasRetorno;
	}
	
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
	
	/* (non-Javadoc)
	 * @see br.com.sose.service.administrativo.parceiros.IPessoaService#listarFornecedores()
	 */
	@RemotingInclude
	@Transactional(readOnly = true)
	public List<Pessoa> listarFornecedores() throws Exception {
		List<Pessoa> pessoas;
		try {
			pessoas =(List<Pessoa>) pessoaDao.listarPessoaPorTipo(ConstantesAplicacao.TIPO_PESSOA_FORNECEDOR.getId());	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return pessoas;
	}
	
	/* (non-Javadoc)
	 * @see br.com.sose.service.administrativo.parceiros.IPessoaService#listarTransportadoras()
	 */
	@RemotingInclude
	@Transactional(readOnly = true)
	public List<Pessoa> listarTransportadoras() throws Exception {
		List<Pessoa> pessoas;
		try {
			pessoas =(List<Pessoa>) pessoaDao.listarPessoaPorTipo(ConstantesAplicacao.TIPO_PESSOA_TRANSPORTADORA.getId());	
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

	/* (non-Javadoc)
	 * @see br.com.sose.service.administrativo.parceiros.IPessoaService#salvarPessoa(br.com.sose.entity.admistrativo.parceiros.Pessoa)
	 */
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Pessoa salvarPessoa(Pessoa pessoa) throws Exception {
		Pessoa pessoaSalva;
		Contato contatoSalvo;
		Endereco enderecoSalvo;
		Set<Contato> contatosSalvos = null;
		Set<Endereco> enderecosSalvos = null;
		Set<Contato> contatos = pessoa.getContatos();
		Set<Endereco> enderecos = pessoa.getEnderecos();
		pessoa.setContatos(null);
		pessoa.setEnderecos(null);
		try {
			if(pessoa.getId() == null || pessoa.getId().equals(new Long(0))){
				pessoaSalva =(Pessoa) pessoaDao.save(pessoa);	
			}else{
				pessoaSalva =(Pessoa) pessoaDao.update(pessoa);	
			}
			if(contatos != null){
				contatosSalvos = new HashSet<Contato>();
				for(Contato c : contatos){
					c.setCliente(pessoaSalva);
					contatoSalvo = contatoService.salvarContato(c);
					if(contatoSalvo != null && contatoSalvo.getId() != 0){
						contatosSalvos.add(contatoSalvo);
					}
				}
			}
			if(enderecos != null){
				enderecosSalvos = new HashSet<Endereco>();
				for(Endereco e : enderecos){
					e.setCliente(pessoaSalva);
					enderecoSalvo = enderecoService.salvarEndereco(e);
					if(enderecoSalvo != null && enderecoSalvo.getId() != 0){
						enderecosSalvos.add(enderecoSalvo);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		pessoaSalva.setContatos(contatosSalvos);
		pessoaSalva.setEnderecos(enderecosSalvos);
		return pessoaSalva;
	}
	
	/* (non-Javadoc)
	 * @see br.com.sose.service.administrativo.parceiros.IPessoaService#excluirPessoa(br.com.sose.entity.admistrativo.parceiros.Pessoa)
	 */
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void excluirPessoa(Pessoa pessoa) throws Exception {
		try {
			contatoService.excluirContatoByPessoa(pessoa);
			enderecoService.excluirEnderecoByPessoa(pessoa);
			pessoaDao.delete(pessoa);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
	}
	
}

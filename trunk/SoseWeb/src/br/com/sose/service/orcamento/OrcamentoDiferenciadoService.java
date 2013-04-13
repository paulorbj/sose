package br.com.sose.service.orcamento;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.daoImpl.orcamento.OrcamentoDiferenciadoDao;
import br.com.sose.entity.orcamento.Orcamento;
import br.com.sose.entity.orcamento.OrcamentoDiferenciado;
import br.com.sose.entity.orcrepGenerico.OrcRepGenerico;
import br.com.sose.entity.recebimento.OrdemServico;
import br.com.sose.entity.reparo.Reparo;
import br.com.sose.service.recebimento.OrdemServicoService;
import br.com.sose.status.orcamentodiferenciado.Aprovado;
import br.com.sose.status.orcamentodiferenciado.Iniciado;
import br.com.sose.status.orcamentodiferenciado.NaoIniciado;
import br.com.sose.status.orcamentodiferenciado.Rejeitado;
import br.com.sose.utils.ConjuntoOrdemServico;

@Service(value="orcamentoDiferenciadoService")
@RemotingDestination(value="orcamentoDiferenciadoService")
public class OrcamentoDiferenciadoService {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	public OrcamentoDiferenciadoDao orcamentoDiferenciadoDao;

	@Autowired
	public OrdemServicoService ordemServicoService;

	@RemotingInclude
	@Transactional(readOnly=true)
	public List<OrcamentoDiferenciado> listarOrcamentosDiferenciados() throws Exception {
		List<OrcamentoDiferenciado> orcamentosDiferenciados = new ArrayList<OrcamentoDiferenciado>();
		List<OrcamentoDiferenciado> orcamentosDiferenciadosAux = null;
		try {
			orcamentosDiferenciadosAux = orcamentoDiferenciadoDao.listarOrcamentosDiferenciados(NaoIniciado.nome);
			if(orcamentosDiferenciadosAux != null && !orcamentosDiferenciadosAux.isEmpty()) orcamentosDiferenciados.addAll(orcamentosDiferenciadosAux);
			orcamentosDiferenciadosAux = orcamentoDiferenciadoDao.listarOrcamentosDiferenciados(Iniciado.nome);
			if(orcamentosDiferenciadosAux != null && !orcamentosDiferenciadosAux.isEmpty()) orcamentosDiferenciados.addAll(orcamentosDiferenciadosAux);
			orcamentosDiferenciadosAux = orcamentoDiferenciadoDao.listarOrcamentosDiferenciados(Aprovado.nome);
			if(orcamentosDiferenciadosAux != null && !orcamentosDiferenciadosAux.isEmpty()) orcamentosDiferenciados.addAll(orcamentosDiferenciadosAux);
			orcamentosDiferenciadosAux = orcamentoDiferenciadoDao.listarOrcamentosDiferenciados(Rejeitado.nome);
			if(orcamentosDiferenciadosAux != null && !orcamentosDiferenciadosAux.isEmpty()) orcamentosDiferenciados.addAll(orcamentosDiferenciadosAux);
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return orcamentosDiferenciados;
	}

	@RemotingInclude
	@Transactional(readOnly=true)
	public OrcamentoDiferenciado buscarPorId(Long id) throws Exception {
		OrcamentoDiferenciado orcamentoDiferenciadoEncontrado;
		try {
			orcamentoDiferenciadoEncontrado =(OrcamentoDiferenciado) orcamentoDiferenciadoDao.buscarPorId(id);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return orcamentoDiferenciadoEncontrado;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public OrcamentoDiferenciado salvarOrcamentoDiferenciado(OrcamentoDiferenciado orcamentoDiferenciado) throws Exception {
		OrcamentoDiferenciado orcamentoDiferenciadoSalvo;
		try {
			if(orcamentoDiferenciado.getId() == null || orcamentoDiferenciado.getId().equals(new Long(0)))
				orcamentoDiferenciadoSalvo =(OrcamentoDiferenciado) orcamentoDiferenciadoDao.save(orcamentoDiferenciado);
			else
				orcamentoDiferenciadoSalvo =(OrcamentoDiferenciado) orcamentoDiferenciadoDao.update(orcamentoDiferenciado);
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return orcamentoDiferenciadoSalvo;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void excluirOrcamentoDiferenciado(OrcamentoDiferenciado orcamentoDiferenciado) throws Exception {
		try {
			orcamentoDiferenciadoDao.delete(orcamentoDiferenciado);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public OrcamentoDiferenciado criarOrcamentoDiferenciado(OrcRepGenerico reparo) throws Exception {
		OrcamentoDiferenciado novoOrcamentoDiferenciado;
		try {
			novoOrcamentoDiferenciado = new OrcamentoDiferenciado();
			novoOrcamentoDiferenciado.setDataCriacao(new Date());
			novoOrcamentoDiferenciado.setCriadoPor(reparo.getTecnicoResponsavel());
			novoOrcamentoDiferenciado.setStatusString(NaoIniciado.nome);
			novoOrcamentoDiferenciado.setReparo((Reparo)reparo);
			OrdemServico os = null;
			if(reparo instanceof Reparo){
				os = ordemServicoService.buscarPorIdSimples(((Reparo)reparo).getOrdemServico().getId());
			}else{
				os = ordemServicoService.buscarPorIdSimples(((Orcamento)reparo).getOrdemServico().getId());
			}
			if(os != null){
				novoOrcamentoDiferenciado.setOrdemServico(os);
				novoOrcamentoDiferenciado =(OrcamentoDiferenciado) salvarOrcamentoDiferenciado(novoOrcamentoDiferenciado);

				os.setOrcamentoDiferenciado(novoOrcamentoDiferenciado);
				os = ordemServicoService.salvarSimplesOrdemServico(os);

				novoOrcamentoDiferenciado.setOrdemServico(os);
			}else{
				throw new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return novoOrcamentoDiferenciado;
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public OrcamentoDiferenciado criarOrcamentoDiferenciadoConjuntoReparo(Reparo reparo, ConjuntoOrdemServico conjunto) throws Exception {
		OrcamentoDiferenciado novoOrcamentoDiferenciado;
		try {

			novoOrcamentoDiferenciado = new OrcamentoDiferenciado();
			novoOrcamentoDiferenciado.setDataCriacao(new Date());
			novoOrcamentoDiferenciado.setCriadoPor(reparo.getTecnicoResponsavel());
			novoOrcamentoDiferenciado.setStatusString(NaoIniciado.nome);
			novoOrcamentoDiferenciado.setReparo(conjunto.getOsPai().getReparo());
			novoOrcamentoDiferenciado.setOrdemServico(conjunto.getOsPai());

			novoOrcamentoDiferenciado =(OrcamentoDiferenciado) salvarOrcamentoDiferenciado(novoOrcamentoDiferenciado);

			conjunto.getOsPai().setOrcamentoDiferenciado(novoOrcamentoDiferenciado);
			ordemServicoService.salvarSimplesOrdemServico(conjunto.getOsPai());

			for(OrdemServico osFilha : conjunto.getListaFilhas()){
				osFilha.setOrcamentoDiferenciado(novoOrcamentoDiferenciado);
				ordemServicoService.salvarSimplesOrdemServico(osFilha);
			}

		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return novoOrcamentoDiferenciado;
	}

	//TODO Esse método deve verificar se nao existe nenhum orcDif aberto. Se nao existir
	//retorno true se existir retorna false. Verificar a data fim
	public Boolean validarCriacaoOrcamentoDiferenciado(OrcRepGenerico reparo) throws Exception {

		return false;
	}

}

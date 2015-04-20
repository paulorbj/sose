package br.com.sose.service.areatecnica;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.daoImpl.areaTecnica.RequisicaoComponenteDao;
import br.com.sose.entity.admistrativo.Componente;
import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.compra.PedidoCompra;
import br.com.sose.entity.orcamento.Orcamento;
import br.com.sose.entity.orcrepGenerico.RequisicaoComponente;
import br.com.sose.entity.reparo.Reparo;
import br.com.sose.service.administrativo.ComponenteService;
import br.com.sose.service.administrativo.ObservacaoService;
import br.com.sose.service.administrativo.UsuarioService;
import br.com.sose.service.areatecnica.exceptions.SaldoInsuficienteException;
import br.com.sose.service.compra.ItemCompraService;
import br.com.sose.service.compra.PedidoCompraService;
import br.com.sose.service.orcamento.OrcamentoService;
import br.com.sose.service.reparo.ReparoService;
import br.com.sose.status.compra.AguardandoCompra;
import br.com.sose.status.estoque.AguardandoAtendimento;
import br.com.sose.status.estoque.Cancelado;
import br.com.sose.status.estoque.Coletado;
import br.com.sose.status.estoque.ColetandoMaterial;
import br.com.sose.status.estoque.ComponenteEmFalta;
import br.com.sose.status.estoque.ComponenteNaoEncontrado;
import br.com.sose.status.estoque.Recebido;
import br.com.sose.status.estoque.Retirado;
import br.com.sose.status.estoque.SendoEntregue;

@Service(value="requisicaoComponenteService")
@RemotingDestination(value="requisicaoComponenteService")
public class RequisicaoComponenteService {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	public RequisicaoComponenteDao requisicaoComponenteDao;

	@Autowired
	public ReparoService reparoService;
	
	@Autowired
	public UsuarioService usuarioService;

	@Autowired
	public OrcamentoService orcamentoService;

	@Autowired
	public ComponenteService componenteService;

	@Autowired
	public PedidoCompraService pedidoCompraService;

	@Autowired
	private ObservacaoService observacaoService;

	@Autowired
	private ItemCompraService itemCompraService;
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public List<RequisicaoComponente> listarRequisicao() throws Exception {
		List<RequisicaoComponente> requisicoes = new ArrayList<RequisicaoComponente>();
		List<RequisicaoComponente> requisicoesAux = null;
		try {
			requisicoesAux = requisicaoComponenteDao.listarRequisicaoPorStatusNoReparo(ComponenteEmFalta.nome);
			if(requisicoesAux != null && !requisicoesAux.isEmpty()) requisicoes.addAll(requisicoesAux);
			requisicoesAux = requisicaoComponenteDao.listarRequisicaoPorStatusNoReparo(AguardandoAtendimento.nome);
			if(requisicoesAux != null && !requisicoesAux.isEmpty()) requisicoes.addAll(requisicoesAux);
			requisicoesAux = requisicaoComponenteDao.listarRequisicaoPorStatusNoReparo(ColetandoMaterial.nome);
			if(requisicoesAux != null && !requisicoesAux.isEmpty()) requisicoes.addAll(requisicoesAux);
			requisicoesAux = requisicaoComponenteDao.listarRequisicaoPorStatusNoReparo(Coletado.nome);
			if(requisicoesAux != null && !requisicoesAux.isEmpty()) requisicoes.addAll(requisicoesAux);
			requisicoesAux = requisicaoComponenteDao.listarRequisicaoPorStatusNoReparo(SendoEntregue.nome);
			if(requisicoesAux != null && !requisicoesAux.isEmpty()) requisicoes.addAll(requisicoesAux);
			requisicoesAux = requisicaoComponenteDao.listarRequisicaoPorStatusNoReparo(Recebido.nome);
			if(requisicoesAux != null && !requisicoesAux.isEmpty()) requisicoes.addAll(requisicoesAux);
			requisicoesAux = requisicaoComponenteDao.listarRequisicaoPorStatusNoReparo(Cancelado.nome);
			if(requisicoesAux != null && !requisicoesAux.isEmpty()) requisicoes.addAll(requisicoesAux);
			requisicoesAux = requisicaoComponenteDao.listarRequisicaoPorStatusNoReparo(Retirado.nome);
			if(requisicoesAux != null && !requisicoesAux.isEmpty()) requisicoes.addAll(requisicoesAux);
			requisicoesAux = requisicaoComponenteDao.listarRequisicaoPorStatusNoReparo(ComponenteNaoEncontrado.nome);
			if(requisicoesAux != null && !requisicoesAux.isEmpty()) requisicoes.addAll(requisicoesAux);
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return requisicoes;
	}

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<RequisicaoComponente> listarRequisicaoEstoque() throws Exception {
		List<RequisicaoComponente> requisicoes = new ArrayList<RequisicaoComponente>();
		List<RequisicaoComponente> requisicoesAux = null;
		try {
			requisicoesAux = requisicaoComponenteDao.listarRequisicaoPorStatus(ComponenteEmFalta.nome);
			if(requisicoesAux != null && !requisicoesAux.isEmpty()) requisicoes.addAll(requisicoesAux);
			requisicoesAux = requisicaoComponenteDao.listarRequisicaoPorStatus(AguardandoAtendimento.nome);
			if(requisicoesAux != null && !requisicoesAux.isEmpty()) requisicoes.addAll(requisicoesAux);
			requisicoesAux = requisicaoComponenteDao.listarRequisicaoPorStatus(ColetandoMaterial.nome);
			if(requisicoesAux != null && !requisicoesAux.isEmpty()) requisicoes.addAll(requisicoesAux);
			requisicoesAux = requisicaoComponenteDao.listarRequisicaoPorStatus(Coletado.nome);
			if(requisicoesAux != null && !requisicoesAux.isEmpty()) requisicoes.addAll(requisicoesAux);
			requisicoesAux = requisicaoComponenteDao.listarRequisicaoPorStatus(SendoEntregue.nome);
			if(requisicoesAux != null && !requisicoesAux.isEmpty()) requisicoes.addAll(requisicoesAux);
			//			requisicoesAux = requisicaoComponenteDao.listarRequisicaoPorStatus(Recebido.nome);
			//			if(requisicoesAux != null && !requisicoesAux.isEmpty()) requisicoes.addAll(requisicoesAux);
			//			requisicoesAux = requisicaoComponenteDao.listarRequisicaoPorStatus(Cancelado.nome);
			//			if(requisicoesAux != null && !requisicoesAux.isEmpty()) requisicoes.addAll(requisicoesAux);
			//			requisicoesAux = requisicaoComponenteDao.listarRequisicaoPorStatus(Retirado.nome);
			//			if(requisicoesAux != null && !requisicoesAux.isEmpty()) requisicoes.addAll(requisicoesAux);
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return requisicoes;
	}

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<RequisicaoComponente> listarRequisicaoPorStatus(String status) throws Exception {
		List<RequisicaoComponente> requisicaoComponentes;
		try {
			requisicaoComponentes =(List<RequisicaoComponente>) requisicaoComponenteDao.listarRequisicaoPorStatus(status);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return requisicaoComponentes;
	}

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<RequisicaoComponente> listarRequisicaoAguardandoAtendimento() throws Exception {
		List<RequisicaoComponente> requisicaoComponentes;
		try {
			requisicaoComponentes =(List<RequisicaoComponente>) requisicaoComponenteDao.listarRequisicaoPorStatus("Aguardando atendimento");	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return requisicaoComponentes;
	}

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<RequisicaoComponente> listarRequisicaoSendoProcessada() throws Exception {
		List<RequisicaoComponente> requisicaoComponentes;
		try {
			requisicaoComponentes =(List<RequisicaoComponente>) requisicaoComponenteDao.listarRequisicaoPorStatus("Sendo processada");	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return requisicaoComponentes;
	}

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<RequisicaoComponente> listarRequisicaoSendoEntrege() throws Exception {
		List<RequisicaoComponente> requisicaoComponentes;
		try {
			requisicaoComponentes =(List<RequisicaoComponente>) requisicaoComponenteDao.listarRequisicaoPorStatus(SendoEntregue.nome);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return requisicaoComponentes;
	}

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<RequisicaoComponente> listarRequisicaoEntregues() throws Exception {
		List<RequisicaoComponente> requisicaoComponentes;
		try {
			requisicaoComponentes =(List<RequisicaoComponente>) requisicaoComponenteDao.listarRequisicaoPorStatus("Entregue");	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return requisicaoComponentes;
	}
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public Long listarTotalRequisicoesUltimos6Meses(Componente componente) throws Exception {
		Long qtdRequisitada;
		try {
			qtdRequisitada =(Long) requisicaoComponenteDao.listarTotalRequisicoesUltimos6Meses(componente);	
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return qtdRequisitada;
	}


	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public RequisicaoComponente salvarRequisicao(RequisicaoComponente requisicaoComponente) throws Exception {
		RequisicaoComponente requisicaoComponenteSalva;
		try {
			if(requisicaoComponente.getId() == null || requisicaoComponente.getId().equals(new Long(0))){
				requisicaoComponenteSalva =(RequisicaoComponente) requisicaoComponenteDao.save(requisicaoComponente);
			}else{
				requisicaoComponenteSalva =(RequisicaoComponente) requisicaoComponenteDao.update(requisicaoComponente);
			}
			requisicaoComponenteDao.flush();
		}catch (Exception e) {
			throw e;
		}
		return requisicaoComponenteSalva;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Boolean alterarStatusAguardandoComponente(RequisicaoComponente requisicaoComponente) throws Exception {
		List<RequisicaoComponente> requisicoes = null;
		try {
			if(requisicaoComponente.getReparo() != null){
				requisicoes = requisicaoComponenteDao.listarRequisicaoPorReparo(requisicaoComponente.getReparo());
			}else{
				requisicoes = requisicaoComponenteDao.listarRequisicaoPorOrcamento(requisicaoComponente.getOrcamento());
			}

			for(RequisicaoComponente rc : requisicoes){
				if(rc.getStatusString().equals(Retirado.nome) || rc.getStatusString().equals(Recebido.nome) || rc.getStatusString().equals(Cancelado.nome) || rc.getStatusString().equals(ComponenteNaoEncontrado.nome)){

				}else{
					return false;
				}
			}
		}catch (Exception e) {
			throw e;
		}
		return true;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public RequisicaoComponente efetivarRequisicao(RequisicaoComponente requisicaoComponente,Usuario realizadoPor) throws Exception {
		RequisicaoComponente requisicaoComponenteSalva;
	
		Componente componente;
		try {
			requisicaoComponente.setDataRequisicao(new Date());
			requisicaoComponente.setStatusString(AguardandoAtendimento.nome);
			requisicaoComponenteSalva =(RequisicaoComponente) salvarRequisicao(requisicaoComponente);

			//TODO - Controle do estoque de componentes
			componente = componenteService.buscarPorId(requisicaoComponente.getComponente().getId());
			Integer saldoEstoque = componente.getQtdEstoque() - requisicaoComponente.getQuantidade();
			if(saldoEstoque >= 0){
				//Possui saldo suficiente no estoque
				componente.setQtdEstoque(saldoEstoque);
				componente = componenteService.salvarComponente(componente);
				requisicaoComponente.setComponente(componente);

				if(componente.getQtdEstoqueMinimo() > 0){
					if(saldoEstoque <= componente.getQtdEstoqueMinimo()){
						PedidoCompra pedidoCompra = pedidoCompraService.buscarPorEstoqueMinimoAguardandoCompra(componente);
						
						Integer quantidadeEmCompra = itemCompraService.calcularQuantidadeAguardandoCompra(componente);
						
						if(pedidoCompra == null && quantidadeEmCompra > 0){
							pedidoCompra = new PedidoCompra();
							pedidoCompra.setDataCriacao(new Date());
							pedidoCompra.setComponente(componente);
							pedidoCompra.setTecnico(usuarioService.buscarPorId(new Long(65)));
							pedidoCompra.setOrigemPedido("Estoque mínimo");
							pedidoCompra.setStatusString("Aguardando compra");
							pedidoCompra.setQuantidade(requisicaoComponente.getQuantidade());							
						}else if(pedidoCompra == null) {
							pedidoCompra = new PedidoCompra();
							pedidoCompra.setDataCriacao(new Date());
							pedidoCompra.setComponente(componente);
							pedidoCompra.setTecnico(usuarioService.buscarPorId(new Long(65)));
							pedidoCompra.setOrigemPedido("Estoque mínimo");
							pedidoCompra.setStatusString("Aguardando compra");
							pedidoCompra.setQuantidade(componente.getQtdEstoqueMinimo() - saldoEstoque);
						}else{
							pedidoCompra.setQuantidade(pedidoCompra.getQuantidade() + requisicaoComponente.getQuantidade());
						}
						
						pedidoCompraService.salvarPedidoCompra(pedidoCompra);
					}
				}
				//TODO - Fazer as alterações para incluir/exibir o icone na listagem geral
				if(requisicaoComponente.getOrcamento() != null){
					Orcamento orc = requisicaoComponente.getOrcamento();
					if(!requisicaoComponente.getOrcamento().getComponentePendente()){
						orc.setComponentePendente(true);
						orc =orcamentoService.salvarOrcamento(orc);
					}
					orc = orcamentoService.buscarPorId(orc.getId());
					requisicaoComponenteSalva.setOrcamento(orc);
					observacaoService.log("Orçamento", "Requisição de componente "+requisicaoComponenteSalva.getId() +" aguardando atendimento",  2, new Date(),orc, realizadoPor);
				}else if(requisicaoComponente.getReparo() != null){
					Reparo rep = requisicaoComponente.getReparo();
					if(!requisicaoComponente.getReparo().getComponentePendente()){
						rep.setComponentePendente(true);
						rep = reparoService.salvarReparo(rep);
					}
					rep = reparoService.buscarPorId(rep.getId());
					requisicaoComponenteSalva.setReparo(rep);
					observacaoService.log("Reparo", "Requisição de componente "+requisicaoComponenteSalva.getId() +" aguardando atendimento",  2, new Date(),rep, realizadoPor);
				}
			}else{
				//Nao possui saldo suficiente no estoque
				throw new SaldoInsuficienteException();
			}
		}catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
		return requisicaoComponenteSalva;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public PedidoCompra efetivarRequisicaoCriarPedidoCompra(PedidoCompra pedidoCompra) throws Exception {
		RequisicaoComponente requisicaoComponenteSalva;
		try {
			pedidoCompra.getRequisicao().setDataRequisicao(new Date());
			pedidoCompra.getRequisicao().setStatusString(ComponenteEmFalta.nome);
			pedidoCompra.setStatusString(AguardandoCompra.nome);
			requisicaoComponenteSalva =(RequisicaoComponente) salvarRequisicao(pedidoCompra.getRequisicao());

			if(requisicaoComponenteSalva.getOrcamento() != null){
				Orcamento orc = requisicaoComponenteSalva.getOrcamento();
				orc.setComponentePendente(true);
				orc.setComponenteEmFalta(true);
				orc =orcamentoService.salvarOrcamento(orc);
				orc = orcamentoService.buscarPorId(orc.getId());
				requisicaoComponenteSalva.setOrcamento(orc);
				observacaoService.log("Orçamento", "Requisição de componente "+requisicaoComponenteSalva.getId() +" componente em falta",  2, new Date(),orc, requisicaoComponenteSalva.getRequisitante());
			}else{
				Reparo rep = requisicaoComponenteSalva.getReparo();
				rep.setComponentePendente(true);
				rep.setComponenteEmFalta(true);
				rep = reparoService.salvarReparo(rep);
				rep = reparoService.buscarPorId(rep.getId());
				requisicaoComponenteSalva.setReparo(rep);
				observacaoService.log("Reparo", "Requisição de componente "+requisicaoComponenteSalva.getId() +" componente em falta",  2, new Date(),rep, requisicaoComponenteSalva.getRequisitante());
			}

			pedidoCompra.setRequisicao(requisicaoComponenteSalva);
			pedidoCompra = pedidoCompraService.salvarPedidoCompra(pedidoCompra);

		}catch (Exception e) {
			throw e;
		}
		return pedidoCompra;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public RequisicaoComponente atenderRequisicao(RequisicaoComponente requisicaoComponente) throws Exception {
		RequisicaoComponente requisicaoComponenteSalva;
		try {
			requisicaoComponente.setDataAtendimento(new Date());
			requisicaoComponente.setStatusString(ColetandoMaterial.nome);
			requisicaoComponenteSalva =(RequisicaoComponente) salvarRequisicao(requisicaoComponente);	
			observacaoService.log("Estoque", "Requisição de componente "+requisicaoComponenteSalva.getId() +" atendida",  2, new Date(),requisicaoComponente.getReparo() != null ? requisicaoComponente.getReparo() : requisicaoComponente.getOrcamento(), requisicaoComponenteSalva.getRequisitante());
		}catch (Exception e) {
			throw e;
		}
		return requisicaoComponenteSalva;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public List<RequisicaoComponente> atenderRequisicoes(List<RequisicaoComponente> requisicoes) throws Exception {
		List<RequisicaoComponente> requisicaoComponenteSalva = null;
		try {
			//			requisicaoComponente.setDataRequisicao(new Date());
			//			requisicaoComponente.setStatus("Aguardando atendimento");
			//			requisicaoComponenteSalva =(RequisicaoComponente) requisicaoComponenteDao.merge(requisicaoComponente);	
		}catch (Exception e) {
			throw e;
		}
		return requisicaoComponenteSalva;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public RequisicaoComponente finalizarColeta(RequisicaoComponente requisicaoComponente) throws Exception {
		RequisicaoComponente requisicaoComponenteSalva;
		try {
			requisicaoComponente.setDataMaterialPronto(new Date());
			requisicaoComponente.setStatusString(Coletado.nome);
			requisicaoComponenteSalva =(RequisicaoComponente) salvarRequisicao(requisicaoComponente);	
		}catch (Exception e) {
			throw e;
		}
		observacaoService.log("Estoque", "Requisição de componente "+requisicaoComponenteSalva.getId() +" material disponível",  2, new Date(),requisicaoComponente.getReparo() != null ? requisicaoComponente.getReparo() : requisicaoComponente.getOrcamento(), requisicaoComponenteSalva.getRequisitante());
		return requisicaoComponenteSalva;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public RequisicaoComponente realizarEntrega(RequisicaoComponente requisicaoComponente) throws Exception {
		RequisicaoComponente requisicaoComponenteSalva;
		try {
			requisicaoComponente.setDataEntrega(new Date());
			requisicaoComponente.setStatusString(SendoEntregue.nome);
			requisicaoComponenteSalva =(RequisicaoComponente) salvarRequisicao(requisicaoComponente);	
		}catch (Exception e) {
			throw e;
		}
		observacaoService.log("Estoque", "Requisição de componente "+requisicaoComponenteSalva.getId() +" sendo entregue",  2, new Date(),requisicaoComponente.getReparo() != null ? requisicaoComponente.getReparo() : requisicaoComponente.getOrcamento(), requisicaoComponenteSalva.getRequisitante());
		return requisicaoComponenteSalva;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public RequisicaoComponente retirarRequisicao(RequisicaoComponente requisicaoComponente) throws Exception {
		RequisicaoComponente requisicaoComponenteSalva;
		try {
			requisicaoComponente.setDataRetirada(new Date());
			requisicaoComponente.setStatusString(Retirado.nome);
			requisicaoComponenteSalva =(RequisicaoComponente) salvarRequisicao(requisicaoComponente);	
		}catch (Exception e) {
			throw e;
		}
		observacaoService.log("Estoque", "Requisição de componente "+requisicaoComponenteSalva.getId() +" retirada",  2, new Date(),requisicaoComponente.getReparo() != null ? requisicaoComponente.getReparo() : requisicaoComponente.getOrcamento(), requisicaoComponenteSalva.getRequisitante());
		return requisicaoComponenteSalva;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public RequisicaoComponente receberMaterial(RequisicaoComponente requisicaoComponente) throws Exception {
		RequisicaoComponente requisicaoComponenteSalva;
		try {
			requisicaoComponente.setDataRecebimento(new Date());
			requisicaoComponente.setStatusString(Recebido.nome);
			requisicaoComponenteSalva =(RequisicaoComponente) salvarRequisicao(requisicaoComponente);	
		}catch (Exception e) {
			throw e;
		}
		observacaoService.log("Estoque", "Requisição de componente "+requisicaoComponenteSalva.getId() +" recebido",  2, new Date(),requisicaoComponente.getReparo() != null ? requisicaoComponente.getReparo() : requisicaoComponente.getOrcamento(), requisicaoComponenteSalva.getRequisitante());
		return requisicaoComponenteSalva;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public RequisicaoComponente cancelarRequisicao(RequisicaoComponente requisicaoComponente) throws Exception {
		RequisicaoComponente requisicaoComponenteSalva;
		try {
			requisicaoComponente.setDataCancelamento(new Date());
			requisicaoComponente.setStatusString(Cancelado.nome);
			requisicaoComponenteSalva =(RequisicaoComponente) salvarRequisicao(requisicaoComponente);	
		}catch (Exception e) {
			throw e;
		}
		observacaoService.log("Estoque", "Requisição de componente "+requisicaoComponenteSalva.getId() +" cancelada",  2, new Date(),requisicaoComponente.getReparo() != null ? requisicaoComponente.getReparo() : requisicaoComponente.getOrcamento(), requisicaoComponenteSalva.getRequisitante());
		return requisicaoComponenteSalva;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public RequisicaoComponente cancelarRequisicaoRestituirComponentes(RequisicaoComponente requisicaoComponente,Usuario realizadoPor) throws Exception {
		RequisicaoComponente requisicaoComponenteSalva;
		try {
			
			PedidoCompra pedidoCompra = pedidoCompraService.buscarPorRequisicao(requisicaoComponente.getId());
			
			if(pedidoCompra != null && pedidoCompra.getItemCompra() != null){
				throw new Exception("A requisição já faz parte de uma compra e não pode ser cancelada!");
			}
			
			if(pedidoCompra != null){
				pedidoCompraService.excluirPedidoCompra(pedidoCompra);
			}
			
			requisicaoComponente.setDataCancelamento(new Date());
			requisicaoComponente.setStatusString(Cancelado.nome);
			requisicaoComponenteSalva =(RequisicaoComponente) salvarRequisicao(requisicaoComponente);	

			//Restituir os componentes ao estoque
			Componente componente = requisicaoComponente.getComponente();
			Integer saldoEstoque = componente.getQtdEstoque() + requisicaoComponente.getQuantidade();
			componente.setQtdEstoque(saldoEstoque);
			componente = componenteService.salvarComponente(componente);
			requisicaoComponenteSalva.setComponente(componente);

			//TODO - Correcao da representação do estoque na listagem geral
			if(this.alterarStatusAguardandoComponente(requisicaoComponente)){
				if(requisicaoComponente.getReparo() != null){
					Reparo rep = requisicaoComponente.getReparo();
					rep.setComponentePendente(false);
					rep.setComponenteEmFalta(false);
					rep = reparoService.salvarReparo(rep);
					requisicaoComponenteSalva.setReparo(rep);
				}else{
					Orcamento orc = requisicaoComponente.getOrcamento();
					orc.setComponentePendente(false);
					orc.setComponenteEmFalta(false);
					orc = orcamentoService.salvarOrcamento(orc);
					requisicaoComponenteSalva.setOrcamento(orc);
				}
			}

		}catch (Exception e) {
			throw e;
		}
		observacaoService.log("Estoque", "Requisição de componente "+requisicaoComponenteSalva.getId() +" cancelada",  2, new Date(),requisicaoComponente.getReparo() != null ? requisicaoComponente.getReparo() : requisicaoComponente.getOrcamento(), realizadoPor);
		return requisicaoComponenteSalva;
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public RequisicaoComponente excluirRequisicao(RequisicaoComponente requisicaoComponente) throws Exception {
		try {
			requisicaoComponenteDao.remover(requisicaoComponente);	
		} catch (ConstraintViolationException e) {
			logger.error(e);
		}catch (Exception e) {
			logger.error(e);
			throw e;
		}
		return requisicaoComponente;
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Set<RequisicaoComponente> reatribuirTecnico(Set<RequisicaoComponente> requisicoes, Usuario novoTecnico) {
		Set<RequisicaoComponente> requisicoesAux = new HashSet<RequisicaoComponente>();
		try {
			for(RequisicaoComponente requisicao : requisicoes){
				requisicao.setRequisitante(novoTecnico);
				requisicao = requisicaoComponenteDao.update(requisicao);
				requisicoesAux.add(requisicao);
			}
			return requisicoesAux;
		} catch (Exception e) {
			return requisicoes;
		}
	}

}

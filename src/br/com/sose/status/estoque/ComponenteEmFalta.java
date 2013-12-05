package br.com.sose.status.estoque;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.entity.admistrativo.Componente;
import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.compra.PedidoCompra;
import br.com.sose.entity.orcamento.Orcamento;
import br.com.sose.entity.orcrepGenerico.RequisicaoComponente;
import br.com.sose.entity.reparo.Reparo;
import br.com.sose.service.administrativo.ComponenteService;
import br.com.sose.service.administrativo.ObservacaoService;
import br.com.sose.service.areatecnica.RequisicaoComponenteService;
import br.com.sose.service.areatecnica.exceptions.SaldoInsuficienteException;
import br.com.sose.service.compra.PedidoCompraService;
import br.com.sose.service.orcamento.OrcamentoService;
import br.com.sose.service.reparo.ReparoService;

@Service("componenteEmFaltaStatusEstoque")
public class ComponenteEmFalta extends StatusEstoque {

	public static final String nome = "Componente em falta"; 
	
	@Autowired
	private RequisicaoComponenteService requisicaoComponenteService;
	
	@Autowired
	private ComponenteService componenteService;
	
	@Autowired
	private ReparoService reparoService;
	
	@Autowired
	private OrcamentoService orcamentoService;

	@Autowired
	private ObservacaoService observacaoService;
	
	@Autowired
	private PedidoCompraService pedidoCompraService;
	
	public ComponenteEmFalta(RequisicaoComponente rc) {
		super.requisicaoComponente = rc;
	}
	
	public ComponenteEmFalta() {
		// TODO Auto-generated constructor stub
	}

	public String getStatus(){
		return nome;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public RequisicaoComponente disponibilizarComponente(Usuario usuario) throws Exception {
		//TODO - Controle do estoque de componentes
		Componente componente = requisicaoComponente.getComponente();
		Integer saldoEstoque = componente.getQtdEstoque() - requisicaoComponente.getQuantidade();
		if(saldoEstoque >= 0){
			requisicaoComponente.setStatusString(AguardandoAtendimento.nome);
			requisicaoComponente = requisicaoComponenteService.salvarRequisicao(requisicaoComponente);
			
			//Possui saldo suficiente no estoque
			componente.setQtdEstoque(saldoEstoque);
			componente = componenteService.salvarComponente(componente);
			requisicaoComponente.setComponente(componente);
			
			if(requisicaoComponente.getOrcamento() != null){
				Orcamento orc = requisicaoComponente.getOrcamento();
				orc.setComponentePendente(true);
				orc =orcamentoService.salvarOrcamento(orc);
				requisicaoComponente.setOrcamento(orc);
			}else{
				Reparo rep = requisicaoComponente.getReparo();
				rep.setComponentePendente(true);
				rep = reparoService.salvarReparo(rep);
				requisicaoComponente.setReparo(rep);
			}
			observacaoService.log("Estoque", "Requisição de componente " + requisicaoComponente.getId() + " saldo disponibilizado",  2, new Date(),requisicaoComponente.getOrcamento() != null ? requisicaoComponente.getOrcamento().getOrdemServico() : requisicaoComponente.getReparo().getOrdemServico(), usuario);
		}else{
			throw new SaldoInsuficienteException();
		}
		return requisicaoComponente;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public RequisicaoComponente cancelarRequisicao(Usuario usuario) throws Exception {
		
		PedidoCompra pedidoCompra = pedidoCompraService.buscarPorRequisicao(requisicaoComponente.getId());
		
		if(pedidoCompra != null && pedidoCompra.getItemCompra() != null){
			throw new Exception("A requisição já faz parte de uma compra e não pode ser cancelada!");
		}
		
		requisicaoComponente.setDataCancelamento(new Date());
		requisicaoComponente.setStatusString(Cancelado.nome);
		requisicaoComponente = requisicaoComponenteService.salvarRequisicao(requisicaoComponente);
		observacaoService.log("Estoque", "Requisição de componente "+requisicaoComponente.getId()+" cancelada", 2, new Date(),requisicaoComponente.getOrcamento() != null ? requisicaoComponente.getOrcamento().getOrdemServico() : requisicaoComponente.getReparo().getOrdemServico(), usuario);
		if(requisicaoComponenteService.alterarStatusAguardandoComponente(requisicaoComponente)){
			if(requisicaoComponente.getReparo() != null){
				Reparo rep = requisicaoComponente.getReparo();
				rep.setComponentePendente(false);
				reparoService.salvarReparo(rep);
			}else{
				Orcamento orc = requisicaoComponente.getOrcamento();
				orc.setComponentePendente(false);
				orcamentoService.salvarOrcamento(orc);
			}
		}
		
		return requisicaoComponente;
	}
	
}

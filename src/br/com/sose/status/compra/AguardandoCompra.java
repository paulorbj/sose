package br.com.sose.status.compra;

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
import br.com.sose.status.estoque.AguardandoAtendimento;

@Service("aguardandoCompraStatusCompra")
public class AguardandoCompra extends StatusCompra {

	public static final String nome = "Aguardando compra"; 
	
	@Autowired
	private RequisicaoComponenteService requisicaoComponenteService;
	
	@Autowired
	private ComponenteService componenteService;
	
	@Autowired
	private ReparoService reparoService;
	
	@Autowired
	private OrcamentoService orcamentoService;

	@Autowired
	private PedidoCompraService pedidoCompraService;
	
	@Autowired
	private ObservacaoService observacaoService;
	
	public AguardandoCompra(PedidoCompra pc) {
		super.pedidoCompra = pc;
	}
	
	public AguardandoCompra() {
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public PedidoCompra comprar(Usuario usuario) throws Exception {

		RequisicaoComponente requisicaoComponente = pedidoCompra.getRequisicao();
		Componente componente = requisicaoComponente.getComponente();
		componente = componenteService.buscarPorId(componente.getId());
		Integer saldoEstoque = componente.getQtdEstoque() - requisicaoComponente.getQuantidade();
		if(saldoEstoque >= 0){
			
			pedidoCompra.setDataFinalizacao(new Date());
			pedidoCompra.setRealizadoPor(usuario);
			pedidoCompra.setStatusString(Comprado.nome);
			pedidoCompra = pedidoCompraService.salvarPedidoCompra(pedidoCompra);
			
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
		return pedidoCompra;
	}

	
}

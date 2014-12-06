package br.com.sose.status.estoque;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.orcamento.Orcamento;
import br.com.sose.entity.orcrepGenerico.ComponenteOrcRep;
import br.com.sose.entity.orcrepGenerico.RequisicaoComponente;
import br.com.sose.entity.reparo.Reparo;
import br.com.sose.service.administrativo.ObservacaoService;
import br.com.sose.service.areatecnica.ComponenteOrcRepService;
import br.com.sose.service.areatecnica.RequisicaoComponenteService;
import br.com.sose.service.orcamento.OrcamentoService;
import br.com.sose.service.reparo.ReparoService;
import br.com.sose.status.reparo.Iniciado;

@Service("sendoEntregueStatusEstoque")
public class SendoEntregue extends StatusEstoque {

	public static final String nome = "Sendo entregue"; 
	
	@Autowired
	private RequisicaoComponenteService requisicaoComponenteService;
	
	@Autowired
	private ReparoService reparoService;
	
	@Autowired
	private OrcamentoService orcamentoService;
	
	@Autowired
	private ComponenteOrcRepService componenteOrcRepService;
	
	@Autowired
	public ObservacaoService observacaoService;
	
	public SendoEntregue(RequisicaoComponente rc) {
		super.requisicaoComponente = rc;
	}
	
	public SendoEntregue() {
		// TODO Auto-generated constructor stub
	}

	public String getStatus(){
		return nome;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public RequisicaoComponente cancelarRequisicao(Usuario usuario) throws Exception {
		throw new Exception("A requisição não pode mais ser cancelada");
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public RequisicaoComponente receberMaterial(Usuario usuario) throws Exception {
		Set<ComponenteOrcRep> componentesRetorno = new HashSet<ComponenteOrcRep>();
		requisicaoComponente.setDataRecebimento(new Date());
		requisicaoComponente.setStatusString(Recebido.nome);
		requisicaoComponente = requisicaoComponenteService.salvarRequisicao(requisicaoComponente);
		
		componenteOrcRepService.criarComponentesOrcRep(requisicaoComponente);
		
		
		if(requisicaoComponenteService.alterarStatusAguardandoComponente(requisicaoComponente)){
			if(requisicaoComponente.getReparo() != null){
				Reparo rep = requisicaoComponente.getReparo();
				rep.setComponentePendente(false);
				rep.setComponenteEmFalta(false);
				//rep.setStatusString(Iniciado.nome);
				rep = reparoService.salvarReparo(rep);
				rep = reparoService.buscarPorId(rep.getId());
				componentesRetorno.addAll(componenteOrcRepService.listarComponentesPosicaoPorReparo(rep));
				rep.setListaComponente(componentesRetorno);
				requisicaoComponente.setReparo(rep);
			}else{
				Orcamento orc = requisicaoComponente.getOrcamento();
				orc.setComponentePendente(false);
				orc.setComponenteEmFalta(false);
				//orc.setStatusString(br.com.sose.status.orcamento.Iniciado.nome);
				orc = orcamentoService.salvarOrcamento(orc);
				orc = orcamentoService.buscarPorId(orc.getId());
				componentesRetorno.addAll(componenteOrcRepService.listarComponentesPosicaoPorOrcamento(orc));
				orc.setListaComponente(componentesRetorno);
				requisicaoComponente.setOrcamento(orc);
			}
		}else{
			if(requisicaoComponente.getReparo() != null){
				Reparo rep = reparoService.buscarPorId(requisicaoComponente.getReparo().getId());
				componentesRetorno.addAll(componenteOrcRepService.listarComponentesPosicaoPorReparo(rep));
				rep.setListaComponente(componentesRetorno);
				requisicaoComponente.setReparo(rep);
			}else{
				Orcamento orc = orcamentoService.buscarPorId(requisicaoComponente.getOrcamento().getId());
				componentesRetorno.addAll(componenteOrcRepService.listarComponentesPosicaoPorOrcamento(orc));
				orc.setListaComponente(componentesRetorno);
				requisicaoComponente.setOrcamento(orc);
			}
		}
		observacaoService.log("Estoque", "Requisição de componente " + requisicaoComponente.getId() + " recebida",  2, new Date(),requisicaoComponente.getOrcamento() != null ? requisicaoComponente.getOrcamento().getOrdemServico() : requisicaoComponente.getReparo().getOrdemServico(), usuario);
		
		return requisicaoComponente;

	}

	
}

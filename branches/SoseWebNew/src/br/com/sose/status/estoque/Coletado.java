package br.com.sose.status.estoque;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.orcamento.Orcamento;
import br.com.sose.entity.orcrepGenerico.RequisicaoComponente;
import br.com.sose.entity.reparo.Reparo;
import br.com.sose.service.administrativo.ObservacaoService;
import br.com.sose.service.areatecnica.ComponenteOrcRepService;
import br.com.sose.service.areatecnica.RequisicaoComponenteService;
import br.com.sose.service.orcamento.OrcamentoService;
import br.com.sose.service.reparo.ReparoService;
import br.com.sose.status.reparo.Iniciado;

@Service("coletadoStatusEstoque")
public class Coletado extends StatusEstoque {

	public static final String nome = "Material disponível"; 
	
	@Autowired
	private RequisicaoComponenteService requisicaoComponenteService;
	
	@Autowired
	private ReparoService reparoService;
	
	@Autowired
	private OrcamentoService orcamentoService;
	
	@Autowired
	private ComponenteOrcRepService componenteOrcRepService;
	
	@Autowired
	private ObservacaoService observacaoService;
	
	public Coletado(RequisicaoComponente rc) {
		super.requisicaoComponente = rc;
	}
	
	public Coletado() {
		// TODO Auto-generated constructor stub
	}

	public String getStatus(){
		return nome;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public RequisicaoComponente cancelarRequisicao(Usuario usuario) throws Exception {
		requisicaoComponente = requisicaoComponenteService.cancelarRequisicaoRestituirComponentes(requisicaoComponente,usuario);
		//observacaoService.log("Estoque", "Requisição de componente "+requisicaoComponente.getId()+" cancelada", 2, new Date(),requisicaoComponente.getOrcamento() != null ? requisicaoComponente.getOrcamento().getOrdemServico() : requisicaoComponente.getReparo().getOrdemServico(), usuario);
		return requisicaoComponente;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public RequisicaoComponente entregarMaterial(Usuario usuario) throws Exception {
		requisicaoComponente.setDataEntrega(new Date());
		requisicaoComponente.setStatusString(SendoEntregue.nome);
		observacaoService.log("Estoque", "Requisição de componente "+requisicaoComponente.getId()+" entregue", 2, new Date(),requisicaoComponente.getOrcamento() != null ? requisicaoComponente.getOrcamento().getOrdemServico() : requisicaoComponente.getReparo().getOrdemServico(), usuario);
		return requisicaoComponenteService.salvarRequisicao(requisicaoComponente);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public RequisicaoComponente retirarMaterial(Usuario usuario) throws Exception {
		requisicaoComponente.setDataRetirada(new Date());
		requisicaoComponente.setStatusString(Retirado.nome);
		requisicaoComponente = requisicaoComponenteService.salvarRequisicao(requisicaoComponente);
		
		componenteOrcRepService.criarComponentesOrcRep(requisicaoComponente);
		
		if(requisicaoComponenteService.alterarStatusAguardandoComponente(requisicaoComponente)){
			if(requisicaoComponente.getReparo() != null){
				Reparo rep = requisicaoComponente.getReparo();
				rep.setComponentePendente(false);
				rep.setComponenteEmFalta(false);
				reparoService.salvarReparo(rep);
			}else{
				Orcamento orc = requisicaoComponente.getOrcamento();
				orc.setComponentePendente(false);
				orc.setComponenteEmFalta(false);
				orcamentoService.salvarOrcamento(orc);
			}
		}
		
		observacaoService.log("Estoque", "Requisição de componente "+requisicaoComponente.getId()+" retirada", 2, new Date(),requisicaoComponente.getOrcamento() != null ? requisicaoComponente.getOrcamento().getOrdemServico() : requisicaoComponente.getReparo().getOrdemServico(), usuario);
		return requisicaoComponente;
	}

	
}

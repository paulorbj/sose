package br.com.sose.status.estoque;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.orcrepGenerico.RequisicaoComponente;

@Service(value="estoqueController")
@RemotingDestination(value="estoqueController")
public class EstoqueController {

	@Autowired
	private AguardandoAtendimento aguardandoAtendimentoEstoqueStatus;
	@Autowired
	private ColetandoMaterial coletandoMaterialEstoqueStatus;
	@Autowired
	private Coletado coletadoEstoqueStatus;
	@Autowired
	private Retirado retiradoEstoqueStatus;
	@Autowired
	private SendoEntregue sendoEntregueEstoqueStatus;
	@Autowired
	private Recebido recebidoEstoqueStatus;	
	@Autowired
	private Cancelado canceladoEstoqueStatus;
	@Autowired
	private ComponenteEmFalta componenteEmFaltaEstoqueStatus;
	@Autowired
	private NotificarEstoque notificarEstoqueStatus;	
	
	private RequisicaoComponente getStatus(RequisicaoComponente requisicaoComponente){
		if(requisicaoComponente.getStatusString() == null || requisicaoComponente.getStatusString().equals("") || requisicaoComponente.getStatusString().equals(AguardandoAtendimento.nome)){
			aguardandoAtendimentoEstoqueStatus.setRequisicaoComponente(requisicaoComponente);
			requisicaoComponente.setStatus(aguardandoAtendimentoEstoqueStatus);
		}else if(requisicaoComponente.getStatusString().equals(ColetandoMaterial.nome)){
			coletandoMaterialEstoqueStatus.setRequisicaoComponente(requisicaoComponente);
			requisicaoComponente.setStatus(coletandoMaterialEstoqueStatus);
		}else if(requisicaoComponente.getStatusString().equals(Coletado.nome)){
			coletadoEstoqueStatus.setRequisicaoComponente(requisicaoComponente);
			requisicaoComponente.setStatus(coletadoEstoqueStatus);
		}else if(requisicaoComponente.getStatusString().equals(Retirado.nome)){
			retiradoEstoqueStatus.setRequisicaoComponente(requisicaoComponente);
			requisicaoComponente.setStatus(retiradoEstoqueStatus);
		}else if(requisicaoComponente.getStatusString().equals(SendoEntregue.nome)){
			sendoEntregueEstoqueStatus.setRequisicaoComponente(requisicaoComponente);
			requisicaoComponente.setStatus(sendoEntregueEstoqueStatus);
		}else if(requisicaoComponente.getStatusString().equals(Recebido.nome)){
			recebidoEstoqueStatus.setRequisicaoComponente(requisicaoComponente);
			requisicaoComponente.setStatus(recebidoEstoqueStatus);
		}else if(requisicaoComponente.getStatusString().equals(Cancelado.nome)){
			canceladoEstoqueStatus.setRequisicaoComponente(requisicaoComponente);
			requisicaoComponente.setStatus(canceladoEstoqueStatus);
		}else if(requisicaoComponente.getStatusString().equals(ComponenteEmFalta.nome)){
			componenteEmFaltaEstoqueStatus.setRequisicaoComponente(requisicaoComponente);
			requisicaoComponente.setStatus(componenteEmFaltaEstoqueStatus);
		}else if(requisicaoComponente.getStatusString().equals(NotificarEstoque.nome)){
			notificarEstoqueStatus.setRequisicaoComponente(requisicaoComponente);
			requisicaoComponente.setStatus(notificarEstoqueStatus);
		}
		return requisicaoComponente;
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public RequisicaoComponente atenderRequisicao(RequisicaoComponente requisicaoComponente,Usuario usuario) throws Exception{
		requisicaoComponente = getStatus(requisicaoComponente);
		return requisicaoComponente.getStatus().atenderRequisicao(usuario);
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public RequisicaoComponente finalizarColeta(RequisicaoComponente requisicaoComponente,Usuario usuario) throws Exception{
		requisicaoComponente = getStatus(requisicaoComponente);
		return requisicaoComponente.getStatus().finalizarColeta(usuario);
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public RequisicaoComponente entregarMaterial(RequisicaoComponente requisicaoComponente,Usuario usuario) throws Exception{
		requisicaoComponente = getStatus(requisicaoComponente);
		return requisicaoComponente.getStatus().entregarMaterial(usuario);
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public RequisicaoComponente retirarMaterial(RequisicaoComponente requisicaoComponente,Usuario usuario) throws Exception{
		requisicaoComponente = getStatus(requisicaoComponente);
		return requisicaoComponente.getStatus().retirarMaterial(usuario);
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public RequisicaoComponente receberMaterial(RequisicaoComponente requisicaoComponente,Usuario usuario) throws Exception{
		requisicaoComponente = getStatus(requisicaoComponente);
		return requisicaoComponente.getStatus().receberMaterial(usuario);
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public RequisicaoComponente cancelarRequisicao(RequisicaoComponente requisicaoComponente,Usuario usuario) throws Exception{
		requisicaoComponente = getStatus(requisicaoComponente);
		return requisicaoComponente.getStatus().cancelarRequisicao(usuario);
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public RequisicaoComponente disponibilizarComponente(RequisicaoComponente requisicaoComponente,Usuario usuario) throws Exception{
		requisicaoComponente = getStatus(requisicaoComponente);
		return requisicaoComponente.getStatus().disponibilizarComponente(usuario);
	}

}

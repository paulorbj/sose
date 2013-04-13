package br.com.sose.status.proposta;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.proposta.ItemProposta;
import br.com.sose.entity.proposta.Proposta;
import br.com.sose.service.administrativo.ObservacaoService;
import br.com.sose.service.proposta.PropostaService;

@Service("aguardandoAprovacaoClienteStatusProposta")
public class AguardandoAprovacaoCliente extends StatusProposta {

	public static final String nome = "Aguardando aprovação do cliente"; 
	
	@Autowired
	private PropostaService propostaService;
	
	@Autowired
	private ObservacaoService observacaoService;
	
	public AguardandoAprovacaoCliente(Proposta p) {
		super.proposta = p;
	}
	
	public AguardandoAprovacaoCliente() {
	}

	@Override
	public Proposta cancelarProposta(Usuario usuario) throws Exception {
		// TODO Auto-generated method stub
		return super.cancelarProposta(usuario);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Proposta finalizarProposta(Usuario usuario) throws Exception {
		proposta.setDataFinalizacao(new Date());
		proposta.setStatusString(Finalizada.nome);
		propostaService.liberarItens(proposta,proposta.getItensProposta());
		
		observacaoService.log("Proposta", "Proposta Nº "+proposta.getNumero()+" finalizada", 2, new Date(),proposta, usuario);
		proposta = propostaService.finalizarProposta(proposta);
//		proposta = propostaService.buscarPorId(proposta.getId());
		return proposta;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Proposta salvarProposta(Usuario usuario) throws Exception {
		proposta = propostaService.salvarProposta(proposta);
//		proposta = propostaService.buscarPorId(proposta.getId());
		return proposta;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Proposta liberarItens(Set<ItemProposta> itens,Usuario usuario) throws Exception {
		proposta = propostaService.liberarItens(proposta,itens);
//		proposta = propostaService.buscarPorId(proposta.getId());
		
		Boolean finalizarProposta = true;
		for(ItemProposta ip : proposta.getItensProposta()){
			if(ip.getDataLiberacao() == null){
				finalizarProposta = false;
			}
		}
		if(finalizarProposta){
			observacaoService.log("Proposta", "Proposta Nº "+proposta.getNumero()+" finalizada", 2, new Date(),proposta, usuario);
			proposta.setDataFinalizacao(new Date());
			proposta.setStatusString(Finalizada.nome);
			proposta = propostaService.finalizarProposta(proposta);
//			proposta = propostaService.buscarPorId(proposta.getId());
		}
		return proposta; 
	}
}

package br.com.sose.status.proposta;

import java.util.Date;
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

@Service("emElaboracaoStatusProposta")
public class EmElaboracao extends StatusProposta {

	public static final String nome = "Em elaboração"; 
	
	@Autowired
	private PropostaService propostaService;
	
	@Autowired
	private ObservacaoService observacaoService;
	
	public EmElaboracao(Proposta p) {
		super.proposta = p;
	}
	
	public EmElaboracao() {
		// TODO Auto-generated constructor stub
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Proposta finalizarProposta(Usuario usuario) throws Exception {
		proposta.setDataFinalizacao(new Date());
		proposta.setStatusString(Finalizada.nome);
		propostaService.liberarItens(proposta,proposta.getItensProposta());
		
		observacaoService.log("Proposta", "Proposta Nº "+proposta.getNumero()+" finalizada", 2, new Date(), proposta, usuario);
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
	public Proposta enviarAoCliente(Usuario usuario) throws Exception {
		proposta.setDataEnvioCliente(new Date());
		proposta.setStatusString(AguardandoAprovacaoCliente.nome);
		proposta = propostaService.salvarProposta(proposta);
		
		observacaoService.log("Proposta", "Proposta Nº "+proposta.getNumero()+" enviada ao cliente", 2, new Date(), proposta, usuario);
		proposta = propostaService.enviarAoCliente(proposta);
//		proposta = propostaService.buscarPorId(proposta.getId());
		return proposta;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Proposta liberarItens(Set<ItemProposta> itens,Usuario usuario) throws Exception {
		proposta = propostaService.salvarProposta(proposta);
		proposta = propostaService.liberarItens(proposta,itens);
//		proposta = propostaService.buscarPorId(proposta.getId());
		return proposta;
	}

	
} 

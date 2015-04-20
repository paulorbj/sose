package br.com.sose.status.proposta;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.proposta.Proposta;
import br.com.sose.service.administrativo.ObservacaoService;
import br.com.sose.service.proposta.PropostaService;

@Service("novaStatusProposta")
public class Nova extends StatusProposta {

	public static final String nome = "Nova"; 
	
	@Autowired
	private PropostaService propostaService;
	
	@Autowired
	private ObservacaoService observacaoService;
	
	public Nova(Proposta p) {
		super.proposta = p;
	}
	
	public Nova() {
	}

	public String getStatus(){
		return "Nova";
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Proposta iniciarProposta(Usuario usuario) throws Exception {
		proposta.setStatusString(EmElaboracao.nome);
		proposta.setDataInicio(new Date());
		proposta.setContato(null);
		proposta = propostaService.salvarProposta(proposta);
		observacaoService.log("Proposta", "Proposta Nº "+proposta.getNumero()+" iniciada", 2, new Date(), proposta, usuario);
//		proposta = propostaService.buscarPorId(proposta.getId());
		return proposta;
	}



	
	
}

package br.com.sose.status.reparo;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.reparo.Reparo;
import br.com.sose.service.reparo.ReparoService;
import br.com.sose.status.orcamento.NaoIniciado;

@Service("aguardandoLiberacaoStatusReparo")
public class AguardandoLiberacao extends StatusReparo {

	@Autowired
	private ReparoService reparoService;
	
	public static final String nome = "Aguardando liberação"; 
	
	public AguardandoLiberacao(Reparo r) {
		super.reparo = r;
	}
	
	public AguardandoLiberacao() {
	}

	public String getNome(){
		return nome;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Reparo atribuirTecnico(Reparo reparo, Usuario usuario,Usuario atribuidoPor) throws Exception {
		reparo.setStatusString(NaoIniciado.nome);
		return reparoService.atribuirTecnico(reparo, usuario,atribuidoPor);	
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Reparo atribuirPrioridade(Reparo reparo, Date date, Usuario usuario) throws Exception {
		return reparoService.atribuirPrioridade(reparo,date, usuario);
	}
	

	
}

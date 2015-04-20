package br.com.sose.status.reparo;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.reparo.Reparo;
import br.com.sose.service.reparo.ReparoService;

@Service("aguardandoAprovacaoLiderStatusReparo")
public class AguardandoOrcamentoDiferenciado extends StatusReparo {

	public static String nome = "Aguardando orçamento diferenciado";
	
	@Autowired
	private ReparoService reparoService;
	
	public AguardandoOrcamentoDiferenciado(Reparo r) {
		super.reparo = r;
	}
	
	public AguardandoOrcamentoDiferenciado() {
	}
	
	public String getNome(){
		return nome;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Reparo atribuirTecnico(Reparo reparo, Usuario usuario, Usuario atribuidoPor) throws Exception {
		return reparoService.reAtribuirTecnico(reparo, usuario, atribuidoPor);	
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Reparo atribuirPrioridade(Reparo reparo, Date date, Usuario usuario) throws Exception {
		return reparoService.atribuirPrioridade(reparo,date, usuario);
	}
	
}

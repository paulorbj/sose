package br.com.sose.status.reparo;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.reparo.Reparo;
import br.com.sose.service.administrativo.ObservacaoService;
import br.com.sose.service.recebimento.OrdemServicoService;
import br.com.sose.service.reparo.ReparoService;
import br.com.sose.status.aplicacao.ReparoSendoRealizado;

@Service("naoIniciadoStatusReparo")
public class NaoIniciado extends StatusReparo {

	public static final String nome = "Não Iniciado"; 
	
	@Autowired
	private ReparoService reparoService;
	
	@Autowired
	private OrdemServicoService ordemServicoService;
	
	@Autowired
	private ObservacaoService observacaoService;
	
	public NaoIniciado(Reparo r) {
		super.reparo = r;
	}
	
	public NaoIniciado() {
	}

	public String getNome(){
		return nome;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Reparo iniciarReparo(Reparo reparo, Usuario usuario) throws Exception {
		reparo.setStatusString(Iniciado.nome);
		reparo.setDataInicio(new Date());
		reparo = reparoService.salvarReparo(reparo);
		
		reparo.getOrdemServico().setStatusString(ReparoSendoRealizado.nome);
		ordemServicoService.salvarOrdemServico(reparo.getOrdemServico());
		
		observacaoService.log("Reparo", "Reparo iniciado", 2, new Date(), reparo, usuario);
		return reparo;
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

package br.com.sose.status.laudotecnico;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.laudoTecnico.LaudoTecnico;
import br.com.sose.entity.recebimento.OrdemServico;
import br.com.sose.service.administrativo.ObservacaoService;
import br.com.sose.service.laudoTecnico.LaudoTecnicoService;
import br.com.sose.service.recebimento.OrdemServicoService;
import br.com.sose.status.aplicacao.LaudoTecnicoSendoRealizado;
import br.com.sose.status.aplicacao.OrcamentoDiferenciadoSendoRealizado;
import br.com.sose.status.orcamentodiferenciado.Iniciado;

@Service("naoIniciadoStatusLaudoTecnico")
public class NaoIniciado extends StatusLaudoTecnico {

	public static String nome = "Não iniciado";
	
	@Autowired
	private LaudoTecnicoService laudoTecnicoService;
	
	@Autowired
	private OrdemServicoService ordemServicoService;
	
	@Autowired
	private ObservacaoService observacaoService;
	
	public NaoIniciado(LaudoTecnico laudoTecnico) {
		this.laudoTecnico = laudoTecnico;
	}

	public NaoIniciado() {
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public LaudoTecnico iniciarLaudoTecnico(Usuario usuario) throws Exception {
		laudoTecnico.setDataInicio(new Date());
		laudoTecnico.setStatusString(Iniciado.nome);		
		laudoTecnico = laudoTecnicoService.salvarLaudoTecnico(laudoTecnico);
		
		OrdemServico os = laudoTecnico.getOrdemServico();
		os.setStatusString(LaudoTecnicoSendoRealizado.nome);
		ordemServicoService.salvarSimplesOrdemServico(os);
		
		observacaoService.log("Laudo técnico", "Laudo técnico Nº "+laudoTecnico.getControle()+" iniciado", 2, new Date(),os, usuario);
		
//		laudoTecnico = laudoTecnicoService.buscarPorId(laudoTecnico.getId());
		return laudoTecnico;
	}
	
	
	
}

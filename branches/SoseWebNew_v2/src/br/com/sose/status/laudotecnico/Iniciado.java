package br.com.sose.status.laudotecnico;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.laudoTecnico.LaudoTecnico;
import br.com.sose.entity.orcamento.Orcamento;
import br.com.sose.entity.recebimento.OrdemServico;
import br.com.sose.entity.reparo.Reparo;
import br.com.sose.service.ArquivoUploadService;
import br.com.sose.service.administrativo.ObservacaoService;
import br.com.sose.service.laudoTecnico.LaudoTecnicoService;
import br.com.sose.service.orcamento.OrcamentoService;
import br.com.sose.service.recebimento.OrdemServicoService;
import br.com.sose.service.reparo.ReparoService;
import br.com.sose.status.aplicacao.AguardandoProposta;
import br.com.sose.status.aplicacao.OrcamentoSendoRealizado;
import br.com.sose.status.aplicacao.ReparoSendoRealizado;
import br.com.sose.status.orcamento.AguardandoConjuntoProposta;
import br.com.sose.status.orcamentodiferenciado.Aprovado;
import br.com.sose.status.reparo.AguardandoConjuntoExpedicao;
import br.com.sose.status.reparo.AguardandoLaudoTecnico;
import br.com.sose.utils.ArquivoUpload;
import br.com.sose.utils.ConjuntoOrdemServico;
import br.com.sose.utils.ConstantesAplicacao;
import br.com.sose.utils.OrdemServicoUtils;

@Service("iniciadoStatusLaudoTecnico")
public class Iniciado extends StatusLaudoTecnico {

	public static String nome = "Iniciado";

	@Autowired
	private LaudoTecnicoService laudoTecnicoService;

	@Autowired
	private ArquivoUploadService arquivoUploadService;

	@Autowired
	private OrdemServicoService ordemServicoService;

	@Autowired
	private ReparoService reparoService;

	@Autowired
	private OrcamentoService orcamentoService;

	@Autowired
	private ObservacaoService observacaoService;

	@Autowired
	private OrdemServicoUtils ordemServicoUtils;

	public Iniciado(LaudoTecnico laudoTecnico) {
		this.laudoTecnico = laudoTecnico;
	}

	public Iniciado() {
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public LaudoTecnico finalizarLaudoTecnico(Usuario usuario) throws Exception {
		laudoTecnico.setDataFim(new Date());
		laudoTecnico.setStatusString(Aprovado.nome);

		Reparo reparo = laudoTecnico.getReparo();
		Orcamento orcamento = laudoTecnico.getOrcamento();
		OrdemServico os = laudoTecnico.getOrdemServico();


		if(reparo != null){

			if(ordemServicoUtils.pertenceConjunto(os)){
				ConjuntoOrdemServico cos = ordemServicoUtils.montarConjunto(os);
				cos.getOsPai().getReparo().setCondicao(ConstantesAplicacao.REPARO_SEM_CONDICAO_DE_REPARO);
				cos.getOsPai().setBloqueado(2);//TODO notificar a realização de um laudo tecnico 
				if(cos.getOsPai().getReparo().getStatusString().equals(AguardandoLaudoTecnico.nome) || cos.getOsPai().getReparo().getStatusString().equals(AguardandoConjuntoExpedicao.nome)){
					cos.getOsPai().getReparo().setStatusString(br.com.sose.status.reparo.Iniciado.nome);
					cos.getOsPai().setStatusString(ReparoSendoRealizado.nome);
				}
				reparoService.salvarReparo(cos.getOsPai().getReparo());
				//TODO - Talvez seja necessario setar o laudo para todas as os's do conjunto (VERIFICAR)
				ordemServicoService.salvarOrdemServico(cos.getOsPai());
				laudoTecnico.setOrdemServico(cos.getOsPai());
				laudoTecnico = laudoTecnicoService.salvarLaudoTecnico(laudoTecnico); 
				observacaoService.log("Laudo técnico", "Laudo técnico Nº "+laudoTecnico.getControle()+" aprovado", 2, new Date(), cos.getOsPai(), usuario);

				for(OrdemServico osFilha : cos.getListaFilhas()){
					osFilha.getReparo().setCondicao(ConstantesAplicacao.REPARO_SEM_CONDICAO_DE_REPARO);
					osFilha.setBloqueado(2);
					if(osFilha.getReparo().getStatusString().equals(AguardandoLaudoTecnico.nome) || osFilha.getReparo().getStatusString().equals(AguardandoConjuntoExpedicao.nome)){
						osFilha.getReparo().setStatusString(br.com.sose.status.reparo.Iniciado.nome);
						osFilha.setStatusString(ReparoSendoRealizado.nome);
					}
					reparoService.salvarReparo(osFilha.getReparo());
					ordemServicoService.salvarOrdemServico(osFilha);
					observacaoService.log("Laudo técnico", "Laudo técnico Nº "+laudoTecnico.getControle()+" aprovado", 2, new Date(), osFilha, usuario);

				}

			}else{
				//TODO - Ao finalizar laudo tecnico, encaminha-lo de volta para o reparo
				reparo.setStatusString(Iniciado.nome);
				reparo.setCondicao(ConstantesAplicacao.REPARO_SEM_CONDICAO_DE_REPARO);

				reparo.setLaudoTecnicoAprovado(new Date());
				reparoService.salvarReparo(reparo);

				os.setStatusString(ReparoSendoRealizado.nome);
				ordemServicoService.salvarOrdemServico(os);

				observacaoService.log("Laudo técnico", "Laudo técnico Nº "+laudoTecnico.getControle()+" aprovado", 2, new Date(), os, usuario);
				laudoTecnico.setOrdemServico(os);
				laudoTecnico = laudoTecnicoService.salvarLaudoTecnico(laudoTecnico); 
			}

		}else{
			if(ordemServicoUtils.pertenceConjunto(os)){
				ConjuntoOrdemServico cos = ordemServicoUtils.montarConjunto(os);
				cos.getOsPai().getOrcamento().setCondicao(ConstantesAplicacao.REPARO_SEM_CONDICAO_DE_REPARO);
				cos.getOsPai().setBloqueado(2);//TODO notificar a realização de um laudo tecnico 
				if(cos.getOsPai().getOrcamento().getStatusString().equals(AguardandoLaudoTecnico.nome)){
					cos.getOsPai().getOrcamento().setStatusString(AguardandoConjuntoProposta.nome);
				}
				orcamentoService.salvarOrcamento(cos.getOsPai().getOrcamento());
				ordemServicoService.salvarOrdemServico(cos.getOsPai());
				laudoTecnico.setOrdemServico(cos.getOsPai());
				laudoTecnico = laudoTecnicoService.salvarLaudoTecnico(laudoTecnico); 
				observacaoService.log("Laudo técnico", "Laudo técnico Nº "+laudoTecnico.getControle()+" aprovado", 2, new Date(), cos.getOsPai(), usuario);

				for(OrdemServico osFilha : cos.getListaFilhas()){
					osFilha.getOrcamento().setCondicao(ConstantesAplicacao.REPARO_SEM_CONDICAO_DE_REPARO);
					osFilha.setBloqueado(2);
					if(osFilha.getOrcamento().getStatusString().equals(AguardandoLaudoTecnico.nome)){
						osFilha.getOrcamento().setStatusString(AguardandoConjuntoProposta.nome);
					}
					orcamentoService.salvarOrcamento(osFilha.getOrcamento());
					ordemServicoService.salvarOrdemServico(osFilha);
					observacaoService.log("Laudo técnico", "Laudo técnico Nº "+laudoTecnico.getControle()+" aprovado", 2, new Date(), osFilha, usuario);

				}

				//Verificar se todas as os's estao em aguardando proposta e se estiverem encaminhar pra proposta
				if(ordemServicoUtils.conjuntoCompleto(orcamento)){	
					cos.getOsPai().setStatusString(AguardandoProposta.nome);
					ordemServicoService.salvarSimplesOrdemServico(cos.getOsPai());

					cos.getOsPai().getOrcamento().setStatusString(br.com.sose.status.orcamento.AguardandoProposta.nome);
					orcamentoService.salvarOrcamento(cos.getOsPai().getOrcamento());

					for(OrdemServico osFilha : cos.getListaFilhas()){
						osFilha.setStatusString(AguardandoProposta.nome);
						ordemServicoService.salvarSimplesOrdemServico(osFilha);


						osFilha.getOrcamento().setStatusString(br.com.sose.status.orcamento.AguardandoProposta.nome);
						orcamentoService.salvarOrcamento(osFilha.getOrcamento());
					}
				}				
			}else{
				//TODO - Ao finalizar laudo tecnico, encaminha-lo para proposta
				orcamento.setStatusString(br.com.sose.status.orcamento.AguardandoProposta.nome);
				orcamento.setCondicao(ConstantesAplicacao.REPARO_SEM_CONDICAO_DE_REPARO);

				orcamento.setLaudoTecnicoAprovado(new Date());
				orcamentoService.salvarOrcamento(orcamento);

				os.setStatusString(AguardandoProposta.nome);
				ordemServicoService.salvarOrdemServico(os);

				observacaoService.log("Laudo técnico", "Laudo técnico Nº "+laudoTecnico.getControle()+" aprovado", 2, new Date(), os, usuario);
				laudoTecnico.setOrdemServico(os);
				laudoTecnico = laudoTecnicoService.salvarLaudoTecnico(laudoTecnico); 
			}


		}

		//		laudoTecnico = laudoTecnicoService.buscarPorId(laudoTecnico.getId());
		return laudoTecnico;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public LaudoTecnico rejeitarLaudoTecnico(Usuario usuario) throws Exception {
		laudoTecnico.setDataFim(new Date());
		laudoTecnico.setStatusString(Rejeitado.nome);

		Reparo reparo = laudoTecnico.getReparo();
		Orcamento orcamento = laudoTecnico.getOrcamento();
		OrdemServico os = laudoTecnico.getOrdemServico();


		if(reparo != null){
			if(ordemServicoUtils.pertenceConjunto(os)){
				ConjuntoOrdemServico cos = ordemServicoUtils.montarConjunto(os);
				cos.getOsPai().getReparo().setCondicao(ConstantesAplicacao.REPARO_COM_CONDICAO_DE_REPARO);
				cos.getOsPai().getReparo().setLaudoTecnicoReprovado(new Date());
				cos.getOsPai().getReparo().setDataRequisicaoLaudoTecnico(null);
				cos.getOsPai().setBloqueado(0);//TODO notificar a rejeição de um laudo tecnico 
				//				cos.getOsPai().setLaudoTecnico(null);
				if(cos.getOsPai().getReparo().getStatusString().equals(AguardandoLaudoTecnico.nome)){
					cos.getOsPai().getReparo().setStatusString(Iniciado.nome);
					cos.getOsPai().setStatusString(ReparoSendoRealizado.nome);
				}
				reparoService.salvarReparo(cos.getOsPai().getReparo());
				ordemServicoService.salvarOrdemServico(cos.getOsPai());
				observacaoService.log("Laudo técnico", "Laudo técnico Nº "+laudoTecnico.getControle()+" rejeitado", 2, new Date(), cos.getOsPai(), usuario);

				for(OrdemServico osFilha : cos.getListaFilhas()){
					osFilha.getReparo().setCondicao(ConstantesAplicacao.REPARO_COM_CONDICAO_DE_REPARO);
					osFilha.getReparo().setLaudoTecnicoReprovado(new Date());
					osFilha.getReparo().setDataRequisicaoLaudoTecnico(null);
					osFilha.setBloqueado(0);
					//					osFilha.setLaudoTecnico(null);
					if(osFilha.getReparo().getStatusString().equals(AguardandoLaudoTecnico.nome)){
						osFilha.getReparo().setStatusString(Iniciado.nome);
						osFilha.setStatusString(ReparoSendoRealizado.nome);
					}
					reparoService.salvarReparo(osFilha.getReparo());
					ordemServicoService.salvarOrdemServico(osFilha);
					observacaoService.log("Laudo técnico", "Laudo técnico Nº "+laudoTecnico.getControle()+" rejeitado", 2, new Date(), osFilha, usuario);

				}
			}else{
				reparo.setStatusString(Iniciado.nome);
				reparo.setLaudoTecnicoReprovado(new Date());
				reparo.setCondicao(ConstantesAplicacao.REPARO_COM_CONDICAO_DE_REPARO);
				reparoService.salvarReparo(reparo);
				os.setStatusString(ReparoSendoRealizado.nome);
				ordemServicoService.salvarOrdemServico(os);

				observacaoService.log("Laudo técnico", "Laudo técnico Nº "+laudoTecnico.getControle()+" rejeitado", 2, new Date(), os, usuario);
			}
		}else{
			if(ordemServicoUtils.pertenceConjunto(os)){
				ConjuntoOrdemServico cos = ordemServicoUtils.montarConjunto(os);
				cos.getOsPai().getOrcamento().setCondicao(ConstantesAplicacao.REPARO_COM_CONDICAO_DE_REPARO);
				cos.getOsPai().getOrcamento().setLaudoTecnicoReprovado(new Date());
				cos.getOsPai().getOrcamento().setDataRequisicaoLaudoTecnico(null);
				cos.getOsPai().setBloqueado(0);//TODO notificar a rejeição de um laudo tecnico 
				//				cos.getOsPai().setLaudoTecnico(null);
				if(cos.getOsPai().getOrcamento().getStatusString().equals(AguardandoLaudoTecnico.nome)){
					cos.getOsPai().getOrcamento().setStatusString(Iniciado.nome);
				}
				cos.getOsPai().getOrcamento().setDataRequisicaoLaudoTecnico(null);
				orcamentoService.salvarOrcamento(cos.getOsPai().getOrcamento());
				ordemServicoService.salvarOrdemServico(cos.getOsPai());
				observacaoService.log("Laudo técnico", "Laudo técnico Nº "+laudoTecnico.getControle()+" rejeitado", 2, new Date(), cos.getOsPai(), usuario);

				for(OrdemServico osFilha : cos.getListaFilhas()){
					osFilha.getOrcamento().setCondicao(ConstantesAplicacao.REPARO_COM_CONDICAO_DE_REPARO);
					osFilha.getOrcamento().setDataRequisicaoLaudoTecnico(null);
					osFilha.getOrcamento().setLaudoTecnicoReprovado(new Date());
					osFilha.setBloqueado(0);
					//					osFilha.setLaudoTecnico(null);
					if(osFilha.getOrcamento().getStatusString().equals(AguardandoLaudoTecnico.nome)){
						osFilha.getOrcamento().setStatusString(Iniciado.nome);
					}
					orcamentoService.salvarOrcamento(osFilha.getOrcamento());
					ordemServicoService.salvarOrdemServico(osFilha);
					observacaoService.log("Laudo técnico", "Laudo técnico Nº "+laudoTecnico.getControle()+" rejeitado", 2, new Date(), osFilha, usuario);

				}
			}else{
				orcamento.setStatusString(Iniciado.nome);
				orcamento.setLaudoTecnicoReprovado(new Date());
				orcamento.setDataRequisicaoLaudoTecnico(null);
				orcamento.setCondicao(ConstantesAplicacao.REPARO_COM_CONDICAO_DE_REPARO);
				orcamentoService.salvarOrcamento(orcamento);
				os.setStatusString(OrcamentoSendoRealizado.nome);
				ordemServicoService.salvarOrdemServico(os);

				observacaoService.log("Laudo técnico", "Laudo técnico Nº "+laudoTecnico.getControle()+" rejeitado", 2, new Date(),os, usuario);
			}

		}

		laudoTecnico.setOrdemServico(os);
		laudoTecnico = laudoTecnicoService.salvarLaudoTecnico(laudoTecnico); 
		//		laudoTecnico = laudoTecnicoService.buscarPorId(laudoTecnico.getId());
		return laudoTecnico;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public LaudoTecnico salvarLaudoTecnico( List<ArquivoUpload> imagens, Usuario usuario) throws Exception {
		laudoTecnico = laudoTecnicoService.salvarLaudoTecnico(laudoTecnico);
		if(imagens != null){
			Integer total = 0;
			for(ArquivoUpload au : imagens){
				if(total < 3){
					arquivoUploadService.salvarArquivoUpload(au);
					if(au.getIsSelected()){
						total++;		
					}
				}else{
					break;
				}
			}
		}
		return laudoTecnico;
	}



}

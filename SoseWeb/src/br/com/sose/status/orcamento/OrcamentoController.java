package br.com.sose.status.orcamento;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.orcamento.Orcamento;
import br.com.sose.entity.reparo.Reparo;
import br.com.sose.service.orcamento.OrcamentoService;
import br.com.sose.to.OrcRepGenericoTO;

@Service(value="orcamentoController")
@RemotingDestination(value="orcamentoController")
public class OrcamentoController {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private AguardandoLiberacao orcamentoAguardandoLiberacao;
	@Autowired
	private NaoIniciado orcamentoNaoIniciado;
	@Autowired
	private Iniciado orcamentoIniciado;	
	@Autowired
	private Finalizado orcamentoFinalizado;
	@Autowired
	private AguardandoLaudoTecnico orcamentoAguardandoLaudoTecnico;
	@Autowired
	private AguardandoAprovacaoLiderProposta orcamentoAguardandoAprovacaoLiderProposta;
	@Autowired
	private AguardandoAprovacaoLiderLaudo orcamentoAguardandoAprovacaoLiderLaudo;
	@Autowired
	private AguardandoAprovacaoLiderDevolucao orcamentoAguardandoAprovacaoLiderDevolucao;
	@Autowired
	private AguardandoProposta orcamentoAguardandoProposta;
	@Autowired
	private AguardandoReparoExterno orcamentoAguardandoReparoExterno;
	@Autowired
	private OrcamentoService orcamentoService;
	@Autowired
	private AguardandoConjuntoProposta orcamentoAguardandoConjuntoProposta;
	@Autowired
	private AguardandoConjuntoExpedicao orcamentoAguardandoConjuntoExpedicao;
	
	public Orcamento converterOrcRepGenericoToOrcamento(OrcRepGenericoTO orcRep){
		try{
			Orcamento orcamento = orcamentoService.buscarPorId(orcRep.getIdOrcRep());
			return orcamento;	
		}catch(Exception e){
			e.printStackTrace(); logger.error(e);
			return null;
			
		}
	}
	
	public Orcamento getStatus(Orcamento orcamento){
		if(orcamento.getStatusString() == null || orcamento.getStatusString().equals("") || orcamento.getStatusString().equals(orcamentoAguardandoLiberacao.getNome())){
			orcamentoAguardandoLiberacao.setOrcamento(orcamento);
			orcamento.setStatus(orcamentoAguardandoLiberacao);
		}else if(orcamento.getStatusString().equals(orcamentoNaoIniciado.getNome())){
			orcamentoNaoIniciado.setOrcamento(orcamento);
			orcamento.setStatus(orcamentoNaoIniciado);
		}else if(orcamento.getStatusString().equals(orcamentoIniciado.getNome())){
			orcamentoIniciado.setOrcamento(orcamento);
			orcamento.setStatus(orcamentoIniciado);
		}else if(orcamento.getStatusString().equals(orcamentoFinalizado.getNome())){
			orcamentoFinalizado.setOrcamento(orcamento);
			orcamento.setStatus(orcamentoFinalizado);
		}else if(orcamento.getStatusString().equals(orcamentoAguardandoLaudoTecnico.getNome())){
			orcamentoAguardandoLaudoTecnico.setOrcamento(orcamento);
			orcamento.setStatus(orcamentoAguardandoLaudoTecnico);
		}else if(orcamento.getStatusString().equals(orcamentoAguardandoAprovacaoLiderProposta.getNome())){
			orcamentoAguardandoAprovacaoLiderProposta.setOrcamento(orcamento);
			orcamento.setStatus(orcamentoAguardandoAprovacaoLiderProposta);
		}else if(orcamento.getStatusString().equals(orcamentoAguardandoAprovacaoLiderLaudo.getNome())){
			orcamentoAguardandoAprovacaoLiderLaudo.setOrcamento(orcamento);
			orcamento.setStatus(orcamentoAguardandoAprovacaoLiderLaudo);
		}else if(orcamento.getStatusString().equals(orcamentoAguardandoAprovacaoLiderDevolucao.getNome())){
			orcamentoAguardandoAprovacaoLiderDevolucao.setOrcamento(orcamento);
			orcamento.setStatus(orcamentoAguardandoAprovacaoLiderDevolucao);
		}else if(orcamento.getStatusString().equals(orcamentoAguardandoProposta.getNome())){
			orcamentoAguardandoProposta.setOrcamento(orcamento);
			orcamento.setStatus(orcamentoAguardandoProposta);
		}else if(orcamento.getStatusString().equals(orcamentoAguardandoReparoExterno.getNome())){
			orcamentoAguardandoReparoExterno.setOrcamento(orcamento);
			orcamento.setStatus(orcamentoAguardandoReparoExterno);
		}else if(orcamento.getStatusString().equals(AguardandoConjuntoProposta.nome)){
			orcamentoAguardandoConjuntoProposta.setOrcamento(orcamento);
			orcamento.setStatus(orcamentoAguardandoConjuntoProposta);
		}else if(orcamento.getStatusString().equals(AguardandoConjuntoExpedicao.nome)){
			orcamentoAguardandoConjuntoExpedicao.setOrcamento(orcamento);
			orcamento.setStatus(orcamentoAguardandoConjuntoExpedicao);
		}
		return orcamento;
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Orcamento salvarOrcamento(Orcamento orcamento) throws Exception{
		orcamento = getStatus(orcamento);
		return orcamento.getStatus().salvarOrcamento(orcamento);
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Orcamento iniciarOrcamento(Orcamento orcamento,Usuario realizadoPor) throws Exception{
		orcamento = getStatus(orcamento);
		return orcamento.getStatus().iniciarOrcamento(orcamento,realizadoPor);
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Orcamento editarOrcamento(Orcamento orcamento) throws Exception{
		orcamento = getStatus(orcamento);
		return orcamento.getStatus().editarOrcamento(orcamento);
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Orcamento encaminharAoLider(Orcamento orcamento,Usuario realizadoPor) throws Exception {
		orcamento = getStatus(orcamento);
		return orcamento.getStatus().encaminharAoLider(orcamento,realizadoPor);
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Orcamento devolverSemReparo(Orcamento orcamento,Usuario realizadoPor) throws Exception {
		orcamento = getStatus(orcamento);
		return orcamento.getStatus().devolverSemReparo(orcamento,realizadoPor);
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Orcamento finalizarOrcamento(Orcamento orcamento,Usuario realizadoPor) throws Exception{
		orcamento = getStatus(orcamento);
		return orcamento.getStatus().finalizarOrcamento(orcamento,realizadoPor);
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Orcamento solicitarLaudoTecnico(Orcamento orcamento,Usuario realizadoPor) throws Exception{
		orcamento = getStatus(orcamento);
		return orcamento.getStatus().solicitarLaudoTecnico(orcamento,realizadoPor);
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Orcamento solicitarReparoExterno(Orcamento orcamento,Usuario realizadoPor) throws Exception{
		orcamento = getStatus(orcamento);
		return orcamento.getStatus().solicitarReparoExterno(orcamento,realizadoPor);
	}
		
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Orcamento atribuirTecnico(Orcamento orcamento, Usuario usuario, Usuario atribuidoPor) throws Exception{
		orcamento = getStatus(orcamento);
		return orcamento.getStatus().atribuirTecnico(orcamento, usuario, atribuidoPor);
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Orcamento realocarTecnico(Orcamento orcamento, Usuario usuario, Usuario atribuidoPor) throws Exception{
		orcamento = getStatus(orcamento);
		return orcamento.getStatus().realocarTecnico(orcamento, usuario, atribuidoPor);
	}

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Orcamento visualizarOrcamento(Orcamento orcamento) throws Exception{
		orcamento = getStatus(orcamento);
		return orcamento.getStatus().visualizarOrcamento(orcamento);
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Orcamento aprovarOrcamento(Orcamento orcamento,Usuario realizadoPor) throws Exception{
		orcamento = getStatus(orcamento);
		return orcamento.getStatus().aprovarOrcamento(orcamento,realizadoPor);
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Orcamento rejeitarOrcamento(Orcamento orcamento,Usuario realizadoPor) throws Exception{
		orcamento = getStatus(orcamento);
		return orcamento.getStatus().rejeitarOrcamento(orcamento,realizadoPor);
	}

	public AguardandoLiberacao getOrcamentoAguardandoLiberacao() {
		return orcamentoAguardandoLiberacao;
	}

	public void setOrcamentoAguardandoLiberacao(
			AguardandoLiberacao orcamentoAguardandoLiberacao) {
		this.orcamentoAguardandoLiberacao = orcamentoAguardandoLiberacao;
	}

	public NaoIniciado getOrcamentoNaoIniciado() {
		return orcamentoNaoIniciado;
	}

	public void setOrcamentoNaoIniciado(NaoIniciado orcamentoNaoIniciado) {
		this.orcamentoNaoIniciado = orcamentoNaoIniciado;
	}

	public Iniciado getOrcamentoIniciado() {
		return orcamentoIniciado;
	}

	public void setOrcamentoIniciado(Iniciado orcamentoIniciado) {
		this.orcamentoIniciado = orcamentoIniciado;
	}

	public Finalizado getOrcamentoFinalizado() {
		return orcamentoFinalizado;
	}

	public void setOrcamentoFinalizado(Finalizado orcamentoFinalizado) {
		this.orcamentoFinalizado = orcamentoFinalizado;
	}

	public AguardandoLaudoTecnico getOrcamentoAguardandoLaudoTecnico() {
		return orcamentoAguardandoLaudoTecnico;
	}

	public void setOrcamentoAguardandoLaudoTecnico(
			AguardandoLaudoTecnico orcamentoAguardandoLaudoTecnico) {
		this.orcamentoAguardandoLaudoTecnico = orcamentoAguardandoLaudoTecnico;
	}

	public AguardandoProposta getOrcamentoAguardandoProposta() {
		return orcamentoAguardandoProposta;
	}

	public void setOrcamentoAguardandoProposta(
			AguardandoProposta orcamentoAguardandoProposta) {
		this.orcamentoAguardandoProposta = orcamentoAguardandoProposta;
	}

	public AguardandoReparoExterno getOrcamentoAguardandoReparoExterno() {
		return orcamentoAguardandoReparoExterno;
	}

	public void setOrcamentoAguardandoReparoExterno(
			AguardandoReparoExterno orcamentoAguardandoReparoExterno) {
		this.orcamentoAguardandoReparoExterno = orcamentoAguardandoReparoExterno;
	}

	public AguardandoConjuntoProposta getOrcamentoAguardandoConjuntoProposta() {
		return orcamentoAguardandoConjuntoProposta;
	}

	public void setOrcamentoAguardandoConjuntoProposta(
			AguardandoConjuntoProposta orcamentoAguardandoConjuntoProposta) {
		this.orcamentoAguardandoConjuntoProposta = orcamentoAguardandoConjuntoProposta;
	}

	public AguardandoConjuntoExpedicao getOrcamentoAguardandoConjuntoExpedicao() {
		return orcamentoAguardandoConjuntoExpedicao;
	}

	public void setOrcamentoAguardandoConjuntoExpedicao(
			AguardandoConjuntoExpedicao orcamentoAguardandoConjuntoExpedicao) {
		this.orcamentoAguardandoConjuntoExpedicao = orcamentoAguardandoConjuntoExpedicao;
	}

	public AguardandoAprovacaoLiderProposta getOrcamentoAguardandoAprovacaoLiderProposta() {
		return orcamentoAguardandoAprovacaoLiderProposta;
	}

	public void setOrcamentoAguardandoAprovacaoLiderProposta(
			AguardandoAprovacaoLiderProposta orcamentoAguardandoAprovacaoLiderProposta) {
		this.orcamentoAguardandoAprovacaoLiderProposta = orcamentoAguardandoAprovacaoLiderProposta;
	}

	public AguardandoAprovacaoLiderLaudo getOrcamentoAguardandoAprovacaoLiderLaudo() {
		return orcamentoAguardandoAprovacaoLiderLaudo;
	}

	public void setOrcamentoAguardandoAprovacaoLiderLaudo(
			AguardandoAprovacaoLiderLaudo orcamentoAguardandoAprovacaoLiderLaudo) {
		this.orcamentoAguardandoAprovacaoLiderLaudo = orcamentoAguardandoAprovacaoLiderLaudo;
	}

	public AguardandoAprovacaoLiderDevolucao getOrcamentoAguardandoAprovacaoLiderDevolucao() {
		return orcamentoAguardandoAprovacaoLiderDevolucao;
	}

	public void setOrcamentoAguardandoAprovacaoLiderDevolucao(
			AguardandoAprovacaoLiderDevolucao orcamentoAguardandoAprovacaoLiderDevolucao) {
		this.orcamentoAguardandoAprovacaoLiderDevolucao = orcamentoAguardandoAprovacaoLiderDevolucao;
	}

	
}

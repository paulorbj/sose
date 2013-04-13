package br.com.sose.entity.reparo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.Hibernate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.beans.factory.annotation.Configurable;

import br.com.sose.entity.orcrepGenerico.AtividadeOrcRep;
import br.com.sose.entity.orcrepGenerico.ComponenteOrcRep;
import br.com.sose.entity.orcrepGenerico.DefeitoOrcRep;
import br.com.sose.entity.orcrepGenerico.OrcRepGenerico;
import br.com.sose.entity.orcrepGenerico.RequisicaoComponente;
import br.com.sose.entity.recebimento.OrdemServico;
import br.com.sose.status.reparo.StatusReparo;
import edu.emory.mathcs.backport.java.util.Collections;

@Entity
@Configurable
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Reparo extends OrcRepGenerico implements Serializable {

	private static final long serialVersionUID = 1L;

	@OneToOne(mappedBy="reparo", cascade=CascadeType.PERSIST,fetch=FetchType.EAGER)
	private OrdemServico ordemServico;

	@OneToMany(mappedBy = "reparo", fetch=FetchType.LAZY)
	private Set<AtividadeOrcRep> listaAtividade;

	@OneToMany(mappedBy = "reparo", fetch=FetchType.LAZY)
	private Set<DefeitoOrcRep> listaDefeito;

	@OneToMany(mappedBy = "reparo", fetch=FetchType.LAZY)
	private Set<ComponenteOrcRep> listaComponente;

	@OneToMany(mappedBy = "reparo", fetch=FetchType.LAZY)
	private Set<RequisicaoComponente> listaRequisicao;

	@Transient
	private StatusReparo status;

	//Datas utilizadas para as msgs de notificacao
	private Date laudoTecnicoAprovado;

	private Date laudoTecnicoReprovado;

	private Date orcamentoDiferenciadoRejeitado;
	
	private Date dataOrcamentoDiferenciadoAprovado;
	
	private Date dataRequisicaoOrcamentoDiferenciado;

	private Date propostaAprovada;

	private Date propostaReprovada;
	
	private Date dataRejeitadoLaudoPeloLider;
	
	private Date dataAprovacaoLaudoPeloLider;
	
	private Date dataAprovacaoDevolucaoSemReparoPeloLider;
	
	private Date dataRejeitadoDevolucaoSemReparoPeloLider;
	
	private Boolean criadoFromOrcamento;

	@Transient
	private Integer color = 0xFFFFFF;


	public StatusReparo getStatus() {
		return status;
	}


	public void setStatus(StatusReparo status) {
		this.status = status;
	}


	public Set<AtividadeOrcRep> getListaAtividade() {
		if(!Hibernate.isInitialized(listaAtividade) || listaAtividade == null){
			listaAtividade = new HashSet<AtividadeOrcRep>();
		}
		return listaAtividade;
	}


	public void setListaAtividade(Set<AtividadeOrcRep> listaAtividade) {
		this.listaAtividade = listaAtividade;
	}


	public Set<DefeitoOrcRep> getListaDefeito() {
		if(!Hibernate.isInitialized(listaDefeito) || listaDefeito == null){
			listaDefeito = new HashSet<DefeitoOrcRep>();
		}
		return listaDefeito;
	}


	public void setListaDefeito(Set<DefeitoOrcRep> listaDefeito) {
		this.listaDefeito = listaDefeito;
	}

	public OrdemServico getOrdemServico() {
		if(!Hibernate.isInitialized(ordemServico)){
			ordemServico = null;	
		}
		return ordemServico;
	}

	public void setOrdemServico(OrdemServico ordemServico) {
		this.ordemServico = ordemServico;
	}


	public Set<ComponenteOrcRep> getListaComponente() {
		if(!Hibernate.isInitialized(listaComponente) || listaComponente == null){
			listaComponente = new HashSet<ComponenteOrcRep>();
		}
		return listaComponente;
	}


	public void setListaComponente(Set<ComponenteOrcRep> listaComponente) {
		this.listaComponente = listaComponente;
	}


	public Set<RequisicaoComponente> getListaRequisicao() {
		if(!Hibernate.isInitialized(listaRequisicao) || listaRequisicao == null){
			listaRequisicao = new HashSet<RequisicaoComponente>();
		}
		return listaRequisicao;
	}


	public void setListaRequisicao(Set<RequisicaoComponente> listaRequisicao) {
		this.listaRequisicao = listaRequisicao;
	}


	public Date getLaudoTecnicoAprovado() {
		return laudoTecnicoAprovado;
	}


	public void setLaudoTecnicoAprovado(Date laudoTecnicoAprovado) {
		this.laudoTecnicoAprovado = laudoTecnicoAprovado;
	}


	public Date getLaudoTecnicoReprovado() {
		return laudoTecnicoReprovado;
	}


	public void setLaudoTecnicoReprovado(Date laudoTecnicoReprovado) {
		this.laudoTecnicoReprovado = laudoTecnicoReprovado;
	}


	public Date getOrcamentoDiferenciadoRejeitado() {
		return orcamentoDiferenciadoRejeitado;
	}


	public void setOrcamentoDiferenciadoRejeitado(
			Date orcamentoDiferenciadoRejeitado) {
		this.orcamentoDiferenciadoRejeitado = orcamentoDiferenciadoRejeitado;
	}


	public Date getPropostaAprovada() {
		return propostaAprovada;
	}


	public void setPropostaAprovada(Date propostaAprovada) {
		this.propostaAprovada = propostaAprovada;
	}


	public Date getPropostaReprovada() {
		return propostaReprovada;
	}


	public void setPropostaReprovada(Date propostaReprovada) {
		this.propostaReprovada = propostaReprovada;
	}

	public Date getDataRejeitadoLaudoPeloLider() {
		return dataRejeitadoLaudoPeloLider;
	}


	public void setDataRejeitadoLaudoPeloLider(Date dataRejeitadoLaudoPeloLider) {
		this.dataRejeitadoLaudoPeloLider = dataRejeitadoLaudoPeloLider;
	}


	public Date getDataAprovacaoLaudoPeloLider() {
		return dataAprovacaoLaudoPeloLider;
	}


	public void setDataAprovacaoLaudoPeloLider(Date dataAprovacaoLaudoPeloLider) {
		this.dataAprovacaoLaudoPeloLider = dataAprovacaoLaudoPeloLider;
	}


	public Date getDataAprovacaoDevolucaoSemReparoPeloLider() {
		return dataAprovacaoDevolucaoSemReparoPeloLider;
	}


	public void setDataAprovacaoDevolucaoSemReparoPeloLider(
			Date dataAprovacaoDevolucaoSemReparoPeloLider) {
		this.dataAprovacaoDevolucaoSemReparoPeloLider = dataAprovacaoDevolucaoSemReparoPeloLider;
	}


	public Date getDataRejeitadoDevolucaoSemReparoPeloLider() {
		return dataRejeitadoDevolucaoSemReparoPeloLider;
	}


	public void setDataRejeitadoDevolucaoSemReparoPeloLider(
			Date dataRejeitadoDevolucaoSemReparoPeloLider) {
		this.dataRejeitadoDevolucaoSemReparoPeloLider = dataRejeitadoDevolucaoSemReparoPeloLider;
	}


	public Date getDataOrcamentoDiferenciadoAprovado() {
		return dataOrcamentoDiferenciadoAprovado;
	}


	public void setDataOrcamentoDiferenciadoAprovado(
			Date dataOrcamentoDiferenciadoAprovado) {
		this.dataOrcamentoDiferenciadoAprovado = dataOrcamentoDiferenciadoAprovado;
	}


	public Date getDataRequisicaoOrcamentoDiferenciado() {
		return dataRequisicaoOrcamentoDiferenciado;
	}


	public void setDataRequisicaoOrcamentoDiferenciado(
			Date dataRequisicaoOrcamentoDiferenciado) {
		this.dataRequisicaoOrcamentoDiferenciado = dataRequisicaoOrcamentoDiferenciado;
	}


	public Boolean getCriadoFromOrcamento() {
		return criadoFromOrcamento;
	}


	public void setCriadoFromOrcamento(Boolean criadoFromOrcamento) {
		this.criadoFromOrcamento = criadoFromOrcamento;
	}



}

package br.com.sose.entity.orcamento;

import java.io.Serializable;
import java.util.Date;
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
import br.com.sose.status.orcamento.StatusOrcamento;

@Entity
@Configurable
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Orcamento extends OrcRepGenerico implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@OneToOne(mappedBy="orcamento", cascade=CascadeType.PERSIST,fetch=FetchType.EAGER)
	protected OrdemServico ordemServico;
	
	@OneToMany(mappedBy = "orcamento",fetch=FetchType.LAZY)
	private Set<AtividadeOrcRep> listaAtividade;

	@OneToMany(mappedBy = "orcamento",fetch=FetchType.LAZY)
	private Set<DefeitoOrcRep> listaDefeito;
	
	@OneToMany(mappedBy = "orcamento",fetch=FetchType.LAZY)
	private Set<ComponenteOrcRep> listaComponente;
	
	@OneToMany(mappedBy = "orcamento",fetch=FetchType.LAZY)
	private Set<RequisicaoComponente> listaRequisicao;
	
	@Transient
	private StatusOrcamento status;
	
	private Date dataEncaminhadoAoLider;

	private Date dataAprovacao;
	
	//Datas utilizadas para as msgs de notificacao
	private Date laudoTecnicoAprovado;
	
	private Date laudoTecnicoReprovado;
	
	private Date propostaReprovada;
	
	private Date rejeitadoPeloLider;
	
	public Date getDataAprovacao() {
		return dataAprovacao;
	}


	public void setDataAprovacao(Date dataAprovacao) {
		this.dataAprovacao = dataAprovacao;
	}


	public StatusOrcamento getStatus() {
		return status;
	}


	public void setStatus(StatusOrcamento status) {
		this.status = status;
	}

	public Set<AtividadeOrcRep> getListaAtividade() {
		if(!Hibernate.isInitialized(listaAtividade)){
			listaAtividade = null;
		}
		return listaAtividade;
	}

	public void setListaAtividade(Set<AtividadeOrcRep> listaAtividade) {
		this.listaAtividade = listaAtividade;
	}

	public Set<DefeitoOrcRep> getListaDefeito() {
		if(!Hibernate.isInitialized(listaDefeito)){
			listaDefeito = null;
		}
		return listaDefeito;
	}

	public void setListaDefeito(Set<DefeitoOrcRep> listaDefeito) {
		this.listaDefeito = listaDefeito;
	}


	public Date getDataEncaminhadoAoLider() {
		return dataEncaminhadoAoLider;
	}


	public void setDataEncaminhadoAoLider(Date dataEncaminhadoAoLider) {
		this.dataEncaminhadoAoLider = dataEncaminhadoAoLider;
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
		if(!Hibernate.isInitialized(listaComponente)){
			listaComponente = null;
		}
		return listaComponente;
	}


	public void setListaComponente(Set<ComponenteOrcRep> listaComponente) {
		this.listaComponente = listaComponente;
	}


	public Set<RequisicaoComponente> getListaRequisicao() {
		if(!Hibernate.isInitialized(listaRequisicao)){
			listaRequisicao = null;
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


	public Date getPropostaReprovada() {
		return propostaReprovada;
	}


	public void setPropostaReprovada(Date propostaReprovada) {
		this.propostaReprovada = propostaReprovada;
	}


	public Date getRejeitadoPeloLider() {
		return rejeitadoPeloLider;
	}


	public void setRejeitadoPeloLider(Date rejeitadoPeloLider) {
		this.rejeitadoPeloLider = rejeitadoPeloLider;
	}


}

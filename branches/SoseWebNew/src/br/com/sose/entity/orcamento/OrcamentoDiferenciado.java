/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.sose.entity.orcamento;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.Hibernate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.beans.factory.annotation.Configurable;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.recebimento.OrdemServico;
import br.com.sose.entity.reparo.Reparo;
import br.com.sose.status.laudotecnico.Finalizado;
import br.com.sose.status.laudotecnico.Iniciado;
import br.com.sose.status.laudotecnico.NaoIniciado;
import br.com.sose.status.laudotecnico.StatusLaudoTecnico;
import br.com.sose.status.orcamentodiferenciado.StatusOrcamentoDiferenciado;

@Entity
@Configurable
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OrcamentoDiferenciado implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "reparo_id", referencedColumnName = "id")
	private Reparo reparo;

	@Column(name = "data_criacao")
	private Date dataCriacao;
	
	@Column(name = "data_inicio")
	private Date dataInicio;

	@Column(name = "data_fim")
	private Date dataFim;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "realizador_id", referencedColumnName = "id")
	private Usuario realizadoPor;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "criador_id", referencedColumnName = "id")
	private Usuario criadoPor;
	
	@Column(name = "informacao_tecnica", length=1000)
	private String informacaoTecnica;
	
	@Column(name = "status")
	private String statusString;
	
	@Column(name = "descricao")
	private String descricao;

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "ordem_servico_id", referencedColumnName = "id")
	private OrdemServico ordemServico;

	@Transient
	private StatusOrcamentoDiferenciado status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public Usuario getRealizadoPor() {
		if(!Hibernate.isInitialized(realizadoPor)){
			realizadoPor = null;	
		}
		return realizadoPor;
	}

	public void setRealizadoPor(Usuario realizadoPor) {
		this.realizadoPor = realizadoPor;
	}

	public Usuario getCriadoPor() {
		if(!Hibernate.isInitialized(criadoPor)){
			criadoPor = null;	
		}
		return criadoPor;
	}

	public void setCriadoPor(Usuario criadoPor) {
		this.criadoPor = criadoPor;
	}

	public String getInformacaoTecnica() {
		return informacaoTecnica;
	}

	public void setInformacaoTecnica(String informacaoTecnica) {
		this.informacaoTecnica = informacaoTecnica;
	}

	public String getStatusString() {
		return statusString;
	}

	public void setStatusString(String statusString) {
		this.statusString = statusString;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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

	public StatusOrcamentoDiferenciado getStatus() {
		return status;
	}

	public void setStatus(StatusOrcamentoDiferenciado status) {
		this.status = status;
	}

	public Reparo getReparo() {
		if(!Hibernate.isInitialized(reparo)){
			reparo = null;	
		}
		return reparo;
	}

	public void setReparo(Reparo reparo) {
		this.reparo = reparo;
	}

}

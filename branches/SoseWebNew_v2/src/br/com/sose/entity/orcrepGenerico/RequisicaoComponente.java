package br.com.sose.entity.orcrepGenerico;

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
import javax.persistence.Transient;

import org.hibernate.Hibernate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.beans.factory.annotation.Configurable;

import br.com.sose.entity.admistrativo.Componente;
import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.orcamento.Orcamento;
import br.com.sose.entity.reparo.Reparo;
import br.com.sose.status.estoque.StatusEstoque;

@Entity
@Configurable
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RequisicaoComponente implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(referencedColumnName="id",name="reparo_id")
	private Reparo reparo;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(referencedColumnName="id",name="orcamento_id")
	private Orcamento orcamento;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "componente_id", referencedColumnName = "id")
	private Componente componente;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "requisitante_id", referencedColumnName = "id")
	private Usuario requisitante;

	private Integer quantidade;

	@Column(name="status")
	private String statusString;
	
	@Transient
	private StatusEstoque status;

	private Date dataRequisicao;

	private Date dataCancelamento;

	private Date dataRetirada;
	
	private Date dataRecebimento;
	
	private Date dataEntrega;
	
	private Date dataAtendimento;
	
	private Date dataMaterialPronto;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "cancelado_por", referencedColumnName = "id")
	private Usuario canceladoPor;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "coletado_por", referencedColumnName = "id")
	private Usuario coletadoPor;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "atendido_por", referencedColumnName = "id")
	private Usuario atendidoPor;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "entregue_por", referencedColumnName = "id")
	private Usuario entreguePor;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "recebido_por", referencedColumnName = "id")
	private Usuario recebidoPor;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "retirado_por", referencedColumnName = "id")
	private Usuario retiradoPor;



	public RequisicaoComponente() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Componente getComponente() {
		if(!Hibernate.isInitialized(componente)){
			componente = null;	
		}
		return componente;
	}

	public void setComponente(Componente componente) {
		this.componente = componente;
	}

	public Usuario getRequisitante() {
		if(!Hibernate.isInitialized(requisitante)){
			requisitante = null;	
		}
		return requisitante;
	}

	public void setRequisitante(Usuario requisitante) {
		this.requisitante = requisitante;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Date getDataRequisicao() {
		return dataRequisicao;
	}

	public void setDataRequisicao(Date dataRequisicao) {
		this.dataRequisicao = dataRequisicao;
	}

	public Date getDataCancelamento() {
		return dataCancelamento;
	}

	public void setDataCancelamento(Date dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}

	public Date getDataRetirada() {
		return dataRetirada;
	}

	public void setDataRetirada(Date dataRetirada) {
		this.dataRetirada = dataRetirada;
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

	public Orcamento getOrcamento() {
		if(!Hibernate.isInitialized(orcamento)){
			orcamento = null;	
		}
		return orcamento;
	}

	public void setOrcamento(Orcamento orcamento) {
		this.orcamento = orcamento;
	}

	public Date getDataRecebimento() {
		return dataRecebimento;
	}

	public void setDataRecebimento(Date dataRecebimento) {
		this.dataRecebimento = dataRecebimento;
	}

	public Date getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(Date dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	public Date getDataAtendimento() {
		return dataAtendimento;
	}

	public void setDataAtendimento(Date dataAtendimento) {
		this.dataAtendimento = dataAtendimento;
	}

	public Date getDataMaterialPronto() {
		return dataMaterialPronto;
	}

	public void setDataMaterialPronto(Date dataMaterialPronto) {
		this.dataMaterialPronto = dataMaterialPronto;
	}

	public String getStatusString() {
		return statusString;
	}

	public void setStatusString(String statusString) {
		this.statusString = statusString;
	}

	public void setStatus(StatusEstoque status) {
		this.status = status;
	}

	public StatusEstoque getStatus() {
		return status;
	}

	public Usuario getCanceladoPor() {
		if(!Hibernate.isInitialized(canceladoPor)){
			canceladoPor = null;	
		}
		return canceladoPor;
	}

	public void setCanceladoPor(Usuario canceladoPor) {
		this.canceladoPor = canceladoPor;
	}

	public Usuario getColetadoPor() {
		if(!Hibernate.isInitialized(coletadoPor)){
			coletadoPor = null;	
		}
		return coletadoPor;
	}

	public void setColetadoPor(Usuario coletadoPor) {
		this.coletadoPor = coletadoPor;
	}

	public Usuario getAtendidoPor() {
		if(!Hibernate.isInitialized(atendidoPor)){
			atendidoPor = null;	
		}
		return atendidoPor;
	}

	public void setAtendidoPor(Usuario atendidoPor) {
		this.atendidoPor = atendidoPor;
	}

	public Usuario getEntreguePor() {
		if(!Hibernate.isInitialized(entreguePor)){
			entreguePor = null;	
		}
		return entreguePor;
	}

	public void setEntreguePor(Usuario entreguePor) {
		this.entreguePor = entreguePor;
	}

	public Usuario getRecebidoPor() {
		if(!Hibernate.isInitialized(recebidoPor)){
			recebidoPor = null;	
		}
		return recebidoPor;
	}

	public void setRecebidoPor(Usuario recebidoPor) {
		this.recebidoPor = recebidoPor;
	}

	public Usuario getRetiradoPor() {
		if(!Hibernate.isInitialized(retiradoPor)){
			retiradoPor = null;	
		}
		return retiradoPor;
	}

	public void setRetiradoPor(Usuario retiradoPor) {
		this.retiradoPor = retiradoPor;
	}


	
}

package br.com.sose.entity.faturamento;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import org.hibernate.Hibernate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.beans.factory.annotation.Configurable;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.admistrativo.parceiros.Pessoa;
import br.com.sose.entity.recebimento.OrdemServico;
import br.com.sose.status.faturamento.StatusFaturamento;

@Entity
@Configurable
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Faturamento implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 50, nullable = false)
	private String nome;
	
	private String numeroFatura;
	
	private Date dataEmissaoFatura;
	
	private Date dataVencimentoFatura;
	
	private Date dataPagamentoFatura;
	
	private Date dataCancelamento;
	
	private String statusString;
	
	@Transient
	private StatusFaturamento status;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "cliente_id", referencedColumnName = "id")
	private Pessoa cliente;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "realizador_id", referencedColumnName = "id")
	private Usuario realizadoPor;
	
	private Date dataCriacaoPreFatura;
	
	private Date dataCriacaoFatura;
	
	@OneToMany(mappedBy = "faturamento", fetch=FetchType.LAZY)
	@OrderBy(value="numeroOrdemServico")
	private Set<OrdemServico> listaOrdemServico;
	
	private BigDecimal totalFatura;
	
	private BigDecimal freteCobrado;
	
	private BigDecimal valorPago;
	
	private Boolean cobrarFrete;
	
	private String observacao;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataCriacaoPreFatura() {
		return dataCriacaoPreFatura;
	}

	public void setDataCriacaoPreFatura(Date dataCriacaoPreFatura) {
		this.dataCriacaoPreFatura = dataCriacaoPreFatura;
	}

	public Date getDataCriacaoFatura() {
		return dataCriacaoFatura;
	}

	public void setDataCriacaoFatura(Date dataCriacaoFatura) {
		this.dataCriacaoFatura = dataCriacaoFatura;
	}

	public Set<OrdemServico> getListaOrdemServico() {
		if(!Hibernate.isInitialized(listaOrdemServico) || listaOrdemServico == null){
			listaOrdemServico = new HashSet<OrdemServico>();
		}
		return listaOrdemServico;
	}

	public void setListaOrdemServico(Set<OrdemServico> listaOrdemServico) {
		this.listaOrdemServico = listaOrdemServico;
	}

	public String getNumeroFatura() {
		return numeroFatura;
	}

	public void setNumeroFatura(String numeroFatura) {
		this.numeroFatura = numeroFatura;
	}

	public Date getDataEmissaoFatura() {
		return dataEmissaoFatura;
	}

	public void setDataEmissaoFatura(Date dataEmissaoFatura) {
		this.dataEmissaoFatura = dataEmissaoFatura;
	}

	public Date getDataVencimentoFatura() {
		return dataVencimentoFatura;
	}

	public void setDataVencimentoFatura(Date dataVencimentoFatura) {
		this.dataVencimentoFatura = dataVencimentoFatura;
	}

	public Date getDataPagamentoFatura() {
		return dataPagamentoFatura;
	}

	public void setDataPagamentoFatura(Date dataPagamentoFatura) {
		this.dataPagamentoFatura = dataPagamentoFatura;
	}

	public String getStatusString() {
		return statusString;
	}

	public void setStatusString(String statusString) {
		this.statusString = statusString;
	}

	public StatusFaturamento getStatus() {
		return status;
	}

	public void setStatus(StatusFaturamento status) {
		this.status = status;
	}

	public Pessoa getCliente() {
		if(!Hibernate.isInitialized(cliente)){
			cliente = null;	
		}
		return cliente;
	}

	public void setCliente(Pessoa cliente) {
		this.cliente = cliente;
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

	public BigDecimal getTotalFatura() {
		return totalFatura;
	}

	public void setTotalFatura(BigDecimal totalFatura) {
		this.totalFatura = totalFatura;
	}

	public BigDecimal getFreteCobrado() {
		return freteCobrado;
	}

	public void setFreteCobrado(BigDecimal freteCobrado) {
		this.freteCobrado = freteCobrado;
	}

	public BigDecimal getValorPago() {
		return valorPago;
	}

	public void setValorPago(BigDecimal valorPago) {
		this.valorPago = valorPago;
	}

	public Boolean getCobrarFrete() {
		return cobrarFrete;
	}

	public void setCobrarFrete(Boolean cobrarFrete) {
		this.cobrarFrete = cobrarFrete;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Date getDataCancelamento() {
		return dataCancelamento;
	}

	public void setDataCancelamento(Date dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}


}

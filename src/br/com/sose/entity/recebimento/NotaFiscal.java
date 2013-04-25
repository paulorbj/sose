package br.com.sose.entity.recebimento;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.Hibernate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.beans.factory.annotation.Configurable;

import br.com.sose.entity.admistrativo.parceiros.Pessoa;
import br.com.sose.status.recebimento.StatusRecebimento;

@Entity
@Configurable
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class NotaFiscal implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String numero;
	
	private String pedidoDeCompra;

	private String caseAvaya;

	private String clienteAvaya;

	@Column(name="status")
	private String statusString;
	
	private Integer tipo;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="cliente_id", referencedColumnName="id")
	private Pessoa cliente;
	
	@Column(name = "data_nota_fiscal")
	@Temporal(TemporalType.TIMESTAMP )
	private Date dataNotaFiscal;

	@Column(name = "data_chegada")
	@Temporal(TemporalType.TIMESTAMP )
	private Date dataChegada;
	
	@Column(name = "data_criacao")
	private Date dataCriacao;

	@Column(name = "valor_nota_fiscal",precision=30,scale=4)
	private BigDecimal valorNotaFiscal;

	@Column(name = "observacao", length=1000)
	private String observacao;
	
	@Column(name = "observacao_anterior", length=1000)
	private String observacaoAnterior;

	@OneToMany(mappedBy = "notaFiscal", fetch=FetchType.LAZY)
	@OrderBy(value="id")
	private Set<OrdemServico> ordensServico;

	@OneToMany(mappedBy = "notaFiscal", fetch=FetchType.LAZY)
	@OrderBy(value="id")
	private Set<ItemNotaFiscal> itensDaNotaFiscal;
	
	@Transient
	private StatusRecebimento status;
	
	private String nomeCliente;

	public NotaFiscal() {

	}
	
	public NotaFiscal(Long id, String numero, String pedidoDeCompra,
			String caseAvaya, String clienteAvaya, String statusString,
			Integer tipo, String nomeCliente, Date dataNotaFiscal,
			Date dataChegada, Date dataCriacao, BigDecimal valorNotaFiscal,
			String observacao, String observacaoAnterior) {
		super();
		this.id = id;
		this.numero = numero;
		this.pedidoDeCompra = pedidoDeCompra;
		this.caseAvaya = caseAvaya;
		this.clienteAvaya = clienteAvaya;
		this.statusString = statusString;
		this.tipo = tipo;
		this.nomeCliente = nomeCliente;
		this.dataNotaFiscal = dataNotaFiscal;
		this.dataChegada = dataChegada;
		this.dataCriacao = dataCriacao;
		this.valorNotaFiscal = valorNotaFiscal;
		this.observacao = observacao;
		this.observacaoAnterior = observacaoAnterior;
	}



	public NotaFiscal(Long id, String numero, String caseAvaya,
			String clienteAvaya, String statusString, Date dataNotaFiscal,
			Date dataChegada, Date dataCriacao, String nomeCliente) {
		super();
		this.id = id;
		this.numero = numero;
		this.caseAvaya = caseAvaya;
		this.clienteAvaya = clienteAvaya;
		this.statusString = statusString;
		this.dataNotaFiscal = dataNotaFiscal;
		this.dataChegada = dataChegada;
		this.dataCriacao = dataCriacao;
		this.nomeCliente = nomeCliente;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getPedidoDeCompra() {
		return pedidoDeCompra;
	}

	public void setPedidoDeCompra(String pedidoDeCompra) {
		this.pedidoDeCompra = pedidoDeCompra;
	}

	public String getCaseAvaya() {
		return caseAvaya;
	}

	public void setCaseAvaya(String caseAvaya) {
		this.caseAvaya = caseAvaya;
	}

	public String getClienteAvaya() {
		return clienteAvaya;
	}

	public void setClienteAvaya(String clienteAvaya) {
		this.clienteAvaya = clienteAvaya;
	}

	public Pessoa getCliente() {
		if(!Hibernate.isInitialized(cliente)){
			cliente = null;	
		}
		return cliente;
	}

	public void setCliente(Pessoa cliente) {
		this.cliente = cliente;
		if(null != cliente){
			this.nomeCliente = cliente.getNomeSistema();
		}
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Set<OrdemServico> getOrdensServico() {
		if(!Hibernate.isInitialized(ordensServico) || ordensServico == null){
			ordensServico = new HashSet<OrdemServico>();
		}
		return ordensServico;
	}

	public void setOrdensServico(Set<OrdemServico> ordensServico) {
		this.ordensServico = ordensServico;
	}

	public Set<ItemNotaFiscal> getItensDaNotaFiscal() {
		if(!Hibernate.isInitialized(itensDaNotaFiscal) || itensDaNotaFiscal == null){
			itensDaNotaFiscal = new HashSet<ItemNotaFiscal>();
		}
		return itensDaNotaFiscal;
	}

	public void setItensDaNotaFiscal(Set<ItemNotaFiscal> itensDaNotaFiscal) {
		this.itensDaNotaFiscal = itensDaNotaFiscal;
	}

	public String getStatusString() {
		return statusString;
	}

	public void setStatusString(String statusString) {
		this.statusString = statusString;
	}

	public Date getDataNotaFiscal() {
		return dataNotaFiscal;
	}

	public void setDataNotaFiscal(Date dataNotaFiscal) {
		this.dataNotaFiscal = dataNotaFiscal;
	}

	public Date getDataChegada() {
		return dataChegada;
	}

	public void setDataChegada(Date dataChegada) {
		this.dataChegada = dataChegada;
	}

	public BigDecimal getValorNotaFiscal() {
		return valorNotaFiscal;
	}

	public void setValorNotaFiscal(BigDecimal valorNotaFiscal) {
		this.valorNotaFiscal = valorNotaFiscal;
	}

	public StatusRecebimento getStatus() {
		return status;
	}

	public void setStatus(StatusRecebimento status) {
		this.status = status;
		this.statusString = this.status.getNome();
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public String getObservacaoAnterior() {
		return observacaoAnterior;
	}

	public void setObservacaoAnterior(String observacaoAnterior) {
		this.observacaoAnterior = observacaoAnterior;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

}

package br.com.sose.entity.proposta;

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
import javax.persistence.Transient;

import org.hibernate.Hibernate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.beans.factory.annotation.Configurable;

import br.com.sose.entity.admistrativo.parceiros.Contato;
import br.com.sose.entity.admistrativo.parceiros.Pessoa;
import br.com.sose.entity.orcamento.OrcamentoDiferenciado;
import br.com.sose.status.proposta.StatusProposta;

@Entity
@Configurable
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Proposta implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="cliente_id", referencedColumnName="id")
	private Pessoa cliente;

	private String numero;

	private String tipo;

	@Column(name="status")
	private String statusString;

	private Date dataFinalizacao;

	private Date dataCriacao;

	private Date dataInicio;

	private Date dataEnvioCliente;

	@OneToMany(mappedBy="proposta",fetch=FetchType.LAZY)
	private Set<ItemProposta> itensProposta;

	private BigDecimal valorFrete;

	private BigDecimal valorDesconto;

	private Float porcentagemDesconto;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="contato_id", referencedColumnName="id")
	private Contato contato;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="orcamento_diferenciado_id", referencedColumnName="id")
	private OrcamentoDiferenciado orcamentoDiferenciado;

	@Transient
	private StatusProposta status;

	@Column(name = "observacao", length=3000)
	private String observacao;

	private String nomeCliente;
	
	@Transient
	private String nNFs;

	public Proposta() {
		// TODO Auto-generated constructor stub
	}


	public Proposta(Long id, String numero, String tipo, String statusString,
			Date dataCriacao,String nomeCliente) {
		super();
		this.id = id;
		this.numero = numero;
		this.tipo = tipo;
		this.statusString = statusString;
		this.dataCriacao = dataCriacao;
		this.nomeCliente = nomeCliente;
	}
	
	public Proposta(Long id, String numero, String tipo, String statusString,
			Date dataCriacao,String nomeCliente, String nNFs) {
		super();
		this.id = id;
		this.numero = numero;
		this.tipo = tipo;
		this.statusString = statusString;
		this.dataCriacao = dataCriacao;
		this.nomeCliente = nomeCliente;
		this.nNFs = nNFs;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getStatusString() {
		return statusString;
	}

	public void setStatusString(String statusString) {
		this.statusString = statusString;
	}

	public StatusProposta getStatus() {
		return status;
	}

	public void setStatus(StatusProposta status) {
		this.status = status;
	}

	public Set<ItemProposta> getItensProposta() {
		if(!Hibernate.isInitialized(itensProposta) || itensProposta == null){
			itensProposta = new HashSet<ItemProposta>();
		}

		return itensProposta;
	}

	public void setItensProposta(Set<ItemProposta> itensProposta) {
		this.itensProposta = itensProposta;
	}

	public Date getDataFinalizacao() {
		return dataFinalizacao;
	}

	public void setDataFinalizacao(Date dataFinalizacao) {
		this.dataFinalizacao = dataFinalizacao;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataEnvioCliente() {
		return dataEnvioCliente;
	}

	public void setDataEnvioCliente(Date dataEnvioCliente) {
		this.dataEnvioCliente = dataEnvioCliente;
	}

	public Pessoa getCliente() {
		if(!Hibernate.isInitialized(cliente)){
			cliente = null;	
		}
		return cliente;
	}

	public void setCliente(Pessoa cliente) {
		this.cliente = cliente;
		if(cliente != null){
			this.nomeCliente = cliente.getNomeSistema();
		}
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public BigDecimal getValorFrete() {
		return valorFrete;
	}

	public void setValorFrete(BigDecimal valorFrete) {
		this.valorFrete = valorFrete;
	}

	public BigDecimal getValorDesconto() {
		return valorDesconto;
	}

	public void setValorDesconto(BigDecimal valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	public Float getPorcentagemDesconto() {
		return porcentagemDesconto;
	}

	public void setPorcentagemDesconto(Float porcentagemDesconto) {
		this.porcentagemDesconto = porcentagemDesconto;
	}

	public Contato getContato() {
		if(!Hibernate.isInitialized(contato)){
			contato = null;	
		}
		return contato;
	}

	public void setContato(Contato contato) {
		this.contato = contato;
	}

	public OrcamentoDiferenciado getOrcamentoDiferenciado() {
		if(!Hibernate.isInitialized(orcamentoDiferenciado)){
			orcamentoDiferenciado = null;	
		}
		return orcamentoDiferenciado;
	}

	public void setOrcamentoDiferenciado(OrcamentoDiferenciado orcamentoDiferenciado) {
		this.orcamentoDiferenciado = orcamentoDiferenciado;
	}

	public String getObservacao() {
		if(!Hibernate.isInitialized(observacao)){
			observacao = null;	
		}
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}


	public String getNomeCliente() {
		return nomeCliente;
	}


	public void setNomeCliente(String nomeCliente) {
		if(nomeCliente != null)
			this.nomeCliente = nomeCliente;
	}


	public String getnNFs() {
		return nNFs;
	}


	public void setnNFs(String nNFs) {
		this.nNFs = nNFs;
	}



}

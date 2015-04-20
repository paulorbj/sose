package br.com.sose.entity.expedicao;

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

import br.com.sose.entity.admistrativo.parceiros.Endereco;
import br.com.sose.entity.admistrativo.parceiros.Pessoa;
import br.com.sose.entity.recebimento.OrdemServico;
import br.com.sose.status.expedicao.StatusExpedicao;

@Entity
@Configurable
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class NotaFiscalRemessa implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;
	
	private Date dataCriacaoPreExpedicao;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "cliente_id", referencedColumnName = "id")
	private Pessoa cliente;

	private String numero;

	@Column(name="status")
	private String statusString;

	private Date dtSaida;
	
	private Date dtCriacao;
	
	private Date dtIniciacao;
	
	private Date dtEmissao;
	
	private Date dtFinalizacao;
	
	@OneToMany(mappedBy = "notaFiscalSaida", fetch=FetchType.LAZY)
	@OrderBy(value="numeroOrdemServico")
	private Set<OrdemServico> ordensServico;

	@OneToMany(mappedBy = "notaFiscalSaida", fetch=FetchType.LAZY)
	@OrderBy(value="id")
	private Set<Volume> volumes;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "transportador_id", referencedColumnName = "id")
	private Pessoa transportador;

	private String placaVeiculo;

	private String nomeMotorista;
	
	private String numeroDocumento;
	
	private String numeroConhecimento;
	
	private String recebidoPor;
	
	private Date dataRecebimentoMaterial;

	private BigDecimal valorCorreio;

	private BigDecimal valorFrete;

	private Date saidaRegistradaEm;
	
	private Date solicitacaoRegistradaEm;
	
	private String tipo;
	
	@Column(name = "observacao", length=1000)
	private String observacao;
	
	@Column(name = "observacao_anterior", length=1000)
	private String observacaoAnterior;	
	
	private String codFrete;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "endereco_entrega_id", referencedColumnName = "id")
	private Endereco enderecoEntrega;
	
	@Transient
	private StatusExpedicao status;
	
	private Long idCliente;
	
	private Long idTransportador;
	
	private Long idEnderecoEntrega;
	
	private String nomeCliente;
	
	public NotaFiscalRemessa() {
	
	}
	
	
	
	public NotaFiscalRemessa(Long id, String numero, String statusString,
			Date dtSaida, Date dtCriacao, String nomeCliente) {
		super();
		this.id = id;
		this.numero = numero;
		this.statusString = statusString;
		this.dtSaida = dtSaida;
		this.dtCriacao = dtCriacao;
		this.nomeCliente = nomeCliente;
	}



	public NotaFiscalRemessa(Long id, String nome,
			Date dataCriacaoPreExpedicao, String numero,
			String statusString, Date dtSaida, Date dtCriacao,
			Date dtIniciacao, Date dtEmissao, Date dtFinalizacao,
			String placaVeiculo, String nomeMotorista,
			String numeroDocumento, String numeroConhecimento,
			String recebidoPor, Date dataRecebimentoMaterial,
			BigDecimal valorCorreio, BigDecimal valorFrete,
			Date saidaRegistradaEm, Date solicitacaoRegistradaEm, String tipo,
			String observacao, String observacaoAnterior, String codFrete,Long idCliente,
			Long idTransportador, Long idEnderecoEntrega) {
		super();
		this.id = id;
		this.nome = nome;
		this.dataCriacaoPreExpedicao = dataCriacaoPreExpedicao;
		this.numero = numero;
		this.statusString = statusString;
		this.dtSaida = dtSaida;
		this.dtCriacao = dtCriacao;
		this.dtIniciacao = dtIniciacao;
		this.dtEmissao = dtEmissao;
		this.dtFinalizacao = dtFinalizacao;
		this.placaVeiculo = placaVeiculo;
		this.nomeMotorista = nomeMotorista;
		this.numeroDocumento = numeroDocumento;
		this.numeroConhecimento = numeroConhecimento;
		this.recebidoPor = recebidoPor;
		this.dataRecebimentoMaterial = dataRecebimentoMaterial;
		this.valorCorreio = valorCorreio;
		this.valorFrete = valorFrete;
		this.saidaRegistradaEm = saidaRegistradaEm;
		this.solicitacaoRegistradaEm = solicitacaoRegistradaEm;
		this.tipo = tipo;
		this.observacao = observacao;
		this.observacaoAnterior = observacaoAnterior;
		this.codFrete = codFrete;
		this.idCliente = idCliente;
		this.idTransportador = idTransportador;
		this.idEnderecoEntrega = idEnderecoEntrega;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getStatusString() {
		return statusString;
	}

	public void setStatusString(String statusString) {
		this.statusString = statusString;
	}

	public Date getDtSaida() {
		return dtSaida;
	}

	public void setDtSaida(Date dtSaida) {
		this.dtSaida = dtSaida;
	}

	public Date getDtCriacao() {
		return dtCriacao;
	}

	public void setDtCriacao(Date dtCriacao) {
		this.dtCriacao = dtCriacao;
	}

	public Set<OrdemServico> getOrdensServico() {
		if(!Hibernate.isInitialized(ordensServico)){
			ordensServico = new HashSet<OrdemServico>();
		}
		return ordensServico;
	}

	public void setOrdensServico(Set<OrdemServico> ordensServico) {
		this.ordensServico = ordensServico;
	}

	public Set<Volume> getVolumes() {
		if(!Hibernate.isInitialized(volumes)){
			volumes = new HashSet<Volume>();
		}
		return volumes;
	}

	public void setVolumes(Set<Volume> volumes) {
		this.volumes = volumes;
	}

	public Pessoa getTransportador() {
		if(!Hibernate.isInitialized(transportador)){
			transportador = null;	
		}
		return transportador;
	}

	public void setTransportador(Pessoa transportador) {
		this.transportador = transportador;
	}

	public String getPlacaVeiculo() {
		return placaVeiculo;
	}

	public void setPlacaVeiculo(String placaVeiculo) {
		this.placaVeiculo = placaVeiculo;
	}

	public String getNomeMotorista() {
		return nomeMotorista;
	}

	public void setNomeMotorista(String nomeMotorista) {
		this.nomeMotorista = nomeMotorista;
	}

	public BigDecimal getValorCorreio() {
		return valorCorreio;
	}

	public void setValorCorreio(BigDecimal valorCorreio) {
		this.valorCorreio = valorCorreio;
	}

	public BigDecimal getValorFrete() {
		return valorFrete;
	}

	public void setValorFrete(BigDecimal valorFrete) {
		this.valorFrete = valorFrete;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Endereco getEnderecoEntrega() {
		if(!Hibernate.isInitialized(enderecoEntrega)){
			enderecoEntrega = null;	
		}
		return enderecoEntrega;
	}

	public void setEnderecoEntrega(Endereco enderecoEntrega) {
		this.enderecoEntrega = enderecoEntrega;
	}

	public StatusExpedicao getStatus() {
		return status;
	}

	public void setStatus(StatusExpedicao status) {
		this.status = status;
	}

	public Date getDtIniciacao() {
		return dtIniciacao;
	}

	public void setDtIniciacao(Date dtIniciacao) {
		this.dtIniciacao = dtIniciacao;
	}

	public Date getDtEmissao() {
		return dtEmissao;
	}

	public void setDtEmissao(Date dtEmissao) {
		this.dtEmissao = dtEmissao;
	}

	public Date getDtFinalizacao() {
		return dtFinalizacao;
	}

	public void setDtFinalizacao(Date dtFinalizacao) {
		this.dtFinalizacao = dtFinalizacao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataCriacaoPreExpedicao() {
		return dataCriacaoPreExpedicao;
	}

	public void setDataCriacaoPreExpedicao(Date dataCriacaoPreExpedicao) {
		this.dataCriacaoPreExpedicao = dataCriacaoPreExpedicao;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getNumeroConhecimento() {
		return numeroConhecimento;
	}

	public void setNumeroConhecimento(String numeroConhecimento) {
		this.numeroConhecimento = numeroConhecimento;
	}

	public String getRecebidoPor() {
		return recebidoPor;
	}

	public void setRecebidoPor(String recebidoPor) {
		this.recebidoPor = recebidoPor;
	}

	public Date getDataRecebimentoMaterial() {
		return dataRecebimentoMaterial;
	}

	public void setDataRecebimentoMaterial(Date dataRecebimentoMaterial) {
		this.dataRecebimentoMaterial = dataRecebimentoMaterial;
	}

	public Date getSaidaRegistradaEm() {
		return saidaRegistradaEm;
	}

	public void setSaidaRegistradaEm(Date saidaRegistradaEm) {
		this.saidaRegistradaEm = saidaRegistradaEm;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getCodFrete() {
		return codFrete;
	}

	public void setCodFrete(String codFrete) {
		this.codFrete = codFrete;
	}

	public Date getSolicitacaoRegistradaEm() {
		return solicitacaoRegistradaEm;
	}

	public void setSolicitacaoRegistradaEm(Date solicitacaoRegistradaEm) {
		this.solicitacaoRegistradaEm = solicitacaoRegistradaEm;
	}

	public String getObservacaoAnterior() {
		return observacaoAnterior;
	}

	public void setObservacaoAnterior(String observacaoAnterior) {
		this.observacaoAnterior = observacaoAnterior;
	}



	public Long getIdCliente() {
		return idCliente;
	}



	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}



	public Long getIdTransportador() {
		return idTransportador;
	}



	public void setIdTransportador(Long idTransportador) {
		this.idTransportador = idTransportador;
	}



	public Long getIdEnderecoEntrega() {
		return idEnderecoEntrega;
	}



	public void setIdEnderecoEntrega(Long idEnderecoEntrega) {
		this.idEnderecoEntrega = idEnderecoEntrega;
	}



	public String getNomeCliente() {
		return nomeCliente;
	}



	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}




}

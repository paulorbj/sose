package br.com.sose.entity.compra;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.Hibernate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.beans.factory.annotation.Configurable;

import br.com.sose.entity.admistrativo.Componente;
import br.com.sose.entity.admistrativo.parceiros.Pessoa;

@Entity
@Configurable
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ItemCompra implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "compra_id", referencedColumnName = "id")
	private Compra compra;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "componente_id", referencedColumnName = "id")
	private Componente componente;
	
	@OneToMany(mappedBy = "itemCompra", fetch=FetchType.LAZY)
	@OrderBy(value="id")
	private Set<PedidoCompra> listaPedidoCompra;
	
	private Integer qtdTotalRequisitada;
	
	private Integer qtdEsperada;
	
	private Integer qtdComprada;
	
	private Integer qtdPedido;
	
	private String tecnicos;
	
	private String nAmostra;
	
	private Boolean possuiAmostra;
	
	private String nNotaFiscal;
	
	private BigDecimal valorUnitario;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "fornecedor_id", referencedColumnName = "id")
	private Pessoa fornecedor;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "componente_equivalente_id", referencedColumnName = "id")
	private Componente componenteEquivalente;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "componente_notificacao_id", referencedColumnName = "id")
	private Componente componenteNotificacao;
	
	private Date dataEntrada;
	
	private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Compra getCompra() {
		return compra;
	}

	public void setCompra(Compra compra) {
		this.compra = compra;
	}

	public Set<PedidoCompra> getListaPedidoCompra() {
		if(!Hibernate.isInitialized(listaPedidoCompra) || listaPedidoCompra == null){
			listaPedidoCompra = new HashSet<PedidoCompra>();
		}
		return listaPedidoCompra;
	}

	public void setListaPedidoCompra(Set<PedidoCompra> listaPedidoCompra) {
		this.listaPedidoCompra = listaPedidoCompra;
	}

	public Componente getComponente() {
		return componente;
	}

	public void setComponente(Componente componente) {
		this.componente = componente;
	}

	public Integer getQtdTotalRequisitada() {
		return qtdTotalRequisitada;
	}

	public void setQtdTotalRequisitada(Integer qtdTotalRequisitada) {
		this.qtdTotalRequisitada = qtdTotalRequisitada;
	}

	public Integer getQtdEsperada() {
		return qtdEsperada;
	}

	public void setQtdEsperada(Integer qtdEsperada) {
		this.qtdEsperada = qtdEsperada;
	}

	public Integer getQtdComprada() {
		return qtdComprada;
	}

	public void setQtdComprada(Integer qtdComprada) {
		this.qtdComprada = qtdComprada;
	}

	public String getnNotaFiscal() {
		return nNotaFiscal;
	}

	public void setnNotaFiscal(String nNotaFiscal) {
		this.nNotaFiscal = nNotaFiscal;
	}

	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public Pessoa getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Pessoa fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Componente getComponenteEquivalente() {
		return componenteEquivalente;
	}

	public void setComponenteEquivalente(Componente componenteEquivalente) {
		this.componenteEquivalente = componenteEquivalente;
	}

	public Date getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(Date dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public Integer getQtdPedido() {
		return qtdPedido;
	}

	public void setQtdPedido(Integer qtdPedido) {
		this.qtdPedido = qtdPedido;
	}

	public String getTecnicos() {
		return tecnicos;
	}

	public void setTecnicos(String tecnicos) {
		this.tecnicos = tecnicos;
	}

	public String getnAmostra() {
		return nAmostra;
	}

	public void setnAmostra(String nAmostra) {
		this.nAmostra = nAmostra;
	}

	public Boolean getPossuiAmostra() {
		return possuiAmostra;
	}

	public void setPossuiAmostra(Boolean possuiAmostra) {
		this.possuiAmostra = possuiAmostra;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Componente getComponenteNotificacao() {
		return componenteNotificacao;
	}

	public void setComponenteNotificacao(Componente componenteNotificacao) {
		this.componenteNotificacao = componenteNotificacao;
	}
	
	
}

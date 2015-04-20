package br.com.sose.entity.compra;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Cacheable;
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
import br.com.sose.entity.orcrepGenerico.RequisicaoComponente;
import br.com.sose.entity.recebimento.OrdemServico;
import br.com.sose.status.compra.StatusCompra;

@Entity
@Configurable
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PedidoCompra implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "ordem_servico_id", referencedColumnName = "id")
	private OrdemServico ordemServico;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "tecnico_id", referencedColumnName = "id")
	private Usuario tecnico;
	
	private Integer quantidade;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "requisicao_id", referencedColumnName = "id")
	private RequisicaoComponente requisicao;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "componente_id", referencedColumnName = "id")
	private Componente componente;
	
	private Boolean possuiAmostra;
	
	private Date dataCriacao;
	
	private Date dataFinalizacao;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "realizador_id", referencedColumnName = "id")
	private Usuario realizadoPor;
	
	private String statusString;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "item_compra_id", referencedColumnName = "id")
	private ItemCompra itemCompra;

	@Transient
	private StatusCompra status;
	
	private String origemPedido;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Usuario getTecnico() {
		if(!Hibernate.isInitialized(tecnico)){
			tecnico = null;	
		}
		return tecnico;
	}

	public void setTecnico(Usuario tecnico) {
		this.tecnico = tecnico;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public RequisicaoComponente getRequisicao() {
		if(!Hibernate.isInitialized(requisicao)){
			requisicao = null;	
		}
		return requisicao;
	}

	public void setRequisicao(RequisicaoComponente requisicao) {
		this.requisicao = requisicao;
	}

	public Boolean getPossuiAmostra() {
		return possuiAmostra;
	}

	public void setPossuiAmostra(Boolean possuiAmostra) {
		this.possuiAmostra = possuiAmostra;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public String getStatusString() {
		return statusString;
	}

	public void setStatusString(String statusString) {
		this.statusString = statusString;
	}

	public StatusCompra getStatus() {
		return status;
	}

	public void setStatus(StatusCompra status) {
		this.status = status;
	}

	public Date getDataFinalizacao() {
		return dataFinalizacao;
	}

	public void setDataFinalizacao(Date dataFinalizacao) {
		this.dataFinalizacao = dataFinalizacao;
	}

	public Usuario getRealizadoPor() {
		return realizadoPor;
	}

	public void setRealizadoPor(Usuario realizadoPor) {
		this.realizadoPor = realizadoPor;
	}

	public ItemCompra getItemCompra() {
		return itemCompra;
	}

	public void setItemCompra(ItemCompra itemCompra) {
		this.itemCompra = itemCompra;
	}

	public Componente getComponente() {
		return componente;
	}

	public void setComponente(Componente componente) {
		this.componente = componente;
	}

	public String getOrigemPedido() {
		return origemPedido;
	}

	public void setOrigemPedido(String origemPedido) {
		this.origemPedido = origemPedido;
	}

	
}

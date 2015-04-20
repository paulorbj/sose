package br.com.sose.entity.compra;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.beans.factory.annotation.Configurable;

import br.com.sose.entity.admistrativo.Componente;
import br.com.sose.entity.admistrativo.Usuario;

@Entity
@Configurable
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Cotacao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "componente_id", referencedColumnName = "id")
	private Componente componente;
	
	private String fornecedor;
	
	private Date dataCotacao;
	
	private BigDecimal valor;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "cotado_por", referencedColumnName = "id")
	private Usuario cotadoPor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Componente getComponente() {
		return componente;
	}

	public void setComponente(Componente componente) {
		this.componente = componente;
	}

	public String getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(String fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Date getDataCotacao() {
		return dataCotacao;
	}

	public void setDataCotacao(Date dataCotacao) {
		this.dataCotacao = dataCotacao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Usuario getCotadoPor() {
		return cotadoPor;
	}

	public void setCotadoPor(Usuario cotadoPor) {
		this.cotadoPor = cotadoPor;
	}

}

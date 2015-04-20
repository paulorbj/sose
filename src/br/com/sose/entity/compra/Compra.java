package br.com.sose.entity.compra;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import org.hibernate.Hibernate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.beans.factory.annotation.Configurable;

import br.com.sose.status.compra.StatusCompra;

@Entity
@Configurable
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Compra implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	private Date dataCriacaoCompra;
	
	@OneToMany(mappedBy = "compra", fetch=FetchType.LAZY)
	@OrderBy(value="id")
	private Set<ItemCompra> listaItemCompra;
	
	private String statusString;

	@Transient
	private StatusCompra status;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataCriacaoCompra() {
		return dataCriacaoCompra;
	}

	public void setDataCriacaoCompra(Date dataCriacaoCompra) {
		this.dataCriacaoCompra = dataCriacaoCompra;
	}

	public Set<ItemCompra> getListaItemCompra() {
		if(!Hibernate.isInitialized(listaItemCompra) || listaItemCompra == null){
			listaItemCompra = new HashSet<ItemCompra>();
		}
		return listaItemCompra;
	}

	public void setListaItemCompra(Set<ItemCompra> listaItemCompra) {
		this.listaItemCompra = listaItemCompra;
	}
}

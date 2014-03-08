package br.com.sose.entity.lpu;

import java.io.Serializable;
import java.math.BigDecimal;
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

import org.hibernate.Hibernate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.beans.factory.annotation.Configurable;

import br.com.sose.entity.admistrativo.Unidade;

@Entity
@Configurable
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UnidadeItemLpu implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="lpu_id", referencedColumnName="id")
	private Lpu lpu;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="unidade_id", referencedColumnName="id")
	private Unidade unidadeServilogi;
	
	private BigDecimal valorReparo;
	
	private BigDecimal valorMinimo;
	
	private BigDecimal valorMaximo;
	
	private BigDecimal valorTeste;
	
	@OneToMany(mappedBy = "unidadeItemLpu", fetch=FetchType.LAZY)
	private Set<ItemLpu> listaItemLpu;
	
	public Unidade getUnidadeServilogi() {
		return unidadeServilogi;
	}

	public void setUnidadeServilogi(Unidade unidadeServilogi) {
		this.unidadeServilogi = unidadeServilogi;
	}
	
	public Set<ItemLpu> getListaItemLpu() {
		if(!Hibernate.isInitialized(listaItemLpu) || listaItemLpu == null){
			listaItemLpu = new HashSet<ItemLpu>();
		}
		return listaItemLpu;
	}

	public void setListaItemLpu(Set<ItemLpu> listaItemLpu) {
		this.listaItemLpu = listaItemLpu;
	}

	public Lpu getLpu() {
		return lpu;
	}

	public void setLpu(Lpu lpu) {
		this.lpu = lpu;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getValorReparo() {
		return valorReparo;
	}

	public void setValorReparo(BigDecimal valorReparo) {
		this.valorReparo = valorReparo;
	}

	public BigDecimal getValorMinimo() {
		return valorMinimo;
	}

	public void setValorMinimo(BigDecimal valorMinimo) {
		this.valorMinimo = valorMinimo;
	}

	public BigDecimal getValorMaximo() {
		return valorMaximo;
	}

	public void setValorMaximo(BigDecimal valorMaximo) {
		this.valorMaximo = valorMaximo;
	}

	public BigDecimal getValorTeste() {
		return valorTeste;
	}

	public void setValorTeste(BigDecimal valorTeste) {
		this.valorTeste = valorTeste;
	}
	
	
}

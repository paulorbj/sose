package br.com.sose.entity.admistrativo;

import java.io.Serializable;
import java.math.BigDecimal;

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

@Entity
@Configurable
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ItemLpu implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="lpu_id", referencedColumnName="id")
	private Lpu lpu;
	
	private String unidade;
	private String equipamento;
	private String fabricante;
	private String codigo1;
	private String codigo2;
	private BigDecimal valor;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="unidade_id", referencedColumnName="id")
	private Unidade unidadeServilogi;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Lpu getLpu() {
		return lpu;
	}

	public void setLpu(Lpu lpu) {
		this.lpu = lpu;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public String getEquipamento() {
		return equipamento;
	}

	public void setEquipamento(String equipamento) {
		this.equipamento = equipamento;
	}

	public String getFabricante() {
		return fabricante;
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

	public String getCodigo1() {
		return codigo1;
	}

	public void setCodigo1(String codigo1) {
		this.codigo1 = codigo1;
	}

	public String getCodigo2() {
		return codigo2;
	}

	public void setCodigo2(String codigo2) {
		this.codigo2 = codigo2;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Unidade getUnidadeServilogi() {
		return unidadeServilogi;
	}

	public void setUnidadeServilogi(Unidade unidadeServilogi) {
		this.unidadeServilogi = unidadeServilogi;
	}
}

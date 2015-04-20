package br.com.sose.entity.lpu;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.Hibernate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.beans.factory.annotation.Configurable;

import br.com.sose.entity.admistrativo.Equipamento;
import br.com.sose.entity.admistrativo.Fabricante;
import br.com.sose.entity.admistrativo.parceiros.Pessoa;

@Entity
@Configurable
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Lpu implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="cliente_id", referencedColumnName="id")
	private Pessoa cliente;
	
	@Column(length = 200, nullable = false)
	private String unidade;

	@Column(length = 1000)
	private String descricao;

	private String codigo1;

	private String codigo2;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="fabricante_id", referencedColumnName="id")
	private Fabricante fabricante;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="equipamento_id", referencedColumnName="id")
	private Equipamento equipamento;

	private String moeda;

	private BigDecimal valorReparo;

	@Column(name = "cadastro_ativo")
	private Boolean cadastroSistemaAtivo;
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Boolean getCadastroSistemaAtivo() {
		return cadastroSistemaAtivo;
	}

	public void setCadastroSistemaAtivo(Boolean cadastroSistemaAtivo) {
		this.cadastroSistemaAtivo = cadastroSistemaAtivo;
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

	public String getUnidade() {
		if(!Hibernate.isInitialized(unidade)){
			unidade = null;	
		}
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
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

	public Fabricante getFabricante() {
		if(!Hibernate.isInitialized(fabricante)){
			fabricante = null;	
		}
		return fabricante;
	}

	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}

	public Equipamento getEquipamento() {
		if(!Hibernate.isInitialized(equipamento)){
			equipamento = null;	
		}
		return equipamento;
	}

	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
	}

	public String getMoeda() {
		return moeda;
	}

	public void setMoeda(String moeda) {
		this.moeda = moeda;
	}

	public BigDecimal getValorReparo() {
		return valorReparo;
	}

	public void setValorReparo(BigDecimal valorReparo) {
		this.valorReparo = valorReparo;
	}
	
}

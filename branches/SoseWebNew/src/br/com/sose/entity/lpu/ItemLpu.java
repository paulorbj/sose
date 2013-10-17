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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.beans.factory.annotation.Configurable;

import br.com.sose.entity.admistrativo.Unidade;

@Entity
@Configurable
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ItemLpu implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
		
	private String unidade;
	private String equipamento;
	private String fabricante;
	private String codigo1;
	private String codigo2;
	private BigDecimal valor;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="lpu_id", referencedColumnName="id")
	private Lpu lpu;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="unidade_item_lpu_id", referencedColumnName="id")
	private UnidadeItemLpu unidadeItemLpu;
	
	@Column(name="associacao_realizada")
	private Boolean associacaoRealizada;
	
	@Column(name="referencia_excel")
	private String referenciaExcel;
	
	public ItemLpu() {
		unidade = "";
		equipamento = "";
		fabricante = "";
		codigo1 = "";
		codigo2 = "";
		valor = new BigDecimal(0);
	}

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

	public Boolean getAssociacaoRealizada() {
		return associacaoRealizada;
	}

	public void setAssociacaoRealizada(Boolean associacaoRealizada) {
		this.associacaoRealizada = associacaoRealizada;
	}

	public String getReferenciaExcel() {
		return referenciaExcel;
	}

	public void setReferenciaExcel(String referenciaExcel) {
		this.referenciaExcel = referenciaExcel;
	}

	public UnidadeItemLpu getUnidadeItemLpu() {
		return unidadeItemLpu;
	}

	public void setUnidadeItemLpu(UnidadeItemLpu unidadeItemLpu) {
		this.unidadeItemLpu = unidadeItemLpu;
	}
}

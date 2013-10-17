package br.com.sose.entity.lpu;

import java.io.Serializable;
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

import org.hibernate.Hibernate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.beans.factory.annotation.Configurable;

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
	
	@Column(name="celula_unidade", length = 4)
	private String celulaUnidade;
	@Column(name="celula_codigo1", length = 4)
	private String celulaCodigo1;
	@Column(name="celula_codigo2", length = 4)
	private String celulaCodigo2;
	@Column(name="celula_fabricante", length = 4)
	private String celulaFabricante;
	@Column(name="celula_equipamento", length = 4)
	private String celulaEquipamento;
	@Column(name="celula_valor", length = 4)
	private String celulaValor;
	
	@Column(name="linha_cabecalho", length = 4)
	private String linhaCabecalho;
	@Column(name="primeira_linha_dados", length = 4)
	private String primeiraLinhaDados;
	@Column(name="ultima_linha_dados", length = 4)
	private String ultimaLinhaDados;
	
	private Boolean ativa;
	
	@Column(name="valido_ate")
	private Date validoAte;
	
	@Column(name="upload_em")
	private Date uploadEm;
	
	@Column(name="rodou_auto_associar")
	private Boolean rodouAutoAssociar;
	
	@OneToMany(mappedBy = "lpu", fetch=FetchType.LAZY)
	private Set<UnidadeItemLpu> listaUnidadeItemLpu;
	
	private String unidade;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCelulaUnidade() {
		return celulaUnidade;
	}

	public void setCelulaUnidade(String celulaUnidade) {
		this.celulaUnidade = celulaUnidade;
	}

	public String getCelulaCodigo1() {
		return celulaCodigo1;
	}

	public void setCelulaCodigo1(String celulaCodigo1) {
		this.celulaCodigo1 = celulaCodigo1;
	}

	public String getCelulaCodigo2() {
		return celulaCodigo2;
	}

	public void setCelulaCodigo2(String celulaCodigo2) {
		this.celulaCodigo2 = celulaCodigo2;
	}

	public String getCelulaFabricante() {
		return celulaFabricante;
	}

	public void setCelulaFabricante(String celulaFabricante) {
		this.celulaFabricante = celulaFabricante;
	}

	public String getCelulaEquipamento() {
		return celulaEquipamento;
	}

	public void setCelulaEquipamento(String celulaEquipamento) {
		this.celulaEquipamento = celulaEquipamento;
	}

	public String getCelulaValor() {
		return celulaValor;
	}

	public void setCelulaValor(String celulaValor) {
		this.celulaValor = celulaValor;
	}

	public String getLinhaCabecalho() {
		return linhaCabecalho;
	}

	public void setLinhaCabecalho(String linhaCabecalho) {
		this.linhaCabecalho = linhaCabecalho;
	}

	public String getPrimeiraLinhaDados() {
		return primeiraLinhaDados;
	}

	public void setPrimeiraLinhaDados(String primeiraLinhaDados) {
		this.primeiraLinhaDados = primeiraLinhaDados;
	}

	public String getUltimaLinhaDados() {
		return ultimaLinhaDados;
	}

	public void setUltimaLinhaDados(String ultimaLinhaDados) {
		this.ultimaLinhaDados = ultimaLinhaDados;
	}

	public Boolean getAtiva() {
		return ativa;
	}

	public void setAtiva(Boolean ativa) {
		this.ativa = ativa;
	}

	public Date getValidoAte() {
		return validoAte;
	}

	public void setValidoAte(Date validoAte) {
		this.validoAte = validoAte;
	}

	public Date getUploadEm() {
		return uploadEm;
	}

	public void setUploadEm(Date uploadEm) {
		this.uploadEm = uploadEm;
	}

	public Boolean getRodouAutoAssociar() {
		return rodouAutoAssociar;
	}

	public void setRodouAutoAssociar(Boolean rodouAutoAssociar) {
		this.rodouAutoAssociar = rodouAutoAssociar;
	}

	public Pessoa getCliente() {
		return cliente;
	}

	public void setCliente(Pessoa cliente) {
		this.cliente = cliente;
	}

	public Set<UnidadeItemLpu> getListaUnidadeItemLpu() {
		if(!Hibernate.isInitialized(listaUnidadeItemLpu) || listaUnidadeItemLpu == null){
			listaUnidadeItemLpu = new HashSet<UnidadeItemLpu>();
		}
		return listaUnidadeItemLpu;
	}

	public void setListaUnidadeItemLpu(Set<UnidadeItemLpu> listaUnidadeItemLpu) {
		this.listaUnidadeItemLpu = listaUnidadeItemLpu;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}
	

}

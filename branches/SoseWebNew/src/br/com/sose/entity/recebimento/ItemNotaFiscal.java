package br.com.sose.entity.recebimento;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.Transient;

import org.hibernate.Hibernate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.beans.factory.annotation.Configurable;

import br.com.sose.entity.admistrativo.Lpu;
import br.com.sose.entity.admistrativo.Unidade;
import br.com.sose.entity.proposta.ItemProposta;

@Entity
@Configurable
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ItemNotaFiscal implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long idTemp;

	private String nome;

	private Integer quantidade;
	
	private String codigo;
	
	private String ncm;
	
	private String cfop;
	
	private String cst;
	
	private String unidadeMedida;

	@Column(name = "valor_unitario", precision=30,scale=4)
	private BigDecimal valorUnitario;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "unidade_id", referencedColumnName = "id")
	private Unidade unidade;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "lpu", referencedColumnName = "id")
	private Lpu lpu;

	@OneToMany(mappedBy = "itemNotaFiscal", fetch=FetchType.LAZY)
	private Set<OrdemServico> ordensServico;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "nota_fiscal_id", referencedColumnName = "id")
	private NotaFiscal notaFiscal;
	
	private String unidadeString;
	
	private String lpuString;
	
	private Integer ordemNaLista;
	
	public ItemNotaFiscal() {
	
	}
	
	

	public ItemNotaFiscal(Long id, String nome, Integer quantidade,
			String codigo, String ncm, String cfop, String cst,
			String unidadeMedida, BigDecimal valorUnitario,
			String unidadeString, String lpuString) {
		super();
		this.id = id;
		this.nome = nome;
		this.quantidade = quantidade;
		this.codigo = codigo;
		this.ncm = ncm;
		this.cfop = cfop;
		this.cst = cst;
		this.unidadeMedida = unidadeMedida;
		this.valorUnitario = valorUnitario;
		this.unidadeString = unidadeString;
		this.lpuString = lpuString;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public Unidade getUnidade() {
		if(!Hibernate.isInitialized(unidade)){
			unidade = null;	
		}
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
		this.unidadeString=unidade.getNome();
	}

	public Lpu getLpu() {
		if(!Hibernate.isInitialized(lpu)){
			lpu = null;	
		}
		return lpu;
	}

	public void setLpu(Lpu lpu) {
		this.lpu = lpu;
		//TODO - verificar LPU
		//this.lpuString = lpu.getUnidade();
	}

	public Set<OrdemServico> getOrdensServico() {
		if(!Hibernate.isInitialized(ordensServico) || ordensServico == null){
			ordensServico = new HashSet<OrdemServico>();
		}
		return ordensServico;
	}

	public void setOrdensServico(Set<OrdemServico> ordensServico) {
		this.ordensServico = ordensServico;
	}

	public NotaFiscal getNotaFiscal() {
		if(!Hibernate.isInitialized(notaFiscal)){
			notaFiscal = null;	
		}
		return notaFiscal;
	}

	public void setNotaFiscal(NotaFiscal notaFiscal) {
		this.notaFiscal = notaFiscal;
	}

	

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNcm() {
		return ncm;
	}

	public void setNcm(String ncm) {
		this.ncm = ncm;
	}

	public String getCfop() {
		return cfop;
	}

	public void setCfop(String cfop) {
		this.cfop = cfop;
	}

	public String getUnidadeMedida() {
		return unidadeMedida;
	}

	public void setUnidadeMedida(String unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}

	public Long getIdTemp() {
		return idTemp;
	}

	public void setIdTemp(Long idTemp) {
		this.idTemp = idTemp;
	}

	public String getCst() {
		return cst;
	}

	public void setCst(String cst) {
		this.cst = cst;
	}

	public String getUnidadeString() {
		return unidadeString;
	}

	public void setUnidadeString(String unidadeString) {
		this.unidadeString = unidadeString;
	}

	public String getLpuString() {
		return lpuString;
	}

	public void setLpuString(String lpuString) {
		this.lpuString = lpuString;
	}



	public Integer getOrdemNaLista() {
		return ordemNaLista;
	}



	public void setOrdemNaLista(Integer ordemNaLista) {
		this.ordemNaLista = ordemNaLista;
	}

	
	
}

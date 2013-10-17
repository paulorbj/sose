package br.com.sose.entity.admistrativo;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.OrderBy;

import org.hibernate.Hibernate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.beans.factory.annotation.Configurable;

import br.com.sose.entity.compra.ItemCompra;
import br.com.sose.entity.compra.PedidoCompra;

@Entity
@Configurable
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Componente implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 1000)
	private String descricao;

	@Column(length = 200, nullable = false)
	private String nome;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(referencedColumnName="id", name="encapsulamento_id")
	private Encapsulamento encapsulamento;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(referencedColumnName="id", name="fabricante_id")
	private Fabricante fabricante;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(referencedColumnName="id", name="tipo_componente_id")
	private TipoComponente tipo;

	private String dataSheet;

	private Integer qtdEstoqueMinimo;

	private Integer qtdEstoque;
	
	private Integer qtdReservada;

	private String posicaoEstoque;

	@Column(name = "cadastro_ativo")
	private Boolean cadastroSistemaAtivo;

	private String pinos;

	private String fornecedor;

	private Integer qtdAcumulada;

	private BigDecimal precoUnitario;

	private Date dtUltimaCompra;

	private String nomeFabricante;

	private String nomeEncapsulamento;

	private String nomeTipoComponente;
	
	private Boolean valido;
	
	private Integer qtdComprada;
	
	@OneToMany(mappedBy = "componenteNotificacao", fetch=FetchType.LAZY)
	@OrderBy(value="id")
	private Set<ItemCompra> itensCompraPendentes;

	public Componente() {
		// TODO Auto-generated constructor stub
	}



	public Componente(Long id, String descricao, String nome,
			Integer qtdEstoqueMinimo, String nomeFabricante,
			String nomeEncapsulamento, String nomeTipoComponente, 
			Integer qtdEstoque, String posicaoEstoque,String pinos) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.nome = nome;
		this.qtdEstoqueMinimo = qtdEstoqueMinimo;
		this.nomeFabricante = nomeFabricante;
		this.nomeEncapsulamento = nomeEncapsulamento;
		this.nomeTipoComponente = nomeTipoComponente;
		this.qtdEstoque = qtdEstoque;
		this.posicaoEstoque = posicaoEstoque;
		this.pinos = pinos;
	}

	public Componente(Long id, String descricao, String nome,
			Integer qtdEstoqueMinimo, String nomeFabricante,
			String nomeEncapsulamento, String nomeTipoComponente, 
			Integer qtdEstoque, String posicaoEstoque,String pinos, Boolean valido, Integer qtdComprada) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.nome = nome;
		this.qtdEstoqueMinimo = qtdEstoqueMinimo;
		this.nomeFabricante = nomeFabricante;
		this.nomeEncapsulamento = nomeEncapsulamento;
		this.nomeTipoComponente = nomeTipoComponente;
		this.qtdEstoque = qtdEstoque;
		this.posicaoEstoque = posicaoEstoque;
		this.pinos = pinos;
		this.qtdEstoque = qtdComprada;
		this.valido = valido;
	}

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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Boolean getCadastroSistemaAtivo() {
		return cadastroSistemaAtivo;
	}

	public void setCadastroSistemaAtivo(Boolean cadastroSistemaAtivo) {
		this.cadastroSistemaAtivo = cadastroSistemaAtivo;
	}

	public Encapsulamento getEncapsulamento() {
		if(!Hibernate.isInitialized(encapsulamento)){
			encapsulamento = null;	
		}
		return encapsulamento;
	}

	public void setEncapsulamento(Encapsulamento encapsulamento) {
		this.encapsulamento = encapsulamento;
		if(encapsulamento != null){
			this.nomeEncapsulamento = encapsulamento.getNome();
		}
	}

	public Fabricante getFabricante() {
		if(!Hibernate.isInitialized(fabricante)){
			fabricante = null;	
		}
		return fabricante;
	}

	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
		if(fabricante != null){
			this.nomeFabricante = fabricante.getNome();
		}
	}

	public TipoComponente getTipo() {
		if(!Hibernate.isInitialized(tipo)){
			tipo = null;	
		}
		return tipo;
	}

	public void setTipo(TipoComponente tipo) {
		this.tipo = tipo;
		if(tipo != null){
			this.nomeTipoComponente = tipo.getNome();
		}
	}

	public String getDataSheet() {
		return dataSheet;
	}

	public void setDataSheet(String dataSheet) {
		this.dataSheet = dataSheet;
	}

	public Integer getQtdEstoqueMinimo() {
		return qtdEstoqueMinimo;
	}

	public void setQtdEstoqueMinimo(Integer qtdEstoqueMinimo) {
		this.qtdEstoqueMinimo = qtdEstoqueMinimo;
	}

	public Integer getQtdEstoque() {
		return qtdEstoque;
	}

	public void setQtdEstoque(Integer qtdEstoque) {
		this.qtdEstoque = qtdEstoque;
	}

	public String getPosicaoEstoque() {
		return posicaoEstoque;
	}

	public void setPosicaoEstoque(String posicaoEstoque) {
		this.posicaoEstoque = posicaoEstoque;
	}

	public String getPinos() {
		return pinos;
	}

	public void setPinos(String pinos) {
		this.pinos = pinos;
	}

	public String getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(String fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Integer getQtdAcumulada() {
		return qtdAcumulada;
	}

	public void setQtdAcumulada(Integer qtdAcumulada) {
		this.qtdAcumulada = qtdAcumulada;
	}

	public BigDecimal getPrecoUnitario() {
		return precoUnitario;
	}

	public void setPrecoUnitario(BigDecimal precoUnitario) {
		this.precoUnitario = precoUnitario;
	}

	public Date getDtUltimaCompra() {
		return dtUltimaCompra;
	}

	public void setDtUltimaCompra(Date dtUltimaCompra) {
		this.dtUltimaCompra = dtUltimaCompra;
	}



	public String getNomeFabricante() {
		return nomeFabricante;
	}



	public void setNomeFabricante(String nomeFabricante) {
		if(nomeFabricante != null)
			this.nomeFabricante = nomeFabricante;
	}

	public String getNomeTipoComponente() {
		return nomeTipoComponente;
	}



	public void setNomeTipoComponente(String nomeTipoComponente) {
		if(nomeTipoComponente != null)
			this.nomeTipoComponente = nomeTipoComponente;
	}



	public String getNomeEncapsulamento() {
		return nomeEncapsulamento;
	}



	public void setNomeEncapsulamento(String nomeEncapsulamento) {
		if(nomeEncapsulamento != null)
			this.nomeEncapsulamento = nomeEncapsulamento;
	}



	public Integer getQtdReservada() {
		return qtdReservada;
	}



	public void setQtdReservada(Integer qtdReservada) {
		this.qtdReservada = qtdReservada;
	}



	public Boolean getValido() {
		return valido;
	}



	public void setValido(Boolean valido) {
		this.valido = valido;
	}



	public Integer getQtdComprada() {
		return qtdComprada;
	}



	public void setQtdComprada(Integer qtdComprada) {
		this.qtdComprada = qtdComprada;
	}



	public Set<ItemCompra> getItensCompraPendentes() {
		if(!Hibernate.isInitialized(itensCompraPendentes) || itensCompraPendentes == null){
			itensCompraPendentes = new HashSet<ItemCompra>();
		}
		return itensCompraPendentes;
	}



	public void setItensCompraPendentes(Set<ItemCompra> itensCompraPendentes) {
		this.itensCompraPendentes = itensCompraPendentes;
	}



}

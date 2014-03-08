package br.com.sose.entity.admistrativo.parceiros;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.Hibernate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.beans.factory.annotation.Configurable;

@Entity
@Configurable
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Pessoa implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final Integer TIPO_PESSOA_FISICA= 1;
	public static final Integer TIPO_PESSOA_JURIDICA= 2;
	
	public static final Integer TIPO_CLIENTE= 1;
	public static final Integer TIPO_FORNECEDOR= 2;
	public static final Integer TIPO_PRESTADOR_SERVICO= 3;
	public static final Integer TIPO_TRANSPORTADORA= 4;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * Representa o nome ou a razao social
	 */
	private String nomeRazaoSocial;
	
	/**
	 * Representa o nome da pessoa no sistema (apelido / nome fantasia)
	 */
	private String nomeSistema;
	
	/**
	 * Representa o cpf do pj / o cnpj do pj
	 */
	private String cpfCnpj;
	
	/**
	 * Representa o rg do pf / o ie do pj
	 */
	private String rgIe;
	
	private String inscricaoMunicipal;
	
	/**
	 * Representa a data de nascimento do pf/data de abertura da pj
	 */
	private Date data;
	
	private Integer prazoDevolucao;
	
	@OneToMany(mappedBy="cliente", fetch=FetchType.LAZY)
	private Set<Endereco> enderecos;
	
	@OneToMany(mappedBy="cliente", fetch=FetchType.LAZY)
	private Set<Contato> contatos;
		
	@Column(name = "cadastro_ativo")
	private Boolean cadastroSistemaAtivo;
	
	/**
	 * cliente
	 * prestadora de servico
	 * transportadora
	 * fornecedor
	 */
	private Integer tipo;
	
	/**
	 * pessoa fisica
	 * pessoa juridica
	 */
	private Integer tipoPessoa;
	
	private Boolean possuiContrato;
	
	private Boolean possuiPedidoCompra;
	
	private Integer tempoGarantia;
	
	private Boolean estenderGarantia;
	
	public Pessoa() {
	}
	
	

	public Pessoa(Long id, String nomeSistema) {
		super();
		this.id = id;
		this.nomeSistema = nomeSistema;
	}



	public Pessoa(Long id, String nomeRazaoSocial, String nomeSistema,
			String cpfCnpj, String rgIe, String inscricaoMunicipal, Date data,
			Integer prazoDevolucao, Boolean cadastroSistemaAtivo, Integer tipo,
			Integer tipoPessoa, Boolean possuiContrato,
			Boolean possuiPedidoCompra, Integer tempoGarantia) {
		super();
		this.id = id;
		this.nomeRazaoSocial = nomeRazaoSocial;
		this.nomeSistema = nomeSistema;
		this.cpfCnpj = cpfCnpj;
		this.rgIe = rgIe;
		this.inscricaoMunicipal = inscricaoMunicipal;
		this.data = data;
		this.prazoDevolucao = prazoDevolucao;
		this.cadastroSistemaAtivo = cadastroSistemaAtivo;
		this.tipo = tipo;
		this.tipoPessoa = tipoPessoa;
		this.possuiContrato = possuiContrato;
		this.possuiPedidoCompra = possuiPedidoCompra;
		this.tempoGarantia = tempoGarantia;
	}
	
	

	public Pessoa(Long id, String nomeRazaoSocial, String nomeSistema,
			String cpfCnpj, String rgIe, Integer tipo, Integer tipoPessoa,
			Boolean possuiContrato,String inscricaoMunicipal) {
		super();
		this.id = id;
		this.nomeRazaoSocial = nomeRazaoSocial;
		this.nomeSistema = nomeSistema;
		this.cpfCnpj = cpfCnpj;
		this.rgIe = rgIe;
		this.tipo = tipo;
		this.tipoPessoa = tipoPessoa;
		this.possuiContrato = possuiContrato;
		this.inscricaoMunicipal = inscricaoMunicipal;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Endereco> getEnderecos() {
		if(!Hibernate.isInitialized(enderecos)){
			enderecos = null;
		}
		return enderecos;
	}

	public void setEnderecos(Set<Endereco> enderecos) {
		this.enderecos = enderecos;
	}


	public Set<Contato> getContatos() {
		if(!Hibernate.isInitialized(contatos)){
			contatos = null;
		}
		return contatos;
	}

	public void setContatos(Set<Contato> contatos) {
		this.contatos = contatos;
	}

	public Boolean getCadastroSistemaAtivo() {
		return cadastroSistemaAtivo;
	}

	public void setCadastroSistemaAtivo(Boolean cadastroSistemaAtivo) {
		this.cadastroSistemaAtivo = cadastroSistemaAtivo;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public String getNomeRazaoSocial() {
		return nomeRazaoSocial;
	}

	public void setNomeRazaoSocial(String nomeRazaoSocial) {
		this.nomeRazaoSocial = nomeRazaoSocial;
	}

	public String getNomeSistema() {
		return nomeSistema;
	}

	public void setNomeSistema(String nomeSistema) {
		this.nomeSistema = nomeSistema;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public String getRgIe() {
		return rgIe;
	}

	public void setRgIe(String rgIe) {
		this.rgIe = rgIe;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Integer getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(Integer tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public Boolean getPossuiContrato() {
		return possuiContrato;
	}

	public void setPossuiContrato(Boolean possuiContrato) {
		this.possuiContrato = possuiContrato;
	}

	public Boolean getPossuiPedidoCompra() {
		return possuiPedidoCompra;
	}

	public void setPossuiPedidoCompra(Boolean possuiPedidoCompra) {
		this.possuiPedidoCompra = possuiPedidoCompra;
	}

	public Integer getTempoGarantia() {
		return tempoGarantia;
	}

	public void setTempoGarantia(Integer tempoGarantia) {
		this.tempoGarantia = tempoGarantia;
	}

	public Integer getPrazoDevolucao() {
		return prazoDevolucao;
	}

	public void setPrazoDevolucao(Integer prazoDevolucao) {
		this.prazoDevolucao = prazoDevolucao;
	}

	public String getInscricaoMunicipal() {
		return inscricaoMunicipal;
	}

	public void setInscricaoMunicipal(String inscricaoMunicipal) {
		this.inscricaoMunicipal = inscricaoMunicipal;
	}



	public Boolean getEstenderGarantia() {
		return estenderGarantia;
	}



	public void setEstenderGarantia(Boolean estenderGarantia) {
		this.estenderGarantia = estenderGarantia;
	}
	
	
	
}

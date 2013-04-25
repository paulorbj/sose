package br.com.sose.entity.admistrativo.parceiros;

import java.io.Serializable;
import java.util.Date;

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

import br.com.sose.entity.admistrativo.Usuario;

@Entity
@Configurable
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Endereco implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 200, name="rua", nullable=false)
	private String logradouro;

	@Column(length = 200, nullable=false)
	private String numero;

	@Column(name = "complemento")
	private String complemento;

	@Column(name = "bairro")
	private String bairro;

	@Column(name = "pais")
	private String pais;

	@Column(length = 200, nullable=false)
	private String cidade;

	@Column(length = 200, nullable=false)
	private String cep;

	@Column(length = 200, nullable=false)
	private String estado;

	private Boolean enderecoComercial;
	
	private Boolean enderecoCobranca;
	
	private Boolean enderecoEntrega;
	
	private String telefone;
	
	private String fax;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "cliente_id", referencedColumnName = "id")
	private Pessoa cliente;
	
	private String nomeCliente;
	
	@Column(name = "cadastro_ativo")
	private Boolean cadastroSistemaAtivo;
	
	public Endereco() {
	}
	

	public Endereco(Long id, String logradouro, String numero,
			String complemento, String bairro, String pais, String cidade,
			String cep, String estado, Boolean enderecoComercial,
			Boolean enderecoCobranca, Boolean enderecoEntrega, String telefone,
			String fax, String nomeCliente, Boolean cadastroSistemaAtivo) {
		super();
		this.id = id;
		this.logradouro = logradouro;
		this.numero = numero;
		this.complemento = complemento;
		this.bairro = bairro;
		this.pais = pais;
		this.cidade = cidade;
		this.cep = cep;
		this.estado = estado;
		this.enderecoComercial = enderecoComercial;
		this.enderecoCobranca = enderecoCobranca;
		this.enderecoEntrega = enderecoEntrega;
		this.telefone = telefone;
		this.fax = fax;
		this.nomeCliente = nomeCliente;
		this.cadastroSistemaAtivo = cadastroSistemaAtivo;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Boolean getEnderecoComercial() {
		return enderecoComercial;
	}

	public void setEnderecoComercial(Boolean enderecoComercial) {
		this.enderecoComercial = enderecoComercial;
	}

	public Boolean getEnderecoCobranca() {
		return enderecoCobranca;
	}

	public void setEnderecoCobranca(Boolean enderecoCobranca) {
		this.enderecoCobranca = enderecoCobranca;
	}

	public Boolean getEnderecoEntrega() {
		return enderecoEntrega;
	}

	public void setEnderecoEntrega(Boolean enderecoEntrega) {
		this.enderecoEntrega = enderecoEntrega;
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

	public Boolean getCadastroSistemaAtivo() {
		return cadastroSistemaAtivo;
	}

	public void setCadastroSistemaAtivo(Boolean cadastroSistemaAtivo) {
		this.cadastroSistemaAtivo = cadastroSistemaAtivo;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}



	public String getNomeCliente() {
		return nomeCliente;
	}



	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
	
	
}

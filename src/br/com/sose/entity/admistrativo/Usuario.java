package br.com.sose.entity.admistrativo;

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

import br.com.sose.entity.admistrativo.parceiros.Endereco;

@Entity
@Configurable
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 50, nullable = false)
	private String usuario;

	@Column(nullable = false)
	private String senha;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(referencedColumnName="id",name="perfil_id")
	private Perfil perfil;

	@Column(length = 50, nullable = false)
	private String nome;

	private String email;

	private String rg;

	private String cpf;

	private Date dataNascimento;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(referencedColumnName="id",name="endereco_id")
	private Endereco endereco;

	private Boolean bloqueado;

	private String nomePerfil;

	private String telefone;

	private String celular;

	public Usuario() {
		// TODO Auto-generated constructor stub
	}



	public Usuario(Long id, String usuario, String nome, String rg, String cpf,
			Date dataNascimento, String nomePerfil, String telefone,
			String celular) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.nome = nome;
		this.rg = rg;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
		this.nomePerfil = nomePerfil;
		this.telefone = telefone;
		this.celular = celular;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getBloqueado() {
		return bloqueado;
	}

	public void setBloqueado(Boolean bloqueado) {
		this.bloqueado = bloqueado;
	}

	public Endereco getEndereco() {
		if(!Hibernate.isInitialized(endereco)){
			endereco = null;
		}
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
		if(endereco != null){
			this.telefone = endereco.getTelefone();
			this.celular = endereco.getFax();
		}
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Perfil getPerfil() {
		if(!Hibernate.isInitialized(perfil) || perfil == null){
			return null;
		}
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
		if(perfil != null){
			this.nomePerfil = perfil.getNome();
		}
	}



	public String getNomePerfil() {
		return nomePerfil;
	}



	public void setNomePerfil(String nomePerfil) {
		if(nomePerfil != null)
			this.nomePerfil = nomePerfil;
	}



	public String getTelefone() {
		return telefone;
	}



	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}



	public String getCelular() {
		return celular;
	}



	public void setCelular(String celular) {
		this.celular = celular;
	}

	//	private Set<Telefone> telefones;
	//
	//
	//	@OneToOne(mappedBy = "lider")
	//	private Laboratorio laboratorioLiderado;
	//
	//	@ManyToMany(mappedBy = "usuarios")
	//	private Set<Laboratorio> laboratorios = new HashSet<Laboratorio>();


}

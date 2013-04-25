package br.com.sose.entity.admistrativo;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.Hibernate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.beans.factory.annotation.Configurable;

@Entity
@Configurable
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Laboratorio implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 1000)
	private String descricao;

	@Column(length = 200, nullable = false)
	private String nome;

	@ManyToMany(fetch=FetchType.LAZY)
	private Set<Usuario> tecnicos;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(referencedColumnName="id", name="lider_id")
	private Usuario lider;

	@Column(name = "cadastro_ativo")
	private Boolean cadastroSistemaAtivo;

	private String nomeLider;

	public Laboratorio() {
		// TODO Auto-generated constructor stub
	}

	public Laboratorio(Long id, String nome, String descricao, String nomeLider) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.nomeLider = nomeLider;
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

	public Set<Usuario> getTecnicos() {
		if(!Hibernate.isInitialized(tecnicos)){
			tecnicos = null;
		}
		return tecnicos;
	}

	public void setTecnicos(Set<Usuario> tecnicos) {
		this.tecnicos = tecnicos;
	}

	public Usuario getLider() {
		if(!Hibernate.isInitialized(lider)){
			lider = null;
		}
		return lider;
	}

	public void setLider(Usuario lider) {
		this.lider = lider;
		if(lider != null){
			this.nomeLider = lider.getUsuario();
		}
	}



	public String getNomeLider() {
		return nomeLider;
	}



	public void setNomeLider(String nomeLider) {
		if(nomeLider != null)
			this.nomeLider = nomeLider;
	}
}

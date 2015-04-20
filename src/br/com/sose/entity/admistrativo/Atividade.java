package br.com.sose.entity.admistrativo;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.beans.factory.annotation.Configurable;

@Entity
@Configurable
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Atividade implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 1000)
	private String descricao;

	@Column(length = 200, nullable = false)
	private String nome;
	
	@Column(name = "cadastro_ativo")
	private Boolean cadastroSistemaAtivo;

	public Atividade() {
		// TODO Auto-generated constructor stub
	}
	
	public Atividade(Long id, String descricao, String nome,
			Boolean cadastroSistemaAtivo) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.nome = nome;
		this.cadastroSistemaAtivo = cadastroSistemaAtivo;
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
	
	
}

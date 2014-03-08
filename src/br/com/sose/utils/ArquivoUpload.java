package br.com.sose.utils;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.beans.factory.annotation.Configurable;

@Entity
@Configurable
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ArquivoUpload implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	@Column(name="nome_original")
	private String nomeOriginal;
	
	@Column(name="identificador_entidade")
	private Long identificadorEntidade;
	
	@Column(name="tipo_entidade")
	private String tipoEntidade;
	
	@Column(name="data_upload")
	private Date dataUpload;
	
	@Column(name="tipo_arquivo")
	private String tipoArquivo;
	
	@Transient
	private String caminhoImagem;
	
	private Boolean isImagemPrincipal;

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

	public Long getIdentificadorEntidade() {
		return identificadorEntidade;
	}

	public void setIdentificadorEntidade(Long identificadorEntidade) {
		this.identificadorEntidade = identificadorEntidade;
	}

	public String getTipoEntidade() {
		return tipoEntidade;
	}

	public void setTipoEntidade(String tipoEntidade) {
		this.tipoEntidade = tipoEntidade;
	}

	public Date getDataUpload() {
		return dataUpload;
	}

	public void setDataUpload(Date dataUpload) {
		this.dataUpload = dataUpload;
	}

	public String getTipoArquivo() {
		return tipoArquivo;
	}

	public void setTipoArquivo(String tipoArquivo) {
		this.tipoArquivo = tipoArquivo;
	}

	public String getNomeOriginal() {
		return nomeOriginal;
	}

	public void setNomeOriginal(String nomeOriginal) {
		this.nomeOriginal = nomeOriginal;
	}

	public String getCaminhoImagem() {
		return caminhoImagem;
	}

	public void setCaminhoImagem(String caminhoImagem) {
		this.caminhoImagem = caminhoImagem;
	}

	public Boolean getIsImagemPrincipal() {
		return isImagemPrincipal;
	}

	public void setIsImagemPrincipal(Boolean isImagemPrincipal) {
		this.isImagemPrincipal = isImagemPrincipal;
	}
	
	
}

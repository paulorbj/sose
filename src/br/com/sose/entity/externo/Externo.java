package br.com.sose.entity.externo;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

// Copia de LaudoTecnico.java

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.Hibernate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.beans.factory.annotation.Configurable;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.admistrativo.parceiros.Contato;
import br.com.sose.entity.admistrativo.parceiros.Pessoa;
import br.com.sose.entity.orcamento.Orcamento;
import br.com.sose.entity.recebimento.OrdemServico;
import br.com.sose.entity.reparo.Reparo;
import br.com.sose.utils.DateUtils;

@Entity
@Configurable
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Externo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
//	@Column(name = "controle")
//	private String controle;

	@Column(name = "data_criacao")
	private Date dataCriacao;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "criador_id", referencedColumnName = "id")
	private Usuario criadoPor;
	


	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "pessoa_externo", referencedColumnName = "id")
	private Pessoa pessoaExterno;
	
	@Column(name = "nf_saida", length=30)
	private String nfSaida;
	
	@Column(name = "nf_retorno", length=30)
	private String nfRetorno;
	
	@OneToMany(mappedBy="id", fetch=FetchType.LAZY)
	private Set<ObservacaoExterno> historico;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}


	public Usuario getCriadoPor() {
		if(!Hibernate.isInitialized(criadoPor)){
			criadoPor = null;	
		}
		return criadoPor;
	}

	public void setCriadoPor(Usuario criadoPor) {
		this.criadoPor = criadoPor;
	}

	

	

	public Externo() {
		super();
		
	}



	
	public String getDataCriacaoString(){
		return DateUtils.formatarDataDDMMYYYY(dataCriacao);
	}
	


	/**
	 * @return the nfSaida
	 */
	public String getNfSaida() {
		return nfSaida;
	}

	/**
	 * @param nfSaida the nfSaida to set
	 */
	public void setNfSaida(String nfSaida) {
		this.nfSaida = nfSaida;
	}

	/**
	 * @return the nfRetorno
	 */
	public String getNfRetorno() {
		return nfRetorno;
	}

	/**
	 * @param nfRetorno the nfRetorno to set
	 */
	public void setNfRetorno(String nfRetorno) {
		this.nfRetorno = nfRetorno;
	}

	/**
	 * @return the observacoes
	 */

	/**
	 * @return the pessoaExterno
	 */
	public Pessoa getPessoaExterno() {
		return pessoaExterno;
	}

	/**
	 * @param pessoaExterno the pessoaExterno to set
	 */
	public void setPessoaExterno(Pessoa pessoaExterno) {
		this.pessoaExterno = pessoaExterno;
	}

	/**
	 * @return the historico
	 */
	public Set<ObservacaoExterno> getHistorico() {
		return historico;
	}

	/**
	 * @param historico the historico to set
	 */
	public void setHistorico(Set<ObservacaoExterno> historico) {
		this.historico = historico;
	}
	
}

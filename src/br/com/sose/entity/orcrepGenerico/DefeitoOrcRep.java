package br.com.sose.entity.orcrepGenerico;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.Hibernate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.beans.factory.annotation.Configurable;

import br.com.sose.entity.admistrativo.Defeito;
import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.orcamento.Orcamento;
import br.com.sose.entity.reparo.Reparo;

@Entity
@Configurable
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DefeitoOrcRep {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(referencedColumnName="id",name="defeito_id")
	private Defeito defeito;
	
	private String justificativa;
	
	private Date data;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(referencedColumnName="id",name="usuario_id")
	private Usuario usuario;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(referencedColumnName="id",name="reparo_id")
	private Reparo reparo;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(referencedColumnName="id",name="orcamento_id")
	private Orcamento orcamento;

	public DefeitoOrcRep() {
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Defeito getDefeito() {
		if(!Hibernate.isInitialized(defeito)){
			defeito = null;	
		}
		return defeito;
	}

	public void setDefeito(Defeito defeito) {
		this.defeito = defeito;
	}

	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Usuario getUsuario() {
		if(!Hibernate.isInitialized(usuario)){
			usuario = null;	
		}
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Reparo getReparo() {
		if(!Hibernate.isInitialized(reparo)){
			reparo = null;	
		}
		return reparo;
	}

	public void setReparo(Reparo reparo) {
		this.reparo = reparo;
	}

	public Orcamento getOrcamento() {
		if(!Hibernate.isInitialized(orcamento)){
			orcamento = null;	
		}
		return orcamento;
	}

	public void setOrcamento(Orcamento orcamento) {
		this.orcamento = orcamento;
	}

	
}

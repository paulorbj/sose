package br.com.sose.entity.estoque;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Cacheable;
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

import br.com.sose.entity.admistrativo.Componente;
import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.orcamento.Orcamento;
import br.com.sose.entity.orcrepGenerico.RequisicaoComponente;
import br.com.sose.entity.reparo.Reparo;

@Entity
@Configurable
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DevolucaoComponente implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(referencedColumnName="id",name="reparo_id")
	private Reparo reparo;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(referencedColumnName="id",name="orcamento_id")
	private Orcamento orcamento;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "componente_id", referencedColumnName = "id")
	private Componente componente;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "requisicao_id", referencedColumnName = "id")
	private RequisicaoComponente requisicao;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "devolvido_por_id", referencedColumnName = "id")
	private Usuario devolvidoPor;

	private Date devolvidoEm;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "recebido_por_id", referencedColumnName = "id")
	private Usuario recebidoPor;
	
	private Date recebidoEm;
	
	private String condicao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Componente getComponente() {
		if(!Hibernate.isInitialized(componente)){
			componente = null;	
		}
		return componente;
	}

	public void setComponente(Componente componente) {
		this.componente = componente;
	}

	public RequisicaoComponente getRequisicao() {
		if(!Hibernate.isInitialized(requisicao)){
			requisicao = null;	
		}
		return requisicao;
	}

	public void setRequisicao(RequisicaoComponente requisicao) {
		this.requisicao = requisicao;
	}

	public Usuario getDevolvidoPor() {
		return devolvidoPor;
	}

	public void setDevolvidoPor(Usuario devolvidoPor) {
		this.devolvidoPor = devolvidoPor;
	}

	public Date getDevolvidoEm() {
		return devolvidoEm;
	}

	public void setDevolvidoEm(Date devolvidoEm) {
		this.devolvidoEm = devolvidoEm;
	}

	public Usuario getRecebidoPor() {
		return recebidoPor;
	}

	public void setRecebidoPor(Usuario recebidoPor) {
		this.recebidoPor = recebidoPor;
	}

	public Date getRecebidoEm() {
		return recebidoEm;
	}

	public void setRecebidoEm(Date recebidoEm) {
		this.recebidoEm = recebidoEm;
	}

	public String getCondicao() {
		return condicao;
	}

	public void setCondicao(String condicao) {
		this.condicao = condicao;
	}

		
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.sose.entity.laudoTecnico;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.Hibernate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.beans.factory.annotation.Configurable;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.orcamento.Orcamento;
import br.com.sose.entity.recebimento.OrdemServico;
import br.com.sose.entity.reparo.Reparo;
import br.com.sose.status.laudotecnico.StatusLaudoTecnico;

@Entity
@Configurable
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LaudoTecnico implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "controle")
	private String controle;

	@Column(name = "data_criacao")
	private Date dataCriacao;
	
	@Column(name = "data_inicio")
	private Date dataInicio;

	@Column(name = "data_fim")
	private Date dataFim;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "realizador_id", referencedColumnName = "id")
	private Usuario realizadoPor;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "criador_id", referencedColumnName = "id")
	private Usuario criadoPor;
	
	@Column(name = "informacao_tecnica", length=1000)
	private String informacaoTecnica;
	
	@Column(name = "status")
	private String statusString;
	
	@Column(name = "descricao")
	private String descricao;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "ordem_servico_id", referencedColumnName = "id")
	private OrdemServico ordemServico;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "reparo_id", referencedColumnName = "id")
	private Reparo reparo;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "orcamento_id", referencedColumnName = "id")
	private Orcamento orcamento;
	
	@Transient
	private StatusLaudoTecnico status;
	
	private String numeroOrdemServico;
	
	private String numeroOrdemServicoPai;
	
	private String cliente;
	
	private String unidade;
	
	private String serieFabricante;
	
	private String serieCliente;
	
	private String laboratorio;
	
	private String tecnico;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getControle() {
		return controle;
	}

	public void setControle(String controle) {
		this.controle = controle;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public Usuario getRealizadoPor() {
		if(!Hibernate.isInitialized(realizadoPor)){
			realizadoPor = null;	
		}
		return realizadoPor;
	}

	public void setRealizadoPor(Usuario realizadoPor) {
		this.realizadoPor = realizadoPor;
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

	public String getInformacaoTecnica() {
		return informacaoTecnica;
	}

	public void setInformacaoTecnica(String informacaoTecnica) {
		this.informacaoTecnica = informacaoTecnica;
	}

	public String getStatusString() {
		return statusString;
	}

	public void setStatusString(String statusString) {
		this.statusString = statusString;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public OrdemServico getOrdemServico() {
		if(!Hibernate.isInitialized(ordemServico)){
			ordemServico = null;	
		}
		return ordemServico;
	}

	public void setOrdemServico(OrdemServico ordemServico) {
		this.ordemServico = ordemServico;
	}

	public StatusLaudoTecnico getStatus() {
		return status;
	}

	public void setStatus(StatusLaudoTecnico status) {
		this.status = status;
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

	public String getNumeroOrdemServico() {
		return numeroOrdemServico;
	}

	public void setNumeroOrdemServico(String numeroOrdemServico) {
		this.numeroOrdemServico = numeroOrdemServico;
	}

	public String getNumeroOrdemServicoPai() {
		return numeroOrdemServicoPai;
	}

	public void setNumeroOrdemServicoPai(String numeroOrdemServicoPai) {
		this.numeroOrdemServicoPai = numeroOrdemServicoPai;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public String getSerieFabricante() {
		return serieFabricante;
	}

	public void setSerieFabricante(String serieFabricante) {
		this.serieFabricante = serieFabricante;
	}

	public String getSerieCliente() {
		return serieCliente;
	}

	public void setSerieCliente(String serieCliente) {
		this.serieCliente = serieCliente;
	}

	public String getLaboratorio() {
		return laboratorio;
	}

	public void setLaboratorio(String laboratorio) {
		this.laboratorio = laboratorio;
	}

	public String getTecnico() {
		return tecnico;
	}

	public void setTecnico(String tecnico) {
		this.tecnico = tecnico;
	}

	public LaudoTecnico(Long id, String controle, String statusString,
			String numeroOrdemServico, String numeroOrdemServicoPai,
			String cliente, String unidade, String serieFabricante,
			String serieCliente, String laboratorio, String tecnico) {
		super();
		this.id = id;
		this.controle = controle;
		this.statusString = statusString;
		this.numeroOrdemServico = numeroOrdemServico;
		this.numeroOrdemServicoPai = numeroOrdemServicoPai;
		this.cliente = cliente;
		this.unidade = unidade;
		this.serieFabricante = serieFabricante;
		this.serieCliente = serieCliente;
		this.laboratorio = laboratorio;
		this.tecnico = tecnico;
	}

	public LaudoTecnico() {
	
	}

}

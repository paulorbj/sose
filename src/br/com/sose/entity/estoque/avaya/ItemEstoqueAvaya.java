package br.com.sose.entity.estoque.avaya;

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
import javax.persistence.Transient;

import org.hibernate.Hibernate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.beans.factory.annotation.Configurable;

import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.recebimento.OrdemServico;
import br.com.sose.status.estoque.avaya.StatusEstoqueAvaya;

@Entity
@Configurable
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ItemEstoqueAvaya implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "os_original_id", referencedColumnName = "id")
	private OrdemServico ordemServicoOriginal;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "os_substituida_id", referencedColumnName = "id")
	private OrdemServico ordemServicoSubstituida;
	
	private Date dataEntrada;
	
	private Date dataSaida;
	
	private Date dataOperacao;
	
	private String posicao;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "usuario_operacao_id", referencedColumnName = "id")
	private Usuario operacaoRealizadaPor;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "usuario_entrada_id", referencedColumnName = "id")
	private Usuario entradaRealizadaPor;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "usuario_saida_id", referencedColumnName = "id")
	private Usuario saidaRealizadaPor;
	
	private String statusString;
	
	@Transient
	private StatusEstoqueAvaya status;
	
	//Atributos de tela
	private String unidade;
	private String numeroOsOriginal;
	private String nsFabricanteOriginal;
	private String nsClienteOriginal;
	private String nfOriginal;
	private String numeroOsSubstituida;
	private String nsFabricanteSubstituida;
	private String nsClienteSubstituida;
	private String nfSubstituida;
	private String usuarioOperacao;
	private Long idOsOrig;
	private Long idOsSubst;
	
	public ItemEstoqueAvaya() {
	}
	
	
	
	
	public ItemEstoqueAvaya(Long id, Date dataOperacao, String posicao,
			String statusString, String unidade, String numeroOsOriginal,
			String nsFabricanteOriginal, String nsClienteOriginal,
			String nfOriginal, String numeroOsSubstituida,
			String nsFabricanteSubstituida, String nsClienteSubstituida,
			String nfSubstituida, String usuarioOperacao,Long idOsOrig, Long idOsSubst) {
		super();
		this.id = id;
		this.dataOperacao = dataOperacao;
		this.posicao = posicao;
		this.statusString = statusString;
		this.unidade = unidade;
		this.numeroOsOriginal = numeroOsOriginal;
		this.nsFabricanteOriginal = nsFabricanteOriginal;
		this.nsClienteOriginal = nsClienteOriginal;
		this.nfOriginal = nfOriginal;
		this.numeroOsSubstituida = numeroOsSubstituida;
		this.nsFabricanteSubstituida = nsFabricanteSubstituida;
		this.nsClienteSubstituida = nsClienteSubstituida;
		this.nfSubstituida = nfSubstituida;
		this.usuarioOperacao = usuarioOperacao;
		this.idOsOrig = idOsOrig;
		this.idOsSubst = idOsSubst;
	}




	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OrdemServico getOrdemServicoOriginal() {
		if(!Hibernate.isInitialized(ordemServicoOriginal)){
			ordemServicoOriginal = null;	
		}
		return ordemServicoOriginal;
	}

	public void setOrdemServicoOriginal(OrdemServico ordemServicoOriginal) {
		this.ordemServicoOriginal = ordemServicoOriginal;
		numeroOsOriginal = ordemServicoOriginal.getNumeroOrdemServico();
		nsFabricanteOriginal = ordemServicoOriginal.getSerieFabricante();
		nsClienteOriginal = ordemServicoOriginal.getSerieCliente();
		nfOriginal = ordemServicoOriginal.getNotaFiscal().getNumero();
		unidade = ordemServicoOriginal.getUnidade().getNome();
	}

	public OrdemServico getOrdemServicoSubstituida() {
		if(!Hibernate.isInitialized(ordemServicoSubstituida)){
			ordemServicoSubstituida = null;	
		}
		return ordemServicoSubstituida;
	}

	public void setOrdemServicoSubstituida(OrdemServico ordemServicoSubstituida) {
		this.ordemServicoSubstituida = ordemServicoSubstituida;
		numeroOsSubstituida = ordemServicoSubstituida.getNumeroOrdemServico();
		nsFabricanteSubstituida = ordemServicoSubstituida.getSerieFabricante();
		nsClienteSubstituida = ordemServicoSubstituida.getSerieCliente();
		nfSubstituida = ordemServicoSubstituida.getNotaFiscal().getNumero();
	}

	public Date getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(Date dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public Date getDataSaida() {
		return dataSaida;
	}

	public void setDataSaida(Date dataSaida) {
		this.dataSaida = dataSaida;
	}

	public Date getDataOperacao() {
		return dataOperacao;
	}

	public void setDataOperacao(Date dataOperacao) {
		this.dataOperacao = dataOperacao;
	}

	public String getPosicao() {
		return posicao;
	}

	public void setPosicao(String posicao) {
		this.posicao = posicao;
	}

	public Usuario getOperacaoRealizadaPor() {
		if(!Hibernate.isInitialized(operacaoRealizadaPor)){
			operacaoRealizadaPor = null;	
		}
		return operacaoRealizadaPor;
	}

	public void setOperacaoRealizadaPor(Usuario operacaoRealizadaPor) {
		this.operacaoRealizadaPor = operacaoRealizadaPor;
	}

	public Usuario getEntradaRealizadaPor() {
		if(!Hibernate.isInitialized(entradaRealizadaPor)){
			entradaRealizadaPor = null;	
		}
		return entradaRealizadaPor;
	}

	public void setEntradaRealizadaPor(Usuario entradaRealizadaPor) {
		this.entradaRealizadaPor = entradaRealizadaPor;
	}

	public Usuario getSaidaRealizadaPor() {
		if(!Hibernate.isInitialized(saidaRealizadaPor)){
			saidaRealizadaPor = null;	
		}
		return saidaRealizadaPor;
	}

	public void setSaidaRealizadaPor(Usuario saidaRealizadaPor) {
		this.saidaRealizadaPor = saidaRealizadaPor;
	}

	public String getStatusString() {
		return statusString;
	}

	public void setStatusString(String statusString) {
		this.statusString = statusString;
	}

	public StatusEstoqueAvaya getStatus() {
		return status;
	}

	public void setStatus(StatusEstoqueAvaya status) {
		this.status = status;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public String getNumeroOsOriginal() {
		return numeroOsOriginal;
	}

	public void setNumeroOsOriginal(String numeroOsOriginal) {
		this.numeroOsOriginal = numeroOsOriginal;
	}

	public String getNsFabricanteOriginal() {
		return nsFabricanteOriginal;
	}

	public void setNsFabricanteOriginal(String nsFabricanteOriginal) {
		this.nsFabricanteOriginal = nsFabricanteOriginal;
	}

	public String getNsClienteOriginal() {
		return nsClienteOriginal;
	}

	public void setNsClienteOriginal(String nsClienteOriginal) {
		this.nsClienteOriginal = nsClienteOriginal;
	}

	public String getNfOriginal() {
		return nfOriginal;
	}

	public void setNfOriginal(String nfOriginal) {
		this.nfOriginal = nfOriginal;
	}

	public String getNumeroOsSubstituida() {
		return numeroOsSubstituida;
	}

	public void setNumeroOsSubstituida(String numeroOsSubstituida) {
		this.numeroOsSubstituida = numeroOsSubstituida;
	}

	public String getNsFabricanteSubstituida() {
		return nsFabricanteSubstituida;
	}

	public void setNsFabricanteSubstituida(String nsFabricanteSubstituida) {
		this.nsFabricanteSubstituida = nsFabricanteSubstituida;
	}

	public String getNsClienteSubstituida() {
		return nsClienteSubstituida;
	}

	public void setNsClienteSubstituida(String nsClienteSubstituida) {
		this.nsClienteSubstituida = nsClienteSubstituida;
	}

	public String getNfSubstituida() {
		return nfSubstituida;
	}

	public void setNfSubstituida(String nfSubstituida) {
		this.nfSubstituida = nfSubstituida;
	}



	public String getUsuarioOperacao() {
		return usuarioOperacao;
	}



	public void setUsuarioOperacao(String usuarioOperacao) {
		this.usuarioOperacao = usuarioOperacao;
	}




	public Long getIdOsOrig() {
		return idOsOrig;
	}




	public void setIdOsOrig(Long idOsOrig) {
		this.idOsOrig = idOsOrig;
	}




	public Long getIdOsSubst() {
		return idOsSubst;
	}




	public void setIdOsSubst(Long idOsSubst) {
		this.idOsSubst = idOsSubst;
	}


}

package br.com.sose.entity.recebimento;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Cacheable;
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
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import org.hibernate.Hibernate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.beans.factory.annotation.Configurable;

import br.com.sose.entity.admistrativo.Lpu;
import br.com.sose.entity.admistrativo.Unidade;
import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.admistrativo.parceiros.Pessoa;
import br.com.sose.entity.expedicao.NotaFiscalRemessa;
import br.com.sose.entity.faturamento.Faturamento;
import br.com.sose.entity.orcamento.Orcamento;
import br.com.sose.entity.orcamento.OrcamentoDiferenciado;
import br.com.sose.entity.proposta.Proposta;
import br.com.sose.entity.reparo.Reparo;
import br.com.sose.status.aplicacao.StatusAplicacao;

@Entity
@Configurable
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OrdemServico implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "id_antigo")
	private Long idAntigo;

	@Column(name = "numero_ordem_servico")
	private String numeroOrdemServico;

	@Column(name = "numero_serie_cliente")
	private String serieCliente;

	@Column(name = "numero_serie_fabricante")
	private String serieFabricante;

	private String caseAvaya;

	private String clienteAvaya;

	private String ordemServicoCliente;

	@Column(name = "observacao", length=3000)
	private String observacao;

	@Column(name = "observacaoExpedicao", length=3000)
	private String observacaoExpedicao;

	@Column(name = "data_abertura")
	private Date dataAbertura;

	@Column(name = "data_finalizacao")
	private Date dataFinalizacao;

	private Boolean garantia;

	private Date dataGarantiaAte;

	private Boolean estenderGarantia;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "os_garantia_id", referencedColumnName = "id")
	private OrdemServico osGarantia;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "item_nota_fiscal_id", referencedColumnName = "id")
	private ItemNotaFiscal itemNotaFiscal;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "nota_fiscal_id", referencedColumnName = "id")
	private NotaFiscal notaFiscal;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "nota_fiscal_saida_id", referencedColumnName = "id")
	private NotaFiscalRemessa notaFiscalSaida;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "cliente_id", referencedColumnName = "id")
	private Pessoa cliente;


	@OneToMany(mappedBy = "unidadePai",fetch=FetchType.LAZY)
	@OrderBy(value="id")
	private Set<OrdemServico> placasFilhas;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "unidade_pai_id", referencedColumnName = "id")
	private OrdemServico unidadePai;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "unidade_id", referencedColumnName = "id")
	private Unidade unidade;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "lpu_id", referencedColumnName = "id")
	private Lpu lpu;

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "orcamento_id", referencedColumnName = "id")
	private Orcamento orcamento;

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "reparo_id", referencedColumnName = "id")
	private Reparo reparo;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "proposta_id", referencedColumnName = "id")
	private Proposta proposta;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "faturamento_id", referencedColumnName = "id")
	private Faturamento faturamento;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "orcamento_diferenciado_id", referencedColumnName = "id")
	private OrcamentoDiferenciado orcamentoDiferenciado;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "gerada_por", referencedColumnName = "id")
	private Usuario usuario;

	@Column(name="status")
	private String statusString;

	private Integer bloqueado;

	@Transient
	private StatusAplicacao status;

	private BigDecimal valorSistema;

	private BigDecimal valorFaturado;

	private String origemFaturamento;

	private Date dataConhecimentoExpedicao;

	private String numeroOrdemServicoPai;

	private String nomeCliente;

	private String nomeUnidade;

	private String nomeFabricante;

	private String nomeUnidadeLpu;

	private String nomeLaboratorio;

	private String nomePrestadorServico;

	private String numeroNotaFiscal;

	private Date dataNotaFiscal;

	private Date dataCriacaoNotaFiscal;

	private Date dataChegadaNotaFiscal;

	private BigDecimal valorUnitario;

	private String numeroProposta;

	private Date dataCriacaoProposta;

	private Boolean isAprovado;

	private String numeroNotaFiscalSaida;

	private Date dataEmissaoNotaFiscalSaida;

	private String condicaoReparo;

	private String condicaoOrcamento;

	private Long idCliente;

	private Long idOrcamento;
	
	private Long idReparo;
	
	private Long idNotaFiscal;
	
	private BigDecimal freteUnitarioProposta;
	
	private BigDecimal freteUnitarioExpedicao;
	
	@Transient
	private String observacaoConsulta;

	public OrdemServico() {

	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OrdemServico(Long id, String numeroOrdemServico,
			String serieCliente, String serieFabricante, String caseAvaya,
			String clienteAvaya, String numeroOrdemServicoPai,
			String nomeUnidade, String numeroNotaFiscal, 
			Long idReparo, Long idOrcamento, Long idNotaFiscal) {
		super();
		this.id = id;
		this.numeroOrdemServico = numeroOrdemServico;
		this.serieCliente = serieCliente;
		this.serieFabricante = serieFabricante;
		this.caseAvaya = caseAvaya;
		this.clienteAvaya = clienteAvaya;
		this.numeroOrdemServicoPai = numeroOrdemServicoPai;
		this.nomeUnidade = nomeUnidade;
		this.numeroNotaFiscal = numeroNotaFiscal;
		this.idReparo = idReparo;
		this.idOrcamento = idOrcamento;
		this.idNotaFiscal = idNotaFiscal;
	}
	
	public OrdemServico(Long id, String numeroOrdemServico,
			String serieCliente, String serieFabricante, String caseAvaya,
			String clienteAvaya, String numeroOrdemServicoPai,
			String nomeUnidade, String numeroNotaFiscal, 
			Long idReparo, Long idOrcamento, Long idNotaFiscal, String nomeLaboratorio) {
		super();
		this.id = id;
		this.numeroOrdemServico = numeroOrdemServico;
		this.serieCliente = serieCliente;
		this.serieFabricante = serieFabricante;
		this.caseAvaya = caseAvaya;
		this.clienteAvaya = clienteAvaya;
		this.numeroOrdemServicoPai = numeroOrdemServicoPai;
		this.nomeUnidade = nomeUnidade;
		this.numeroNotaFiscal = numeroNotaFiscal;
		this.idReparo = idReparo;
		this.idOrcamento = idOrcamento;
		this.idNotaFiscal = idNotaFiscal;
		this.nomeLaboratorio = nomeLaboratorio;
	}

	public OrdemServico(Long id, String numeroOrdemServico,
			String serieCliente, String serieFabricante, String caseAvaya,
			String clienteAvaya, String numeroOrdemServicoPai,
			String nomeUnidade, String numeroNotaFiscal, Boolean garantia,
			String condicaoOrcamento, String condicaoReparo,Long idCliente, String nomeCliente) {
		super();
		this.id = id;
		this.numeroOrdemServico = numeroOrdemServico;
		this.serieCliente = serieCliente;
		this.serieFabricante = serieFabricante;
		this.caseAvaya = caseAvaya;
		this.clienteAvaya = clienteAvaya;
		this.numeroOrdemServicoPai = numeroOrdemServicoPai;
		this.nomeUnidade = nomeUnidade;
		this.numeroNotaFiscal = numeroNotaFiscal;
		this.garantia = garantia;
		this.condicaoOrcamento = condicaoOrcamento;
		this.condicaoReparo = condicaoReparo;
		this.idCliente = idCliente;
		this.nomeCliente = nomeCliente;
	}



	public String getSerieCliente() {
		if(serieCliente != null){
			return serieCliente.trim();
		}
		return serieCliente;
	}

	public void setSerieCliente(String serieCliente) {
		if(serieCliente != null)
			serieCliente = serieCliente.trim();
		this.serieCliente = serieCliente;
	}

	public String getSerieFabricante() {
		if(serieFabricante != null){
			return serieFabricante.trim();
		}
		return serieFabricante;
	}

	public void setSerieFabricante(String serieFabricante) {
		if(serieFabricante != null)
			serieFabricante = serieFabricante.trim();
		this.serieFabricante = serieFabricante;
	}

	public String getOrdemServicoCliente() {
		return ordemServicoCliente;
	}

	public void setOrdemServicoCliente(String ordemServicoCliente) {
		this.ordemServicoCliente = ordemServicoCliente;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getObservacaoExpedicao() {
		return observacaoExpedicao;
	}

	public void setObservacaoExpedicao(String observacaoExpedicao) {
		this.observacaoExpedicao = observacaoExpedicao;
	}

	public Date getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(Date dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public Date getDataFinalizacao() {
		return dataFinalizacao;
	}

	public void setDataFinalizacao(Date dataFinalizacao) {
		this.dataFinalizacao = dataFinalizacao;
	}

	public Boolean getGarantia() {
		return garantia;
	}

	public void setGarantia(Boolean garantia) {
		this.garantia = garantia;
	}

	public ItemNotaFiscal getItemNotaFiscal() {
		if(!Hibernate.isInitialized(itemNotaFiscal)){
			itemNotaFiscal = null;	
		}
		return itemNotaFiscal;
	}

	public void setItemNotaFiscal(ItemNotaFiscal itemNotaFiscal) {
		this.itemNotaFiscal = itemNotaFiscal;
	}

	public NotaFiscal getNotaFiscal() {
		if(!Hibernate.isInitialized(notaFiscal)){
			notaFiscal = null;	
		}
		return notaFiscal;
	}

	public void setNotaFiscal(NotaFiscal notaFiscal) {
		this.notaFiscal = notaFiscal;
		if(null != notaFiscal){
			this.numeroNotaFiscal = notaFiscal.getNumero();
			this.dataNotaFiscal = notaFiscal.getDataNotaFiscal();
			this.dataChegadaNotaFiscal = notaFiscal.getDataChegada();
			this.dataCriacaoNotaFiscal = notaFiscal.getDataCriacao();
		}
	}

	public NotaFiscalRemessa getNotaFiscalSaida() {
		if(!Hibernate.isInitialized(notaFiscalSaida)){
			notaFiscalSaida = null;	
		}
		return notaFiscalSaida;
	}

	public void setNotaFiscalSaida(NotaFiscalRemessa notaFiscalSaida) {
		this.notaFiscalSaida = notaFiscalSaida;
	}

	public Pessoa getCliente() {
		if(!Hibernate.isInitialized(cliente) || cliente == null){
			return null;
		}
		return cliente;
	}

	public void setCliente(Pessoa cliente) {
		this.cliente = cliente;
		if(null != cliente)
			this.nomeCliente = cliente.getNomeSistema();
	}

	public Set<OrdemServico> getPlacasFilhas() {
		if(!Hibernate.isInitialized(placasFilhas) || placasFilhas == null){
			placasFilhas = new HashSet<OrdemServico>();
		}
		return placasFilhas;
	}

	public void setPlacasFilhas(Set<OrdemServico> placasFilhas) {
		this.placasFilhas = placasFilhas;
	}

	public OrdemServico getUnidadePai() {
		if(!Hibernate.isInitialized(unidadePai)){
			unidadePai = null;	
		}
		return unidadePai;
	}

	public void setUnidadePai(OrdemServico unidadePai) {
		this.unidadePai = unidadePai;
		if(null != unidadePai){
			this.numeroOrdemServicoPai = unidadePai.numeroOrdemServico;
		}
	}

	public Unidade getUnidade() {
		if(!Hibernate.isInitialized(unidade) || unidade == null){
			return null;
		}
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
		if(null != unidade){
			this.nomeUnidade = unidade.getNome();
			this.nomeFabricante = unidade.getFabricante() != null ? unidade.getFabricante().getNome() : "";
			this.nomeLaboratorio = unidade.getLaboratorio() != null ? unidade.getLaboratorio().getNome() : "";
			this.nomePrestadorServico = unidade.getPrestadorServicoExterno() != null ? unidade.getPrestadorServicoExterno().getNomeSistema() : "";
		}
	}

	public Lpu getLpu() {
		return lpu;
	}

	public void setLpu(Lpu lpu) {
		this.lpu = lpu;
		if(null != lpu){
			//TODO - verificar LPU
			//this.nomeUnidadeLpu = lpu.getUnidade();
		}else{
			this.nomeUnidadeLpu = "";
		}
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

	public Reparo getReparo() {
		if(!Hibernate.isInitialized(reparo)){
			reparo = null;	
		}
		return reparo;
	}

	public void setReparo(Reparo reparo) {
		this.reparo = reparo;
	}

	public Proposta getProposta() {
		if(!Hibernate.isInitialized(proposta)){
			proposta = null;	
		}
		return proposta;
	}

	public void setProposta(Proposta proposta) {
		this.proposta = proposta;
		if(null != proposta)
			this.numeroProposta = proposta.getNumero();
	}

	public Faturamento getFaturamento() {
		if(!Hibernate.isInitialized(faturamento)){
			faturamento = null;	
		}
		return faturamento;
	}

	public void setFaturamento(Faturamento faturamento) {
		this.faturamento = faturamento;
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

	public String getStatusString() {
		return statusString;
	}

	public void setStatusString(String statusString) {
		this.statusString = statusString;
	}

	public StatusAplicacao getStatus() {
		return status;
	}

	public void setStatus(StatusAplicacao status) {
		this.status = status;
		this.statusString = this.status.getNome();
	}

	public String getNumeroOrdemServico() {
		if(numeroOrdemServico != null){
			numeroOrdemServico = numeroOrdemServico.trim();
		}
		return numeroOrdemServico;
	}

	public void setNumeroOrdemServico(String numeroOrdemServico) {
		this.numeroOrdemServico = numeroOrdemServico;
	}

	public OrdemServico getOsGarantia() {
		if(!Hibernate.isInitialized(osGarantia)){
			osGarantia = null;	
		}
		return osGarantia;
	}

	public void setOsGarantia(OrdemServico osGarantia) {
		this.osGarantia = osGarantia;
	}

	public OrcamentoDiferenciado getOrcamentoDiferenciado() {
		if(!Hibernate.isInitialized(orcamentoDiferenciado)){
			orcamentoDiferenciado = null;	
		}
		return orcamentoDiferenciado;
	}

	public void setOrcamentoDiferenciado(OrcamentoDiferenciado orcamentoDiferenciado) {
		this.orcamentoDiferenciado = orcamentoDiferenciado;
	}

	public Integer getBloqueado() {
		return bloqueado;
	}

	public void setBloqueado(Integer bloqueado) {
		this.bloqueado = bloqueado;
	}

	public Date getDataGarantiaAte() {
		return dataGarantiaAte;
	}

	public void setDataGarantiaAte(Date dataGarantiaAte) {
		this.dataGarantiaAte = dataGarantiaAte;
	}

	public Boolean getEstenderGarantia() {
		return estenderGarantia;
	}

	public void setEstenderGarantia(Boolean estenderGarantia) {
		this.estenderGarantia = estenderGarantia;
	}

	public BigDecimal getValorFaturado() {
		return valorFaturado;
	}

	public void setValorFaturado(BigDecimal valorFaturado) {
		this.valorFaturado = valorFaturado;
	}

	public String getOrigemFaturamento() {
		return origemFaturamento;
	}

	public void setOrigemFaturamento(String origemFaturamento) {
		this.origemFaturamento = origemFaturamento;
	}

	public BigDecimal getValorSistema() {
		return valorSistema;
	}

	public void setValorSistema(BigDecimal valorSistema) {
		this.valorSistema = valorSistema;
	}

	public Date getDataConhecimentoExpedicao() {
		return dataConhecimentoExpedicao;
	}

	public void setDataConhecimentoExpedicao(Date dataConhecimentoExpedicao) {
		this.dataConhecimentoExpedicao = dataConhecimentoExpedicao;
	}

	public String getCaseAvaya() {
		return caseAvaya;
	}

	public void setCaseAvaya(String caseAvaya) {
		this.caseAvaya = caseAvaya;
	}

	public String getClienteAvaya() {
		return clienteAvaya;
	}

	public void setClienteAvaya(String clienteAvaya) {
		this.clienteAvaya = clienteAvaya;
	}

	public String getNumeroOrdemServicoPai() {
		return numeroOrdemServicoPai;
	}

	public void setNumeroOrdemServicoPai(String numeroOrdemServicoPai) {
		this.numeroOrdemServicoPai = numeroOrdemServicoPai;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getNomeUnidade() {
		return nomeUnidade;
	}

	public void setNomeUnidade(String nomeUnidade) {
		this.nomeUnidade = nomeUnidade;
	}

	public String getNomeFabricante() {
		return nomeFabricante;
	}

	public void setNomeFabricante(String nomeFabricante) {
		this.nomeFabricante = nomeFabricante;
	}

	public String getNomeUnidadeLpu() {
		return nomeUnidadeLpu;
	}

	public void setNomeUnidadeLpu(String nomeUnidadeLpu) {
		this.nomeUnidadeLpu = nomeUnidadeLpu;
	}

	public String getNomeLaboratorio() {
		return nomeLaboratorio;
	}

	public void setNomeLaboratorio(String nomeLaboratorio) {
		this.nomeLaboratorio = nomeLaboratorio;
	}

	public String getNomePrestadorServico() {
		return nomePrestadorServico;
	}

	public void setNomePrestadorServico(String nomePrestadorServico) {
		this.nomePrestadorServico = nomePrestadorServico;
	}

	public String getNumeroNotaFiscal() {
		return numeroNotaFiscal;
	}

	public void setNumeroNotaFiscal(String numeroNotaFiscal) {
		this.numeroNotaFiscal = numeroNotaFiscal;
	}

	public Date getDataNotaFiscal() {
		return dataNotaFiscal;
	}

	public void setDataNotaFiscal(Date dataNotaFiscal) {
		this.dataNotaFiscal = dataNotaFiscal;
	}

	public Date getDataCriacaoNotaFiscal() {
		return dataCriacaoNotaFiscal;
	}

	public void setDataCriacaoNotaFiscal(Date dataCriacaoNotaFiscal) {
		this.dataCriacaoNotaFiscal = dataCriacaoNotaFiscal;
	}

	public Date getDataChegadaNotaFiscal() {
		return dataChegadaNotaFiscal;
	}

	public void setDataChegadaNotaFiscal(Date dataChegadaNotaFiscal) {
		this.dataChegadaNotaFiscal = dataChegadaNotaFiscal;
	}

	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public String getNumeroProposta() {
		return numeroProposta;
	}

	public void setNumeroProposta(String numeroProposta) {
		this.numeroProposta = numeroProposta;
	}

	public Date getDataCriacaoProposta() {
		return dataCriacaoProposta;
	}

	public void setDataCriacaoProposta(Date dataCriacaoProposta) {
		this.dataCriacaoProposta = dataCriacaoProposta;
	}

	public Boolean getIsAprovado() {
		return isAprovado;
	}

	public void setIsAprovado(Boolean isAprovado) {
		this.isAprovado = isAprovado;
	}

	public String getNumeroNotaFiscalSaida() {
		return numeroNotaFiscalSaida;
	}

	public void setNumeroNotaFiscalSaida(String numeroNotaFiscalSaida) {
		this.numeroNotaFiscalSaida = numeroNotaFiscalSaida;
	}

	public Date getDataEmissaoNotaFiscalSaida() {
		return dataEmissaoNotaFiscalSaida;
	}

	public void setDataEmissaoNotaFiscalSaida(Date dataEmissaoNotaFiscalSaida) {
		this.dataEmissaoNotaFiscalSaida = dataEmissaoNotaFiscalSaida;
	}

	public Long getIdAntigo() {
		return idAntigo;
	}

	public void setIdAntigo(Long idAntigo) {
		this.idAntigo = idAntigo;
	}



	public String getCondicaoReparo() {
		return condicaoReparo;
	}



	public void setCondicaoReparo(String condicaoReparo) {
		this.condicaoReparo = condicaoReparo;
	}



	public String getCondicaoOrcamento() {
		return condicaoOrcamento;
	}



	public void setCondicaoOrcamento(String condicaoOrcamento) {
		this.condicaoOrcamento = condicaoOrcamento;
	}



	public Long getIdCliente() {
		return idCliente;
	}



	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}



	public Long getIdOrcamento() {
		return idOrcamento;
	}



	public void setIdOrcamento(Long idOrcamento) {
		this.idOrcamento = idOrcamento;
	}



	public Long getIdReparo() {
		return idReparo;
	}



	public void setIdReparo(Long idReparo) {
		this.idReparo = idReparo;
	}



	public Long getIdNotaFiscal() {
		return idNotaFiscal;
	}



	public void setIdNotaFiscal(Long idNotaFiscal) {
		this.idNotaFiscal = idNotaFiscal;
	}



	public BigDecimal getFreteUnitarioProposta() {
		return freteUnitarioProposta;
	}



	public void setFreteUnitarioProposta(BigDecimal freteUnitarioProposta) {
		this.freteUnitarioProposta = freteUnitarioProposta;
	}



	public BigDecimal getFreteUnitarioExpedicao() {
		return freteUnitarioExpedicao;
	}



	public void setFreteUnitarioExpedicao(BigDecimal freteUnitarioExpedicao) {
		this.freteUnitarioExpedicao = freteUnitarioExpedicao;
	}



	public String getObservacaoConsulta() {
		return observacaoConsulta;
	}



	public void setObservacaoConsulta(String observacaoConsulta) {
		this.observacaoConsulta = observacaoConsulta;
	}


}

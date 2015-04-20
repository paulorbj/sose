package br.com.sose.to;

import java.util.Date;

public class ConsultaTO {

	private Long id;
	
	private String nOs;
	
	private String nOsPai;
	
	private Long idUnidadePai;
	
	private Boolean garantia;
	
	private Long idLpu;
	
	private String propostaTipo;
		
	private String status;
	
	private String reparoCondicao;
	
	private Date reparoDtFim;
	
	private String orcamentoCondicao;
	
	private Date orcamentoDtFim;
	
	private String cliente;
	
	private String unidade;
	
	private String nSerieFabricante;
	
	private String nSerieCliente;
	
	private String nNf;
	
	private String laboratorio;
	
	private String nProposta;
	
	private String caseAvaya;
	
	private String clienteAvaya;
	
	private String nNfSaida;
	
	private Date dataChegada;
	
	private String condicao;
	
	private String origem;
	
	private Boolean isAprovado;
	
	private Date dataAprovacao;
	
	private String statusEstoque;
	
	private String posicaoEstoque;
	
	private String obsNotaFiscal;
	
	private String obsFaturamento;
	
	private String obsNotaFiscalSaida;
	
	private String obsOrcamento;
	
	private String obsProposta;
	
	private String obsReparo;
	
	private String obsOrdemServico;
	
	private Long idOrcamento;
	
	private Long idReparo;
	
	private Date dataEmissaoNFSaida;
	
	private Date dataEntradaNF;
	
	private Date dataChegadaNF;
	
	private String obsConsulta;

	public ConsultaTO() {
	}
	
	public ConsultaTO(Long id, String nOs, String nOsPai, String status,
			String cliente, String unidade, String nSerieFabricante,
			String nSerieCliente, String nNf, String laboratorio,
			String nProposta, String caseAvaya,
			String clienteAvaya, String nNfSaida, Date dataChegada) {
		super();
		this.id = id;
		this.nOs = nOs;
		this.nOsPai = nOsPai;
		this.status = status;
		this.cliente = cliente;
		this.unidade = unidade;
		this.nSerieFabricante = nSerieFabricante;
		this.nSerieCliente = nSerieCliente;
		this.nNf = nNf;
		this.laboratorio = laboratorio;
		this.nProposta = nProposta;
		this.caseAvaya = caseAvaya;
		this.clienteAvaya = clienteAvaya;
		this.nNfSaida = nNfSaida;
		this.dataChegada = dataChegada;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getnOs() {
		return nOs;
	}

	public void setnOs(String nOs) {
		this.nOs = nOs;
	}

	public String getnOsPai() {
		return nOsPai;
	}

	public void setnOsPai(String nOsPai) {
		this.nOsPai = nOsPai;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getnSerieFabricante() {
		return nSerieFabricante;
	}

	public void setnSerieFabricante(String nSerieFabricante) {
		this.nSerieFabricante = nSerieFabricante;
	}

	public String getnSerieCliente() {
		return nSerieCliente;
	}

	public void setnSerieCliente(String nSerieCliente) {
		this.nSerieCliente = nSerieCliente;
	}

	public String getnNf() {
		return nNf;
	}

	public void setnNf(String nNf) {
		this.nNf = nNf;
	}

	public String getLaboratorio() {
		return laboratorio;
	}

	public void setLaboratorio(String laboratorio) {
		this.laboratorio = laboratorio;
	}

	public String getnProposta() {
		return nProposta;
	}

	public void setnProposta(String nProposta) {
		this.nProposta = nProposta;
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

	public String getnNfSaida() {
		return nNfSaida;
	}

	public void setnNfSaida(String nNfSaida) {
		this.nNfSaida = nNfSaida;
	}

	public Date getDataChegada() {
		return dataChegada;
	}

	public void setDataChegada(Date dataChegada) {
		this.dataChegada = dataChegada;
	}

	public String getCondicao() {
		return condicao;
	}

	public void setCondicao(String condicao) {
		this.condicao = condicao;
	}

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

	public Boolean getIsAprovado() {
		return isAprovado;
	}

	public void setIsAprovado(Boolean isAprovado) {
		this.isAprovado = isAprovado;
	}

	public Date getDataAprovacao() {
		return dataAprovacao;
	}

	public void setDataAprovacao(Date dataAprovacao) {
		this.dataAprovacao = dataAprovacao;
	}

	public String getStatusEstoque() {
		return statusEstoque;
	}

	public void setStatusEstoque(String statusEstoque) {
		this.statusEstoque = statusEstoque;
	}

	public String getPosicaoEstoque() {
		return posicaoEstoque;
	}

	public void setPosicaoEstoque(String posicaoEstoque) {
		this.posicaoEstoque = posicaoEstoque;
	}

	public String getObsOrcamento() {
		return obsOrcamento;
	}

	public void setObsOrcamento(String obsOrcamento) {
		this.obsOrcamento = obsOrcamento;
	}

	public String getObsProposta() {
		return obsProposta;
	}

	public void setObsProposta(String obsProposta) {
		this.obsProposta = obsProposta;
	}

	public String getObsReparo() {
		return obsReparo;
	}

	public void setObsReparo(String obsReparo) {
		this.obsReparo = obsReparo;
	}

	public Long getIdUnidadePai() {
		return idUnidadePai;
	}

	public void setIdUnidadePai(Long idUnidadePai) {
		this.idUnidadePai = idUnidadePai;
	}

	public Boolean getGarantia() {
		return garantia;
	}

	public void setGarantia(Boolean garantia) {
		this.garantia = garantia;
	}

	public Long getIdLpu() {
		return idLpu;
	}

	public void setIdLpu(Long idLpu) {
		this.idLpu = idLpu;
	}

	public String getPropostaTipo() {
		return propostaTipo;
	}

	public void setPropostaTipo(String propostaTipo) {
		this.propostaTipo = propostaTipo;
	}

	public String getReparoCondicao() {
		return reparoCondicao;
	}

	public void setReparoCondicao(String reparoCondicao) {
		this.reparoCondicao = reparoCondicao;
	}

	public Date getReparoDtFim() {
		return reparoDtFim;
	}

	public void setReparoDtFim(Date reparoDtFim) {
		this.reparoDtFim = reparoDtFim;
	}

	public String getOrcamentoCondicao() {
		return orcamentoCondicao;
	}

	public void setOrcamentoCondicao(String orcamentoCondicao) {
		this.orcamentoCondicao = orcamentoCondicao;
	}

	public Date getOrcamentoDtFim() {
		return orcamentoDtFim;
	}

	public void setOrcamentoDtFim(Date orcamentoDtFim) {
		this.orcamentoDtFim = orcamentoDtFim;
	}

	public String getObsNotaFiscal() {
		return obsNotaFiscal;
	}

	public void setObsNotaFiscal(String obsNotaFiscal) {
		this.obsNotaFiscal = obsNotaFiscal;
	}

	public String getObsFaturamento() {
		return obsFaturamento;
	}

	public void setObsFaturamento(String obsFaturamento) {
		this.obsFaturamento = obsFaturamento;
	}

	public String getObsNotaFiscalSaida() {
		return obsNotaFiscalSaida;
	}

	public void setObsNotaFiscalSaida(String obsNotaFiscalSaida) {
		this.obsNotaFiscalSaida = obsNotaFiscalSaida;
	}

	public String getObsOrdemServico() {
		return obsOrdemServico;
	}

	public void setObsOrdemServico(String obsOrdemServico) {
		this.obsOrdemServico = obsOrdemServico;
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

	public Date getDataEmissaoNFSaida() {
		return dataEmissaoNFSaida;
	}

	public void setDataEmissaoNFSaida(Date dataEmissaoNFSaida) {
		this.dataEmissaoNFSaida = dataEmissaoNFSaida;
	}

	public Date getDataEntradaNF() {
		return dataEntradaNF;
	}

	public void setDataEntradaNF(Date dataEntradaNF) {
		this.dataEntradaNF = dataEntradaNF;
	}

	public Date getDataChegadaNF() {
		return dataChegadaNF;
	}

	public void setDataChegadaNF(Date dataChegadaNF) {
		this.dataChegadaNF = dataChegadaNF;
	}

	public String getObsConsulta() {
		return obsConsulta;
	}

	public void setObsConsulta(String obsConsulta) {
		this.obsConsulta = obsConsulta;
	}
	
	
}

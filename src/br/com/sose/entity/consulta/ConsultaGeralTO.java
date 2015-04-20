package br.com.sose.entity.consulta;

import java.math.BigDecimal;
import java.util.Date;

public class ConsultaGeralTO {
	
	private String numeroOrdemServico;
	private String numeroOrdemServicoPai;
	private String status;
	private String cliente;
	private String unidade;
	private String nsFabricante;
	private String nsCliente;
	private String nOsCliente;
	private String fabricante;
	private String lpu;
	private String laboratorio;
	private String prestadorServico;
	private String nNfEntrada;
	private Date dtNfEmissao;
	private Date dtNfEntrada;
	private Date dtChegada;
	private BigDecimal vUnitario;
	private String clienteAvaya;
	private String caseAvaya;
	private String nProposta;
	private Date dtProposta;
	private Boolean isPropostaAprovada;
	private Date dtAprovacao;
	private String nNfSaida;
	private Date dtNfSaida;
	private String obsRecebimento;
	private String obsOrcamento;
	private String obsProposta;
	private String obsReparo;
	private String obsExpedicao;
	private Integer diasServilogi;
	
	

	public ConsultaGeralTO(String numeroOrdemServico,
			String numeroOrdemServicoPai, String status, String cliente,
			String unidade, String nsFabricante, String nsCliente,
			String nOsCliente, String fabricante, String lpu,
			String laboratorio, String prestadorServico, String nNfEntrada,
			Date dtNfEmissao, Date dtNfEntrada,Date dtChegada,
			BigDecimal vUnitario, String clienteAvaya,String caseAvaya,
			String nProposta) {
		super();
		this.numeroOrdemServico = numeroOrdemServico;
		this.numeroOrdemServicoPai = numeroOrdemServicoPai;
		this.status = status;
		this.cliente = cliente;
		this.unidade = unidade;
		this.nsFabricante = nsFabricante;
		this.nsCliente = nsCliente;
		this.nOsCliente = nOsCliente;
		this.fabricante = fabricante;
		this.lpu = lpu;
		this.laboratorio = laboratorio;
		this.prestadorServico = prestadorServico;
		this.nNfEntrada = nNfEntrada;
		this.dtNfEmissao = dtNfEmissao;
		this.dtNfEntrada = dtNfEmissao;
		this.dtChegada = dtNfEmissao;
		this.vUnitario = vUnitario;
		this.clienteAvaya = clienteAvaya;
		this.caseAvaya = caseAvaya;
		this.nProposta = nProposta;
	}
	
	public ConsultaGeralTO() {
		// TODO Auto-generated constructor stub
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

	public String getNsFabricante() {
		return nsFabricante;
	}

	public void setNsFabricante(String nsFabricante) {
		this.nsFabricante = nsFabricante;
	}

	public String getNsCliente() {
		return nsCliente;
	}

	public void setNsCliente(String nsCliente) {
		this.nsCliente = nsCliente;
	}

	public String getnOsCliente() {
		return nOsCliente;
	}

	public void setnOsCliente(String nOsCliente) {
		this.nOsCliente = nOsCliente;
	}

	public String getFabricante() {
		return fabricante;
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

	public String getLpu() {
		return lpu;
	}

	public void setLpu(String lpu) {
		this.lpu = lpu;
	}

	public String getLaboratorio() {
		return laboratorio;
	}

	public void setLaboratorio(String laboratorio) {
		this.laboratorio = laboratorio;
	}

	public String getPrestadorServico() {
		return prestadorServico;
	}

	public void setPrestadorServico(String prestadorServico) {
		this.prestadorServico = prestadorServico;
	}

	public String getnNfEntrada() {
		return nNfEntrada;
	}

	public void setnNfEntrada(String nNfEntrada) {
		this.nNfEntrada = nNfEntrada;
	}

	public Date getDtNfEmissao() {
		return dtNfEmissao;
	}

	public void setDtNfEmissao(Date dtNfEmissao) {
		this.dtNfEmissao = dtNfEmissao;
	}

	public Date getDtNfEntrada() {
		return dtNfEntrada;
	}

	public void setDtNfEntrada(Date dtNfEntrada) {
		this.dtNfEntrada = dtNfEntrada;
	}

	public Date getDtChegada() {
		return dtChegada;
	}

	public void setDtChegada(Date dtChegada) {
		this.dtChegada = dtChegada;
	}

	public BigDecimal getvUnitario() {
		return vUnitario;
	}

	public void setvUnitario(BigDecimal vUnitario) {
		this.vUnitario = vUnitario;
	}

	public String getClienteAvaya() {
		return clienteAvaya;
	}

	public void setClienteAvaya(String clienteAvaya) {
		this.clienteAvaya = clienteAvaya;
	}

	public String getCaseAvaya() {
		return caseAvaya;
	}

	public void setCaseAvaya(String caseAvaya) {
		this.caseAvaya = caseAvaya;
	}

	public String getnProposta() {
		return nProposta;
	}

	public void setnProposta(String nProposta) {
		this.nProposta = nProposta;
	}

	public Date getDtProposta() {
		return dtProposta;
	}

	public void setDtProposta(Date dtProposta) {
		this.dtProposta = dtProposta;
	}

	public Boolean getIsPropostaAprovada() {
		return isPropostaAprovada;
	}

	public void setIsPropostaAprovada(Boolean isPropostaAprovada) {
		this.isPropostaAprovada = isPropostaAprovada;
	}

	public Date getDtAprovacao() {
		return dtAprovacao;
	}

	public void setDtAprovacao(Date dtAprovacao) {
		this.dtAprovacao = dtAprovacao;
	}

	public String getnNfSaida() {
		return nNfSaida;
	}

	public void setnNfSaida(String nNfSaida) {
		this.nNfSaida = nNfSaida;
	}

	public Date getDtNfSaida() {
		return dtNfSaida;
	}

	public void setDtNfSaida(Date dtNfSaida) {
		this.dtNfSaida = dtNfSaida;
	}

	public String getObsRecebimento() {
		return obsRecebimento;
	}

	public void setObsRecebimento(String obsRecebimento) {
		this.obsRecebimento = obsRecebimento;
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

	public String getObsExpedicao() {
		return obsExpedicao;
	}

	public void setObsExpedicao(String obsExpedicao) {
		this.obsExpedicao = obsExpedicao;
	}

	public Integer getDiasServilogi() {
		return diasServilogi;
	}

	public void setDiasServilogi(Integer diasServilogi) {
		this.diasServilogi = diasServilogi;
	}
}

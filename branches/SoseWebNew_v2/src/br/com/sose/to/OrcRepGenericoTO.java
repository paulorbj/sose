package br.com.sose.to;

import java.io.Serializable;
import java.util.Date;

public class OrcRepGenericoTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String finalidade;

	private String statusString;

	private String tecnico;

	private String numeroOrdemServico;

	private String numeroOrdemServicoPai;

	private String cliente;

	private String unidade;

	private String numeroNotaFiscal;

	private String numeroSerieFabricante;

	private String numeroSerieCliente;

	private String laboratorio;

	private Date dataPrioridade;

	private Date dataChegadaServilogi;

	private Date dataEntradaReparo;

	private Date laudoTecnicoAprovado;

	private Date laudoTecnicoReprovado;

	private Date propostaAprovada;

	private Date propostaReprovada;

	private Date orcamentoDiferenciadoRejeitado;

	private Date rejeitadoPeloLider;

	private Long idOrcRep;

	private Long idTecnicoResponsavel;
	
	private Date dataLimite;
	
	private Integer prazoDevolucao;
	
	private Integer bloqueado;
	
	private String caseAvaya;
	
	private String clienteAvaya;
	
	private String condicao;
	
	private Long idLaboratorio;
	
	private Boolean componentePendente;
	
	private Boolean criadoFromOrcamento;
	
	public OrcRepGenericoTO() {
	}

	//Orcamento
	public OrcRepGenericoTO(String finalidade, String statusString,
			String tecnico, String numeroOrdemServico,
			String numeroOrdemServicoPai, String cliente, String unidade,
			String numeroNotaFiscal, String numeroSerieFabricante,
			String numeroSerieCliente, String laboratorio, Date dataPrioridade,
			Date dataChegadaServilogi, Date dataEntradaReparo,
			Date laudoTecnicoAprovado, Date laudoTecnicoReprovado,
			Date propostaAprovada, Date propostaReprovada,
			Date orcamentoDiferenciadoRejeitado,
			Long idOrcRep, Long idTecnicoResponsavel, Date dataLimite,Integer prazoDevolucao,Integer bloqueado,String caseAvaya, String clienteAvaya,String condicao, Long idLaboratorio) {
		super();
		this.finalidade = finalidade;
		this.statusString = statusString;
		this.tecnico = tecnico;
		this.numeroOrdemServico = numeroOrdemServico;
		this.numeroOrdemServicoPai = numeroOrdemServicoPai;
		this.cliente = cliente;
		this.unidade = unidade;
		this.numeroNotaFiscal = numeroNotaFiscal;
		this.numeroSerieFabricante = numeroSerieFabricante;
		this.numeroSerieCliente = numeroSerieCliente;
		this.laboratorio = laboratorio;
		this.dataPrioridade = dataPrioridade;
		this.dataChegadaServilogi = dataChegadaServilogi;
		this.dataEntradaReparo = dataEntradaReparo;
		this.laudoTecnicoAprovado = laudoTecnicoAprovado;
		this.laudoTecnicoReprovado = laudoTecnicoReprovado;
		this.propostaAprovada = propostaAprovada;
		this.propostaReprovada = propostaReprovada;
		this.orcamentoDiferenciadoRejeitado = orcamentoDiferenciadoRejeitado;
		this.idOrcRep = idOrcRep;
		this.idTecnicoResponsavel = idTecnicoResponsavel;
		this.dataLimite = dataLimite;
		this.prazoDevolucao = prazoDevolucao;
		this.bloqueado = bloqueado;
		this.caseAvaya = caseAvaya;
		this.clienteAvaya = clienteAvaya;
		this.condicao = condicao;
		this.idLaboratorio = idLaboratorio;
	}
	
	//Reparo
	public OrcRepGenericoTO(String finalidade, String statusString,
			String tecnico, String numeroOrdemServico,
			String numeroOrdemServicoPai, String cliente, String unidade,
			String numeroNotaFiscal, String numeroSerieFabricante,
			String numeroSerieCliente, String laboratorio, Date dataPrioridade,
			Date dataChegadaServilogi, Date dataEntradaReparo,
			Date laudoTecnicoAprovado, Date laudoTecnicoReprovado,
			Date propostaReprovada,
			Date rejeitadoPeloLider,
			Long idOrcRep, Long idTecnicoResponsavel, Date dataLimite, Integer prazoDevolucao, Integer bloqueado, String caseAvaya, String clienteAvaya,String condicao, Long idLaboratorio) {
		super();
		this.finalidade = finalidade;
		this.statusString = statusString;
		this.tecnico = tecnico;
		this.numeroOrdemServico = numeroOrdemServico;
		this.numeroOrdemServicoPai = numeroOrdemServicoPai;
		this.cliente = cliente;
		this.unidade = unidade;
		this.numeroNotaFiscal = numeroNotaFiscal;
		this.numeroSerieFabricante = numeroSerieFabricante;
		this.numeroSerieCliente = numeroSerieCliente;
		this.laboratorio = laboratorio;
		this.dataPrioridade = dataPrioridade;
		this.dataChegadaServilogi = dataChegadaServilogi;
		this.dataEntradaReparo = dataEntradaReparo;
		this.laudoTecnicoAprovado = laudoTecnicoAprovado;
		this.laudoTecnicoReprovado = laudoTecnicoReprovado;
		this.propostaReprovada = propostaReprovada;
		this.rejeitadoPeloLider = rejeitadoPeloLider;
		this.idOrcRep = idOrcRep;
		this.idTecnicoResponsavel = idTecnicoResponsavel;
		this.dataLimite = dataLimite;
		this.prazoDevolucao = prazoDevolucao;
		this.bloqueado = bloqueado;
		this.caseAvaya = caseAvaya;
		this.clienteAvaya = clienteAvaya;
		this.condicao = condicao;
		this.idLaboratorio = idLaboratorio;
	}

	//Reparo
	public OrcRepGenericoTO(String finalidade, String statusString,
			String tecnico, String numeroOrdemServico,
			String numeroOrdemServicoPai, String cliente, String unidade,
			String numeroNotaFiscal, String numeroSerieFabricante,
			String numeroSerieCliente, String laboratorio, Date dataPrioridade,
			Date dataChegadaServilogi, Date dataEntradaReparo,
			Date laudoTecnicoAprovado, Date laudoTecnicoReprovado,
			Date propostaAprovada, Date propostaReprovada,
			Date orcamentoDiferenciadoRejeitado,
			Long idOrcRep, Long idTecnicoResponsavel, Date dataLimite,Integer prazoDevolucao,Integer bloqueado,String caseAvaya, 
			String clienteAvaya,String condicao, Long idLaboratorio, Boolean componentePendente, Boolean criadoFromOrcamento) {
		super();
		this.finalidade = finalidade;
		this.statusString = statusString;
		this.tecnico = tecnico;
		this.numeroOrdemServico = numeroOrdemServico;
		this.numeroOrdemServicoPai = numeroOrdemServicoPai;
		this.cliente = cliente;
		this.unidade = unidade;
		this.numeroNotaFiscal = numeroNotaFiscal;
		this.numeroSerieFabricante = numeroSerieFabricante;
		this.numeroSerieCliente = numeroSerieCliente;
		this.laboratorio = laboratorio;
		this.dataPrioridade = dataPrioridade;
		this.dataChegadaServilogi = dataChegadaServilogi;
		this.dataEntradaReparo = dataEntradaReparo;
		this.laudoTecnicoAprovado = laudoTecnicoAprovado;
		this.laudoTecnicoReprovado = laudoTecnicoReprovado;
		this.propostaAprovada = propostaAprovada;
		this.propostaReprovada = propostaReprovada;
		this.orcamentoDiferenciadoRejeitado = orcamentoDiferenciadoRejeitado;
		this.idOrcRep = idOrcRep;
		this.idTecnicoResponsavel = idTecnicoResponsavel;
		this.dataLimite = dataLimite;
		this.prazoDevolucao = prazoDevolucao;
		this.bloqueado = bloqueado;
		this.caseAvaya = caseAvaya;
		this.clienteAvaya = clienteAvaya;
		this.condicao = condicao;
		this.idLaboratorio = idLaboratorio;
		this.componentePendente = componentePendente;
		this.criadoFromOrcamento = criadoFromOrcamento;
	}
	
	//Orcamento
	public OrcRepGenericoTO(String finalidade, String statusString,
			String tecnico, String numeroOrdemServico,
			String numeroOrdemServicoPai, String cliente, String unidade,
			String numeroNotaFiscal, String numeroSerieFabricante,
			String numeroSerieCliente, String laboratorio, Date dataPrioridade,
			Date dataChegadaServilogi, Date dataEntradaReparo,
			Date laudoTecnicoAprovado, Date laudoTecnicoReprovado,
			Date propostaReprovada,
			Date rejeitadoPeloLider,
			Long idOrcRep, Long idTecnicoResponsavel, Date dataLimite, Integer prazoDevolucao, Integer bloqueado, String caseAvaya, String clienteAvaya,String condicao, Long idLaboratorio, Boolean componentePendente) {
		super();
		this.finalidade = finalidade;
		this.statusString = statusString;
		this.tecnico = tecnico;
		this.numeroOrdemServico = numeroOrdemServico;
		this.numeroOrdemServicoPai = numeroOrdemServicoPai;
		this.cliente = cliente;
		this.unidade = unidade;
		this.numeroNotaFiscal = numeroNotaFiscal;
		this.numeroSerieFabricante = numeroSerieFabricante;
		this.numeroSerieCliente = numeroSerieCliente;
		this.laboratorio = laboratorio;
		this.dataPrioridade = dataPrioridade;
		this.dataChegadaServilogi = dataChegadaServilogi;
		this.dataEntradaReparo = dataEntradaReparo;
		this.laudoTecnicoAprovado = laudoTecnicoAprovado;
		this.laudoTecnicoReprovado = laudoTecnicoReprovado;
		this.propostaReprovada = propostaReprovada;
		this.rejeitadoPeloLider = rejeitadoPeloLider;
		this.idOrcRep = idOrcRep;
		this.idTecnicoResponsavel = idTecnicoResponsavel;
		this.dataLimite = dataLimite;
		this.prazoDevolucao = prazoDevolucao;
		this.bloqueado = bloqueado;
		this.caseAvaya = caseAvaya;
		this.clienteAvaya = clienteAvaya;
		this.condicao = condicao;
		this.idLaboratorio = idLaboratorio;
		this.componentePendente = componentePendente;
	}
	
	public String getFinalidade() {
		return finalidade;
	}

	public void setFinalidade(String finalidade) {
		this.finalidade = finalidade;
	}

	public String getStatusString() {
		return statusString;
	}

	public void setStatusString(String statusString) {
		this.statusString = statusString;
	}

	public String getTecnico() {
		return tecnico;
	}

	public void setTecnico(String tecnico) {
		this.tecnico = tecnico;
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

	public String getNumeroNotaFiscal() {
		return numeroNotaFiscal;
	}

	public void setNumeroNotaFiscal(String numeroNotaFiscal) {
		this.numeroNotaFiscal = numeroNotaFiscal;
	}

	public String getNumeroSerieFabricante() {
		return numeroSerieFabricante;
	}

	public void setNumeroSerieFabricante(String numeroSerieFabricante) {
		this.numeroSerieFabricante = numeroSerieFabricante;
	}

	public String getNumeroSerieCliente() {
		return numeroSerieCliente;
	}

	public void setNumeroSerieCliente(String numeroSerieCliente) {
		this.numeroSerieCliente = numeroSerieCliente;
	}

	public String getLaboratorio() {
		return laboratorio;
	}

	public void setLaboratorio(String laboratorio) {
		this.laboratorio = laboratorio;
	}

	public Date getDataPrioridade() {
		return dataPrioridade;
	}

	public void setDataPrioridade(Date dataPrioridade) {
		this.dataPrioridade = dataPrioridade;
	}

	public Date getDataChegadaServilogi() {
		return dataChegadaServilogi;
	}

	public void setDataChegadaServilogi(Date dataChegadaServilogi) {
		this.dataChegadaServilogi = dataChegadaServilogi;
	}

	public Date getDataEntradaReparo() {
		return dataEntradaReparo;
	}

	public void setDataEntradaReparo(Date dataEntradaReparo) {
		this.dataEntradaReparo = dataEntradaReparo;
	}

	public Date getLaudoTecnicoAprovado() {
		return laudoTecnicoAprovado;
	}

	public void setLaudoTecnicoAprovado(Date laudoTecnicoAprovado) {
		this.laudoTecnicoAprovado = laudoTecnicoAprovado;
	}

	public Date getLaudoTecnicoReprovado() {
		return laudoTecnicoReprovado;
	}

	public void setLaudoTecnicoReprovado(Date laudoTecnicoReprovado) {
		this.laudoTecnicoReprovado = laudoTecnicoReprovado;
	}

	public Date getPropostaAprovada() {
		return propostaAprovada;
	}

	public void setPropostaAprovada(Date propostaAprovada) {
		this.propostaAprovada = propostaAprovada;
	}

	public Date getPropostaReprovada() {
		return propostaReprovada;
	}

	public void setPropostaReprovada(Date propostaReprovada) {
		this.propostaReprovada = propostaReprovada;
	}

	public Date getOrcamentoDiferenciadoRejeitado() {
		return orcamentoDiferenciadoRejeitado;
	}

	public void setOrcamentoDiferenciadoRejeitado(
			Date orcamentoDiferenciadoRejeitado) {
		this.orcamentoDiferenciadoRejeitado = orcamentoDiferenciadoRejeitado;
	}

	public Date getRejeitadoPeloLider() {
		return rejeitadoPeloLider;
	}

	public void setRejeitadoPeloLider(Date rejeitadoPeloLider) {
		this.rejeitadoPeloLider = rejeitadoPeloLider;
	}

	public Long getIdOrcRep() {
		return idOrcRep;
	}

	public void setIdOrcRep(Long idOrcRep) {
		this.idOrcRep = idOrcRep;
	}

	public Long getIdTecnicoResponsavel() {
		return idTecnicoResponsavel;
	}

	public void setIdTecnicoResponsavel(Long idTecnicoResponsavel) {
		this.idTecnicoResponsavel = idTecnicoResponsavel;
	}

	public Date getDataLimite() {
		return dataLimite;
	}

	public void setDataLimite(Date dataLimite) {
		this.dataLimite = dataLimite;
	}

	public Integer getPrazoDevolucao() {
		return prazoDevolucao;
	}

	public void setPrazoDevolucao(Integer prazoDevolucao) {
		this.prazoDevolucao = prazoDevolucao;
	}

	public Integer getBloqueado() {
		return bloqueado;
	}

	public void setBloqueado(Integer bloqueado) {
		this.bloqueado = bloqueado;
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

	public String getCondicao() {
		return condicao;
	}

	public void setCondicao(String condicao) {
		this.condicao = condicao;
	}

	public Long getIdLaboratorio() {
		return idLaboratorio;
	}

	public void setIdLaboratorio(Long idLaboratorio) {
		this.idLaboratorio = idLaboratorio;
	}

	public Boolean getComponentePendente() {
		return componentePendente;
	}

	public void setComponentePendente(Boolean componentePendente) {
		this.componentePendente = componentePendente;
	}

	public Boolean getCriadoFromOrcamento() {
		return criadoFromOrcamento;
	}

	public void setCriadoFromOrcamento(Boolean criadoFromOrcamento) {
		this.criadoFromOrcamento = criadoFromOrcamento;
	}


}

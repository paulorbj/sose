
package br.com.sose.entity.orcrepGenerico;

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.Hibernate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.beans.factory.annotation.Configurable;

import br.com.sose.entity.admistrativo.Usuario;

@MappedSuperclass
@Configurable
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OrcRepGenerico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	@Column(name="status")
	protected String statusString;
	
	@Column(name = "dt_entrada", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date dataEntrada;

	@Column(name = "dt_inicio", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date dataInicio;

	@Column(name = "dt_fim", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date dataFim;

	@Column(name = "dt_liberacao_lider", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date dataLiberacao;

	@Column(name = "dt_abertura_lider")
	protected Date dataAberturaLider;

	@Column(name = "dt_abertura_tecnico")
	protected Date dataAberturaTecnico;
	
	@Column(name = "dt_enviado_laudo_tecnico")
	protected Date dataEnvioParaLaudoTecnico;
	
	@Column(name = "dt_finalizacao_laudo_tecnico")
	protected Date dataLaudoTecnicoFinalizado;

	@Column(name = "dt_requisicao_orc_diferenciado")
	protected Date dataRequisicaoOrcDiferenciado;
	
	@Column(name = "dt_aprovacao_orc_diferenciado_lider")
	protected Date dataAprovacaoOrcDiferenciadoLider;

	@Column(name = "dt_reprovacao_orc_diferenciado_lider")
	protected Date dataReprovacaoOrcDiferenciadoLider;

	@Column(name = "dt_aprovacao_proposta_orc_diferenciado")
	protected Date dataAprovacaoPropostaOrcDiferenciado;

	@Column(name = "dt_reprovacao_proposta_orc_diferenciado")
	protected Date dataReprovacaoPropostaOrcDiferenciadoLider;
	
	@Column(name = "dt_requisicao_laudo_tecnico")
	protected Date dataRequisicaoLaudoTecnico;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "usuario_id", referencedColumnName = "id")
	protected Usuario tecnicoResponsavel;

	@Column(name = "observacao", length=1000)
	protected String observacao;

	@Column(name = "cadastro_sistema_ativo")
	protected Boolean cadastroSistemaAtivo;
	
	protected String condicao;

	@Column(name = "ja_reparado")
	protected Boolean jaReparado;
	
	@Column(name = "tempo_gasto")
	protected Integer tempoGasto;

	protected Date prioridade;
	
	protected Boolean transferido;
	
	protected Date dataLimite;
	
	protected Boolean componentePendente;
	
	protected Boolean componenteEmFalta;

	public OrcRepGenerico() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(Date dataEntrada) {
		this.dataEntrada = dataEntrada;
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

	public Date getDataLiberacao() {
		return dataLiberacao;
	}

	public void setDataLiberacao(Date dataLiberacao) {
		this.dataLiberacao = dataLiberacao;
	}

	public Date getDataAberturaLider() {
		return dataAberturaLider;
	}

	public void setDataAberturaLider(Date dataAberturaLider) {
		this.dataAberturaLider = dataAberturaLider;
	}

	public Date getDataAberturaTecnico() {
		return dataAberturaTecnico;
	}

	public void setDataAberturaTecnico(Date dataAberturaTecnico) {
		this.dataAberturaTecnico = dataAberturaTecnico;
	}

	public Date getDataEnvioParaLaudoTecnico() {
		return dataEnvioParaLaudoTecnico;
	}

	public void setDataEnvioParaLaudoTecnico(Date dataEnvioParaLaudoTecnico) {
		this.dataEnvioParaLaudoTecnico = dataEnvioParaLaudoTecnico;
	}

	public Date getDataLaudoTecnicoFinalizado() {
		return dataLaudoTecnicoFinalizado;
	}

	public void setDataLaudoTecnicoFinalizado(Date dataLaudoTecnicoFinalizado) {
		this.dataLaudoTecnicoFinalizado = dataLaudoTecnicoFinalizado;
	}

	public Date getDataRequisicaoOrcDiferenciado() {
		return dataRequisicaoOrcDiferenciado;
	}

	public void setDataRequisicaoOrcDiferenciado(Date dataRequisicaoOrcDiferenciado) {
		this.dataRequisicaoOrcDiferenciado = dataRequisicaoOrcDiferenciado;
	}

	public Date getDataAprovacaoOrcDiferenciadoLider() {
		return dataAprovacaoOrcDiferenciadoLider;
	}

	public void setDataAprovacaoOrcDiferenciadoLider(
			Date dataAprovacaoOrcDiferenciadoLider) {
		this.dataAprovacaoOrcDiferenciadoLider = dataAprovacaoOrcDiferenciadoLider;
	}

	public Date getDataReprovacaoOrcDiferenciadoLider() {
		return dataReprovacaoOrcDiferenciadoLider;
	}

	public void setDataReprovacaoOrcDiferenciadoLider(
			Date dataReprovacaoOrcDiferenciadoLider) {
		this.dataReprovacaoOrcDiferenciadoLider = dataReprovacaoOrcDiferenciadoLider;
	}

	public Date getDataAprovacaoPropostaOrcDiferenciado() {
		return dataAprovacaoPropostaOrcDiferenciado;
	}

	public void setDataAprovacaoPropostaOrcDiferenciado(
			Date dataAprovacaoPropostaOrcDiferenciado) {
		this.dataAprovacaoPropostaOrcDiferenciado = dataAprovacaoPropostaOrcDiferenciado;
	}

	public Date getDataReprovacaoPropostaOrcDiferenciadoLider() {
		return dataReprovacaoPropostaOrcDiferenciadoLider;
	}

	public void setDataReprovacaoPropostaOrcDiferenciadoLider(
			Date dataReprovacaoPropostaOrcDiferenciadoLider) {
		this.dataReprovacaoPropostaOrcDiferenciadoLider = dataReprovacaoPropostaOrcDiferenciadoLider;
	}

	public Usuario getTecnicoResponsavel() {
		if(!Hibernate.isInitialized(tecnicoResponsavel)){
			tecnicoResponsavel = null;	
		}
		return tecnicoResponsavel;
	}

	public void setTecnicoResponsavel(Usuario tecnicoResponsavel) {
		this.tecnicoResponsavel = tecnicoResponsavel;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Boolean getCadastroSistemaAtivo() {
		return cadastroSistemaAtivo;
	}

	public void setCadastroSistemaAtivo(Boolean cadastroSistemaAtivo) {
		this.cadastroSistemaAtivo = cadastroSistemaAtivo;
	}

	public String getCondicao() {
		return condicao;
	}

	public void setCondicao(String condicao) {
		this.condicao = condicao;
	}

	public Boolean getJaReparado() {
		return jaReparado;
	}

	public void setJaReparado(Boolean jaReparado) {
		this.jaReparado = jaReparado;
	}

	public Integer getTempoGasto() {
		return tempoGasto;
	}

	public void setTempoGasto(Integer tempoGasto) {
		this.tempoGasto = tempoGasto;
	}

	public Date getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(Date prioridade) {
		this.prioridade = prioridade;
	}

	public Boolean getTransferido() {
		return transferido;
	}

	public void setTransferido(Boolean transferido) {
		this.transferido = transferido;
	}

	public String getStatusString() {
		return statusString;
	}

	public void setStatusString(String statusString) {
		this.statusString = statusString;
	}

	public Date getDataLimite() {
		return dataLimite;
	}

	public void setDataLimite(Date dataLimite) {
		this.dataLimite = dataLimite;
	}

	public Date getDataRequisicaoLaudoTecnico() {
		return dataRequisicaoLaudoTecnico;
	}

	public void setDataRequisicaoLaudoTecnico(Date dataRequisicaoLaudoTecnico) {
		this.dataRequisicaoLaudoTecnico = dataRequisicaoLaudoTecnico;
	}

	public Boolean getComponentePendente() {
		return componentePendente;
	}

	public void setComponentePendente(Boolean componentePendente) {
		this.componentePendente = componentePendente;
	}

	public Boolean getComponenteEmFalta() {
		return componenteEmFalta;
	}

	public void setComponenteEmFalta(Boolean componenteEmFalta) {
		this.componenteEmFalta = componenteEmFalta;
	}

}

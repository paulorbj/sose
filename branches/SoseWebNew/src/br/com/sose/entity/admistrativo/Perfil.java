package br.com.sose.entity.admistrativo;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.beans.factory.annotation.Configurable;

@Entity
@Configurable
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Perfil implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;

	private String descricao;

	@Column(name = "cadastro_ativo")
	private Boolean cadastroSistemaAtivo;

	//Permissoes de modulo
	private Boolean menuCadastro;

	private Boolean menuRecebimento;

	private Boolean menuAreaTecnica;

	private Boolean menuExpedicao;

	private Boolean menuProposta;

	private Boolean menuEstoque;

	private Boolean menuExterno;

	private Boolean menuFaturamento;

	private Boolean menuAvaya;

	private Boolean menuConsulta;

	//Permissao de sub menu cadastro
	private Boolean subMenuCadastroAtividade;

	private Boolean subMenuCadastroComponente;

	private Boolean subMenuCadastroDefeito;

	private Boolean subMenuCadastroEncapsulamento;

	private Boolean subMenuCadastroTipoComponente;

	private Boolean subMenuCadastroEquipamento;

	private Boolean subMenuCadastroFabricante;

	private Boolean subMenuCadastroLaboratorio;

	private Boolean subMenuCadastroPessoa;

	private Boolean subMenuCadastroUnidade;

	private Boolean subMenuCadastroUsuario;

	private Boolean subMenuCadastroPerfil;


	//Permissoes de sub menu recebimento

	private Boolean subMenuRecebimentouNotaFiscal;

	private Boolean subMenuRecebimentoLpu;

	private Boolean subMenuRecebimentoEtiqueta;

	//Permissoes de sub menu area tecnica

	private Boolean subMenuAreaTecnicaListagemGeral;

	private Boolean subMenuAreaTecnicaRequisicaoComponente;

	private Boolean subMenuAreaTecnicaLaudoTecnico;

	private Boolean subMenuAreaTecnicaOrcamentoDiferenciado;

	//Permissoes de sub menu expedicao

	private Boolean subMenuExpedicaoBaixaOs;

	private Boolean subMenuExpedicaoListagemNfSaida;
	//Permissoes de sub menu proposta

	private Boolean subMenuPropostaBaixaOs;

	private Boolean subMenuPropostaListagemProposta;

	//Permissoes de sub menu estoque

	private Boolean subMenuEstoqueRequisicaoComponente;
	
	private Boolean subMenuEstoqueComponente;
	
	private Boolean subMenuEstoqueDevolucaoComponente;
	
	private Boolean subMenuEstoquePedidoCompra;
	
	private Boolean subMenuEstoqueListagemCompra;
	
	//Permissoes de sub menu externo

	//Permissoes de sub menu faturamento
	private Boolean subMenuBaixaFaturamento;

	private Boolean subMenuListagemFaturamento;


	//Permissoes de sub menu consulta
	private Boolean subMenuConsultaConsultaGeral;
	
	private Boolean subMenuConsultaGraficos;
	
	//Permissoes de sub menu avaya
	
	private Boolean subMenuAvayaGerenciamentoEstoque;
	
	//Acoes Listagem geral
	private Boolean acaoAtribuirTecnico;
			
	private Boolean acaoAtribuirPrioridade;
	
	private Boolean acaoPermitirVisulizarObservacaoSigilosa;
	
	private Boolean acaoPermitirAprovarDevolucaoSemReparo;
	
	private Boolean acaoPermitirAprovarSolicitacaoLaudoTecnico;
	
	private Boolean acaoPermitirAprovarSolicitacaoOrcamentoDiferenciado;
	
	private Boolean acaoPermitirAprovarSolicitacaoProposta;
	
	private Boolean acaoDesabilitarAbaPropostaConsulta;
	
	private Boolean acaoPrioridadeGerencialParaAtribuicoes;
	
	private Boolean acaoPrioridadeGerencialParaIncluirConsulta;
	
	private Boolean atualizarDadosOrdemServico;

	private Boolean acaoPrioridadeGerencialAprovacoes;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Boolean getMenuCadastro() {
		return menuCadastro;
	}

	public void setMenuCadastro(Boolean menuCadastro) {
		this.menuCadastro = menuCadastro;
	}

	public Boolean getMenuRecebimento() {
		return menuRecebimento;
	}

	public void setMenuRecebimento(Boolean menuRecebimento) {
		this.menuRecebimento = menuRecebimento;
	}

	public Boolean getMenuAreaTecnica() {
		return menuAreaTecnica;
	}

	public void setMenuAreaTecnica(Boolean menuAreaTecnica) {
		this.menuAreaTecnica = menuAreaTecnica;
	}

	public Boolean getMenuExpedicao() {
		return menuExpedicao;
	}

	public void setMenuExpedicao(Boolean menuExpedicao) {
		this.menuExpedicao = menuExpedicao;
	}

	public Boolean getMenuProposta() {
		return menuProposta;
	}

	public void setMenuProposta(Boolean menuProposta) {
		this.menuProposta = menuProposta;
	}

	public Boolean getMenuEstoque() {
		return menuEstoque;
	}

	public void setMenuEstoque(Boolean menuEstoque) {
		this.menuEstoque = menuEstoque;
	}

	public Boolean getMenuExterno() {
		return menuExterno;
	}

	public void setMenuExterno(Boolean menuExterno) {
		this.menuExterno = menuExterno;
	}

	public Boolean getMenuFaturamento() {
		return menuFaturamento;
	}

	public void setMenuFaturamento(Boolean menuFaturamento) {
		this.menuFaturamento = menuFaturamento;
	}

	public Boolean getMenuAvaya() {
		return menuAvaya;
	}

	public void setMenuAvaya(Boolean menuAvaya) {
		this.menuAvaya = menuAvaya;
	}

	public Boolean getMenuConsulta() {
		return menuConsulta;
	}

	public void setMenuConsulta(Boolean menuConsulta) {
		this.menuConsulta = menuConsulta;
	}

	public Boolean getSubMenuCadastroAtividade() {
		return subMenuCadastroAtividade;
	}

	public void setSubMenuCadastroAtividade(Boolean subMenuCadastroAtividade) {
		this.subMenuCadastroAtividade = subMenuCadastroAtividade;
	}

	public Boolean getSubMenuCadastroComponente() {
		return subMenuCadastroComponente;
	}

	public void setSubMenuCadastroComponente(Boolean subMenuCadastroComponente) {
		this.subMenuCadastroComponente = subMenuCadastroComponente;
	}

	public Boolean getSubMenuCadastroDefeito() {
		return subMenuCadastroDefeito;
	}

	public void setSubMenuCadastroDefeito(Boolean subMenuCadastroDefeito) {
		this.subMenuCadastroDefeito = subMenuCadastroDefeito;
	}

	public Boolean getSubMenuCadastroEncapsulamento() {
		return subMenuCadastroEncapsulamento;
	}

	public void setSubMenuCadastroEncapsulamento(
			Boolean subMenuCadastroEncapsulamento) {
		this.subMenuCadastroEncapsulamento = subMenuCadastroEncapsulamento;
	}

	public Boolean getSubMenuCadastroTipoComponente() {
		return subMenuCadastroTipoComponente;
	}

	public void setSubMenuCadastroTipoComponente(
			Boolean subMenuCadastroTipoComponente) {
		this.subMenuCadastroTipoComponente = subMenuCadastroTipoComponente;
	}

	public Boolean getSubMenuCadastroEquipamento() {
		return subMenuCadastroEquipamento;
	}

	public void setSubMenuCadastroEquipamento(Boolean subMenuCadastroEquipamento) {
		this.subMenuCadastroEquipamento = subMenuCadastroEquipamento;
	}

	public Boolean getSubMenuCadastroFabricante() {
		return subMenuCadastroFabricante;
	}

	public void setSubMenuCadastroFabricante(Boolean subMenuCadastroFabricante) {
		this.subMenuCadastroFabricante = subMenuCadastroFabricante;
	}

	public Boolean getSubMenuCadastroLaboratorio() {
		return subMenuCadastroLaboratorio;
	}

	public void setSubMenuCadastroLaboratorio(Boolean subMenuCadastroLaboratorio) {
		this.subMenuCadastroLaboratorio = subMenuCadastroLaboratorio;
	}

	public Boolean getSubMenuCadastroPessoa() {
		return subMenuCadastroPessoa;
	}

	public void setSubMenuCadastroPessoa(Boolean subMenuCadastroPessoa) {
		this.subMenuCadastroPessoa = subMenuCadastroPessoa;
	}

	public Boolean getSubMenuCadastroUnidade() {
		return subMenuCadastroUnidade;
	}

	public void setSubMenuCadastroUnidade(Boolean subMenuCadastroUnidade) {
		this.subMenuCadastroUnidade = subMenuCadastroUnidade;
	}

	public Boolean getSubMenuCadastroUsuario() {
		return subMenuCadastroUsuario;
	}

	public void setSubMenuCadastroUsuario(Boolean subMenuCadastroUsuario) {
		this.subMenuCadastroUsuario = subMenuCadastroUsuario;
	}

	public Boolean getSubMenuCadastroPerfil() {
		return subMenuCadastroPerfil;
	}

	public void setSubMenuCadastroPerfil(Boolean subMenuCadastroPerfil) {
		this.subMenuCadastroPerfil = subMenuCadastroPerfil;
	}

	public Boolean getSubMenuRecebimentouNotaFiscal() {
		return subMenuRecebimentouNotaFiscal;
	}

	public void setSubMenuRecebimentouNotaFiscal(Boolean subMenuRecebimentouNotaFiscal) {
		this.subMenuRecebimentouNotaFiscal = subMenuRecebimentouNotaFiscal;
	}

	public Boolean getSubMenuRecebimentoLpu() {
		return subMenuRecebimentoLpu;
	}

	public void setSubMenuRecebimentoLpu(Boolean subMenuRecebimentoLpu) {
		this.subMenuRecebimentoLpu = subMenuRecebimentoLpu;
	}

	public Boolean getSubMenuRecebimentoEtiqueta() {
		return subMenuRecebimentoEtiqueta;
	}

	public void setSubMenuRecebimentoEtiqueta(Boolean subMenuRecebimentoEtiqueta) {
		this.subMenuRecebimentoEtiqueta = subMenuRecebimentoEtiqueta;
	}

	public Boolean getSubMenuAreaTecnicaListagemGeral() {
		return subMenuAreaTecnicaListagemGeral;
	}

	public void setSubMenuAreaTecnicaListagemGeral(
			Boolean subMenuAreaTecnicaListagemGeral) {
		this.subMenuAreaTecnicaListagemGeral = subMenuAreaTecnicaListagemGeral;
	}

	public Boolean getSubMenuAreaTecnicaRequisicaoComponente() {
		return subMenuAreaTecnicaRequisicaoComponente;
	}

	public void setSubMenuAreaTecnicaRequisicaoComponente(
			Boolean subMenuAreaTecnicaRequisicaoComponente) {
		this.subMenuAreaTecnicaRequisicaoComponente = subMenuAreaTecnicaRequisicaoComponente;
	}

	public Boolean getSubMenuAreaTecnicaLaudoTecnico() {
		return subMenuAreaTecnicaLaudoTecnico;
	}

	public void setSubMenuAreaTecnicaLaudoTecnico(
			Boolean subMenuAreaTecnicaLaudoTecnico) {
		this.subMenuAreaTecnicaLaudoTecnico = subMenuAreaTecnicaLaudoTecnico;
	}

	public Boolean getSubMenuAreaTecnicaOrcamentoDiferenciado() {
		return subMenuAreaTecnicaOrcamentoDiferenciado;
	}

	public void setSubMenuAreaTecnicaOrcamentoDiferenciado(
			Boolean subMenuAreaTecnicaOrcamentoDiferenciado) {
		this.subMenuAreaTecnicaOrcamentoDiferenciado = subMenuAreaTecnicaOrcamentoDiferenciado;
	}

	public Boolean getSubMenuExpedicaoBaixaOs() {
		return subMenuExpedicaoBaixaOs;
	}

	public void setSubMenuExpedicaoBaixaOs(Boolean subMenuExpedicaoBaixaOs) {
		this.subMenuExpedicaoBaixaOs = subMenuExpedicaoBaixaOs;
	}

	public Boolean getSubMenuExpedicaoListagemNfSaida() {
		return subMenuExpedicaoListagemNfSaida;
	}

	public void setSubMenuExpedicaoListagemNfSaida(
			Boolean subMenuExpedicaoListagemNfSaida) {
		this.subMenuExpedicaoListagemNfSaida = subMenuExpedicaoListagemNfSaida;
	}

	public Boolean getSubMenuPropostaBaixaOs() {
		return subMenuPropostaBaixaOs;
	}

	public void setSubMenuPropostaBaixaOs(Boolean subMenuPropostaBaixaOs) {
		this.subMenuPropostaBaixaOs = subMenuPropostaBaixaOs;
	}

	public Boolean getSubMenuPropostaListagemProposta() {
		return subMenuPropostaListagemProposta;
	}

	public void setSubMenuPropostaListagemProposta(
			Boolean subMenuPropostaListagemProposta) {
		this.subMenuPropostaListagemProposta = subMenuPropostaListagemProposta;
	}

	public Boolean getSubMenuEstoqueRequisicaoComponente() {
		return subMenuEstoqueRequisicaoComponente;
	}

	public void setSubMenuEstoqueRequisicaoComponente(
			Boolean subMenuEstoqueRequisicaoComponente) {
		this.subMenuEstoqueRequisicaoComponente = subMenuEstoqueRequisicaoComponente;
	}

	public Boolean getSubMenuEstoqueComponente() {
		return subMenuEstoqueComponente;
	}

	public void setSubMenuEstoqueComponente(Boolean subMenuEstoqueComponente) {
		this.subMenuEstoqueComponente = subMenuEstoqueComponente;
	}

	public Boolean getCadastroSistemaAtivo() {
		return cadastroSistemaAtivo;
	}

	public void setCadastroSistemaAtivo(Boolean cadastroSistemaAtivo) {
		this.cadastroSistemaAtivo = cadastroSistemaAtivo;
	}

	public Boolean getSubMenuConsultaConsultaGeral() {
		return subMenuConsultaConsultaGeral;
	}

	public void setSubMenuConsultaConsultaGeral(Boolean subMenuConsultaConsultaGeral) {
		this.subMenuConsultaConsultaGeral = subMenuConsultaConsultaGeral;
	}

	public Boolean getAcaoAtribuirTecnico() {
		return acaoAtribuirTecnico;
	}

	public void setAcaoAtribuirTecnico(Boolean acaoAtribuirTecnico) {
		this.acaoAtribuirTecnico = acaoAtribuirTecnico;
	}

	public Boolean getAcaoAtribuirPrioridade() {
		return acaoAtribuirPrioridade;
	}

	public void setAcaoAtribuirPrioridade(Boolean acaoAtribuirPrioridade) {
		this.acaoAtribuirPrioridade = acaoAtribuirPrioridade;
	}

	public Boolean getAcaoPermitirVisulizarObservacaoSigilosa() {
		return acaoPermitirVisulizarObservacaoSigilosa;
	}

	public void setAcaoPermitirVisulizarObservacaoSigilosa(
			Boolean acaoPermitirVisulizarObservacaoSigilosa) {
		this.acaoPermitirVisulizarObservacaoSigilosa = acaoPermitirVisulizarObservacaoSigilosa;
	}

	public Boolean getAcaoPermitirAprovarDevolucaoSemReparo() {
		return acaoPermitirAprovarDevolucaoSemReparo;
	}

	public void setAcaoPermitirAprovarDevolucaoSemReparo(
			Boolean acaoPermitirAprovarDevolucaoSemReparo) {
		this.acaoPermitirAprovarDevolucaoSemReparo = acaoPermitirAprovarDevolucaoSemReparo;
	}

	public Boolean getAcaoPermitirAprovarSolicitacaoLaudoTecnico() {
		return acaoPermitirAprovarSolicitacaoLaudoTecnico;
	}

	public void setAcaoPermitirAprovarSolicitacaoLaudoTecnico(
			Boolean acaoPermitirAprovarSolicitacaoLaudoTecnico) {
		this.acaoPermitirAprovarSolicitacaoLaudoTecnico = acaoPermitirAprovarSolicitacaoLaudoTecnico;
	}

	public Boolean getAcaoPermitirAprovarSolicitacaoOrcamentoDiferenciado() {
		return acaoPermitirAprovarSolicitacaoOrcamentoDiferenciado;
	}

	public void setAcaoPermitirAprovarSolicitacaoOrcamentoDiferenciado(
			Boolean acaoPermitirAprovarSolicitacaoOrcamentoDiferenciado) {
		this.acaoPermitirAprovarSolicitacaoOrcamentoDiferenciado = acaoPermitirAprovarSolicitacaoOrcamentoDiferenciado;
	}

	public Boolean getAcaoPermitirAprovarSolicitacaoProposta() {
		return acaoPermitirAprovarSolicitacaoProposta;
	}

	public void setAcaoPermitirAprovarSolicitacaoProposta(
			Boolean acaoPermitirAprovarSolicitacaoProposta) {
		this.acaoPermitirAprovarSolicitacaoProposta = acaoPermitirAprovarSolicitacaoProposta;
	}

	public Boolean getSubMenuEstoqueDevolucaoComponente() {
		return subMenuEstoqueDevolucaoComponente;
	}

	public void setSubMenuEstoqueDevolucaoComponente(
			Boolean subMenuEstoqueDevolucaoComponente) {
		this.subMenuEstoqueDevolucaoComponente = subMenuEstoqueDevolucaoComponente;
	}

	public Boolean getSubMenuEstoquePedidoCompra() {
		return subMenuEstoquePedidoCompra;
	}

	public void setSubMenuEstoquePedidoCompra(Boolean subMenuEstoquePedidoCompra) {
		this.subMenuEstoquePedidoCompra = subMenuEstoquePedidoCompra;
	}

	public Boolean getAcaoDesabilitarAbaPropostaConsulta() {
		return acaoDesabilitarAbaPropostaConsulta;
	}

	public void setAcaoDesabilitarAbaPropostaConsulta(
			Boolean acaoDesabilitarAbaPropostaConsulta) {
		this.acaoDesabilitarAbaPropostaConsulta = acaoDesabilitarAbaPropostaConsulta;
	}

	public Boolean getAcaoPrioridadeGerencialParaAtribuicoes() {
		return acaoPrioridadeGerencialParaAtribuicoes;
	}

	public void setAcaoPrioridadeGerencialParaAtribuicoes(
			Boolean acaoPrioridadeGerencialParaAtribuicoes) {
		this.acaoPrioridadeGerencialParaAtribuicoes = acaoPrioridadeGerencialParaAtribuicoes;
	}

	public Boolean getSubMenuAvayaGerenciamentoEstoque() {
		return subMenuAvayaGerenciamentoEstoque;
	}

	public void setSubMenuAvayaGerenciamentoEstoque(
			Boolean subMenuAvayaGerenciamentoEstoque) {
		this.subMenuAvayaGerenciamentoEstoque = subMenuAvayaGerenciamentoEstoque;
	}

	public Boolean getAcaoPrioridadeGerencialParaIncluirConsulta() {
		return acaoPrioridadeGerencialParaIncluirConsulta;
	}

	public void setAcaoPrioridadeGerencialParaIncluirConsulta(
			Boolean acaoPrioridadeGerencialParaIncluirConsulta) {
		this.acaoPrioridadeGerencialParaIncluirConsulta = acaoPrioridadeGerencialParaIncluirConsulta;
	}

	public Boolean getSubMenuBaixaFaturamento() {
		return subMenuBaixaFaturamento;
	}

	public void setSubMenuBaixaFaturamento(Boolean subMenuBaixaFaturamento) {
		this.subMenuBaixaFaturamento = subMenuBaixaFaturamento;
	}

	public Boolean getSubMenuListagemFaturamento() {
		return subMenuListagemFaturamento;
	}

	public void setSubMenuListagemFaturamento(Boolean subMenuListagemFaturamento) {
		this.subMenuListagemFaturamento = subMenuListagemFaturamento;
	}

	public Boolean getAtualizarDadosOrdemServico() {
		return atualizarDadosOrdemServico;
	}

	public void setAtualizarDadosOrdemServico(Boolean atualizarDadosOrdemServico) {
		this.atualizarDadosOrdemServico = atualizarDadosOrdemServico;
	}

	public Boolean getAcaoPrioridadeGerencialAprovacoes() {
		return acaoPrioridadeGerencialAprovacoes;
	}

	public void setAcaoPrioridadeGerencialAprovacoes(
			Boolean acaoPrioridadeGerencialAprovacoes) {
		this.acaoPrioridadeGerencialAprovacoes = acaoPrioridadeGerencialAprovacoes;
	}

	public Boolean getSubMenuEstoqueListagemCompra() {
		return subMenuEstoqueListagemCompra;
	}

	public void setSubMenuEstoqueListagemCompra(Boolean subMenuEstoqueListagemCompra) {
		this.subMenuEstoqueListagemCompra = subMenuEstoqueListagemCompra;
	}

	public Boolean getSubMenuConsultaGraficos() {
		return subMenuConsultaGraficos;
	}

	public void setSubMenuConsultaGraficos(Boolean subMenuConsultaGraficos) {
		this.subMenuConsultaGraficos = subMenuConsultaGraficos;
	}

	
}

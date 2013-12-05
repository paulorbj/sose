package entities.administrativo
{
	
	[Bindable]	
	[RemoteClass(alias="br.com.sose.entity.admistrativo.Perfil")]	
	public class Perfil
	{
		
		private var _id:Number;
		private var _descricao:String;
		private var _nome:String;
		private var _cadastroSistemaRealizadoEm:Date;
		private var _cadastroSistemaRealizadoPor:Usuario;
		private var _cadastroSistemaAtivo:Boolean;
		
		private var _menuCadastro:Boolean;
		
		private var _menuRecebimento:Boolean;
		
		private var _menuAreaTecnica:Boolean;
		
		private var _menuExpedicao:Boolean;
		
		private var _menuProposta:Boolean;
		
		private var _menuEstoque:Boolean;
		
		private var _menuExterno:Boolean;
		
		private var _menuFaturamento:Boolean;
		
		private var _menuAvaya:Boolean;
		
		private var _menuConsulta:Boolean;
		
		//Permissao de sub menu cadastro
		private var _subMenuCadastroAtividade:Boolean;
		
		private var _subMenuCadastroComponente:Boolean;
		
		private var _subMenuCadastroDefeito:Boolean;
		
		private var _subMenuCadastroEncapsulamento:Boolean;
		
		private var _subMenuCadastroTipoComponente:Boolean;
		
		private var _subMenuCadastroEquipamento:Boolean;
		
		private var _subMenuCadastroFabricante:Boolean;
		
		private var _subMenuCadastroLaboratorio:Boolean;
		
		private var _subMenuCadastroPessoa:Boolean;
		
		private var _subMenuCadastroUnidade:Boolean;
		
		private var _subMenuCadastroUsuario:Boolean;
		
		private var _subMenuCadastroPerfil:Boolean;
		
		
		//Permissoes de sub menu recebimento
		
		private var _subMenuRecebimentouNotaFiscal:Boolean;
		
		private var _subMenuRecebimentoLpu:Boolean;
		
		private var _subMenuRecebimentoEtiqueta:Boolean;
		
		//Permissoes de sub menu area tecnica
		
		private var _subMenuAreaTecnicaListagemGeral:Boolean;
		
		private var _subMenuAreaTecnicaRequisicaoComponente:Boolean;
		
		private var _subMenuAreaTecnicaLaudoTecnico:Boolean;
		
		private var _subMenuAreaTecnicaOrcamentoDiferenciado:Boolean;
		
		//Permissoes de sub menu expedicao
		
		private var _subMenuExpedicaoBaixaOs:Boolean;
		
		private var _subMenuExpedicaoListagemNfSaida:Boolean;
		//Permissoes de sub menu proposta
		
		private var _subMenuPropostaBaixaOs:Boolean;
		
		private var _subMenuPropostaListagemProposta:Boolean;
		
		//Permissoes de sub menu estoque
		
		private var _subMenuEstoqueRequisicaoComponente:Boolean;
		
		private var _subMenuEstoqueComponente:Boolean;
		
		private var _subMenuEstoqueDevolucaoComponente:Boolean;
		
		private var _subMenuEstoquePedidoCompra:Boolean;
		
		private var _subMenuEstoqueListagemCompra:Boolean;
		
		//Permissoes de sub menu externo
		
		//Permissoes de sub menu faturamento
		private var _subMenuBaixaFaturamento:Boolean;
		
		private var _subMenuListagemFaturamento:Boolean;
		
		//Permissoes de sub menu consulta
		private var _subMenuConsultaConsultaGeral:Boolean;
		
		//Permissoes de sub menu avaya
		private var _subMenuAvayaGerenciamentoEstoque:Boolean;

		//Acoes Listagem geral
		private var _acaoAtribuirTecnico:Boolean;
		
		private var _acaoAtribuirPrioridade:Boolean;
		
		//Acoes observacoes
		private var _acaoPermitirVisulizarObservacaoSigilosa:Boolean;
		
		private var _acaoPermitirAprovarDevolucaoSemReparo:Boolean;
		
		private var _acaoPermitirAprovarSolicitacaoLaudoTecnico:Boolean;
		
		private var _acaoPermitirAprovarSolicitacaoOrcamentoDiferenciado:Boolean;
		
		private var _acaoPermitirAprovarSolicitacaoProposta:Boolean;
		
		private var _acaoDesabilitarAbaPropostaConsulta:Boolean;
		
		private var _acaoPrioridadeGerencialParaAtribuicoes:Boolean;
		
		private var _acaoPrioridadeGerencialParaIncluirConsulta:Boolean;
		
		private var _atualizarDadosOrdemServico:Boolean;
		
		private var _acaoPrioridadeGerencialAprovacoes:Boolean;
		
		public function Perfil(){
			_id = 0;
			_nome = "";
		}
		
	
		
		public function get id():Number
		{
			return _id;
		}

		public function set id(value:Number):void
		{
			_id = value;
		}

		public function get descricao():String
		{
			return _descricao;
		}

		public function set descricao(value:String):void
		{
			_descricao = value;
		}

		public function get nome():String
		{
			return _nome;
		}

		public function set nome(value:String):void
		{
			_nome = value;
		}

		public function get menuCadastro():Boolean
		{
			return _menuCadastro;
		}

		public function set menuCadastro(value:Boolean):void
		{
			_menuCadastro = value;
		}

		public function get menuRecebimento():Boolean
		{
			return _menuRecebimento;
		}

		public function set menuRecebimento(value:Boolean):void
		{
			_menuRecebimento = value;
		}

		public function get menuAreaTecnica():Boolean
		{
			return _menuAreaTecnica;
		}

		public function set menuAreaTecnica(value:Boolean):void
		{
			_menuAreaTecnica = value;
		}

		public function get menuExpedicao():Boolean
		{
			return _menuExpedicao;
		}

		public function set menuExpedicao(value:Boolean):void
		{
			_menuExpedicao = value;
		}

		public function get menuProposta():Boolean
		{
			return _menuProposta;
		}

		public function set menuProposta(value:Boolean):void
		{
			_menuProposta = value;
		}

		public function get menuEstoque():Boolean
		{
			return _menuEstoque;
		}

		public function set menuEstoque(value:Boolean):void
		{
			_menuEstoque = value;
		}

		public function get menuExterno():Boolean
		{
			return _menuExterno;
		}

		public function set menuExterno(value:Boolean):void
		{
			_menuExterno = value;
		}

		public function get menuFaturamento():Boolean
		{
			return _menuFaturamento;
		}

		public function set menuFaturamento(value:Boolean):void
		{
			_menuFaturamento = value;
		}

		public function get menuAvaya():Boolean
		{
			return _menuAvaya;
		}

		public function set menuAvaya(value:Boolean):void
		{
			_menuAvaya = value;
		}

		public function get menuConsulta():Boolean
		{
			return _menuConsulta;
		}

		public function set menuConsulta(value:Boolean):void
		{
			_menuConsulta = value;
		}

		public function get subMenuCadastroAtividade():Boolean
		{
			return _subMenuCadastroAtividade;
		}

		public function set subMenuCadastroAtividade(value:Boolean):void
		{
			_subMenuCadastroAtividade = value;
		}

		public function get subMenuCadastroComponente():Boolean
		{
			return _subMenuCadastroComponente;
		}

		public function set subMenuCadastroComponente(value:Boolean):void
		{
			_subMenuCadastroComponente = value;
		}

		public function get subMenuCadastroDefeito():Boolean
		{
			return _subMenuCadastroDefeito;
		}

		public function set subMenuCadastroDefeito(value:Boolean):void
		{
			_subMenuCadastroDefeito = value;
		}

		public function get subMenuCadastroEncapsulamento():Boolean
		{
			return _subMenuCadastroEncapsulamento;
		}

		public function set subMenuCadastroEncapsulamento(value:Boolean):void
		{
			_subMenuCadastroEncapsulamento = value;
		}

		public function get subMenuCadastroTipoComponente():Boolean
		{
			return _subMenuCadastroTipoComponente;
		}

		public function set subMenuCadastroTipoComponente(value:Boolean):void
		{
			_subMenuCadastroTipoComponente = value;
		}

		public function get subMenuCadastroEquipamento():Boolean
		{
			return _subMenuCadastroEquipamento;
		}

		public function set subMenuCadastroEquipamento(value:Boolean):void
		{
			_subMenuCadastroEquipamento = value;
		}

		public function get subMenuCadastroFabricante():Boolean
		{
			return _subMenuCadastroFabricante;
		}

		public function set subMenuCadastroFabricante(value:Boolean):void
		{
			_subMenuCadastroFabricante = value;
		}

		public function get subMenuCadastroLaboratorio():Boolean
		{
			return _subMenuCadastroLaboratorio;
		}

		public function set subMenuCadastroLaboratorio(value:Boolean):void
		{
			_subMenuCadastroLaboratorio = value;
		}

		public function get subMenuCadastroPessoa():Boolean
		{
			return _subMenuCadastroPessoa;
		}

		public function set subMenuCadastroPessoa(value:Boolean):void
		{
			_subMenuCadastroPessoa = value;
		}

		public function get subMenuCadastroUnidade():Boolean
		{
			return _subMenuCadastroUnidade;
		}

		public function set subMenuCadastroUnidade(value:Boolean):void
		{
			_subMenuCadastroUnidade = value;
		}

		public function get subMenuCadastroUsuario():Boolean
		{
			return _subMenuCadastroUsuario;
		}

		public function set subMenuCadastroUsuario(value:Boolean):void
		{
			_subMenuCadastroUsuario = value;
		}

		public function get subMenuCadastroPerfil():Boolean
		{
			return _subMenuCadastroPerfil;
		}

		public function set subMenuCadastroPerfil(value:Boolean):void
		{
			_subMenuCadastroPerfil = value;
		}

		public function get subMenuRecebimentouNotaFiscal():Boolean
		{
			return _subMenuRecebimentouNotaFiscal;
		}

		public function set subMenuRecebimentouNotaFiscal(value:Boolean):void
		{
			_subMenuRecebimentouNotaFiscal = value;
		}

		public function get subMenuRecebimentoLpu():Boolean
		{
			return _subMenuRecebimentoLpu;
		}

		public function set subMenuRecebimentoLpu(value:Boolean):void
		{
			_subMenuRecebimentoLpu = value;
		}

		public function get subMenuRecebimentoEtiqueta():Boolean
		{
			return _subMenuRecebimentoEtiqueta;
		}

		public function set subMenuRecebimentoEtiqueta(value:Boolean):void
		{
			_subMenuRecebimentoEtiqueta = value;
		}

		public function get subMenuAreaTecnicaListagemGeral():Boolean
		{
			return _subMenuAreaTecnicaListagemGeral;
		}

		public function set subMenuAreaTecnicaListagemGeral(value:Boolean):void
		{
			_subMenuAreaTecnicaListagemGeral = value;
		}

		public function get subMenuAreaTecnicaRequisicaoComponente():Boolean
		{
			return _subMenuAreaTecnicaRequisicaoComponente;
		}

		public function set subMenuAreaTecnicaRequisicaoComponente(value:Boolean):void
		{
			_subMenuAreaTecnicaRequisicaoComponente = value;
		}

		public function get subMenuAreaTecnicaLaudoTecnico():Boolean
		{
			return _subMenuAreaTecnicaLaudoTecnico;
		}

		public function set subMenuAreaTecnicaLaudoTecnico(value:Boolean):void
		{
			_subMenuAreaTecnicaLaudoTecnico = value;
		}

		public function get subMenuAreaTecnicaOrcamentoDiferenciado():Boolean
		{
			return _subMenuAreaTecnicaOrcamentoDiferenciado;
		}

		public function set subMenuAreaTecnicaOrcamentoDiferenciado(value:Boolean):void
		{
			_subMenuAreaTecnicaOrcamentoDiferenciado = value;
		}

		public function get subMenuExpedicaoBaixaOs():Boolean
		{
			return _subMenuExpedicaoBaixaOs;
		}

		public function set subMenuExpedicaoBaixaOs(value:Boolean):void
		{
			_subMenuExpedicaoBaixaOs = value;
		}

		public function get subMenuExpedicaoListagemNfSaida():Boolean
		{
			return _subMenuExpedicaoListagemNfSaida;
		}

		public function set subMenuExpedicaoListagemNfSaida(value:Boolean):void
		{
			_subMenuExpedicaoListagemNfSaida = value;
		}

		public function get subMenuPropostaBaixaOs():Boolean
		{
			return _subMenuPropostaBaixaOs;
		}

		public function set subMenuPropostaBaixaOs(value:Boolean):void
		{
			_subMenuPropostaBaixaOs = value;
		}

		public function get subMenuPropostaListagemProposta():Boolean
		{
			return _subMenuPropostaListagemProposta;
		}

		public function set subMenuPropostaListagemProposta(value:Boolean):void
		{
			_subMenuPropostaListagemProposta = value;
		}

		public function get subMenuEstoqueRequisicaoComponente():Boolean
		{
			return _subMenuEstoqueRequisicaoComponente;
		}

		public function set subMenuEstoqueRequisicaoComponente(value:Boolean):void
		{
			_subMenuEstoqueRequisicaoComponente = value;
		}

		public function get subMenuEstoqueComponente():Boolean
		{
			return _subMenuEstoqueComponente;
		}

		public function set subMenuEstoqueComponente(value:Boolean):void
		{
			_subMenuEstoqueComponente = value;
		}

		public function get cadastroSistemaRealizadoEm():Date
		{
			return _cadastroSistemaRealizadoEm;
		}

		public function set cadastroSistemaRealizadoEm(value:Date):void
		{
			_cadastroSistemaRealizadoEm = value;
		}

		public function get cadastroSistemaRealizadoPor():Usuario
		{
			return _cadastroSistemaRealizadoPor;
		}

		public function set cadastroSistemaRealizadoPor(value:Usuario):void
		{
			_cadastroSistemaRealizadoPor = value;
		}

		public function get cadastroSistemaAtivo():Boolean
		{
			return _cadastroSistemaAtivo;
		}

		public function set cadastroSistemaAtivo(value:Boolean):void
		{
			_cadastroSistemaAtivo = value;
		}

		public function get subMenuConsultaConsultaGeral():Boolean
		{
			return _subMenuConsultaConsultaGeral;
		}

		public function set subMenuConsultaConsultaGeral(value:Boolean):void
		{
			_subMenuConsultaConsultaGeral = value;
		}

		public function get acaoAtribuirTecnico():Boolean
		{
			return _acaoAtribuirTecnico;
		}

		public function set acaoAtribuirTecnico(value:Boolean):void
		{
			_acaoAtribuirTecnico = value;
		}

		public function get acaoAtribuirPrioridade():Boolean
		{
			return _acaoAtribuirPrioridade;
		}

		public function set acaoAtribuirPrioridade(value:Boolean):void
		{
			_acaoAtribuirPrioridade = value;
		}

		public function get acaoPermitirVisulizarObservacaoSigilosa():Boolean
		{
			return _acaoPermitirVisulizarObservacaoSigilosa;
		}

		public function set acaoPermitirVisulizarObservacaoSigilosa(value:Boolean):void
		{
			_acaoPermitirVisulizarObservacaoSigilosa = value;
		}

		public function get acaoPermitirAprovarDevolucaoSemReparo():Boolean
		{
			return _acaoPermitirAprovarDevolucaoSemReparo;
		}

		public function set acaoPermitirAprovarDevolucaoSemReparo(value:Boolean):void
		{
			_acaoPermitirAprovarDevolucaoSemReparo = value;
		}

		public function get acaoPermitirAprovarSolicitacaoLaudoTecnico():Boolean
		{
			return _acaoPermitirAprovarSolicitacaoLaudoTecnico;
		}

		public function set acaoPermitirAprovarSolicitacaoLaudoTecnico(value:Boolean):void
		{
			_acaoPermitirAprovarSolicitacaoLaudoTecnico = value;
		}

		public function get acaoPermitirAprovarSolicitacaoOrcamentoDiferenciado():Boolean
		{
			return _acaoPermitirAprovarSolicitacaoOrcamentoDiferenciado;
		}

		public function set acaoPermitirAprovarSolicitacaoOrcamentoDiferenciado(value:Boolean):void
		{
			_acaoPermitirAprovarSolicitacaoOrcamentoDiferenciado = value;
		}

		public function get acaoPermitirAprovarSolicitacaoProposta():Boolean
		{
			return _acaoPermitirAprovarSolicitacaoProposta;
		}

		public function set acaoPermitirAprovarSolicitacaoProposta(value:Boolean):void
		{
			_acaoPermitirAprovarSolicitacaoProposta = value;
		}

		public function get subMenuEstoqueDevolucaoComponente():Boolean
		{
			return _subMenuEstoqueDevolucaoComponente;
		}

		public function set subMenuEstoqueDevolucaoComponente(value:Boolean):void
		{
			_subMenuEstoqueDevolucaoComponente = value;
		}

		public function get subMenuEstoquePedidoCompra():Boolean
		{
			return _subMenuEstoquePedidoCompra;
		}

		public function set subMenuEstoquePedidoCompra(value:Boolean):void
		{
			_subMenuEstoquePedidoCompra = value;
		}

		public function get acaoDesabilitarAbaPropostaConsulta():Boolean
		{
			return _acaoDesabilitarAbaPropostaConsulta;
		}

		public function set acaoDesabilitarAbaPropostaConsulta(value:Boolean):void
		{
			_acaoDesabilitarAbaPropostaConsulta = value;
		}

		public function get acaoPrioridadeGerencialParaAtribuicoes():Boolean
		{
			return _acaoPrioridadeGerencialParaAtribuicoes;
		}

		public function set acaoPrioridadeGerencialParaAtribuicoes(value:Boolean):void
		{
			_acaoPrioridadeGerencialParaAtribuicoes = value;
		}

		public function get subMenuAvayaGerenciamentoEstoque():Boolean
		{
			return _subMenuAvayaGerenciamentoEstoque;
		}

		public function set subMenuAvayaGerenciamentoEstoque(value:Boolean):void
		{
			_subMenuAvayaGerenciamentoEstoque = value;
		}

		public function get acaoPrioridadeGerencialParaIncluirConsulta():Boolean
		{
			return _acaoPrioridadeGerencialParaIncluirConsulta;
		}

		public function set acaoPrioridadeGerencialParaIncluirConsulta(value:Boolean):void
		{
			_acaoPrioridadeGerencialParaIncluirConsulta = value;
		}

		public function get subMenuBaixaFaturamento():Boolean
		{
			return _subMenuBaixaFaturamento;
		}

		public function set subMenuBaixaFaturamento(value:Boolean):void
		{
			_subMenuBaixaFaturamento = value;
		}

		public function get subMenuListagemFaturamento():Boolean
		{
			return _subMenuListagemFaturamento;
		}

		public function set subMenuListagemFaturamento(value:Boolean):void
		{
			_subMenuListagemFaturamento = value;
		}

		public function get atualizarDadosOrdemServico():Boolean
		{
			return _atualizarDadosOrdemServico;
		}

		public function set atualizarDadosOrdemServico(value:Boolean):void
		{
			_atualizarDadosOrdemServico = value;
		}

		public function get acaoPrioridadeGerencialAprovacoes():Boolean
		{
			return _acaoPrioridadeGerencialAprovacoes;
		}

		public function set acaoPrioridadeGerencialAprovacoes(value:Boolean):void
		{
			_acaoPrioridadeGerencialAprovacoes = value;
		}

		public function get subMenuEstoqueListagemCompra():Boolean
		{
			return _subMenuEstoqueListagemCompra;
		}

		public function set subMenuEstoqueListagemCompra(value:Boolean):void
		{
			_subMenuEstoqueListagemCompra = value;
		}


	}
}
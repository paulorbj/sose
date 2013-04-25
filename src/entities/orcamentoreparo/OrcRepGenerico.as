package entities.orcamentoreparo
{
	import entities.administrativo.Usuario;
	import entities.recebimento.OrdemServico;
	
	import mx.collections.ArrayCollection;
	
	import utils.ScreenUtils;
	
	[Bindable]	
	[RemoteClass(alias="br.com.sose.entity.orcrepGenerico.OrcRepGenerico")]	
	public class OrcRepGenerico
	{
		
		private var _id:Number;
		private var _statusString:String;
		private var _ordemServico:OrdemServico;
		private var _dataEntrada:Date;
		private var _dataInicio:Date;
		private var _dataFim:Date;
		private var _dataLiberacao:Date;
		private var _dataAberturaLider:Date;
		private var _dataAberturaTecnico:Date;
		private var _dataEnvioParaLaudoTecnico:Date;
		private var _dataLaudoTecnicoFinalizado:Date;
		private var _dataRequisicaoOrcDiferenciado:Date;
		private var _dataAprovacaoOrcDiferenciadoLider:Date;
		private var _dataReprovacaoOrcDiferenciadoLider:Date;
		private var _dataAprovacaoPropostaOrcDiferenciado:Date;
		private var _dataReprovacaoPropostaOrcDiferenciadoLider:Date;
		private var _tecnicoResponsavel:Usuario;
		private var _observacao:String;
		private var _cadastroSistemaAtivo:Boolean;
		private var _condicao:String;
		private var _jaReparado:Boolean;
		private var _tempoGasto:int;
		private var _prioridade:Date;
		private var _dataLimite:Date;
		private var _listaAtividade:ArrayCollection;
		private var _listaDefeito:ArrayCollection;
		private var _listaComponente:ArrayCollection;
		private var _listaRequisicao:ArrayCollection;
		private var _transferido:Boolean;
		private var _isSelected:Boolean;
		private var _isEnabled:Boolean = true;
		private var _tempoNaServilogi:int;
		private var _tempoNoLaboratorio:int;
		private var _prazoReparo:int;
		private var _dataRequisicaoLaudoTecnico:Date;
		private var _componentePendente:Boolean;

		
		public function get id():Number
		{
			return _id;
		}

		public function set id(value:Number):void
		{
			_id = value;
		}

		public function get statusString():String
		{
			return _statusString;
		}

		public function set statusString(value:String):void
		{
			_statusString = value;
		}

		public function get ordemServico():OrdemServico
		{
			return _ordemServico;
		}

		public function set ordemServico(value:OrdemServico):void
		{
			_ordemServico = value;
		}

		public function get dataEntrada():Date
		{
			return _dataEntrada;
		}
		
		public function get dataEntradaString():String
		{
			if(_dataEntrada){
				return ScreenUtils.formatarDataDDMMYYYY(_dataEntrada);
			}else{
				return "";	
			}
		}

		public function set dataEntrada(value:Date):void
		{
			_dataEntrada = value;
		}

		public function get dataInicio():Date
		{
			return _dataInicio;
		}

		public function set dataInicio(value:Date):void
		{
			_dataInicio = value;
		}

		public function get dataFim():Date
		{
			return _dataFim;
		}

		public function set dataFim(value:Date):void
		{
			_dataFim = value;
		}

		public function get dataLiberacao():Date
		{
			return _dataLiberacao;
		}

		public function set dataLiberacao(value:Date):void
		{
			_dataLiberacao = value;
		}

		public function get dataAberturaLider():Date
		{
			return _dataAberturaLider;
		}

		public function set dataAberturaLider(value:Date):void
		{
			_dataAberturaLider = value;
		}

		public function get dataAberturaTecnico():Date
		{
			return _dataAberturaTecnico;
		}

		public function set dataAberturaTecnico(value:Date):void
		{
			_dataAberturaTecnico = value;
		}

		public function get dataEnvioParaLaudoTecnico():Date
		{
			return _dataEnvioParaLaudoTecnico;
		}

		public function set dataEnvioParaLaudoTecnico(value:Date):void
		{
			_dataEnvioParaLaudoTecnico = value;
		}

		public function get dataLaudoTecnicoFinalizado():Date
		{
			return _dataLaudoTecnicoFinalizado;
		}

		public function set dataLaudoTecnicoFinalizado(value:Date):void
		{
			_dataLaudoTecnicoFinalizado = value;
		}

		public function get dataRequisicaoOrcDiferenciado():Date
		{
			return _dataRequisicaoOrcDiferenciado;
		}

		public function set dataRequisicaoOrcDiferenciado(value:Date):void
		{
			_dataRequisicaoOrcDiferenciado = value;
		}

		public function get dataAprovacaoOrcDiferenciadoLider():Date
		{
			return _dataAprovacaoOrcDiferenciadoLider;
		}

		public function set dataAprovacaoOrcDiferenciadoLider(value:Date):void
		{
			_dataAprovacaoOrcDiferenciadoLider = value;
		}

		public function get dataReprovacaoOrcDiferenciadoLider():Date
		{
			return _dataReprovacaoOrcDiferenciadoLider;
		}

		public function set dataReprovacaoOrcDiferenciadoLider(value:Date):void
		{
			_dataReprovacaoOrcDiferenciadoLider = value;
		}

		public function get dataAprovacaoPropostaOrcDiferenciado():Date
		{
			return _dataAprovacaoPropostaOrcDiferenciado;
		}

		public function set dataAprovacaoPropostaOrcDiferenciado(value:Date):void
		{
			_dataAprovacaoPropostaOrcDiferenciado = value;
		}

		public function get dataReprovacaoPropostaOrcDiferenciadoLider():Date
		{
			return _dataReprovacaoPropostaOrcDiferenciadoLider;
		}

		public function set dataReprovacaoPropostaOrcDiferenciadoLider(value:Date):void
		{
			_dataReprovacaoPropostaOrcDiferenciadoLider = value;
		}

		public function get tecnicoResponsavel():Usuario
		{
			return _tecnicoResponsavel;
		}

		public function set tecnicoResponsavel(value:Usuario):void
		{
			_tecnicoResponsavel = value;
		}

		public function get observacao():String
		{
			return _observacao;
		}

		public function set observacao(value:String):void
		{
			_observacao = value;
		}

		public function get cadastroSistemaAtivo():Boolean
		{
			return _cadastroSistemaAtivo;
		}

		public function set cadastroSistemaAtivo(value:Boolean):void
		{
			_cadastroSistemaAtivo = value;
		}

		public function get condicao():String
		{
			return _condicao;
		}

		public function set condicao(value:String):void
		{
			_condicao = value;
		}

		public function get jaReparado():Boolean
		{
			return _jaReparado;
		}

		public function set jaReparado(value:Boolean):void
		{
			_jaReparado = value;
		}

		public function get tempoGasto():int
		{
			return _tempoGasto;
		}

		public function set tempoGasto(value:int):void
		{
			_tempoGasto = value;
		}

		public function get prioridade():Date
		{
			return _prioridade;
		}

		public function set prioridade(value:Date):void
		{
			_prioridade = value;
		}

		public function get listaAtividade():ArrayCollection
		{
			return _listaAtividade;
		}

		public function set listaAtividade(value:ArrayCollection):void
		{
			_listaAtividade = value;
		}

		public function get listaDefeito():ArrayCollection
		{
			return _listaDefeito;
		}

		public function set listaDefeito(value:ArrayCollection):void
		{
			_listaDefeito = value;
		}

		public function get listaComponente():ArrayCollection
		{
			return _listaComponente;
		}

		public function set listaComponente(value:ArrayCollection):void
		{
			_listaComponente = value;
		}

		public function get transferido():Boolean
		{
			return _transferido;
		}

		public function set transferido(value:Boolean):void
		{
			_transferido = value;
		}

		public function get isSelected():Boolean
		{
			return _isSelected;
		}

		public function set isSelected(value:Boolean):void
		{
			_isSelected = value;
		}

		public function get isEnabled():Boolean
		{
			return _isEnabled;
		}

		public function set isEnabled(value:Boolean):void
		{
			_isEnabled = value;
		}

		public function get listaRequisicao():ArrayCollection
		{
			if(_listaRequisicao == null){
				_listaRequisicao = new ArrayCollection();
			}
			return _listaRequisicao;
		}

		public function set listaRequisicao(value:ArrayCollection):void
		{
			_listaRequisicao = value;
		}

		public function get tempoNoLaboratorio():int {
			var dataFinal:Date = new Date();
			// Calcula a diferença entre hoje e da data de inicio
			var diferenca:Number;
			if(_dataEntrada)
				diferenca = dataFinal.time - _dataEntrada.time;
			else
				diferenca = 0;
			// Quantidade de milissegundos em um dia		
			var tempoDia:int = 1000 * 60 * 60 * 24;
			return diferenca / tempoDia;	
		}
		
		public function get tempoNaServilogi():int {
			var dataFinal:Date = new Date();
			// Calcula a diferença entre hoje e da data de inicio
			if(_ordemServico == null || _ordemServico.notaFiscal == null || _ordemServico.notaFiscal.dataChegada == null){
				return 0;
			}
			var diferenca:Number = dataFinal.time - _ordemServico.notaFiscal.dataChegada.time;
			// Quantidade de milissegundos em um dia		
			var tempoDia:int = 1000 * 60 * 60 * 24;
			return diferenca / tempoDia;	
		}

		public function set tempoNaServilogi(value:int):void
		{
			_tempoNaServilogi = value;
		}

		public function set tempoNoLaboratorio(value:int):void
		{
			_tempoNoLaboratorio = value;
		}
		


		public function get dataLimite():Date
		{
			return _dataLimite
		}

		public function set dataLimite(value:Date):void
		{
			_dataLimite = value;
		}

		public function get prazoReparo():int
		{
			var tempoDia:int = 1000 * 60 * 60 * 24;
			var diferenca:Number;
			if(_prioridade){
				 diferenca =_prioridade.time - new Date().time;
				return diferenca/tempoDia;
			}else{
				diferenca = ScreenUtils.somarDias(_dataLimite,_ordemServico.cliente.prazoDevolucao).time - new Date().time;
				return diferenca/tempoDia;
			}
		}

		public function set prazoReparo(value:int):void
		{
			_prazoReparo = value;
		}
		
		public function get prioridadeString():String
		{
			if(_prioridade)
				return ScreenUtils.formatarDataDDMMYYYY(_prioridade);
			else{
				return "";
			}
		}
		
		public function get osPaiOrdenar():String
		{
			if(_ordemServico.unidadePai){
				return _ordemServico.unidadePai.numeroOrdemServico;
			}else{
				return _ordemServico.numeroOrdemServico;
			}
		}
		
		public function get osFilhaOrdenar():String
		{
			if(_ordemServico.unidadePai){
				return _ordemServico.numeroOrdemServico;
			}else{
				return "0";
			}
		}

		public function get dataRequisicaoLaudoTecnico():Date
		{
			return _dataRequisicaoLaudoTecnico;
		}

		public function set dataRequisicaoLaudoTecnico(value:Date):void
		{
			_dataRequisicaoLaudoTecnico = value;
		}

		public function get componentePendente():Boolean
		{
			return _componentePendente;
		}

		public function set componentePendente(value:Boolean):void
		{
			_componentePendente = value;
		}



	}
}
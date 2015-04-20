package to
{
	import utils.ScreenUtils;
	
	[Bindable]	
	[RemoteClass(alias="br.com.sose.to.OrcRepGenericoTO")]	
	public class OrcRepGenericoTO
	{
		
		private var _finalidade:String;
		
		private var _statusString:String;
		
		private var _tecnico:String;
		
		private var _numeroOrdemServico:String;
		
		private var _numeroOrdemServicoPai:String;
		
		private var _cliente:String;
		
		private var _unidade:String;
		
		private var _numeroNotaFiscal:String;
		
		private var _numeroSerieFabricante:String;
		
		private var _numeroSerieCliente:String;
		
		private var _laboratorio:String;
		
		private var _dataPrioridade:Date;
		
		private var _dataChegadaServilogi:Date;
		
		private var _dataEntradaReparo:Date;
		
		private var _laudoTecnicoAprovado:Date;
		
		private var _laudoTecnicoReprovado:Date;
		
		private var _propostaAprovada:Date;
		
		private var _propostaReprovada:Date;
		
		private var _orcamentoDiferenciadoRejeitado:Date;
		
		private var _rejeitadoPeloLider:Date;
		
		private var _idOrcRep:Number;
		
		private var _idTecnicoResponsavel:Number;
		
		private var _dataLimite:Date;
		
		private var _prazoDevolucao:Number;
		
		private var _tempoNaServilogi:int;
		private var _tempoNoLaboratorio:int;
		private var _prazoReparo:int;
		
		private var _isSelected:Boolean;
		
		private var _isEnabled:Boolean = true;
		
		private var _bloqueado:Number;
		
		private var _caseAvaya:String;
		
		private var _clienteAvaya:String;
		
		private var _condicao:String;
		
		private var _idLaboratorio:Number;
		
		private var _componentePendente:Boolean;
		
		private var _componenteEmFalta:Boolean;
		
		private var _criadoFromOrcamento:Boolean;
		
		public function OrcRepGenericoTO()
		{
		}
		
		
		public function get finalidade():String
		{
			return _finalidade;
		}
		
		public function set finalidade(value:String):void
		{
			_finalidade = value;
		}
		
		public function get statusString():String
		{
			return _statusString;
		}
		
		public function set statusString(value:String):void
		{
			_statusString = value;
		}
		
		public function get tecnico():String
		{
			return _tecnico;
		}
		
		public function set tecnico(value:String):void
		{
			_tecnico = value;
		}
		
		public function get numeroOrdemServico():String
		{
			return _numeroOrdemServico;
		}
		
		public function set numeroOrdemServico(value:String):void
		{
			_numeroOrdemServico = value;
		}
		
		public function get numeroOrdemServicoPai():String
		{
			return _numeroOrdemServicoPai;
		}
		
		public function set numeroOrdemServicoPai(value:String):void
		{
			_numeroOrdemServicoPai = value;
		}
		
		public function get cliente():String
		{
			return _cliente;
		}
		
		public function set cliente(value:String):void
		{
			_cliente = value;
		}
		
		public function get unidade():String
		{
			return _unidade;
		}
		
		public function set unidade(value:String):void
		{
			_unidade = value;
		}
		
		public function get numeroNotaFiscal():String
		{
			return _numeroNotaFiscal;
		}
		
		public function set numeroNotaFiscal(value:String):void
		{
			_numeroNotaFiscal = value;
		}
		
		public function get numeroSerieFabricante():String
		{
			return _numeroSerieFabricante;
		}
		
		public function set numeroSerieFabricante(value:String):void
		{
			_numeroSerieFabricante = value;
		}
		
		public function get numeroSerieCliente():String
		{
			return _numeroSerieCliente;
		}
		
		public function set numeroSerieCliente(value:String):void
		{
			_numeroSerieCliente = value;
		}
		
		public function get laboratorio():String
		{
			return _laboratorio;
		}
		
		public function set laboratorio(value:String):void
		{
			_laboratorio = value;
		}
		
		public function get dataPrioridade():Date
		{
			return _dataPrioridade;
		}
		
		public function set dataPrioridade(value:Date):void
		{
			_dataPrioridade = value;
		}
		
		public function get dataChegadaServilogi():Date
		{
			return _dataChegadaServilogi;
		}
		
		public function set dataChegadaServilogi(value:Date):void
		{
			_dataChegadaServilogi = value;
		}
		
		public function get dataEntradaReparo():Date
		{
			return _dataEntradaReparo;
		}
		
		public function set dataEntradaReparo(value:Date):void
		{
			_dataEntradaReparo = value;
		}
		
		public function get laudoTecnicoAprovado():Date
		{
			return _laudoTecnicoAprovado;
		}
		
		public function set laudoTecnicoAprovado(value:Date):void
		{
			_laudoTecnicoAprovado = value;
		}
		
		public function get laudoTecnicoReprovado():Date
		{
			return _laudoTecnicoReprovado;
		}
		
		public function set laudoTecnicoReprovado(value:Date):void
		{
			_laudoTecnicoReprovado = value;
		}
		
		public function get propostaAprovada():Date
		{
			return _propostaAprovada;
		}
		
		public function set propostaAprovada(value:Date):void
		{
			_propostaAprovada = value;
		}
		
		public function get propostaReprovada():Date
		{
			return _propostaReprovada;
		}
		
		public function set propostaReprovada(value:Date):void
		{
			_propostaReprovada = value;
		}
		
		public function get orcamentoDiferenciadoRejeitado():Date
		{
			return _orcamentoDiferenciadoRejeitado;
		}
		
		public function set orcamentoDiferenciadoRejeitado(value:Date):void
		{
			_orcamentoDiferenciadoRejeitado = value;
		}
		
		public function get rejeitadoPeloLider():Date
		{
			return _rejeitadoPeloLider;
		}
		
		public function set rejeitadoPeloLider(value:Date):void
		{
			_rejeitadoPeloLider = value;
		}
		
		public function get idOrcRep():Number
		{
			return _idOrcRep;
		}
		
		public function set idOrcRep(value:Number):void
		{
			_idOrcRep = value;
		}
		
		public function get idTecnicoResponsavel():Number
		{
			return _idTecnicoResponsavel;
		}
		
		public function set idTecnicoResponsavel(value:Number):void
		{
			_idTecnicoResponsavel = value;
		}
		
		public function get tempoNoLaboratorio():int {
			var dataFinal:Date = new Date();
			// Calcula a diferença entre hoje e da data de inicio
			if(_dataEntradaReparo == null){
				if(_dataChegadaServilogi)
					_dataEntradaReparo = _dataChegadaServilogi;
				else
					_dataEntradaReparo = new Date();
			}
			var diferenca:Number = dataFinal.time - _dataEntradaReparo.time;
			// Quantidade de milissegundos em um dia		
			var tempoDia:int = 1000 * 60 * 60 * 24;
			return diferenca / tempoDia;	
		}
		
		public function get tempoNaServilogi():int {
			var dataFinal:Date = new Date();
			// Calcula a diferença entre hoje e da data de inicio
			if(_dataChegadaServilogi == null){
				_dataChegadaServilogi = new Date();
			}
			var diferenca:Number = dataFinal.time - _dataChegadaServilogi.time;
			// Quantidade de milissegundos em um dia		
			var tempoDia:int = 1000 * 60 * 60 * 24;
			return diferenca / tempoDia;	
		}
		
		
		public function get prazoReparo():int
		{
			var tempoDia:int = 1000 * 60 * 60 * 24;
			var diferenca:Number;
			if(_dataPrioridade){
				diferenca =_dataPrioridade.time - new Date().time;
				return diferenca/tempoDia;
			}else{
				diferenca = ScreenUtils.somarDias(_dataLimite,_prazoDevolucao).time - new Date().time;
				return diferenca/tempoDia;
			}
		}
		
		public function get prioridadeString():String
		{
			if(_dataPrioridade)
				return ScreenUtils.formatarDataDDMMYYYY(_dataPrioridade);
			else{
				return "";
			}
		}
		
		public function get osPaiOrdenar():String
		{
			if(_numeroOrdemServicoPai){
				return _numeroOrdemServicoPai;
			}else{
				return _numeroOrdemServico;
			}
		}
		
		public function get osFilhaOrdenar():String
		{
			if(_numeroOrdemServicoPai){
				return _numeroOrdemServicoPai;
			}else{
				return "0";
			}
		}
		
		public function get prazoDevolucao():Number
		{
			return _prazoDevolucao;
		}
		
		public function set prazoDevolucao(value:Number):void
		{
			_prazoDevolucao = value;
		}
		
		public function get dataLimite():Date
		{
			return _dataLimite;
		}
		
		public function set dataLimite(value:Date):void
		{
			_dataLimite = value;
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
		
		public function set tempoNaServilogi(value:int):void
		{
			_tempoNaServilogi = value;
		}
		
		public function set tempoNoLaboratorio(value:int):void
		{
			_tempoNoLaboratorio = value;
		}
		
		public function set prazoReparo(value:int):void
		{
			_prazoReparo = value;
		}
		
		public function get bloqueado():Number
		{
			return _bloqueado;
		}
		
		public function set bloqueado(value:Number):void
		{
			_bloqueado = value;
		}
		
		public function get caseAvaya():String
		{
			return _caseAvaya;
		}
		
		public function set caseAvaya(value:String):void
		{
			_caseAvaya = value;
		}
		
		public function get clienteAvaya():String
		{
			return _clienteAvaya;
		}
		
		public function set clienteAvaya(value:String):void
		{
			_clienteAvaya = value;
		}
		
		public function get condicao():String
		{
			return _condicao;
		}
		
		public function set condicao(value:String):void
		{
			_condicao = value;
		}
		
		public function get idLaboratorio():Number
		{
			return _idLaboratorio;
		}
		
		public function set idLaboratorio(value:Number):void
		{
			_idLaboratorio = value;
		}

		public function get componentePendente():Boolean
		{
			return _componentePendente;
		}

		public function set componentePendente(value:Boolean):void
		{
			_componentePendente = value;
		}

		public function get criadoFromOrcamento():Boolean
		{
			return _criadoFromOrcamento;
		}

		public function set criadoFromOrcamento(value:Boolean):void
		{
			_criadoFromOrcamento = value;
		}

		public function get componenteEmFalta():Boolean
		{
			return _componenteEmFalta;
		}

		public function set componenteEmFalta(value:Boolean):void
		{
			_componenteEmFalta = value;
		}
		
		
	}
}
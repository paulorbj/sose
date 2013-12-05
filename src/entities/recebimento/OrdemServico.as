package entities.recebimento
{
	import entities.administrativo.ItemLpu;
	import entities.administrativo.Lpu;
	import entities.administrativo.Unidade;
	import entities.administrativo.Usuario;
	import entities.administrativo.parceiros.Pessoa;
	import entities.expedicao.NotaFiscalRemessa;
	import entities.faturamento.Faturamento;
	import entities.laudoTecnico.LaudoTecnico;
	import entities.orcamentoDiferenciado.OrcamentoDiferenciado;
	import entities.orcamentoreparo.Orcamento;
	import entities.orcamentoreparo.Reparo;
	import entities.proposta.Proposta;
	
	import mx.collections.ArrayCollection;
	
	import utils.ScreenUtils;
	
	[Bindable]	
	[RemoteClass(alias="br.com.sose.entity.recebimento.OrdemServico")]	
	public class OrdemServico
	{
		private var _id:Number;
		private var _numeroOrdemServico:String;
		private var _serieCliente:String;
		private var _serieFabricante:String;
		private var _ordemServicoCliente:String;
		private var _observacao:String;
		private var _dataAbertura:Date;
		private var _dataFinalizacao:Date;
		private var _garantia:Boolean;
		private var _osGarantia:OrdemServico;
		private var _dataGarantiaAte:Date;
		private var _estenderGarantia:Boolean;
		private var _itemNotaFiscal:ItemNotaFiscal;
		private var _notaFiscal:NotaFiscal;
		private var _notaFiscalSaida:NotaFiscalRemessa;
		private var _cliente:Pessoa;
		private var _placasFilhas:ArrayCollection;
		private var _unidadePai:OrdemServico;
		private var _unidade:Unidade;
		private var _lpu:Lpu;
		private var _orcamento:Orcamento;
		private var _reparo:Reparo;
		private var _proposta:Proposta;
		private var _faturamento:Faturamento;
		private var _laudoTecnico:LaudoTecnico;
		private var _orcamentoDiferenciado:OrcamentoDiferenciado;
		private var _usuario:Usuario;
		private var _statusString:String;
		private var _isSelected:Boolean;
		private var _isEnabled:Boolean = true;
		private var _bloqueado:Number;
		private var _valorFaturado:Number;
		private var _valorSistema:Number;
		private var _origemFaturamento:String;
		private var _caseAvaya:String;
		private var _clienteAvaya:String;
		private var _condicaoReparo:String;
		private var _condicaoOrcamento:String;
		private var _numeroOrdemServicoPai:String;
		private var _nomeCliente:String;
		private var _nomeUnidade:String;
		private var _nomeFabricante:String;
		private var _nomeUnidadeLpu:String;
		private var _nomeLaboratorio:String;
		private var _nomePrestadorServico:String;
		private var _numeroNotaFiscal:String;
		private var _dataConhecimentoExpedicao:Date;
		private var _idAntigo:Number;
		private var _observacaoExpedicao:String;
		private var _dataChegadaNotaFiscal:Date;
		private var _dataCriacaoProposta:Date;
		private var _isAprovado:Boolean;
		private var _dataNotaFiscal:Date;
		private var _dataEmissaoNotaFiscalSaida:Date;
		private var _valorUnitario:Number;
		private var _numeroProposta:String;
		private var _dataCriacaoNotaFiscal:Date;
		private var _numeroNotaFiscalSaida:String;
		private var _idOrcamento:Number;
		private var _idReparo:Number;
		private var _idCliente:Number;
		private var _idNotaFiscal:Number;
		private var _freteUnitarioProposta:Number;
		private var _freteUnitarioExpedicao:Number;
		private var _valorFreteCobrado:Number;
		private var _observacaoConsulta:String;
		private var _isEnabledBaixaExpedicao:Boolean = true;
		private var _itemLpu:ItemLpu;

		public function OrdemServico()
		{
			_id = 0;
		}
		
		public function get id():Number
		{
			return _id;
		}

		public function set id(value:Number):void
		{
			_id = value;
		}

		public function get serieCliente():String
		{
			return _serieCliente;
		}

		public function set serieCliente(value:String):void
		{
			_serieCliente = value;
		}

		public function get serieFabricante():String
		{
			return _serieFabricante;
		}

		public function set serieFabricante(value:String):void
		{
			_serieFabricante = value;
		}

		public function get ordemServicoCliente():String
		{
			return _ordemServicoCliente;
		}

		public function set ordemServicoCliente(value:String):void
		{
			_ordemServicoCliente = value;
		}

		public function get observacao():String
		{
			return _observacao;
		}

		public function set observacao(value:String):void
		{
			_observacao = value;
		}

		public function get dataAbertura():Date
		{
			return _dataAbertura;
		}

		public function set dataAbertura(value:Date):void
		{
			_dataAbertura = value;
		}

		public function get dataFinalizacao():Date
		{
			return _dataFinalizacao;
		}

		public function set dataFinalizacao(value:Date):void
		{
			_dataFinalizacao = value;
		}

		public function get garantia():Boolean
		{
			return _garantia;
		}

		public function set garantia(value:Boolean):void
		{
			_garantia = value;
		}

		public function get itemNotaFiscal():ItemNotaFiscal
		{
			return _itemNotaFiscal;
		}

		public function set itemNotaFiscal(value:ItemNotaFiscal):void
		{
			_itemNotaFiscal = value;
		}

		public function get notaFiscal():NotaFiscal
		{
			return _notaFiscal;
		}

		public function set notaFiscal(value:NotaFiscal):void
		{
			_notaFiscal = value;
		}

		public function get notaFiscalSaida():NotaFiscalRemessa
		{
			return _notaFiscalSaida;
		}

		public function set notaFiscalSaida(value:NotaFiscalRemessa):void
		{
			_notaFiscalSaida = value;
		}

		public function get cliente():Pessoa
		{
			return _cliente;
		}

		public function set cliente(value:Pessoa):void
		{
			_cliente = value;
		}

		public function get placasFilhas():ArrayCollection
		{
			if(_placasFilhas == null){
				_placasFilhas = new ArrayCollection();
			}
			return _placasFilhas;
		}

		public function set placasFilhas(value:ArrayCollection):void
		{
			_placasFilhas = value;
		}

		public function get unidadePai():OrdemServico
		{
			return _unidadePai;
		}

		public function set unidadePai(value:OrdemServico):void
		{
			_unidadePai = value;
		}

		public function get unidade():Unidade
		{
			return _unidade;
		}

		public function set unidade(value:Unidade):void
		{
			_unidade = value;
		}

		public function get lpu():Lpu
		{
			return _lpu;
		}

		public function set lpu(value:Lpu):void
		{
			_lpu = value;
		}

		public function get orcamento():Orcamento
		{
			return _orcamento;
		}

		public function set orcamento(value:Orcamento):void
		{
			_orcamento = value;
		}

		public function get reparo():Reparo
		{
			return _reparo;
		}

		public function set reparo(value:Reparo):void
		{
			_reparo = value;
		}

		public function get proposta():Proposta
		{
			return _proposta;
		}

		public function set proposta(value:Proposta):void
		{
			_proposta = value;
		}

		public function get faturamento():Faturamento
		{
			return _faturamento;
		}

		public function set faturamento(value:Faturamento):void
		{
			_faturamento = value;
		}

		public function get laudoTecnico():LaudoTecnico
		{
			return _laudoTecnico;
		}

		public function set laudoTecnico(value:LaudoTecnico):void
		{
			_laudoTecnico = value;
		}

		public function get usuario():Usuario
		{
			return _usuario;
		}

		public function set usuario(value:Usuario):void
		{
			_usuario = value;
		}

		public function get statusString():String
		{
			return _statusString;
		}

		public function set statusString(value:String):void
		{
			_statusString = value;
		}

		public function get numeroOrdemServico():String
		{
			return _numeroOrdemServico;
		}

		public function set numeroOrdemServico(value:String):void
		{
			_numeroOrdemServico = value;
		}

		public function get osGarantia():OrdemServico
		{
			return _osGarantia;
		}

		public function set osGarantia(value:OrdemServico):void
		{
			_osGarantia = value;
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

		public function get orcamentoDiferenciado():OrcamentoDiferenciado
		{
			return _orcamentoDiferenciado;
		}

		public function set orcamentoDiferenciado(value:OrcamentoDiferenciado):void
		{
			_orcamentoDiferenciado = value;
		}

		public function get bloqueado():Number
		{
			return _bloqueado;
		}

		public function set bloqueado(value:Number):void
		{
			_bloqueado = value;
		}
		
		public function get idItemNotaFiscal():Number
		{
			return _itemNotaFiscal.id;
		}

		public function get dataGarantiaAte():Date
		{
			return _dataGarantiaAte;
		}

		public function set dataGarantiaAte(value:Date):void
		{
			_dataGarantiaAte = value;
		}

		public function get estenderGarantia():Boolean
		{
			return _estenderGarantia;
		}
		
		public function get dataGarantiaAteString():String
		{
			if(_dataGarantiaAte)
				return ScreenUtils.formatarDataDDMMYYYY(_dataGarantiaAte);
			else{
				return "";
			}

		}

		public function set estenderGarantia(value:Boolean):void
		{
			_estenderGarantia = value;
		}

		public function get valorFaturado():Number
		{
			return _valorFaturado;
		}

		public function set valorFaturado(value:Number):void
		{
			_valorFaturado = value;
		}

		public function get valorSistema():Number
		{
			return _valorSistema;
		}

		public function set valorSistema(value:Number):void
		{
			_valorSistema = value;
		}

		public function get origemFaturamento():String
		{
			return _origemFaturamento;
		}

		public function set origemFaturamento(value:String):void
		{
			_origemFaturamento = value;
		}
		
		public function get isVisibleGarantiaExtensao():Boolean
		{
			if(_unidadePai == null){
				return true;
			}else{
				return false;
			}
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

		public function get condicaoReparo():String
		{
			return _condicaoReparo;
		}

		public function set condicaoReparo(value:String):void
		{
			_condicaoReparo = value;
		}

		public function get condicaoOrcamento():String
		{
			return _condicaoOrcamento;
		}

		public function set condicaoOrcamento(value:String):void
		{
			_condicaoOrcamento = value;
		}

		public function get numeroOrdemServicoPai():String
		{
			return _numeroOrdemServicoPai;
		}

		public function set numeroOrdemServicoPai(value:String):void
		{
			_numeroOrdemServicoPai = value;
		}

		public function get nomeCliente():String
		{
			return _nomeCliente;
		}

		public function set nomeCliente(value:String):void
		{
			_nomeCliente = value;
		}

		public function get nomeUnidade():String
		{
			return _nomeUnidade;
		}

		public function set nomeUnidade(value:String):void
		{
			_nomeUnidade = value;
		}

		public function get nomeFabricante():String
		{
			return _nomeFabricante;
		}

		public function set nomeFabricante(value:String):void
		{
			_nomeFabricante = value;
		}

		public function get nomeUnidadeLpu():String
		{
			return _nomeUnidadeLpu;
		}

		public function set nomeUnidadeLpu(value:String):void
		{
			_nomeUnidadeLpu = value;
		}

		public function get nomeLaboratorio():String
		{
			return _nomeLaboratorio;
		}

		public function set nomeLaboratorio(value:String):void
		{
			_nomeLaboratorio = value;
		}

		public function get nomePrestadorServico():String
		{
			return _nomePrestadorServico;
		}

		public function set nomePrestadorServico(value:String):void
		{
			_nomePrestadorServico = value;
		}

		public function get numeroNotaFiscal():String
		{
			return _numeroNotaFiscal;
		}

		public function set numeroNotaFiscal(value:String):void
		{
			_numeroNotaFiscal = value;
		}

		public function get dataConhecimentoExpedicao():Date
		{
			return _dataConhecimentoExpedicao;
		}

		public function set dataConhecimentoExpedicao(value:Date):void
		{
			_dataConhecimentoExpedicao = value;
		}

		public function get idAntigo():Number
		{
			return _idAntigo;
		}

		public function set idAntigo(value:Number):void
		{
			_idAntigo = value;
		}

		public function get observacaoExpedicao():String
		{
			return _observacaoExpedicao;
		}

		public function set observacaoExpedicao(value:String):void
		{
			_observacaoExpedicao = value;
		}

		public function get dataChegadaNotaFiscal():Date
		{
			return _dataChegadaNotaFiscal;
		}

		public function set dataChegadaNotaFiscal(value:Date):void
		{
			_dataChegadaNotaFiscal = value;
		}

		public function get dataCriacaoProposta():Date
		{
			return _dataCriacaoProposta;
		}

		public function set dataCriacaoProposta(value:Date):void
		{
			_dataCriacaoProposta = value;
		}

		public function get isAprovado():Boolean
		{
			return _isAprovado;
		}

		public function set isAprovado(value:Boolean):void
		{
			_isAprovado = value;
		}

		public function get dataNotaFiscal():Date
		{
			return _dataNotaFiscal;
		}

		public function set dataNotaFiscal(value:Date):void
		{
			_dataNotaFiscal = value;
		}

		public function get dataEmissaoNotaFiscalSaida():Date
		{
			return _dataEmissaoNotaFiscalSaida;
		}

		public function set dataEmissaoNotaFiscalSaida(value:Date):void
		{
			_dataEmissaoNotaFiscalSaida = value;
		}

		public function get valorUnitario():Number
		{
			return _valorUnitario;
		}

		public function set valorUnitario(value:Number):void
		{
			_valorUnitario = value;
		}

		public function get numeroProposta():String
		{
			return _numeroProposta;
		}

		public function set numeroProposta(value:String):void
		{
			_numeroProposta = value;
		}

		public function get dataCriacaoNotaFiscal():Date
		{
			return _dataCriacaoNotaFiscal;
		}

		public function set dataCriacaoNotaFiscal(value:Date):void
		{
			_dataCriacaoNotaFiscal = value;
		}

		public function get numeroNotaFiscalSaida():String
		{
			return _numeroNotaFiscalSaida;
		}

		public function set numeroNotaFiscalSaida(value:String):void
		{
			_numeroNotaFiscalSaida = value;
		}
		
		public function get osPaiOrdenar():String
		{
			if(_unidadePai){
				return _unidadePai.numeroOrdemServico;
			}else{
				return numeroOrdemServico;
			}
		}
		
		public function get osFilhaOrdenar():String
		{
			if(unidadePai){
				return numeroOrdemServico;
			}else{
				return "0";
			}
		}

		public function get ordenarPorItemNotaFiscal():Number
		{
			if(_itemNotaFiscal && _itemNotaFiscal.idTemp){
				return _itemNotaFiscal.idTemp;
			}else{
				return 0;
			}
		}
		
		public function get idOrcamento():Number
		{
			return _idOrcamento;
		}

		public function set idOrcamento(value:Number):void
		{
			_idOrcamento = value;
		}

		public function get idReparo():Number
		{
			return _idReparo;
		}

		public function set idReparo(value:Number):void
		{
			_idReparo = value;
		}

		public function get idCliente():Number
		{
			return _idCliente;
		}

		public function set idCliente(value:Number):void
		{
			_idCliente = value;
		}

		public function get idNotaFiscal():Number
		{
			return _idNotaFiscal;
		}

		public function set idNotaFiscal(value:Number):void
		{
			_idNotaFiscal = value;
		}

		public function get freteUnitarioProposta():Number
		{
			return _freteUnitarioProposta;
		}

		public function set freteUnitarioProposta(value:Number):void
		{
			_freteUnitarioProposta = value;
		}

		public function get freteUnitarioExpedicao():Number
		{
			return _freteUnitarioExpedicao;
		}

		public function set freteUnitarioExpedicao(value:Number):void
		{
			_freteUnitarioExpedicao = value;
		}

		public function get valorFreteCobrado():Number
		{
			return _valorFreteCobrado;
		}

		public function set valorFreteCobrado(value:Number):void
		{
			_valorFreteCobrado = value;
		}

		public function get tempoNaServilogi():int {
			var dataFinal:Date = new Date();
			// Calcula a diferença entre hoje e da data de inicio

			var diferenca:Number = dataFinal.time - _notaFiscal.dataChegada.time;
			// Quantidade de milissegundos em um dia		
			var tempoDia:int = 1000 * 60 * 60 * 24;
			return diferenca / tempoDia;	
		}

		public function get observacaoConsulta():String
		{
			return _observacaoConsulta;
		}

		public function set observacaoConsulta(value:String):void
		{
			_observacaoConsulta = value;
		}

		public function get isEnabledBaixaExpedicao():Boolean
		{
			if(this.unidadePai == null){
				return true;
			}else{
				return false;
			}
		}
		
		public function set isEnabledBaixaExpedicao(value:Boolean):void
		{
			_isEnabled = value;
		}

		public function get itemLpu():ItemLpu
		{
			return _itemLpu;
		}

		public function set itemLpu(value:ItemLpu):void
		{
			_itemLpu = value;
		}


	}
}
package entities.faturamento
{
	import entities.administrativo.Usuario;
	import entities.administrativo.parceiros.Pessoa;
	import entities.recebimento.OrdemServico;
	
	import mx.collections.ArrayCollection;
	import mx.collections.Sort;
	import mx.collections.SortField;
	
	import utils.ScreenUtils;
	
	[Bindable]	
	[RemoteClass(alias="br.com.sose.entity.faturamento.Faturamento")]	
	public class Faturamento
	{
		
		private var _id:Number;
		private var _nome:String;	
		private var _numeroFatura:String;		
		private var _dataEmissaoFatura:Date;	
		private var _dataVencimentoFatura:Date;
		private var _dataPagamentoFatura:Date;		
		private var _statusString:String;		
		private var _cliente:Pessoa;		
		private var _realizadoPor:Usuario;
		private var _dataCriacaoPreFatura:Date;
		private var _dataCriacaoFatura:Date;
		private var _listaOrdemServico:ArrayCollection;
		private var _totalFatura:Number;
		private var _freteCobrado:Number;
		private var _valorPago:Number;
		private var _cobrarFrete:Boolean;
		private var _observacao:String;
		private var sortAtividade:Sort;
		private var _dataCancelamento:Date;
		
		public function Faturamento(){
			_cobrarFrete = true;
			sortAtividade = new Sort();
			sortAtividade.fields=[new SortField("numeroOrdemServico",true,false,true)];

		}
		
		public function get id():Number
		{
			return _id;
		}
		
		public function set id(value:Number):void
		{
			_id = value;
		}
		
		public function get nome():String
		{
			return _nome;
		}
		
		public function set nome(value:String):void
		{
			_nome = value;
		}
		
		public function get numeroFatura():String
		{
			return _numeroFatura;
		}
		
		public function set numeroFatura(value:String):void
		{
			_numeroFatura = value;
		}
		
		public function get dataEmissaoFatura():Date
		{
			return _dataEmissaoFatura;
		}
		
		public function set dataEmissaoFatura(value:Date):void
		{
			_dataEmissaoFatura = value;
		}
		
		public function get dataEmissaoFaturaString():String
		{
			if(_dataEmissaoFatura)
				return ScreenUtils.formatarDataDDMMYYYY(_dataEmissaoFatura);
			else{
				return "";
			}
		}
		
		public function get dataVencimentoFatura():Date
		{
			return _dataVencimentoFatura;
		}
		
		public function set dataVencimentoFatura(value:Date):void
		{
			_dataVencimentoFatura = value;
		}
		
		public function get dataVencimentoFaturaString():String
		{
			if(_dataVencimentoFatura)
				return ScreenUtils.formatarDataDDMMYYYY(_dataVencimentoFatura);
			else{
				return "";
			}
		}
		
		public function get dataPagamentoFatura():Date
		{
			return _dataPagamentoFatura;
		}
		
		public function set dataPagamentoFatura(value:Date):void
		{
			_dataPagamentoFatura = value;
		}
		
		public function get dataPagamentoFaturaString():String
		{
			if(_dataPagamentoFatura)
				return ScreenUtils.formatarDataDDMMYYYY(_dataPagamentoFatura);
			else{
				return "";
			}
		}
		
		public function get statusString():String
		{
			return _statusString;
		}
		
		public function set statusString(value:String):void
		{
			_statusString = value;
		}
		
		public function get cliente():Pessoa
		{
			return _cliente;
		}
		
		public function set cliente(value:Pessoa):void
		{
			_cliente = value;
		}
		
		public function get realizadoPor():Usuario
		{
			return _realizadoPor;
		}
		
		public function set realizadoPor(value:Usuario):void
		{
			_realizadoPor = value;
		}
		
		public function get dataCriacaoPreFatura():Date
		{
			return _dataCriacaoPreFatura;
		}
		
		public function set dataCriacaoPreFatura(value:Date):void
		{
			_dataCriacaoPreFatura = value;
		}
		
		public function get dataCriacaoFatura():Date
		{
			return _dataCriacaoFatura;
		}
		
		public function set dataCriacaoFatura(value:Date):void
		{
			_dataCriacaoFatura = value;
		}
		
		public function get listaOrdemServico():ArrayCollection
		{
			return _listaOrdemServico;
		}
		
		public function set listaOrdemServico(value:ArrayCollection):void
		{
			_listaOrdemServico = value;
		}
		
		public function get totalFrete():Number
		{
			var total:Number = 0;
			if(_listaOrdemServico){
				for each(var os:OrdemServico in _listaOrdemServico){
					if(os.freteUnitarioProposta > os.freteUnitarioExpedicao){
						total = total + os.freteUnitarioProposta;
					}else{
						total = total + os.freteUnitarioExpedicao;
					}
				}
			}
			return total;
		}
		
		public function get totalFreteProposta():Number
		{
			var total:Number = 0;
			if(_listaOrdemServico){
				for each(var os:OrdemServico in _listaOrdemServico){
					total = total + os.freteUnitarioProposta;
				}
			}
			return total;
		}
		
		public function get totalFreteExpedicao():Number
		{
			var total:Number = 0;
			if(_listaOrdemServico){
				for each(var os:OrdemServico in _listaOrdemServico){
					total = total + os.freteUnitarioExpedicao;
				}
			}
			return total;
		}

		public function get totalFatura():Number
		{
			var total:Number = 0;
			if(_listaOrdemServico){
				for each(var os:OrdemServico in _listaOrdemServico){
					total = total + os.valorFaturado;
				}
			}
			return total;
		}

		public function set totalFatura(value:Number):void
		{
			_totalFatura = value;
		}
		
		public function get totalSistema():Number
		{
			var total:Number = 0;
			if(_listaOrdemServico){
				for each(var os:OrdemServico in _listaOrdemServico){
					total = total + os.valorSistema;
				}
			}
			return total;
		}
		

		public function get freteCobrado():Number
		{
			return _freteCobrado;
		}

		public function set freteCobrado(value:Number):void
		{
			_freteCobrado = value;
		}

		public function get valorPago():Number
		{
			return _valorPago;
		}

		public function set valorPago(value:Number):void
		{
			_valorPago = value;
		}

		public function get cobrarFrete():Boolean
		{
			return _cobrarFrete;
		}

		public function set cobrarFrete(value:Boolean):void
		{
			_cobrarFrete = value;
		}

		public function get observacao():String
		{
			return _observacao;
		}

		public function set observacao(value:String):void
		{
			_observacao = value;
		}

		public function get dataCancelamento():Date
		{
			return _dataCancelamento;
		}

		public function set dataCancelamento(value:Date):void
		{
			_dataCancelamento = value;
		}	
	}
}
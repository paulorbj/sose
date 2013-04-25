package entities.recebimento
{
	import entities.administrativo.parceiros.Pessoa;
	
	import mx.collections.ArrayCollection;
	
	import utils.ScreenUtils;
	import utils.TipoNotaFiscal;
	
	[Bindable]	
	[RemoteClass(alias="br.com.sose.entity.recebimento.NotaFiscal")]	
	public class NotaFiscal
	{
		private var _id:Number;		
		private var _numero:String;
		private var _tipo:Number;
		private var _tipoNotaFiscal:TipoNotaFiscal;
		private var _pedidoDeCompra:String;	
		private var _caseAvaya:String;	
		private var _clienteAvaya:String;
		private var _statusString:String;
		private var _cliente:Pessoa;
		private var _dataNotaFiscal:Date;
		private var _dataChegada:Date;
		private var _dataCriacao:Date;
		private var _valorNotaFiscal:Number;
		private var _observacao:String;
		private var _observacaoAnterior:String;
		private var _ordensServico:ArrayCollection;
		private var _itensDaNotaFiscal:ArrayCollection;
		private var _nomeCliente:String;
		
		public function NotaFiscal()
		{
			_cliente = new Pessoa();
			_itensDaNotaFiscal = new ArrayCollection();
			_ordensServico = new ArrayCollection();
			_statusString = "";
		}
		
		public function get id():Number
		{
			return _id;
		}

		public function set id(value:Number):void
		{
			_id = value;
		}

		public function get numero():String
		{
			return _numero;
		}

		public function set numero(value:String):void
		{
			_numero = value;
		}

		public function get pedidoDeCompra():String
		{
			return _pedidoDeCompra;
		}

		public function set pedidoDeCompra(value:String):void
		{
			_pedidoDeCompra = value;
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
			if(value)
				_nomeCliente = value.nomeSistema;
		}

		public function get dataNotaFiscal():Date
		{
			return _dataNotaFiscal;
		}

		public function set dataNotaFiscal(value:Date):void
		{
			_dataNotaFiscal = value;
		}
		
		public function get dataNotaFiscalString():String
		{
			if(_dataNotaFiscal)
				return ScreenUtils.formatarDataDDMMYYYY(_dataNotaFiscal);
			else{
				return "";
			}
		}

		public function get dataChegada():Date
		{
			return _dataChegada;
		}

		public function set dataChegada(value:Date):void
		{
			_dataChegada = value;
		}
		
		public function get dataChegadaString():String
		{
			if(_dataChegada)
				return ScreenUtils.formatarDataDDMMYYYY(_dataChegada);
			else{
				return "";
			}
		}

		public function get valorNotaFiscal():Number
		{
			return _valorNotaFiscal;
		}

		public function set valorNotaFiscal(value:Number):void
		{
			_valorNotaFiscal = value;
		}

		public function get observacao():String
		{
			return _observacao;
		}

		public function set observacao(value:String):void
		{
			_observacao = value;
		}

		public function get ordensServico():ArrayCollection
		{
			return _ordensServico;
		}

		public function set ordensServico(value:ArrayCollection):void
		{
			_ordensServico = value;
		}

		public function get itensDaNotaFiscal():ArrayCollection
		{
			if(_itensDaNotaFiscal == null){
				_itensDaNotaFiscal = new ArrayCollection();
			}
			return _itensDaNotaFiscal;
		}

		public function set itensDaNotaFiscal(value:ArrayCollection):void
		{
			_itensDaNotaFiscal = value;
		}

		public function get tipo():Number
		{
			return _tipo;
		}

		public function set tipo(value:Number):void
		{
			_tipo = value;
			if(_tipo){
				for each(var t:TipoNotaFiscal in ScreenUtils.tipoNotaFiscalList){
					if(t.id == _tipo){
					_tipoNotaFiscal = t;
					break;
					}
				}
			}
		}

		public function get tipoNotaFiscal():TipoNotaFiscal
		{
			return _tipoNotaFiscal;
		}

		public function set tipoNotaFiscal(value:TipoNotaFiscal):void
		{
			_tipoNotaFiscal = value;
			if(_tipoNotaFiscal){
				_tipo = _tipoNotaFiscal.id;
			}
		}

		public function get dataCriacao():Date
		{
			return _dataCriacao;
		}

		public function set dataCriacao(value:Date):void
		{
			_dataCriacao = value;
		}
		
		public function get dataCriacaoString():String
		{
			if(_dataCriacao)
				return ScreenUtils.formatarDataDDMMYYYY(_dataCriacao);
			else{
				return "";
			}
		}

		public function get observacaoAnterior():String
		{
			return _observacaoAnterior;
		}

		public function set observacaoAnterior(value:String):void
		{
			_observacaoAnterior = value;
		}

		public function get nomeCliente():String
		{
			return _nomeCliente;
		}

		public function set nomeCliente(value:String):void
		{
			_nomeCliente = value;
		}


	}
}
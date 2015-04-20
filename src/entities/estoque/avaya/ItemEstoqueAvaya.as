package entities.estoque.avaya
{
	import entities.administrativo.Usuario;
	import entities.recebimento.OrdemServico;
	
	[Bindable]	
	[RemoteClass(alias="br.com.sose.entity.estoque.avaya.ItemEstoqueAvaya")]	
	public class ItemEstoqueAvaya
	{
		
		private var _id:Number;
	
		private var _ordemServicoOriginal:OrdemServico;

		private var _ordemServicoSubstituida:OrdemServico;
		
		private var _dataEntrada:Date;
		
		private var _dataSaida:Date;
		
		private var _dataOperacao:Date;
		
		private var _posicao:String;

		private var _operacaoRealizadaPor:Usuario;

		private var _entradaRealizadaPor:Usuario;

		private var _saidaRealizadaPor:Usuario;
		
		private var _statusString:String;
		
		private var _isSelected:Boolean;
		
		private var _isEnabled:Boolean=true;
		
		private var _unidade:String;
		private var _numeroOsOriginal:String;
		private var _nsFabricanteOriginal:String;
		private var _nsClienteOriginal:String;
		private var _nfOriginal:String;
		private var _numeroOsSubstituida:String;
		private var _nsFabricanteSubstituida:String;
		private var _nsClienteSubstituida:String;
		private var _nfSubstituida:String;
		private var _usuarioOperacao:String;
		private var _idOsOrig:Number;
		private var _idOsSubst:Number;
		
		public function get id():Number
		{
			return _id;
		}
		
		public function set id(value:Number):void
		{
			_id = value;
		}

		public function get ordemServicoOriginal():OrdemServico
		{
			return _ordemServicoOriginal;
		}

		public function set ordemServicoOriginal(value:OrdemServico):void
		{
			_ordemServicoOriginal = value;
		}

		public function get ordemServicoSubstituida():OrdemServico
		{
			return _ordemServicoSubstituida;
		}

		public function set ordemServicoSubstituida(value:OrdemServico):void
		{
			_ordemServicoSubstituida = value;
		}

		public function get dataEntrada():Date
		{
			return _dataEntrada;
		}

		public function set dataEntrada(value:Date):void
		{
			_dataEntrada = value;
		}

		public function get dataSaida():Date
		{
			return _dataSaida;
		}

		public function set dataSaida(value:Date):void
		{
			_dataSaida = value;
		}

		public function get dataOperacao():Date
		{
			return _dataOperacao;
		}

		public function set dataOperacao(value:Date):void
		{
			_dataOperacao = value;
		}

		public function get posicao():String
		{
			return _posicao;
		}

		public function set posicao(value:String):void
		{
			_posicao = value;
		}

		public function get operacaoRealizadaPor():Usuario
		{
			return _operacaoRealizadaPor;
		}

		public function set operacaoRealizadaPor(value:Usuario):void
		{
			_operacaoRealizadaPor = value;
		}

		public function get entradaRealizadaPor():Usuario
		{
			return _entradaRealizadaPor;
		}

		public function set entradaRealizadaPor(value:Usuario):void
		{
			_entradaRealizadaPor = value;
		}

		public function get saidaRealizadaPor():Usuario
		{
			return _saidaRealizadaPor;
		}

		public function set saidaRealizadaPor(value:Usuario):void
		{
			_saidaRealizadaPor = value;
		}

		public function get statusString():String
		{
			return _statusString;
		}

		public function set statusString(value:String):void
		{
			_statusString = value;
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

		public function get unidade():String
		{
			return _unidade;
		}

		public function set unidade(value:String):void
		{
			_unidade = value;
		}

		public function get numeroOsOriginal():String
		{
			return _numeroOsOriginal;
		}

		public function set numeroOsOriginal(value:String):void
		{
			_numeroOsOriginal = value;
		}

		public function get nsFabricanteOriginal():String
		{
			return _nsFabricanteOriginal;
		}

		public function set nsFabricanteOriginal(value:String):void
		{
			_nsFabricanteOriginal = value;
		}

		public function get nsClienteOriginal():String
		{
			return _nsClienteOriginal;
		}

		public function set nsClienteOriginal(value:String):void
		{
			_nsClienteOriginal = value;
		}

		public function get nfOriginal():String
		{
			return _nfOriginal;
		}

		public function set nfOriginal(value:String):void
		{
			_nfOriginal = value;
		}

		public function get numeroOsSubstituida():String
		{
			return _numeroOsSubstituida;
		}

		public function set numeroOsSubstituida(value:String):void
		{
			_numeroOsSubstituida = value;
		}

		public function get nsFabricanteSubstituida():String
		{
			return _nsFabricanteSubstituida;
		}

		public function set nsFabricanteSubstituida(value:String):void
		{
			_nsFabricanteSubstituida = value;
		}

		public function get nsClienteSubstituida():String
		{
			return _nsClienteSubstituida;
		}

		public function set nsClienteSubstituida(value:String):void
		{
			_nsClienteSubstituida = value;
		}

		public function get nfSubstituida():String
		{
			return _nfSubstituida;
		}

		public function set nfSubstituida(value:String):void
		{
			_nfSubstituida = value;
		}

		public function get usuarioOperacao():String
		{
			return _usuarioOperacao;
		}

		public function set usuarioOperacao(value:String):void
		{
			_usuarioOperacao = value;
		}

		public function get idOsOrig():Number
		{
			return _idOsOrig;
		}

		public function set idOsOrig(value:Number):void
		{
			_idOsOrig = value;
		}

		public function get idOsSubst():Number
		{
			return _idOsSubst;
		}

		public function set idOsSubst(value:Number):void
		{
			_idOsSubst = value;
		}
		
		

	}
}
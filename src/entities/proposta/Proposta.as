package entities.proposta
{
	import entities.administrativo.Usuario;
	import entities.administrativo.parceiros.Contato;
	import entities.administrativo.parceiros.Pessoa;
	import entities.orcamentoDiferenciado.OrcamentoDiferenciado;
	
	import mx.collections.ArrayCollection;
	
	import utils.ScreenUtils;
	
	[Bindable]	
	[RemoteClass(alias="br.com.sose.entity.proposta.Proposta")]	
	public class Proposta
	{
		private var _id:Number;
		private var _numero:String;
		private var _cliente:Pessoa;
		private var _tipo:String;
		private var _statusString:String;
		private var _dataCriacao:Date;
		private var _dataInicio:Date;
		private var _dataEnvioCliente:Date;
		private var _dataFinalizacao:Date;
		private var _itensProposta:ArrayCollection;
		private var _valorFrete:Number;
		private var _valorDesconto:Number;	
		private var _porcentagemDesconto:Number;
		private var _contato:Contato;
		private var _orcamentoDiferenciado:OrcamentoDiferenciado;
		private var _nomeCliente:String;
		private var _nNFs:String;
		
		public function get id():Number
		{
			return _id;
		}
		
		public function set id(value:Number):void
		{
			_id = value;
		}

		public function get cliente():Pessoa
		{
			return _cliente;
		}

		public function set cliente(value:Pessoa):void
		{
			_cliente = value;
		}

		public function get tipo():String
		{
			return _tipo;
		}

		public function set tipo(value:String):void
		{
			_tipo = value;
		}

		public function get statusString():String
		{
			return _statusString;
		}

		public function set statusString(value:String):void
		{
			_statusString = value;
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

		public function get dataInicio():Date
		{
			return _dataInicio;
		}

		public function set dataInicio(value:Date):void
		{
			_dataInicio = value;
		}
		
		public function get dataInicioString():String
		{
			if(_dataInicio)
				return ScreenUtils.formatarDataDDMMYYYY(_dataInicio);
			else{
				return "";
			}
		}

		public function get dataEnvioCliente():Date
		{
			return _dataEnvioCliente;
		}

		public function set dataEnvioCliente(value:Date):void
		{
			_dataEnvioCliente = value;
		}
		
		public function get dataEnvioClienteString():String
		{
			if(_dataEnvioCliente)
				return ScreenUtils.formatarDataDDMMYYYY(_dataEnvioCliente);
			else{
				return "";
			}
		}

		public function get itensProposta():ArrayCollection
		{
			return _itensProposta;
		}

		public function set itensProposta(value:ArrayCollection):void
		{
			_itensProposta = value;
		}

		public function get numero():String
		{
			return _numero;
		}

		public function set numero(value:String):void
		{
			_numero = value;
		}

		public function get valorFrete():Number
		{
			return _valorFrete;
		}

		public function set valorFrete(value:Number):void
		{
			_valorFrete = value;
		}

		public function get valorDesconto():Number
		{
			return _valorDesconto;
		}

		public function set valorDesconto(value:Number):void
		{
			_valorDesconto = value;
		}

		public function get porcentagemDesconto():Number
		{
			return _porcentagemDesconto;
		}

		public function set porcentagemDesconto(value:Number):void
		{
			_porcentagemDesconto = value;
		}

		public function get contato():Contato
		{
			return _contato;
		}

		public function set contato(value:Contato):void
		{
			_contato = value;
		}

		public function get dataFinalizacao():Date
		{
			return _dataFinalizacao;
		}

		public function set dataFinalizacao(value:Date):void
		{
			_dataFinalizacao = value;
		}
		
		public function get dataFinalizacaoString():String
		{
			if(_dataFinalizacao)
				return ScreenUtils.formatarDataDDMMYYYY(_dataFinalizacao);
			else{
				return "";
			}
		}

		public function get orcamentoDiferenciado():OrcamentoDiferenciado
		{
			return _orcamentoDiferenciado;
		}

		public function set orcamentoDiferenciado(value:OrcamentoDiferenciado):void
		{
			_orcamentoDiferenciado = value;
		}

		public function get nomeCliente():String
		{
			return _nomeCliente;
		}

		public function set nomeCliente(value:String):void
		{
			_nomeCliente = value;
		}

		public function get nNFs():String
		{
			return _nNFs;
		}

		public function set nNFs(value:String):void
		{
			_nNFs = value;
		}
		
	
	}
}
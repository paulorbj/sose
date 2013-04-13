package entities.expedicao
{
	import entities.administrativo.Usuario;
	import entities.administrativo.parceiros.Endereco;
	import entities.administrativo.parceiros.Pessoa;
	import entities.recebimento.OrdemServico;
	
	import mx.collections.ArrayCollection;
	
	import utils.ScreenUtils;
	
	[Bindable]	
	[RemoteClass(alias="br.com.sose.entity.expedicao.NotaFiscalRemessa")]	
	public class NotaFiscalRemessa
	{
		
		private var _id:Number;
		private var _nome:String;
		private var _dataCriacaoPreExpedicao:Date;
		private var _cliente:Pessoa;
		private var _numero:String;
		private var _statusString:String;
		private var _dtSaida:Date;
		private var _dtFinalizacao:Date;
		private var _dtCriacao:Date;
		private var _dtIniciacao:Date;
		private var _dtEmissao:Date;
		private var _ordensServico:ArrayCollection;
		private var _volumes:ArrayCollection;
		private var _transportador:Pessoa;
		private var _placaVeiculo:String;
		private var _nomeMotorista:String;
		private var _valorCorreio:Number;
		private var _valorFrete:Number;
		private var _tipo:String;
		private var _enderecoEntrega:Endereco;		
		private var _numeroDocumento:String;
		private var _numeroConhecimento:String;
		private var _recebidoPor:String;
		private var _dataRecebimentoMaterial:Date;
		private var _saidaRegistradaEm:Date;
		private var _solicitacaoRegistradaEm:Date;
		private var _observacao:String;
		private var _codFrete:String;
		private var _nomeCliente:String;
		private var _idCliente:Number;
		private var _observacaoAnterior:String;
		private var _idEnderecoEntrega:Number;
		private var _idTransportador:Number;
		
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

		public function get numero():String
		{
			return _numero;
		}

		public function set numero(value:String):void
		{
			_numero = value;
		}

		public function get statusString():String
		{
			return _statusString;
		}

		public function set statusString(value:String):void
		{
			_statusString = value;
		}

		public function get dtSaida():Date
		{
			return _dtSaida;
		}

		public function set dtSaida(value:Date):void
		{
			_dtSaida = value;
		}
		
		public function get dtSaidaString():String
		{
			if(_dtSaida)
				return ScreenUtils.formatarDataDDMMYYYY(_dtSaida);
			else{
				return "";
			}
		}

		public function get dtCriacao():Date
		{
			return _dtCriacao;
		}

		public function set dtCriacao(value:Date):void
		{
			_dtCriacao = value;
		}
		
		public function get dtCriacaoString():String
		{
			if(_dtCriacao)
				return ScreenUtils.formatarDataDDMMYYYY(_dtCriacao);
			else{
				return "";
			}
		}

		public function get ordensServico():ArrayCollection
		{
			return _ordensServico;
		}

		public function set ordensServico(value:ArrayCollection):void
		{
			_ordensServico = value;
		}

		public function get volumes():ArrayCollection
		{
			return _volumes;
		}

		public function set volumes(value:ArrayCollection):void
		{
			_volumes = value;
		}

		public function get transportador():Pessoa
		{
			return _transportador;
		}

		public function set transportador(value:Pessoa):void
		{
			_transportador = value;
		}

		public function get placaVeiculo():String
		{
			return _placaVeiculo;
		}

		public function set placaVeiculo(value:String):void
		{
			_placaVeiculo = value;
		}

		public function get nomeMotorista():String
		{
			return _nomeMotorista;
		}

		public function set nomeMotorista(value:String):void
		{
			_nomeMotorista = value;
		}

		public function get valorCorreio():Number
		{
			return _valorCorreio;
		}

		public function set valorCorreio(value:Number):void
		{
			_valorCorreio = value;
		}

		public function get valorFrete():Number
		{
			return _valorFrete;
		}

		public function set valorFrete(value:Number):void
		{
			_valorFrete = value;
		}

		public function get tipo():String
		{
			return _tipo;
		}

		public function set tipo(value:String):void
		{
			_tipo = value;
		}

		public function get enderecoEntrega():Endereco
		{
			return _enderecoEntrega;
		}

		public function set enderecoEntrega(value:Endereco):void
		{
			_enderecoEntrega = value;
		}

		public function get dtIniciacao():Date
		{
			return _dtIniciacao;
		}

		public function set dtIniciacao(value:Date):void
		{
			_dtIniciacao = value;
		}
		
		public function get dtIniciacaoString():String
		{
			if(_dtIniciacao)
				return ScreenUtils.formatarDataDDMMYYYY(_dtIniciacao);
			else{
				return "";
			}
		}

		public function get dtEmissao():Date
		{
			return _dtEmissao;
		}

		public function set dtEmissao(value:Date):void
		{
			_dtEmissao = value;
		}
		
		public function get dtEmissaoString():String
		{
			if(_dtEmissao)
				return ScreenUtils.formatarDataDDMMYYYY(_dtEmissao);
			else{
				return "";
			}
		}

		public function get dtFinalizacao():Date
		{
			return _dtFinalizacao;
		}

		public function set dtFinalizacao(value:Date):void
		{
			_dtFinalizacao = value;
		}
		
		public function get dtFinalizacaoString():String
		{
			if(_dtFinalizacao)
				return ScreenUtils.formatarDataDDMMYYYY(_dtFinalizacao);
			else{
				return "";
			}
		}

		public function get nome():String
		{
			return _nome;
		}

		public function set nome(value:String):void
		{
			_nome = value;
		}

		public function get dataCriacaoPreExpedicao():Date
		{
			return _dataCriacaoPreExpedicao;
		}

		public function set dataCriacaoPreExpedicao(value:Date):void
		{
			_dataCriacaoPreExpedicao = value;
		}
		
		public function get dataCriacaoPreExpedicaoString():String
		{
			if(_dataCriacaoPreExpedicao)
				return ScreenUtils.formatarDataDDMMYYYY(_dataCriacaoPreExpedicao);
			else{
				return "";
			}
		}

		public function get numeroDocumento():String
		{
			return _numeroDocumento;
		}

		public function set numeroDocumento(value:String):void
		{
			_numeroDocumento = value;
		}

		public function get numeroConhecimento():String
		{
			return _numeroConhecimento;
		}

		public function set numeroConhecimento(value:String):void
		{
			_numeroConhecimento = value;
		}

		public function get recebidoPor():String
		{
			return _recebidoPor;
		}

		public function set recebidoPor(value:String):void
		{
			_recebidoPor = value;
		}

		public function get dataRecebimentoMaterial():Date
		{
			return _dataRecebimentoMaterial;
		}

		public function set dataRecebimentoMaterial(value:Date):void
		{
			_dataRecebimentoMaterial = value;
		}
		
		public function get dataRecebimentoMaterialString():String
		{
			if(_dataRecebimentoMaterial)
				return ScreenUtils.formatarDataDDMMYYYY(_dataRecebimentoMaterial);
			else{
				return "";
			}
		}

		public function get saidaRegistradaEm():Date
		{
			return _saidaRegistradaEm;
		}

		public function set saidaRegistradaEm(value:Date):void
		{
			_saidaRegistradaEm = value;
		}
		
		public function get saidaRegistradaEmString():String
		{
			if(_saidaRegistradaEm)
				return ScreenUtils.formatarDataDDMMYYYY(_saidaRegistradaEm);
			else{
				return "";
			}
		}

		public function get observacao():String
		{
			return _observacao;
		}

		public function set observacao(value:String):void
		{
			_observacao = value;
		}

		public function get codFrete():String
		{
			return _codFrete;
		}

		public function set codFrete(value:String):void
		{
			_codFrete = value;
		}

		public function get solicitacaoRegistradaEm():Date
		{
			return _solicitacaoRegistradaEm;
		}

		public function set solicitacaoRegistradaEm(value:Date):void
		{
			_solicitacaoRegistradaEm = value;
		}
		
		public function get solicitacaoRegistradaEmString():String
		{
			if(_solicitacaoRegistradaEm)
				return ScreenUtils.formatarDataDDMMYYYY(_solicitacaoRegistradaEm);
			else{
				return "";
			}
		}

		public function get nomeCliente():String
		{
			return _nomeCliente;
		}

		public function set nomeCliente(value:String):void
		{
			_nomeCliente = value;
		}

		public function get idCliente():Number
		{
			return _idCliente;
		}

		public function set idCliente(value:Number):void
		{
			_idCliente = value;
		}

		public function get observacaoAnterior():String
		{
			return _observacaoAnterior;
		}

		public function set observacaoAnterior(value:String):void
		{
			_observacaoAnterior = value;
		}

		public function get idEnderecoEntrega():Number
		{
			return _idEnderecoEntrega;
		}

		public function set idEnderecoEntrega(value:Number):void
		{
			_idEnderecoEntrega = value;
		}

		public function get idTransportador():Number
		{
			return _idTransportador;
		}

		public function set idTransportador(value:Number):void
		{
			_idTransportador = value;
		}
		
		public function get totalFreteProposta():Number
		{
			var total:Number = 0;
			if(_ordensServico){
				for each(var os:OrdemServico in _ordensServico){
					total = total + os.freteUnitarioProposta;
				}
			}
			return total;
		}
		

	}
}
package entities.orcamentoreparo
{
	import entities.administrativo.Componente;
	import entities.administrativo.Usuario;
	
	import utils.ScreenUtils;
	
	[Bindable]	
	[RemoteClass(alias="br.com.sose.entity.orcrepGenerico.RequisicaoComponente")]	
	public class RequisicaoComponente
	{
		private var _id:Number;
		private var _reparo:Reparo;
		private var _orcamento:Orcamento;
		private var _componente:Componente;
		private var _requisitante:Usuario;
		private var _quantidade:Number;
		private var _statusString:String;
		private var _dataRequisicao:Date;
		private var _dataCancelamento:Date;
		private var _canceladoPor:Usuario;
		private var _dataRetirada:Date;
		private var _retiradoPor:Usuario;
		private var _isSelected:Boolean;
		private var _isEnabled:Boolean = false;
		private var _componenteNome:String;
		private var _dataRecebimento:Date;
		private var _recebidoPor:Usuario;
		private var _dataEntrega:Date;
		private var _entreguePor:Usuario
		private var _dataAtendimento:Date;
		private var _atendidoPor:Usuario;
		private var _dataMaterialPronto:Date;
		private var _coletadoPor:Usuario;
		
		public function get id():Number
		{
			return _id;
		}

		public function set id(value:Number):void
		{
			_id = value;
		}

		public function get reparo():Reparo
		{
			return _reparo;
		}

		public function set reparo(value:Reparo):void
		{
			_reparo = value;
		}

		public function get orcamento():Orcamento
		{
			return _orcamento;
		}

		public function set orcamento(value:Orcamento):void
		{
			_orcamento = value;
		}

		public function get componente():Componente
		{
			return _componente;
		}

		public function set componente(value:Componente):void
		{
			_componente = value;
		}
		
		public function get componenteNome():String
		{
			return _componente.nome;
		}
	

		public function get requisitante():Usuario
		{
			return _requisitante;
		}

		public function set requisitante(value:Usuario):void
		{
			_requisitante = value;
		}

		public function get quantidade():Number
		{
			return _quantidade;
		}

		public function set quantidade(value:Number):void
		{
			_quantidade = value;
		}

		public function get statusString():String
		{
			return _statusString;
		}

		public function set statusString(value:String):void
		{
			_statusString = value;
		}

		public function get dataRequisicao():Date
		{
			return _dataRequisicao;
		}

		public function set dataRequisicao(value:Date):void
		{
			_dataRequisicao = value;
		}
		
		public function get dataRequisicaoString():String
		{
			if(_dataRequisicao)
				return ScreenUtils.formatarDataDDMMYYYY(_dataRequisicao);
			else{
				return "";
			}
		}

		public function get dataCancelamento():Date
		{
			return _dataCancelamento;
		}

		public function set dataCancelamento(value:Date):void
		{
			_dataCancelamento = value;
		}
		
		public function get dataCancelamentoString():String
		{
			if(_dataCancelamento)
				return ScreenUtils.formatarDataDDMMYYYY(_dataCancelamento);
			else{
				return "";
			}
		}

		public function get dataRetirada():Date
		{
			return _dataRetirada;
		}

		public function set dataRetirada(value:Date):void
		{
			_dataRetirada = value;
		}
		
		public function get dataRetiradaString():String
		{
			if(_dataRetirada)
				return ScreenUtils.formatarDataDDMMYYYY(_dataRetirada);
			else{
				return "";
			}
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

		public function get retiradoPor():Usuario
		{
			return _retiradoPor;
		}

		public function set retiradoPor(value:Usuario):void
		{
			_retiradoPor = value;
		}

		public function get recebidoPor():Usuario
		{
			return _recebidoPor;
		}

		public function set recebidoPor(value:Usuario):void
		{
			_recebidoPor = value;
		}

		public function get entreguePor():Usuario
		{
			return _entreguePor;
		}

		public function set entreguePor(value:Usuario):void
		{
			_entreguePor = value;
		}

		public function get atendidoPor():Usuario
		{
			return _atendidoPor;
		}

		public function set atendidoPor(value:Usuario):void
		{
			_atendidoPor = value;
		}

		public function get coletadoPor():Usuario
		{
			return _coletadoPor;
		}

		public function set coletadoPor(value:Usuario):void
		{
			_coletadoPor = value;
		}

		public function get dataRecebimento():Date
		{
			return _dataRecebimento;
		}

		public function set dataRecebimento(value:Date):void
		{
			_dataRecebimento = value;
		}
		
		public function get dataRecebimentoString():String
		{
			if(_dataRecebimento)
				return ScreenUtils.formatarDataDDMMYYYY(_dataRecebimento);
			else{
				return "";
			}
		}

		public function get dataEntrega():Date
		{
			return _dataEntrega;
		}

		public function set dataEntrega(value:Date):void
		{
			_dataEntrega = value;
		}
		
		public function get dataEntregaString():String
		{
			if(_dataEntrega)
				return ScreenUtils.formatarDataDDMMYYYY(_dataEntrega);
			else{
				return "";
			}
		}

		public function get dataAtendimento():Date
		{
			return _dataAtendimento;
		}

		public function set dataAtendimento(value:Date):void
		{
			_dataAtendimento = value;
		}
		
		public function get dataAtendimentoString():String
		{
			if(_dataAtendimento)
				return ScreenUtils.formatarDataDDMMYYYY(_dataAtendimento);
			else{
				return "";
			}
		}

		public function get dataMaterialPronto():Date
		{
			return _dataMaterialPronto;
		}

		public function set dataMaterialPronto(value:Date):void
		{
			_dataMaterialPronto = value;
		}
		
		public function get dataMaterialProntoString():String
		{
			if(_dataMaterialPronto)
				return ScreenUtils.formatarDataDDMMYYYY(_dataMaterialPronto);
			else{
				return "";
			}
		}

		public function get canceladoPor():Usuario
		{
			return _canceladoPor;
		}

		public function set canceladoPor(value:Usuario):void
		{
			_canceladoPor = value;
		}

		
	}
}
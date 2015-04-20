package entities.proposta
{
	import entities.administrativo.Lpu;
	import entities.recebimento.OrdemServico;
	
	[Bindable]	
	[RemoteClass(alias="br.com.sose.entity.proposta.ItemProposta")]	
	public class ItemProposta
	{
		
		private var _id:Number;
		private var _ordemServico:OrdemServico;
		private var _proposta:Proposta;
		private var _valorSemDesconto:Number;
		private var _valorDesconto:Number;	
		private var _porcentagemDesconto:Number;
		private var _lpu:Lpu;
		private var _observacao:String;
		private var _isAprovado:Boolean;
		private var _dataFinalizacao:Date;
		private var _dataAprovacao:Date;
		private var _isSelected:Boolean;
		private var _isEnabled:Boolean = true;
		private var _dataLiberacao:Date;
		
		public function ItemProposta(){
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
		
		public function get ordemServico():OrdemServico
		{
			return _ordemServico;
		}
		
		public function set ordemServico(value:OrdemServico):void
		{
			_ordemServico = value;
		}
		
		public function get proposta():Proposta
		{
			return _proposta;
		}
		
		public function set proposta(value:Proposta):void
		{
			_proposta = value;
		}
		
		public function get valorSemDesconto():Number
		{
			return _valorSemDesconto;
		}
		
		public function set valorSemDesconto(value:Number):void
		{
			_valorSemDesconto = value;
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
		
		public function get lpu():Lpu
		{
			return _lpu;
		}
		
		public function set lpu(value:Lpu):void
		{
			_lpu = value;
		}
		
		public function get observacao():String
		{
			return _observacao;
		}
		
		public function set observacao(value:String):void
		{
			_observacao = value;
		}
		
		public function get isSelected():Boolean
		{
			return _isSelected;
		}
		
		public function set isSelected(value:Boolean):void
		{
			_isSelected = value;
		}
		
		public function get isAprovado():Boolean
		{
			return _isAprovado;
		}
		
		public function set isAprovado(value:Boolean):void
		{
			_isAprovado = value;
		}
		
		public function get dataFinalizacao():Date
		{
			return _dataFinalizacao;
		}
		
		public function set dataFinalizacao(value:Date):void
		{
			_dataFinalizacao = value;
		}
		
		public function get dataAprovacao():Date
		{
			return _dataAprovacao;
		}
		
		public function set dataAprovacao(value:Date):void
		{
			_dataAprovacao = value;
		}
		
		public function get isEnabled():Boolean
		{
			if(_ordemServico.unidadePai == null){
				if(dataAprovacao != null){
					_isEnabled = false;
				}
				if(_proposta.tipo == "Orçamento"){
					if(_ordemServico.orcamento == null || _ordemServico.orcamento.condicao == "Sem condição de reparo" || _ordemServico.orcamento.condicao == "Devolução sem reparo"){
						_isEnabled = false;
					}
				}else{
					if(_ordemServico.reparo == null || _ordemServico.reparo.condicao == "Sem condição de reparo" || _ordemServico.reparo.condicao == "Devolução sem reparo"){
						_isEnabled = false;
					}
				}
			}else{
				_isEnabled = false;
			}
			
			return _isEnabled;
		}
		
		public function set isEnabled(value:Boolean):void
		{
			_isEnabled = value;
		}
		
		public function get dataLiberacao():Date
		{
			return _dataLiberacao;
		}
		
		public function set dataLiberacao(value:Date):void
		{
			_dataLiberacao = value;
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
		
		public function get btsAprovacaoEnabled():Boolean
		{
			if(_proposta.statusString == 'Aguardando aprovação do cliente' && dataAprovacao == null){
				return true;
			}else{
				return false;
			}
		}
		
		public function get btsAprovacaoVisible():Boolean
		{
			if(_ordemServico.unidadePai == null){
				if(_proposta.tipo == "Orçamento"){
					if(_ordemServico.orcamento.condicao == "Com condição de reparo"){
						return true;
					}else{
						return false;
					}
				}else{
					if(_ordemServico.reparo.condicao == "Com condição de reparo"){
						return true;
					}else{
						return false;
					}
				}
			}else{
				//Se for filha nao mostra os botoes
				return false;
			}			
		}
		
		public function get btEdicaoVisible():Boolean
		{
			if(_proposta.tipo == "Orçamento"){
				if(_ordemServico.orcamento.condicao == "Com condição de reparo"){
					return true;
				}else{
					return false;
				}
			}else{
				if(_ordemServico.reparo.condicao == "Com condição de reparo"){
					return true;
				}else{
					return false;
				}
			}		
		}
		
		public function get statusItem():String
		{
			if(_proposta.tipo == "Orçamento"){
				if((_ordemServico.unidadePai == null && _ordemServico.orcamento.condicao == "Com condição de reparo") || (_ordemServico.unidadePai != null && _ordemServico.unidadePai.orcamento.condicao == "Com condição de reparo")){
					if(dataAprovacao == null){
						return "Aguardando aprovação";
					}
					else if(dataAprovacao != null && isAprovado){
						return "Aprovado";
					}
					else if(dataAprovacao != null && !isAprovado){
						return "Reprovado";
					}
				}else{
					if(dataLiberacao == null){
						return "Aguardando liberação";
					}else{
						return "Liberado"
					}	
				}
			}else{
				if((_ordemServico.unidadePai == null && _ordemServico.reparo.condicao == "Com condição de reparo") || (_ordemServico.unidadePai != null && _ordemServico.unidadePai.reparo.condicao == "Com condição de reparo")){
					if(dataAprovacao == null){
						return "Aguardando aprovação";
					}
					else if(dataAprovacao != null && isAprovado){
						return "Aprovado";
					}
					else if(dataAprovacao != null && !isAprovado){
						return "Reprovado";
					}
				}else{
					if(dataLiberacao == null){
						return "Aguardando liberação";
					}else{
						return "Liberado"
					}	
				}
			}
			return "";
		}
		
	}
}
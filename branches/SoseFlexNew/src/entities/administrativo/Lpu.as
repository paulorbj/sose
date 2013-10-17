package entities.administrativo
{
	import entities.administrativo.parceiros.Pessoa;
	
	import mx.collections.ArrayCollection;
	
	import utils.ScreenUtils;
	
	[Bindable]	
	[RemoteClass(alias="br.com.sose.entity.lpu.Lpu")]	
	public class Lpu
	{
		
		private var _id:Number;
		private var _cliente:Pessoa;
		private var _celulaUnidade:String;
		private var _celulaCodigo1:String;
		private var _celulaCodigo2:String;
		private var _celulaFabricante:String;
		private var _celulaEquipamento:String;
		private var _celulaValor:String;

		private var _linhaCabecalho:String;
		private var _primeiraLinhaDados:String;
		private var _ultimaLinhaDados:String;
				
		private var _ativa:Boolean;
		
		private var _validoAte:Date;
		
		private var _uploadEm:Date;
		
		private var _rodouAutoAssociar:Boolean;
				
		private var _listaUnidadeItemLpu:ArrayCollection;
		
		public function Lpu(){
		}
	
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

		public function get celulaUnidade():String
		{
			return _celulaUnidade;
		}

		public function set celulaUnidade(value:String):void
		{
			_celulaUnidade = value;
		}

		public function get celulaCodigo1():String
		{
			return _celulaCodigo1;
		}

		public function set celulaCodigo1(value:String):void
		{
			_celulaCodigo1 = value;
		}

		public function get celulaCodigo2():String
		{
			return _celulaCodigo2;
		}

		public function set celulaCodigo2(value:String):void
		{
			_celulaCodigo2 = value;
		}

		public function get celulaFabricante():String
		{
			return _celulaFabricante;
		}

		public function set celulaFabricante(value:String):void
		{
			_celulaFabricante = value;
		}

		public function get celulaEquipamento():String
		{
			return _celulaEquipamento;
		}

		public function set celulaEquipamento(value:String):void
		{
			_celulaEquipamento = value;
		}

		public function get celulaValor():String
		{
			return _celulaValor;
		}

		public function set celulaValor(value:String):void
		{
			_celulaValor = value;
		}

		public function get linhaCabecalho():String
		{
			return _linhaCabecalho;
		}

		public function set linhaCabecalho(value:String):void
		{
			_linhaCabecalho = value;
		}

		public function get primeiraLinhaDados():String
		{
			return _primeiraLinhaDados;
		}

		public function set primeiraLinhaDados(value:String):void
		{
			_primeiraLinhaDados = value;
		}

		public function get ultimaLinhaDados():String
		{
			return _ultimaLinhaDados;
		}

		public function set ultimaLinhaDados(value:String):void
		{
			_ultimaLinhaDados = value;
		}

		public function get ativa():Boolean
		{
			return _ativa;
		}

		public function set ativa(value:Boolean):void
		{
			_ativa = value;
		}

		public function get validoAte():Date
		{
			return _validoAte;
		}

		public function set validoAte(value:Date):void
		{
			_validoAte = value;
		}
		
		public function get validoAteString():String
		{
			if(_validoAte)
				return ScreenUtils.formatarDataDDMMYYYY(_validoAte);
			else{
				return "";
			}
		}
		
		public function set validoAteString(value:String):void
		{

		}

		public function get uploadEm():Date
		{
			return _uploadEm;
		}

		public function set uploadEm(value:Date):void
		{
			_uploadEm = value;
		}
		
		public function get uploadEmString():String
		{
			if(_uploadEm)
				return ScreenUtils.formatarDataDDMMYYYY(_uploadEm);
			else{
				return "";
			}
		}
		
		public function set uploadEmString(value:String):void
		{
		}

		public function get rodouAutoAssociar():Boolean
		{
			return _rodouAutoAssociar;
		}

		public function set rodouAutoAssociar(value:Boolean):void
		{
			_rodouAutoAssociar = value;
		}

		public function get listaUnidadeItemLpu():ArrayCollection
		{
			return _listaUnidadeItemLpu;
		}

		public function set listaUnidadeItemLpu(value:ArrayCollection):void
		{
			_listaUnidadeItemLpu = value;
		}


	}
}
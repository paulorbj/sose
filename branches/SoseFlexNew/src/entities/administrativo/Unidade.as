package entities.administrativo
{
	import entities.administrativo.parceiros.Pessoa;
	
	import mx.collections.ArrayCollection;
	
	[Bindable]	
	[RemoteClass(alias="br.com.sose.entity.admistrativo.Unidade")]	
	public class Unidade
	{
		
		private var _id:Number;
		private var _descricao:String;
		private var _nome:String;
		private var _cadastroSistemaAtivo:Boolean;
		private var _codigo:String;
		private var _equipamento:Equipamento;
		private var _fabricante:Fabricante;
		private var _laboratorio:Laboratorio;
		private var _prestadorServicoExterno:Pessoa;
		private var _nomeFabricante:String;
		private var _nomeEquipamento:String;
		private var _nomeLaboratorio:String;
		private var _nomePrestadorServico:String;
		
		public function Unidade(){
			_id = 0;
			_nome = "";
			_cadastroSistemaAtivo = true;
		}
		
		public function get id():Number
		{
			return _id;
		}
		
		public function set id(value:Number):void
		{
			_id = value;
		}
		
		public function get descricao():String
		{
			return _descricao;
		}
		
		public function set descricao(value:String):void
		{
			_descricao = value;
		}
		
		public function get nome():String
		{
			return _nome;
		}
		
		public function set nome(value:String):void
		{
			_nome = value;
		}
		
		public function get cadastroSistemaAtivo():Boolean
		{
			return _cadastroSistemaAtivo;
		}
		
		public function set cadastroSistemaAtivo(value:Boolean):void
		{
			_cadastroSistemaAtivo = value;
		}
		
		public function get codigo():String
		{
			return _codigo;
		}
		
		public function set codigo(value:String):void
		{
			_codigo = value;
		}
		
		public function get equipamento():Equipamento
		{
			return _equipamento;
		}
		
		public function set equipamento(value:Equipamento):void
		{
			_equipamento = value;
		}
		
		public function get fabricante():Fabricante
		{
			return _fabricante;
		}
		
		public function set fabricante(value:Fabricante):void
		{
			_fabricante = value;
		}
		
		public function get laboratorio():Laboratorio
		{
			return _laboratorio;
		}
		
		public function set laboratorio(value:Laboratorio):void
		{
			_laboratorio = value;
		}
		
		public function get prestadorServicoExterno():Pessoa
		{
			return _prestadorServicoExterno;
		}
		
		public function set prestadorServicoExterno(value:Pessoa):void
		{
			_prestadorServicoExterno = value;
		}
		
		public function get codigoUnidade():String
		{
			if(_codigo != null)
				return _nome + "::" + _codigo;
			else
				return _nome;
		}

		public function get nomeFabricante():String
		{
			return _nomeFabricante;
		}

		public function set nomeFabricante(value:String):void
		{
			_nomeFabricante = value;
		}

		public function get nomeEquipamento():String
		{
			return _nomeEquipamento;
		}

		public function set nomeEquipamento(value:String):void
		{
			_nomeEquipamento = value;
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

		
	}
}
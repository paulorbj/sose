package entities.administrativo
{
	import entities.estoque.Estoque;
	
	import mx.collections.ArrayCollection;
	
	[Bindable]	
	[RemoteClass(alias="br.com.sose.entity.admistrativo.Componente")]	
	public class Componente
	{
		
		private var _id:Number;
		private var _descricao:String;
		private var _nome:String;
		private var _encapsulamento:Encapsulamento;
		private var _fabricante:Fabricante;
		private var _tipo:TipoComponente;
		private var _estoque:Estoque;
		private var _qtdEstoqueMinimo:Number;
		private var _qtdEstoque:Number;
		private var _posicaoEstoque:String;
		private var _dataSheet:String;
		private var _componentesEquivalentes:ArrayCollection;
		private var _fornecedores:ArrayCollection;
		private var _cadastroSistemaRealizadoEm:Date;
		private var _cadastroSistemaRealizadoPor:Usuario;
		private var _cadastroSistemaAtivo:Boolean;
		private var _pinos:String;
		private var _fornecedor:String;
		private var _qtdAcumulada:Number;
		private var _precoUnitario:Number;
		private var _dtUltimaCompra:Date;
		private var _nomeFabricante:String;
		private var _nomeEncapsulamento:String;
		private var _nomeTipoComponente:String;
		private var _valido:Boolean;
		private var _qtdComprada:Number;
		private var _dtUltimoInventario:Date;
		
		public function Componente(){
			_id = 0;
			_nome = "";
			_qtdEstoqueMinimo = 0;
			_qtdAcumulada = 0;
			_precoUnitario = 0;
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
		
		public function get encapsulamento():Encapsulamento
		{
			return _encapsulamento;
		}
		
		public function set encapsulamento(value:Encapsulamento):void
		{
			_encapsulamento = value;
		}
		
		public function get fabricante():Fabricante
		{
			return _fabricante;
		}
		
		public function set fabricante(value:Fabricante):void
		{
			_fabricante = value;
		}
		
		public function get tipo():TipoComponente
		{
			return _tipo;
		}
		
		public function set tipo(value:TipoComponente):void
		{
			_tipo = value;
		}
		
		public function get estoque():Estoque
		{
			return _estoque;
		}
		
		public function set estoque(value:Estoque):void
		{
			_estoque = value;
		}
		
		public function get dataSheet():String
		{
			return _dataSheet;
		}
		
		public function set dataSheet(value:String):void
		{
			_dataSheet = value;
		}
		
		public function get componentesEquivalentes():ArrayCollection
		{
			return _componentesEquivalentes;
		}
		
		public function set componentesEquivalentes(value:ArrayCollection):void
		{
			_componentesEquivalentes = value;
		}
		
		public function get fornecedores():ArrayCollection
		{
			return _fornecedores;
		}
		
		public function set fornecedores(value:ArrayCollection):void
		{
			_fornecedores = value;
		}
		
		public function get cadastroSistemaRealizadoEm():Date
		{
			return _cadastroSistemaRealizadoEm;
		}
		
		public function set cadastroSistemaRealizadoEm(value:Date):void
		{
			_cadastroSistemaRealizadoEm = value;
		}
		
		public function get cadastroSistemaRealizadoPor():Usuario
		{
			return _cadastroSistemaRealizadoPor;
		}
		
		public function set cadastroSistemaRealizadoPor(value:Usuario):void
		{
			_cadastroSistemaRealizadoPor = value;
		}
		
		public function get cadastroSistemaAtivo():Boolean
		{
			return _cadastroSistemaAtivo;
		}
		
		public function set cadastroSistemaAtivo(value:Boolean):void
		{
			_cadastroSistemaAtivo = value;
		}
		
		public function get qtdEstoqueMinimo():Number
		{
			return _qtdEstoqueMinimo;
		}
		
		public function set qtdEstoqueMinimo(value:Number):void
		{
			_qtdEstoqueMinimo = value;
		}

		public function get qtdEstoque():Number
		{
			return _qtdEstoque;
		}

		public function set qtdEstoque(value:Number):void
		{
			_qtdEstoque = value;
		}

		public function get posicaoEstoque():String
		{
			return _posicaoEstoque;
		}

		public function set posicaoEstoque(value:String):void
		{
			_posicaoEstoque = value;
		}

		public function get pinos():String
		{
			return _pinos;
		}

		public function set pinos(value:String):void
		{
			_pinos = value;
		}

		public function get fornecedor():String
		{
			return _fornecedor;
		}

		public function set fornecedor(value:String):void
		{
			_fornecedor = value;
		}

		public function get qtdAcumulada():Number
		{
			return _qtdAcumulada;
		}

		public function set qtdAcumulada(value:Number):void
		{
			_qtdAcumulada = value;
		}

		public function get precoUnitario():Number
		{
			return _precoUnitario;
		}

		public function set precoUnitario(value:Number):void
		{
			_precoUnitario = value;
		}

		public function get dtUltimaCompra():Date
		{
			return _dtUltimaCompra;
		}

		public function set dtUltimaCompra(value:Date):void
		{
			_dtUltimaCompra = value;
		}

		public function get nomeFabricante():String
		{
			return _nomeFabricante;
		}

		public function set nomeFabricante(value:String):void
		{
			_nomeFabricante = value;
		}

		public function get nomeEncapsulamento():String
		{
			return _nomeEncapsulamento;
		}

		public function set nomeEncapsulamento(value:String):void
		{
			_nomeEncapsulamento = value;
		}

		public function get nomeTipoComponente():String
		{
			return _nomeTipoComponente;
		}

		public function set nomeTipoComponente(value:String):void
		{
			_nomeTipoComponente = value;
		}

		public function get valido():Boolean
		{
			return _valido;
		}

		public function set valido(value:Boolean):void
		{
			_valido = value;
		}

		public function get qtdComprada():Number
		{
			return _qtdComprada;
		}

		public function set qtdComprada(value:Number):void
		{
			_qtdComprada = value;
		}

		public function get dtUltimoInventario():Date
		{
			return _dtUltimoInventario;
		}

		public function set dtUltimoInventario(value:Date):void
		{
			_dtUltimoInventario = value;
		}
		
		
	}
	
}
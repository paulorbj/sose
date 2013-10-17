package entities.compra
{
	import entities.administrativo.Componente;
	import entities.administrativo.Usuario;
	import entities.orcamentoreparo.RequisicaoComponente;
	import entities.recebimento.OrdemServico;
	
	[Bindable]	
	[RemoteClass(alias="br.com.sose.entity.compra.PedidoCompra")]	
	public class PedidoCompra
	{
		private var _id:Number;
		private var _ordemServico:OrdemServico;
		private var _tecnico:Usuario;
		private var _quantidade:Number;
		private var _requisicao:RequisicaoComponente;
		private var _possuiAmostra:Boolean;
		private var _componente:Componente;
		private var _dataCriacao:Date;
		private var _statusString:String;
		private var _itemCompra:ItemCompra;
		private var _isSelected:Boolean;
		private var _isEnabled:Boolean = true;
		private var _origemPedido:String;
			
		public function PedidoCompra(){
			_id = 0;

		}
		
		public function get dataCriacao():Date
		{
			return _dataCriacao;
		}

		public function set dataCriacao(value:Date):void
		{
			_dataCriacao = value;
		}

		public function get possuiAmostra():Boolean
		{
			return _possuiAmostra;
		}

		public function set possuiAmostra(value:Boolean):void
		{
			_possuiAmostra = value;
		}

		public function get requisicao():RequisicaoComponente
		{
			return _requisicao;
		}

		public function set requisicao(value:RequisicaoComponente):void
		{
			_requisicao = value;
		}

		public function get quantidade():Number
		{
			return _quantidade;
		}

		public function set quantidade(value:Number):void
		{
			_quantidade = value;
		}

		public function get tecnico():Usuario
		{
			return _tecnico;
		}

		public function set tecnico(value:Usuario):void
		{
			_tecnico = value;
		}

		public function get ordemServico():OrdemServico
		{
			return _ordemServico;
		}

		public function set ordemServico(value:OrdemServico):void
		{
			_ordemServico = value;
		}

		public function get id():Number
		{
			return _id;
		}

		public function set id(value:Number):void
		{
			_id = value;
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

		public function get itemCompra():ItemCompra
		{
			return _itemCompra;
		}

		public function set itemCompra(value:ItemCompra):void
		{
			_itemCompra = value;
		}

		public function get componente():Componente
		{
			return _componente;
		}

		public function set componente(value:Componente):void
		{
			_componente = value;
		}

		public function get origemPedido():String
		{
			return _origemPedido;
		}

		public function set origemPedido(value:String):void
		{
			_origemPedido = value;
		}


	}
}
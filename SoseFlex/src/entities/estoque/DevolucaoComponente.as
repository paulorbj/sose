package entities.estoque
{
	import entities.administrativo.Componente;
	import entities.administrativo.Usuario;
	import entities.orcamentoreparo.Orcamento;
	import entities.orcamentoreparo.Reparo;
	import entities.orcamentoreparo.RequisicaoComponente;
	
	[Bindable]	
	[RemoteClass(alias="br.com.sose.entity.estoque.DevolucaoComponente")]	
	public class DevolucaoComponente
	{
		
		private var _id:Number;
		private var _reparo:Reparo;
		private var _orcamento:Orcamento;
		private var _componente:Componente;
		private var _requisicao:RequisicaoComponente;
		private var _devolvidoPor:Usuario;	
		private var _devolvidoEm:Date;
		private var _recebidoPor:Usuario;	
		private var _recebidoEm:Date;		
		private var _condicao:String;
	
		

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

		public function get requisicao():RequisicaoComponente
		{
			return _requisicao;
		}

		public function set requisicao(value:RequisicaoComponente):void
		{
			_requisicao = value;
		}

		public function get devolvidoPor():Usuario
		{
			return _devolvidoPor;
		}

		public function set devolvidoPor(value:Usuario):void
		{
			_devolvidoPor = value;
		}

		public function get devolvidoEm():Date
		{
			return _devolvidoEm;
		}

		public function set devolvidoEm(value:Date):void
		{
			_devolvidoEm = value;
		}

		public function get recebidoPor():Usuario
		{
			return _recebidoPor;
		}

		public function set recebidoPor(value:Usuario):void
		{
			_recebidoPor = value;
		}

		public function get recebidoEm():Date
		{
			return _recebidoEm;
		}

		public function set recebidoEm(value:Date):void
		{
			_recebidoEm = value;
		}

		public function get condicao():String
		{
			return _condicao;
		}

		public function set condicao(value:String):void
		{
			_condicao = value;
		}


	}
}
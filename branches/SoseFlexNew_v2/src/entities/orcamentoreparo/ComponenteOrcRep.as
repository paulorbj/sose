package entities.orcamentoreparo
{
	import entities.administrativo.Componente;
	
	[Bindable]	
	[RemoteClass(alias="br.com.sose.entity.orcrepGenerico.ComponenteOrcRep")]		
	public class ComponenteOrcRep
	{
		private var _id:Number;
		private var _reparo:Reparo;
		private var _orcamento:Orcamento;
		private var _componente:Componente;
		private var _requisicao:RequisicaoComponente;
		private var _posicao:String;
		private var _condicao:String;
		
		public function ComponenteOrcRep(){
			_id = 0;
			_posicao = "";
		}
		
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

		public function get componente():Componente
		{
			return _componente;
		}

		public function set componente(value:Componente):void
		{
			_componente = value;
		}

		public function get posicao():String
		{
			return _posicao;
		}

		public function set posicao(value:String):void
		{
			_posicao = value;
		}

		public function get orcamento():Orcamento
		{
			return _orcamento;
		}

		public function set orcamento(value:Orcamento):void
		{
			_orcamento = value;
		}

		public function get requisicao():RequisicaoComponente
		{
			return _requisicao;
		}

		public function set requisicao(value:RequisicaoComponente):void
		{
			_requisicao = value;
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
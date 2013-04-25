package entities.orcamentoreparo
{
	import entities.administrativo.Atividade;
	import entities.administrativo.Usuario;
	
	[Bindable]	
	[RemoteClass(alias="br.com.sose.entity.orcrepGenerico.AtividadeOrcRep")]	
	public class AtividadeOrcRep
	{
		
		private var _id:Number;
		private var _atividade:Atividade;
		private var _justificativa:String;
		private var _data:Date;
		private var _usuario:Usuario;
		private var _reparo:Reparo;
		private var _orcamento:Orcamento;

		
		public function AtividadeOrcRep(){
			_id = 0;
			_justificativa = "";
		}
		
		public function get id():Number
		{
			return _id;
		}
		
		public function set id(value:Number):void
		{
			_id = value;
		}

		public function get atividade():Atividade
		{
			return _atividade;
		}

		public function set atividade(value:Atividade):void
		{
			_atividade = value;
		}

		public function get justificativa():String
		{
			return _justificativa;
		}

		public function set justificativa(value:String):void
		{
			_justificativa = value;
		}

		public function get data():Date
		{
			return _data;
		}

		public function set data(value:Date):void
		{
			_data = value;
		}

		public function get usuario():Usuario
		{
			return _usuario;
		}

		public function set usuario(value:Usuario):void
		{
			_usuario = value;
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
		
		
	}
		
}
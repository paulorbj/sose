package entities.orcamentoreparo
{
	import entities.administrativo.Defeito;
	import entities.administrativo.Usuario;
	
	[Bindable]	
	[RemoteClass(alias="br.com.sose.entity.orcrepGenerico.DefeitoOrcRep")]	
	public class DefeitoOrcRep
	{
		
		private var _id:Number;
		private var _defeito:Defeito;
		private var _justificativa:String;
		private var _data:Date;
		private var _usuario:Usuario;
		private var _reparo:Reparo;
		private var _orcamento:Orcamento;
		
		public function DefeitoOrcRep(){
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

		public function get defeito():Defeito
		{
			return _defeito;
		}

		public function set defeito(value:Defeito):void
		{
			_defeito = value;
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
package entities.orcamentoreparo
{
	
	[Bindable]	
	[RemoteClass(alias="br.com.sose.entity.orcamento.Orcamento")]	
	public class Orcamento extends OrcRepGenerico
	{

		private var _laudoTecnicoAprovado:Date;
		
		private var _laudoTecnicoReprovado:Date;
		
		private var _propostaReprovada:Date;
		
		private var _rejeitadoPeloLider:Date;
		
		public function get laudoTecnicoAprovado():Date
		{
			return _laudoTecnicoAprovado;
		}

		public function set laudoTecnicoAprovado(value:Date):void
		{
			_laudoTecnicoAprovado = value;
		}

		public function get laudoTecnicoReprovado():Date
		{
			return _laudoTecnicoReprovado;
		}

		public function set laudoTecnicoReprovado(value:Date):void
		{
			_laudoTecnicoReprovado = value;
		}

		public function get propostaReprovada():Date
		{
			return _propostaReprovada;
		}

		public function set propostaReprovada(value:Date):void
		{
			_propostaReprovada = value;
		}

		public function get rejeitadoPeloLider():Date
		{
			return _rejeitadoPeloLider;
		}

		public function set rejeitadoPeloLider(value:Date):void
		{
			_rejeitadoPeloLider = value;
		}


	}
}
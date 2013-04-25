package entities.orcamentoreparo
{
	import entities.recebimento.OrdemServico;
	
	[Bindable]	
	[RemoteClass(alias="br.com.sose.entity.reparo.Reparo")]	
	public class Reparo extends OrcRepGenerico
	{

		private var _laudoTecnicoAprovado:Date;
		
		private var _laudoTecnicoReprovado:Date;
		
		private var _orcamentoDiferenciadoRejeitado:Date;
		
		private var _propostaAprovada:Date;
		
		private var _propostaReprovada:Date;
		
		private var _color:uint;
		
		private var _criadoFromOrcamento:Boolean;

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

		public function get orcamentoDiferenciadoRejeitado():Date
		{
			return _orcamentoDiferenciadoRejeitado;
		}

		public function set orcamentoDiferenciadoRejeitado(value:Date):void
		{
			_orcamentoDiferenciadoRejeitado = value;
		}

		public function get propostaAprovada():Date
		{
			return _propostaAprovada;
		}

		public function set propostaAprovada(value:Date):void
		{
			_propostaAprovada = value;
		}

		public function get propostaReprovada():Date
		{
			return _propostaReprovada;
		}

		public function set propostaReprovada(value:Date):void
		{
			_propostaReprovada = value;
		}

		public function get color():uint
		{
			return _color;
		}

		public function set color(value:uint):void
		{
			_color = value;
		}

		public function get criadoFromOrcamento():Boolean
		{
			return _criadoFromOrcamento;
		}

		public function set criadoFromOrcamento(value:Boolean):void
		{
			_criadoFromOrcamento = value;
		}


	}
}
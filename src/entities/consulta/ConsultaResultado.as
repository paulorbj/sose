package entities.consulta
{	
	import mx.collections.ArrayCollection;
	
	[Bindable]	
	[RemoteClass(alias="br.com.sose.entity.consulta.ConsultaResultado")]	
	public class ConsultaResultado
	{
		
		private var _listaNotaFiscal:ArrayCollection;
		private var _listaOrdemServico:ArrayCollection;
		private var _listaNotaFiscalSaida:ArrayCollection;
	
		public function get listaNotaFiscal():ArrayCollection
		{
			return _listaNotaFiscal;
		}

		public function set listaNotaFiscal(value:ArrayCollection):void
		{
			_listaNotaFiscal = value;
		}

		public function get listaOrdemServico():ArrayCollection
		{
			return _listaOrdemServico;
		}

		public function set listaOrdemServico(value:ArrayCollection):void
		{
			_listaOrdemServico = value;
		}

		public function get listaNotaFiscalSaida():ArrayCollection
		{
			return _listaNotaFiscalSaida;
		}

		public function set listaNotaFiscalSaida(value:ArrayCollection):void
		{
			_listaNotaFiscalSaida = value;
		}


	}
}
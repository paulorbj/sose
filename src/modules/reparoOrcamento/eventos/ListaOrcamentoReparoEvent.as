package modules.reparoOrcamento.eventos
{
	import entities.administrativo.Usuario;
	import entities.orcamentoreparo.OrcRepGenerico;
	
	import flash.events.Event;
	
	import mx.collections.ArrayCollection;
	
	import to.OrcRepGenericoTO;
	
	public class ListaOrcamentoReparoEvent extends Event
	{
		public static const ATRIBUIR_TECNICO:String = "atribuirTecnico";
		public static const ATRIBUIR_PRIORIDADE:String = "atribuirPrioridade";
		public static const EXCLUIR_ELEMENTOS:String = "editarOrcamento";
		public static const VISUALIZAR_REPARO:String = "visualizarReparo";
		public static const EDITAR_REPARO:String = "editarReparo";
		public static const EDITAR_ORCAMENTO:String = "editarOrcamento";
		public static const VISUALIZAR_ORCAMENTO:String = "visualizarOrcamento";
		//public static const VISUALIZAR_ORCAMENTO:String = "visualizarOrcamento";

		private var _orcRep:OrcRepGenerico;
		private	var _listaOrcamentoReparo:ArrayCollection;
		private var _tecnico:Usuario;
		private var _atribuidoPor:Usuario
		private var _data:Date;
		
		public function ListaOrcamentoReparoEvent(tipo:String,lista:ArrayCollection,tecnico:Usuario,data:Date=null,orcRep:OrcRepGenerico = null,atribuidoPor:Usuario = null)
		{
			super(tipo, true, false);
			this._listaOrcamentoReparo = lista;
			this._tecnico = tecnico;
			this._data = data;
			this._orcRep = orcRep;
			this._atribuidoPor = atribuidoPor;
		}		

		public function get listaOrcamentoReparo():ArrayCollection
		{
			return _listaOrcamentoReparo;
		}

		public function set listaOrcamentoReparo(value:ArrayCollection):void
		{
			_listaOrcamentoReparo = value;
		}

		public function get tecnico():Usuario
		{
			return _tecnico;
		}

		public function set tecnico(value:Usuario):void
		{
			_tecnico = value;
		}

		public function get data():Date
		{
			return _data;
		}

		public function set data(value:Date):void
		{
			_data = value;
		}

		public function get orcRep():OrcRepGenerico
		{
			return _orcRep;
		}

		public function set orcRep(value:OrcRepGenerico):void
		{
			_orcRep = value;
		}

		public function get atribuidoPor():Usuario
		{
			return _atribuidoPor;
		}

		public function set atribuidoPor(value:Usuario):void
		{
			_atribuidoPor = value;
		}



	}
}
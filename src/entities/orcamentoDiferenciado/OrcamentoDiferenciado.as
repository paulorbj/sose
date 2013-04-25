package entities.orcamentoDiferenciado
{
	import entities.administrativo.Usuario;
	import entities.orcamentoreparo.Reparo;
	import entities.recebimento.OrdemServico;
	
	[Bindable]	
	[RemoteClass(alias="br.com.sose.entity.orcamento.OrcamentoDiferenciado")]	
	public class OrcamentoDiferenciado
	{
		
		private var _id:Number;
		private var _reparo:Reparo;
		private var _dataCriacao:Date;
		private var _dataInicio:Date;
		private var _dataFim:Date;
		private var _realizadoPor:Usuario;
		private var _criadoPor:Usuario;
		private var _informacaoTecnica:String;
		private var _statusString:String;
		private var _descricao:String;
		private var _ordemServico:OrdemServico;

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

		public function get descricao():String
		{
			return _descricao;
		}

		public function set descricao(value:String):void
		{
			_descricao = value;
		}

		public function get ordemServico():OrdemServico
		{
			return _ordemServico;
		}

		public function set ordemServico(value:OrdemServico):void
		{
			_ordemServico = value;
		}

		public function get reparo():Reparo
		{
			return _reparo;
		}

		public function set reparo(value:Reparo):void
		{
			_reparo = value;
		}

		public function get dataCriacao():Date
		{
			return _dataCriacao;
		}

		public function set dataCriacao(value:Date):void
		{
			_dataCriacao = value;
		}

		public function get dataInicio():Date
		{
			return _dataInicio;
		}

		public function set dataInicio(value:Date):void
		{
			_dataInicio = value;
		}

		public function get dataFim():Date
		{
			return _dataFim;
		}

		public function set dataFim(value:Date):void
		{
			_dataFim = value;
		}

		public function get realizadoPor():Usuario
		{
			return _realizadoPor;
		}

		public function set realizadoPor(value:Usuario):void
		{
			_realizadoPor = value;
		}

		public function get criadoPor():Usuario
		{
			return _criadoPor;
		}

		public function set criadoPor(value:Usuario):void
		{
			_criadoPor = value;
		}

		public function get informacaoTecnica():String
		{
			return _informacaoTecnica;
		}

		public function set informacaoTecnica(value:String):void
		{
			_informacaoTecnica = value;
		}


	}
}
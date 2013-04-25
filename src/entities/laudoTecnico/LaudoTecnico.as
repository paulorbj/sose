package entities.laudoTecnico
{
	import entities.administrativo.Usuario;
	import entities.orcamentoreparo.Orcamento;
	import entities.orcamentoreparo.Reparo;
	import entities.recebimento.OrdemServico;
	
	[Bindable]	
	[RemoteClass(alias="br.com.sose.entity.laudoTecnico.LaudoTecnico")]	
	public class LaudoTecnico
	{
		
		private var _id:Number;
		private var _controle:String;
		private var _dataCriacao:Date;
		private var _dataInicio:Date;
		private var _dataFim:Date;
		private var _realizadoPor:Usuario;
		private var _criadoPor:Usuario;
		private var _informacaoTecnica:String;
		private var _statusString:String;
		private var _descricao:String;
		private var _ordemServico:OrdemServico;
		private var _reparo:Reparo;
		private var _orcamento:Orcamento;
		private var _numeroOrdemServico:String;
		private var _numeroOrdemServicoPai:String;
		private var _cliente:String;
		private var _unidade:String;
		private var _serieFabricante:String;
		private var _serieCliente:String;
		private var _laboratorio:String;
		private var _tecnico:String;

		public function get id():Number
		{
			return _id;
		}

		public function set id(value:Number):void
		{
			_id = value;
		}

		public function get controle():String
		{
			return _controle;
		}

		public function set controle(value:String):void
		{
			_controle = value;
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

		public function get numeroOrdemServico():String
		{
			return _numeroOrdemServico;
		}

		public function set numeroOrdemServico(value:String):void
		{
			_numeroOrdemServico = value;
		}

		public function get numeroOrdemServicoPai():String
		{
			return _numeroOrdemServicoPai;
		}

		public function set numeroOrdemServicoPai(value:String):void
		{
			_numeroOrdemServicoPai = value;
		}

		public function get cliente():String
		{
			return _cliente;
		}

		public function set cliente(value:String):void
		{
			_cliente = value;
		}

		public function get unidade():String
		{
			return _unidade;
		}

		public function set unidade(value:String):void
		{
			_unidade = value;
		}

		public function get serieFabricante():String
		{
			return _serieFabricante;
		}

		public function set serieFabricante(value:String):void
		{
			_serieFabricante = value;
		}

		public function get serieCliente():String
		{
			return _serieCliente;
		}

		public function set serieCliente(value:String):void
		{
			_serieCliente = value;
		}

		public function get laboratorio():String
		{
			return _laboratorio;
		}

		public function set laboratorio(value:String):void
		{
			_laboratorio = value;
		}

		public function get tecnico():String
		{
			return _tecnico;
		}

		public function set tecnico(value:String):void
		{
			_tecnico = value;
		}


	}
}
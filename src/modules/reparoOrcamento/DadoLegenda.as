package modules.reparoOrcamento
{
	[Bindable]
	public class DadoLegenda
	{
		private var _nOrcamentoAguardandoProposta:Number = 0;
		private var _nReparoAguardandoProposta:Number = 0;
		private var _nOrcamentoAguardandoLiberacao:Number = 0;
		private var _nReparoAguardandoLiberacao:Number = 0;
		private var _nOrcamentoNaoIniciado:Number = 0;
		private var _nReparoNaoIniciado:Number = 0;
		private var _nOrcamentoIniciado:Number = 0;
		private var _nReparoIniciado:Number = 0;
		private var _nOrcamentoFinalizado:Number = 0;
		private var _nReparoFinalizado:Number = 0;
		private var _nOrcamentoRejeitado:Number = 0;
		private var _nReparoRejeitado:Number = 0;
		private var _nTotalReparo:Number = 0;
		private var _nTotalOrcamento:Number = 0;
		private var _nOrcamentoAguardandoLaudoTecnico:Number = 0;
		private var _nReparoAguardandoLaudoTecnico:Number = 0;
		private var _nOrcamentoAguardandoOrcamentoDiferenciado:Number = 0;
		private var _nReparoAguardandoOrcamentoDiferenciado:Number = 0;
		private var _nOrcamentoLaudoTecnicoRejeitado:Number = 0;
		private var _nReparoLaudoTecnicoRejeitado:Number = 0;
		private var _nOrcamentoLaudoTecnicoAprovado:Number = 0;
		private var _nReparoLaudoTecnicoAprovado:Number = 0;
		private var _nOrcamentoOrcamentoDiferenciadoRejeitado:Number = 0;
		private var _nReparoOrcamentoDiferenciadoRejeitado:Number = 0;
		private var _nOrcamentoPopostaAprovada:Number = 0;
		private var _nReparoPopostaAprovada:Number = 0;
		private var _nOrcamentoPropostaReprovada:Number = 0;
		private var _nReparoPropostaReprovada:Number = 0;
		private var _nOrcamentoAguardandoComponente:Number = 0;
		private var _nReparoAguardandoComponente:Number = 0;
		private var _nOrcamentoComponenteEmFalta:Number = 0;
		private var _nReparoComponenteEmFalta:Number = 0;		
		private var _nOrcamentoAguardandoAprovacaoLider:Number = 0;
		private var _nReparoAguardandoAprovacaoLider:Number = 0;
		
		public function DadoLegenda()
		{
		}

		

		public function get nOrcamentoAguardandoProposta():Number
		{
			return _nOrcamentoAguardandoProposta;
		}

		public function set nOrcamentoAguardandoProposta(value:Number):void
		{
			_nOrcamentoAguardandoProposta = value;
		}

		public function get nReparoAguardandoProposta():Number
		{
			return _nReparoAguardandoProposta;
		}

		public function set nReparoAguardandoProposta(value:Number):void
		{
			_nReparoAguardandoProposta = value;
		}

		public function get nOrcamentoAguardandoLiberacao():Number
		{
			return _nOrcamentoAguardandoLiberacao;
		}

		public function set nOrcamentoAguardandoLiberacao(value:Number):void
		{
			_nOrcamentoAguardandoLiberacao = value;
		}

		public function get nReparoAguardandoLiberacao():Number
		{
			return _nReparoAguardandoLiberacao;
		}

		public function set nReparoAguardandoLiberacao(value:Number):void
		{
			_nReparoAguardandoLiberacao = value;
		}

		public function get nOrcamentoNaoIniciado():Number
		{
			return _nOrcamentoNaoIniciado;
		}

		public function set nOrcamentoNaoIniciado(value:Number):void
		{
			_nOrcamentoNaoIniciado = value;
		}

		public function get nReparoNaoIniciado():Number
		{
			return _nReparoNaoIniciado;
		}

		public function set nReparoNaoIniciado(value:Number):void
		{
			_nReparoNaoIniciado = value;
		}

		public function get nOrcamentoIniciado():Number
		{
			return _nOrcamentoIniciado;
		}

		public function set nOrcamentoIniciado(value:Number):void
		{
			_nOrcamentoIniciado = value;
		}

		public function get nReparoIniciado():Number
		{
			return _nReparoIniciado;
		}

		public function set nReparoIniciado(value:Number):void
		{
			_nReparoIniciado = value;
		}

		public function get nOrcamentoFinalizado():Number
		{
			return _nOrcamentoFinalizado;
		}

		public function set nOrcamentoFinalizado(value:Number):void
		{
			_nOrcamentoFinalizado = value;
		}

		public function get nReparoFinalizado():Number
		{
			return _nReparoFinalizado;
		}

		public function set nReparoFinalizado(value:Number):void
		{
			_nReparoFinalizado = value;
		}

		public function get nOrcamentoRejeitado():Number
		{
			return _nOrcamentoRejeitado;
		}

		public function set nOrcamentoRejeitado(value:Number):void
		{
			_nOrcamentoRejeitado = value;
		}

		public function get nReparoRejeitado():Number
		{
			return _nReparoRejeitado;
		}

		public function set nReparoRejeitado(value:Number):void
		{
			_nReparoRejeitado = value;
		}

		public function get nTotalReparo():Number
		{
			return _nTotalReparo;
		}

		public function set nTotalReparo(value:Number):void
		{
			_nTotalReparo = value;
		}

		public function get nTotalOrcamento():Number
		{
			return _nTotalOrcamento;
		}

		public function set nTotalOrcamento(value:Number):void
		{
			_nTotalOrcamento = value;
		}

		public function get nOrcamentoAguardandoLaudoTecnico():Number
		{
			return _nOrcamentoAguardandoLaudoTecnico;
		}

		public function set nOrcamentoAguardandoLaudoTecnico(value:Number):void
		{
			_nOrcamentoAguardandoLaudoTecnico = value;
		}

		public function get nReparoAguardandoLaudoTecnico():Number
		{
			return _nReparoAguardandoLaudoTecnico;
		}

		public function set nReparoAguardandoLaudoTecnico(value:Number):void
		{
			_nReparoAguardandoLaudoTecnico = value;
		}

		public function get nOrcamentoAguardandoOrcamentoDiferenciado():Number
		{
			return _nOrcamentoAguardandoOrcamentoDiferenciado;
		}

		public function set nOrcamentoAguardandoOrcamentoDiferenciado(value:Number):void
		{
			_nOrcamentoAguardandoOrcamentoDiferenciado = value;
		}

		public function get nReparoAguardandoOrcamentoDiferenciado():Number
		{
			return _nReparoAguardandoOrcamentoDiferenciado;
		}

		public function set nReparoAguardandoOrcamentoDiferenciado(value:Number):void
		{
			_nReparoAguardandoOrcamentoDiferenciado = value;
		}

		public function get nOrcamentoLaudoTecnicoRejeitado():Number
		{
			return _nOrcamentoLaudoTecnicoRejeitado;
		}

		public function set nOrcamentoLaudoTecnicoRejeitado(value:Number):void
		{
			_nOrcamentoLaudoTecnicoRejeitado = value;
		}

		public function get nReparoLaudoTecnicoRejeitado():Number
		{
			return _nReparoLaudoTecnicoRejeitado;
		}

		public function set nReparoLaudoTecnicoRejeitado(value:Number):void
		{
			_nReparoLaudoTecnicoRejeitado = value;
		}

		public function get nOrcamentoLaudoTecnicoAprovado():Number
		{
			return _nOrcamentoLaudoTecnicoAprovado;
		}

		public function set nOrcamentoLaudoTecnicoAprovado(value:Number):void
		{
			_nOrcamentoLaudoTecnicoAprovado = value;
		}

		public function get nReparoLaudoTecnicoAprovado():Number
		{
			return _nReparoLaudoTecnicoAprovado;
		}

		public function set nReparoLaudoTecnicoAprovado(value:Number):void
		{
			_nReparoLaudoTecnicoAprovado = value;
		}

		public function get nOrcamentoOrcamentoDiferenciadoRejeitado():Number
		{
			return _nOrcamentoOrcamentoDiferenciadoRejeitado;
		}

		public function set nOrcamentoOrcamentoDiferenciadoRejeitado(value:Number):void
		{
			_nOrcamentoOrcamentoDiferenciadoRejeitado = value;
		}

		public function get nReparoOrcamentoDiferenciadoRejeitado():Number
		{
			return _nReparoOrcamentoDiferenciadoRejeitado;
		}

		public function set nReparoOrcamentoDiferenciadoRejeitado(value:Number):void
		{
			_nReparoOrcamentoDiferenciadoRejeitado = value;
		}

		public function get nOrcamentoPopostaAprovada():Number
		{
			return _nOrcamentoPopostaAprovada;
		}

		public function set nOrcamentoPopostaAprovada(value:Number):void
		{
			_nOrcamentoPopostaAprovada = value;
		}

		public function get nReparoPopostaAprovada():Number
		{
			return _nReparoPopostaAprovada;
		}

		public function set nReparoPopostaAprovada(value:Number):void
		{
			_nReparoPopostaAprovada = value;
		}

		public function get nOrcamentoPropostaReprovada():Number
		{
			return _nOrcamentoPropostaReprovada;
		}

		public function set nOrcamentoPropostaReprovada(value:Number):void
		{
			_nOrcamentoPropostaReprovada = value;
		}

		public function get nReparoPropostaReprovada():Number
		{
			return _nReparoPropostaReprovada;
		}

		public function set nReparoPropostaReprovada(value:Number):void
		{
			_nReparoPropostaReprovada = value;
		}

		public function get nOrcamentoAguardandoComponente():Number
		{
			return _nOrcamentoAguardandoComponente;
		}

		public function set nOrcamentoAguardandoComponente(value:Number):void
		{
			_nOrcamentoAguardandoComponente = value;
		}

		public function get nReparoAguardandoComponente():Number
		{
			return _nReparoAguardandoComponente;
		}

		public function set nReparoAguardandoComponente(value:Number):void
		{
			_nReparoAguardandoComponente = value;
		}

		public function get nOrcamentoComponenteEmFalta():Number
		{
			return _nOrcamentoComponenteEmFalta;
		}

		public function set nOrcamentoComponenteEmFalta(value:Number):void
		{
			_nOrcamentoComponenteEmFalta = value;
		}

		public function get nReparoComponenteEmFalta():Number
		{
			return _nReparoComponenteEmFalta;
		}

		public function set nReparoComponenteEmFalta(value:Number):void
		{
			_nReparoComponenteEmFalta = value;
		}

		public function get nOrcamentoAguardandoAprovacaoLider():Number
		{
			return _nOrcamentoAguardandoAprovacaoLider;
		}

		public function set nOrcamentoAguardandoAprovacaoLider(value:Number):void
		{
			_nOrcamentoAguardandoAprovacaoLider = value;
		}

		public function get nReparoAguardandoAprovacaoLider():Number
		{
			return _nReparoAguardandoAprovacaoLider;
		}

		public function set nReparoAguardandoAprovacaoLider(value:Number):void
		{
			_nReparoAguardandoAprovacaoLider = value;
		}


	}
}
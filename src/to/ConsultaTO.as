package to
{
	import utils.ScreenUtils;

	[Bindable]	
	[RemoteClass(alias="br.com.sose.to.ConsultaTO")]
	public class ConsultaTO
	{
		
		private var _id:Number;
		
		private var _nOs:String;
		
		private var _nOsPai:String;
		
		private var _idUnidadePai:Number;
		
		private var _garantia:Boolean;
		
		private var _idLpu:Number;
		
		private var _propostaTipo:String;
		
		private var _status:String;
		
		private var _reparoCondicao:String;
		
		private var _reparoDtFim:Date;
		
		private var _orcamentoCondicao:String;
		
		private var _orcamentoDtFim:Date;
		
		private var _cliente:String;
		
		private var _unidade:String;
		
		private var _nSerieFabricante:String;
		
		private var _nSerieCliente:String;
		
		private var _nNf:String;
		
		private var _laboratorio:String;
		
		private var _nProposta:String;
		
		private var _caseAvaya:String;
		
		private var _clienteAvaya:String;
		
		private var _nNfSaida:String;
		
		private var _dataChegada:Date;
		
		private var _condicao:String;
		
		private var _origem:String;
		
		private var _isAprovado:Boolean;
		
		private var _dataAprovacao:Date;
		
		private var _statusEstoque:String;
		
		private var _posicaoEstoque:String;
		
		private var _obsNotaFiscal:String;
		
		private var _obsFaturamento:String;
		
		private var _obsNotaFiscalSaida:String;
		
		private var _obsOrcamento:String;
		
		private var _obsProposta:String;
		
		private var _obsReparo:String;
		
		private var _obsOrdemServico:String;
		
		private var _idOrcamento:Number;
		
		private var _idReparo:Number;
		
		private var _dataEmissaoNFSaida:Date;
		
		private var _dataEntradaNF:Date;
		
		private var _dataChegadaNF:Date;
		
		private var _obsConsulta:String;
				
		public function ConsultaTO()
		{
		}
		
		public function get id():Number
		{
			return _id;
		}
		
		public function set id(value:Number):void
		{
			_id = value;
		}
		
		public function get nOs():String
		{
			return _nOs;
		}
		
		public function set nOs(value:String):void
		{
			_nOs = value;
		}
		
		public function get nOsPai():String
		{
			return _nOsPai;
		}
		
		public function set nOsPai(value:String):void
		{
			_nOsPai = value;
		}
		
		public function get status():String
		{
			return _status;
		}
		
		public function set status(value:String):void
		{
			_status = value;
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
		
		public function get nSerieFabricante():String
		{
			return _nSerieFabricante;
		}
		
		public function set nSerieFabricante(value:String):void
		{
			_nSerieFabricante = value;
		}
		
		public function get nSerieCliente():String
		{
			return _nSerieCliente;
		}
		
		public function set nSerieCliente(value:String):void
		{
			_nSerieCliente = value;
		}
		
		public function get nNf():String
		{
			return _nNf;
		}
		
		public function set nNf(value:String):void
		{
			_nNf = value;
		}
		
		public function get laboratorio():String
		{
			return _laboratorio;
		}
		
		public function set laboratorio(value:String):void
		{
			_laboratorio = value;
		}
		
		public function get nProposta():String
		{
			return _nProposta;
		}
		
		public function set nProposta(value:String):void
		{
			_nProposta = value;
		}
		
		public function get caseAvaya():String
		{
			return _caseAvaya;
		}
		
		public function set caseAvaya(value:String):void
		{
			_caseAvaya = value;
		}
		
		public function get clienteAvaya():String
		{
			return _clienteAvaya;
		}
		
		public function set clienteAvaya(value:String):void
		{
			_clienteAvaya = value;
		}
		
		public function get nNfSaida():String
		{
			return _nNfSaida;
		}
		
		public function set nNfSaida(value:String):void
		{
			_nNfSaida = value;
		}
		
		public function get dataChegada():Date
		{
			return _dataChegada;
		}
		
		public function set dataChegada(value:Date):void
		{
			_dataChegada = value;
		}
		
		public function get condicao():String
		{
			if(_idOrcamento == 34182){
				trace("teste"); 
			}
			if(_idReparo != 0 && _reparoDtFim != null){
				if(_reparoCondicao == "Com condição de reparo"){
					return "Reparado";
				}else if(_reparoCondicao == "Sem condição de reparo"){
					return "Irreparável";
				}else{
					if(_reparoCondicao == null || _reparoCondicao == ''){
						trace('T1 _idReparo: '+_idReparo + ' _idOrcamento: '+_idOrcamento);
					}
					return _reparoCondicao;
				}
			}else if(_idReparo != 0 && _reparoDtFim == null){
				if(_reparoCondicao == null || _reparoCondicao == ''){
					trace('T2 _idReparo: '+_idReparo +  ' _idOrcamento: '+_idOrcamento);
				}
				return _reparoCondicao;	
			}
			else if(_idOrcamento != 0 && _orcamentoDtFim != null){
				if(_orcamentoCondicao == "Com condição de reparo"){
					return "Reparado";
				}else if(_orcamentoCondicao == "Sem condição de reparo"){
					return "Irreparável";
				}else{
					if(_orcamentoCondicao == null || _orcamentoCondicao == ''){
						trace('T1 _idReparo: '+_idReparo + ' _idOrcamento: '+_idOrcamento);
					}
					return _orcamentoCondicao;
				}
			}else{
				return _orcamentoCondicao;
			}
			
		}
		
		public function set condicao(value:String):void
		{
			_condicao = value;
		}
		
		public function get origem():String
		{
			if(_garantia){
				return "Garantia";
			}else{
				if(_propostaTipo == "Orçamento diferenciado"){
					return "Orçamento diferenciado";
				}else if(_idLpu != 0){
					return "LPU";
				}else{
					return "Orçamento";	
				}
			}
			return _origem;
		}
		
		public function set origem(value:String):void
		{
			_origem = value;
		}
		
		public function get isAprovado():Boolean
		{
			return _isAprovado;
		}
		
		public function set isAprovado(value:Boolean):void
		{
			_isAprovado = value;
		}
		
		public function get dataAprovacao():Date
		{
			return _dataAprovacao;
		}
		
		public function set dataAprovacao(value:Date):void
		{
			_dataAprovacao = value;
		}
		
		public function get propostaAprovada():String
		{
			if(_nProposta != null && _nProposta != ""){
				if(_isAprovado){
					return "Aprovado";
				}else{
					return "Reprovado";
				}
			}else{
				return "";
			}
		}
		
		public function get statusEstoque():String
		{
			return _statusEstoque;
		}
		
		public function set statusEstoque(value:String):void
		{
			_statusEstoque = value;
		}
		
		public function get posicaoEstoque():String
		{
			return _posicaoEstoque;
		}
		
		public function set posicaoEstoque(value:String):void
		{
			_posicaoEstoque = value;
		}
		
		public function get obsOrcamento():String
		{
			return _obsOrcamento;
		}
		
		public function set obsOrcamento(value:String):void
		{
			_obsOrcamento = value;
		}
		
		public function get obsProposta():String
		{
			return _obsProposta;
		}
		
		public function set obsProposta(value:String):void
		{
			_obsProposta = value;
		}
		
		public function get obsReparo():String
		{
			return _obsReparo;
		}
		
		public function set obsReparo(value:String):void
		{
			_obsReparo = value;
		}
		
		public function get idUnidadePai():Number
		{
			return _idUnidadePai;
		}
		
		public function set idUnidadePai(value:Number):void
		{
			_idUnidadePai = value;
		}
		
		public function get garantia():Boolean
		{
			return _garantia;
		}
		
		public function set garantia(value:Boolean):void
		{
			_garantia = value;
		}
		
		public function get idLpu():Number
		{
			return _idLpu;
		}
		
		public function set idLpu(value:Number):void
		{
			_idLpu = value;
		}
		
		public function get propostaTipo():String
		{
			return _propostaTipo;
		}
		
		public function set propostaTipo(value:String):void
		{
			_propostaTipo = value;
		}
		
		public function get reparoCondicao():String
		{
			return _reparoCondicao;
		}
		
		public function set reparoCondicao(value:String):void
		{
			_reparoCondicao = value;
		}
		
		public function get reparoDtFim():Date
		{
			return _reparoDtFim;
		}
		
		public function set reparoDtFim(value:Date):void
		{
			_reparoDtFim = value;
		}
		
		public function get reparoDtFimString():String
		{
			if(_reparoDtFim)
				return ScreenUtils.formatarDataDDMMYYYY(_reparoDtFim);
			else{
				return "";
			}
		}
		
		public function get orcamentoCondicao():String
		{
			return _orcamentoCondicao;
		}
		
		public function set orcamentoCondicao(value:String):void
		{
			_orcamentoCondicao = value;
		}
		
		public function get orcamentoDtFim():Date
		{
			return _orcamentoDtFim;
		}
		
		public function set orcamentoDtFim(value:Date):void
		{
			_orcamentoDtFim = value;
		}
		
		public function get orcamentoDtFimString():String
		{
			if(_orcamentoDtFim)
				return ScreenUtils.formatarDataDDMMYYYY(_orcamentoDtFim);
			else{
				return "";
			}
		}
		
		public function get obsNotaFiscal():String
		{
			return _obsNotaFiscal;
		}
		
		public function set obsNotaFiscal(value:String):void
		{
			_obsNotaFiscal = value;
		}
		
		public function get obsFaturamento():String
		{
			return _obsFaturamento;
		}
		
		public function set obsFaturamento(value:String):void
		{
			_obsFaturamento = value;
		}
		
		public function get obsNotaFiscalSaida():String
		{
			return _obsNotaFiscalSaida;
		}
		
		public function set obsNotaFiscalSaida(value:String):void
		{
			_obsNotaFiscalSaida = value;
		}
		
		public function get obsOrdemServico():String
		{
			return _obsOrdemServico;
		}
		
		public function set obsOrdemServico(value:String):void
		{
			_obsOrdemServico = value;
		}
		
		public function get tempoNaServilogi():int {
			var dataFinal:Date = new Date();
			// Calcula a diferença entre hoje e da data de inicio
			if(_dataChegada == null){
				return 0;
			}
			var diferenca:Number = dataFinal.time - _dataChegada.time;
			// Quantidade de milissegundos em um dia		
			var tempoDia:int = 1000 * 60 * 60 * 24;
			return diferenca / tempoDia;	
		}
		
		public function get idOrcamento():Number
		{
			return _idOrcamento;
		}
		
		public function set idOrcamento(value:Number):void
		{
			_idOrcamento = value;
		}
		
		public function get idReparo():Number
		{
			return _idReparo;
		}
		
		public function set idReparo(value:Number):void
		{
			_idReparo = value;
		}
		
		public function get dataEntradaNFString():String
		{
			if(_dataEntradaNF)
				return ScreenUtils.formatarDataDDMMYYYY(_dataEntradaNF);
			else{
				return "";
			}
		}
		
		public function get dataEmissaoNFSaidaString():String
		{
			if(_dataEmissaoNFSaida)
				return ScreenUtils.formatarDataDDMMYYYY(_dataEmissaoNFSaida);
			else{
				return "";
			}
		}

		public function get dataEmissaoNFSaida():Date
		{
			return _dataEmissaoNFSaida;
		}

		public function set dataEmissaoNFSaida(value:Date):void
		{
			_dataEmissaoNFSaida = value;
		}

		public function get dataEntradaNF():Date
		{
			return _dataEntradaNF;
		}

		public function set dataEntradaNF(value:Date):void
		{
			_dataEntradaNF = value;
		}
		
		public function get dataChegadaNFString():String
		{
			if(_dataChegadaNF)
				return ScreenUtils.formatarDataDDMMYYYY(_dataChegadaNF);
			else{
				return "";
			}
		}

		public function get dataChegadaNF():Date
		{
			return _dataChegadaNF;
		}

		public function set dataChegadaNF(value:Date):void
		{
			_dataChegadaNF = value;
		}

		public function get obsConsulta():String
		{
			return _obsConsulta;
		}

		public function set obsConsulta(value:String):void
		{
			_obsConsulta = value;
		}
		
		
	
	}
}
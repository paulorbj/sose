package entities.administrativo

{
	import entities.administrativo.Usuario;
	import entities.administrativo.parceiros.Pessoa;
	
	import flash.sampler.NewObjectSample;
	
	import mx.collections.ArrayCollection;
	
	[Bindable]	
	[RemoteClass(alias="br.com.sose.entity.externo.Externo")]	
	public class ObservacaoExterno
		
	{
		private var _id:Number;
		
		private var _dataCriacao:Date;
		private var _criadoPor:Usuario;
		private var _pessoaExterno:Pessoa;
		private var _nfSaida:String;
		private var _nfRetorno:String;
		private var _observacoes:String;
		
		/*	private var _telefones:ArrayCollection;
		private var _contatos:ArrayCollection;
		private var _cadastroSistemaRealizadoEm:Date;
		private var _cadastroSistemaRealizadoPor:Usuario;
		private var _cadastroSistemaAtivo:Boolean;
		private var _tipo:Number;
		private var _tipoPessoa:Number;
		private var _nomeRazaoSocial:String;
		private var _nomeSistema:String;
		private var _cpfCnpj:String;
		private var _rgIe:String;
		private var _inscricaoMunicipal:String;
		private var _data:Date;
		private var _possuiContrato:Boolean;
		private var _possuiPedidoCompra:Boolean;
		private var _tempoGarantia:Number;
		private var _prazoDevolucao:Number;
		private var _estenderGarantia:Boolean;*/
		
		
		/*No java esta assim:
		private Long id;
		private String controle;
		private Date dataCriacao;
		private Usuario criadoPor;
		private Pessoa pessoaExterno;
		private String nfSaida;
		private String nfRetorno;
		private String observacoes;
		*/
		
		
		public function ObservacaoExterno(dataCriacao:Date, pessoaExterno:Pessoa, criadoPor:Usuario, nfSaida:String, nfRetorno:String,  obss:String)
		{
			_id=0;
			_criadoPor = criadoPor;
			_pessoaExterno = pessoaExterno;
			_nfSaida = nfSaida;
			_nfRetorno = nfRetorno;
			_observacoes= obss;
			_dataCriacao = dataCriacao;
		}
		
		public function get id():Number
		{
			return _id;
		}
		
		public function set id(value:Number):void
		{
			_id = value;
		}
		
		public function get criadoPor():Usuario
		{
			return _criadoPor;
		}
		
		public function set criadoPor(value:Usuario):void
		{
			_criadoPor = value;
		}
		
		public function get pessoaExterno():Pessoa
		{
			return _pessoaExterno;
		}
		
		public function set pessoaExterno(value:Pessoa):void
		{
			_pessoaExterno = value;
		}
		
		public function get nfSaida():String
		{
			return _nfSaida;
		}
		
		public function set nfSaida(value:String):void
		{
			_nfSaida = value;
		}
		
		public function get nfRetorno():String
		{
			return _nfRetorno;
		}
		
		public function set nfRetorno(value:String):void
		{
			_nfRetorno = value;
		}
		
		
		
		public function get dataCriacao():Date
		{
			return _dataCriacao;
		}
		
		public function set dataCriacao(value:Date):void
		{
			_dataCriacao = value;
		}
		
		public function get observacoes():String
		{
			return _observacoes;
		}
		
		public function set observacoes(value:String):void
		{
			_observacoes = value;
		}
		
		
		
		
		
		
		
	}
}
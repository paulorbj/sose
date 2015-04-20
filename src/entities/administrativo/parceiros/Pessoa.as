package entities.administrativo.parceiros
{
	import entities.administrativo.Usuario;
	
	import flash.sampler.NewObjectSample;
	
	import mx.collections.ArrayCollection;
	
	[Bindable]	
	[RemoteClass(alias="br.com.sose.entity.admistrativo.parceiros.Pessoa")]	
	public class Pessoa
	{
		private var _id:Number;
		private var _enderecos:ArrayCollection;
		private var _telefones:ArrayCollection;
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
		private var _estenderGarantia:Boolean;

		public function Pessoa()
		{
			_id=0;
			_nomeSistema = "";
			_prazoDevolucao = 0;
			_tempoGarantia = 0;
			_cadastroSistemaAtivo = true;
			_enderecos = new ArrayCollection();
			_contatos = new ArrayCollection();
		}
		
		public function get id():Number
		{
			return _id;
		}

		public function set id(value:Number):void
		{
			_id = value;
		}

		public function get enderecos():ArrayCollection
		{
			return _enderecos;
		}

		public function set enderecos(value:ArrayCollection):void
		{
			_enderecos = value;
		}

		public function get telefones():ArrayCollection
		{
			return _telefones;
		}

		public function set telefones(value:ArrayCollection):void
		{
			_telefones = value;
		}

		public function get contatos():ArrayCollection
		{
			return _contatos;
		}

		public function set contatos(value:ArrayCollection):void
		{
			_contatos = value;
		}

		public function get cadastroSistemaRealizadoEm():Date
		{
			return _cadastroSistemaRealizadoEm;
		}

		public function set cadastroSistemaRealizadoEm(value:Date):void
		{
			_cadastroSistemaRealizadoEm = value;
		}

		public function get cadastroSistemaRealizadoPor():Usuario
		{
			return _cadastroSistemaRealizadoPor;
		}

		public function set cadastroSistemaRealizadoPor(value:Usuario):void
		{
			_cadastroSistemaRealizadoPor = value;
		}

		public function get cadastroSistemaAtivo():Boolean
		{
			return _cadastroSistemaAtivo;
		}

		public function set cadastroSistemaAtivo(value:Boolean):void
		{
			_cadastroSistemaAtivo = value;
		}

		public function get tipo():Number
		{
			return _tipo;
		}

		public function set tipo(value:Number):void
		{
			_tipo = value;
		}

		public function get tipoPessoa():Number
		{
			return _tipoPessoa;
		}

		public function set tipoPessoa(value:Number):void
		{
			_tipoPessoa = value;
		}

		public function get nomeRazaoSocial():String
		{
			return _nomeRazaoSocial;
		}

		public function set nomeRazaoSocial(value:String):void
		{
			_nomeRazaoSocial = value;
		}

		public function get nomeSistema():String
		{
			return _nomeSistema;
		}

		public function set nomeSistema(value:String):void
		{
			_nomeSistema = value;
		}

		public function get cpfCnpj():String
		{
			return _cpfCnpj;
		}

		public function set cpfCnpj(value:String):void
		{
			_cpfCnpj = value;
		}

		public function get rgIe():String
		{
			return _rgIe;
		}

		public function set rgIe(value:String):void
		{
			_rgIe = value;
		}

		public function get data():Date
		{
			return _data;
		}

		public function set data(value:Date):void
		{
			_data = value;
		}

		public function get possuiContrato():Boolean
		{
			return _possuiContrato;
		}

		public function set possuiContrato(value:Boolean):void
		{
			_possuiContrato = value;
		}

		public function get possuiPedidoCompra():Boolean
		{
			return _possuiPedidoCompra;
		}

		public function set possuiPedidoCompra(value:Boolean):void
		{
			_possuiPedidoCompra = value;
		}

		public function get tempoGarantia():Number
		{
			return _tempoGarantia;
		}

		public function set tempoGarantia(value:Number):void
		{
			_tempoGarantia = value;
		}

		public function get prazoDevolucao():Number
		{
			return _prazoDevolucao;
		}

		public function set prazoDevolucao(value:Number):void
		{
			_prazoDevolucao = value;
		}

		public function get inscricaoMunicipal():String
		{
			return _inscricaoMunicipal;
		}

		public function set inscricaoMunicipal(value:String):void
		{
			_inscricaoMunicipal = value;
		}

		public function get estenderGarantia():Boolean
		{
			return _estenderGarantia;
		}

		public function set estenderGarantia(value:Boolean):void
		{
			_estenderGarantia = value;
		}


	}
}
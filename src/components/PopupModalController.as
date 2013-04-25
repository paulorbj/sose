package components {
	
	import components.events.UserRegisterNumberModalEvent;
	
	import entities.recebimento.OrdemServico;
	
	import modules.recebimento.eventos.ConfirmarInformacoesModalEvent;
	import modules.recebimento.eventos.VerificarGarantiaModalEvent;
	import modules.recebimento.notaFiscal.ConfirmarInformacoesModal;
	import modules.recebimento.notaFiscal.VerificacaoGarantiaModal;
	import modules.view.Login;
	
	import mx.core.FlexGlobals;
	import mx.managers.PopUpManager;

	public class PopupModalController {
		
		private static var instance:PopupModalController = null;

		private var app:SoseFlex = FlexGlobals.topLevelApplication as SoseFlex;

		private var _modalAutenticacaoUsuario:UserRegisterNumberModal = null;
		private var _modalVerificarGarantia:VerificacaoGarantiaModal = null;
		private var _modalConfirmarInformacoes:ConfirmarInformacoesModal = null;
		
		public static function getInstance():PopupModalController {
			if (instance == null) instance = new PopupModalController();
			return instance;
		}	
		
		public function PopupModalController()
		{
			createModalAutenticacaoUsuario();
			createModalVerificarGarantia();
			createModalConfirmarInformacoes();
		}
		
		public function createModalAutenticacaoUsuario():void {
			if(_modalAutenticacaoUsuario == null) {
				_modalAutenticacaoUsuario = new UserRegisterNumberModal();
			}
		}
		
		public function createModalVerificarGarantia():void {
			if(_modalVerificarGarantia == null) {
				_modalVerificarGarantia = new VerificacaoGarantiaModal();
			}
		}
		
		public function createModalConfirmarInformacoes():void {
			if(_modalConfirmarInformacoes == null) {
				_modalConfirmarInformacoes = new ConfirmarInformacoesModal();
			}
		}
		
		public function showModalAutenticacaoUsuario(metodo:Function):void {
			_modalAutenticacaoUsuario.clear();
			_modalAutenticacaoUsuario.addEventListener(UserRegisterNumberModalEvent.USER_REGISTER_NUMBER_MODAL, metodo,false);
			PopUpManager.addPopUp(_modalAutenticacaoUsuario, app, true);
			PopUpManager.centerPopUp(_modalAutenticacaoUsuario);
			if(Login.usuarioAutenticado){
				_modalAutenticacaoUsuario.usuario.text = Login.usuarioAutenticado.usuario;
				_modalAutenticacaoUsuario.usuario.enabled = false;
				_modalAutenticacaoUsuario.senha.text = "";
				_modalAutenticacaoUsuario.senha.setFocus();
			}else{
				_modalAutenticacaoUsuario.usuario.text = "";
				_modalAutenticacaoUsuario.senha.text = "";
				_modalAutenticacaoUsuario.usuario.enabled = true;
				_modalAutenticacaoUsuario.usuario.setFocus();
			}

		}
		
		public function showModalVerificarGarantia(metodo:Function,osAntiga:OrdemServico,osCorrente:OrdemServico):void {
			_modalVerificarGarantia.addEventListener(VerificarGarantiaModalEvent.RETORNAR_ORDEM_SERVICO_MODAL, metodo,false);
			_modalVerificarGarantia.osAntiga = osAntiga;
			_modalVerificarGarantia.osCorrente = osCorrente;
			PopUpManager.addPopUp(_modalVerificarGarantia, app, true);
			PopUpManager.centerPopUp(_modalVerificarGarantia);
			
		}

		public function hideUserModal():void {
			PopUpManager.removePopUp(_modalAutenticacaoUsuario);
		}
		
		public function hideModalVerificarGarantia():void {
			PopUpManager.removePopUp(_modalVerificarGarantia);
		}
		
		public function showModalConfirmarInformacoes(metodo:Function,osCorrente:OrdemServico):void {
			_modalConfirmarInformacoes.addEventListener(ConfirmarInformacoesModalEvent.RETORNAR_ORDEM_SERVICO_CONFIRMAR_MODAL, metodo,false);
			_modalConfirmarInformacoes.osCorrente = osCorrente;
			PopUpManager.addPopUp(_modalConfirmarInformacoes, app, true);
			PopUpManager.centerPopUp(_modalConfirmarInformacoes);
			
		}
		
		public function hideModalConfirmarInformacoes():void {
			PopUpManager.removePopUp(_modalConfirmarInformacoes);
		}
		

	}
}
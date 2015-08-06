package br.com.sose.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import br.com.sose.entity.admistrativo.Perfil;
import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.componentes.Aviso;
import br.com.sose.service.administrativo.PerfilService;
import br.com.sose.service.administrativo.UsuarioService;
import br.com.sose.service.componentes.AvisoService;

public class ApplicationListener extends HttpServlet implements	ServletContextListener {


	private static final long serialVersionUID = 1L;

	private ApplicationContext	context;
	
	private UsuarioService usuarioService;
	
	private PerfilService perfilService;
	
	private AvisoService avisoService;
	
	private Logger logger = Logger.getLogger(this.getClass());

	public ApplicationListener() {
		logger.debug("Inicializando SOSE");
	}

	
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		logger.debug("TESTANDO LOG");		
		context = WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext());
		usuarioService = context.getBean(UsuarioService.class);
		perfilService = context.getBean(PerfilService.class);

		if(perfilService.buscarPorNome("Administrativo") == null){
			Perfil perfil = new Perfil();
			perfil.setAcaoAtribuirPrioridade(true);
			perfil.setAcaoAtribuirTecnico(true);
			
			perfil.setCadastroSistemaAtivo(true);
			
			perfil.setMenuAreaTecnica(true);
			perfil.setMenuAvaya(true);
			perfil.setMenuCadastro(true);
			perfil.setMenuConsulta(true);
			perfil.setMenuEstoque(true);
			perfil.setMenuExpedicao(true);
			perfil.setMenuExterno(true);
			perfil.setMenuFaturamento(true);
			perfil.setMenuProposta(true);
			perfil.setMenuRecebimento(true);
			
			perfil.setSubMenuAreaTecnicaLaudoTecnico(true);
			perfil.setSubMenuAreaTecnicaListagemGeral(true);
			perfil.setSubMenuAreaTecnicaOrcamentoDiferenciado(true);
			perfil.setSubMenuAreaTecnicaRequisicaoComponente(true);
			
			perfil.setSubMenuCadastroAtividade(true);
			perfil.setSubMenuCadastroComponente(true);
			perfil.setSubMenuCadastroDefeito(true);
			perfil.setSubMenuCadastroEncapsulamento(true);
			perfil.setSubMenuCadastroEquipamento(true);
			perfil.setSubMenuCadastroFabricante(true);
			perfil.setSubMenuCadastroLaboratorio(true);
			perfil.setSubMenuCadastroPerfil(true);
			perfil.setSubMenuCadastroPessoa(true);
			perfil.setSubMenuCadastroTipoComponente(true);
			perfil.setSubMenuCadastroUnidade(true);
			perfil.setSubMenuCadastroUsuario(true);
			
			perfil.setSubMenuConsultaConsultaGeral(true);
			
			perfil.setSubMenuEstoqueComponente(true);
			perfil.setSubMenuEstoqueRequisicaoComponente(true);
			
			perfil.setSubMenuExpedicaoBaixaOs(true);
			perfil.setSubMenuExpedicaoListagemNfSaida(true);
			
			perfil.setSubMenuPropostaBaixaOs(true);
			perfil.setSubMenuPropostaListagemProposta(true);
			
			perfil.setSubMenuRecebimentoEtiqueta(true);
			perfil.setSubMenuRecebimentoLpu(true);
			perfil.setSubMenuRecebimentouNotaFiscal(true);
			
			perfil.setNome("Administrativo");
			
			try {
				perfilService.salvarPerfil(perfil);
				logger.debug("Perfil 'Administrativo'inserted on the system");
			} catch (Exception e) {
				logger.debug("Error inserting perfil administrator.",e);
				e.printStackTrace(); logger.error(e);
			}
			
		}
		
		if (usuarioService.buscarPorLogin("admin") == null) {			
			Usuario usuario = new Usuario();
			Perfil perfil = perfilService.buscarPorNome("Administrativo");
			usuario.setPerfil(perfil);
			usuario.setId(null);
			usuario.setUsuario("admin");
			usuario.setSenha("1");
			usuario.setNome("Administrador do sistema");
			usuario.setBloqueado(false);
			usuario.setEndereco(null);
			try {
				usuarioService.salvarUsuario(usuario);
				logger.debug("Administrator inserted on the system");
			} catch (Exception e) {
				logger.debug("Error inserting administrator.",e);
				e.printStackTrace(); logger.error(e);
			}
		}
		
		if (usuarioService.buscarPorLogin("sistema") == null) {			
			Usuario usuario = new Usuario();
			Perfil perfil = perfilService.buscarPorNome("Sistema");
			usuario.setPerfil(perfil);
			usuario.setId(null);
			usuario.setUsuario("sistema");
			usuario.setSenha("sistema1234");
			usuario.setNome("Sistema");
			usuario.setBloqueado(false);
			usuario.setEndereco(null);
			try {
				usuarioService.salvarUsuario(usuario);
				logger.debug("Administrator inserted on the system");
			} catch (Exception e) {
				logger.debug("Error inserting administrator.",e);
				e.printStackTrace(); logger.error(e);
			}
		}
		
		for(int i = 1; i<=4; i++) {
			if(avisoService.buscarPorId(i) == null){
				Aviso aviso = new Aviso();
				aviso.setTitulo("Novo aviso");
				aviso.setMensagem("Nenhum aviso disponível");
				avisoService.salvarAviso(aviso);
			}
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		logger.debug("Finalizando SOSE");
	}


}

package br.com.sose.service.administrativo;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.daoImpl.administrativo.UsuarioDao;
import br.com.sose.daoImpl.administrativo.parceiros.EnderecoDao;
import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.admistrativo.parceiros.Endereco;
import br.com.sose.exceptions.UsuarioExistenteException;
import br.com.sose.exceptions.UsuarioNaoExclusaoDependenciaExistenteException;
import br.com.sose.utils.LoginUtils;
import br.com.sose.utils.PasswordUtil;

@Service(value="usuarioService")
@RemotingDestination(value="usuarioService")
public class UsuarioService {

	private Logger logger = Logger.getLogger(this.getClass());

	
	@Autowired
	public UsuarioDao usuarioDao;
	
	@Autowired
	public EnderecoDao enderecoDao;

	@RemotingInclude
	@Transactional(readOnly = true)
	public List<Usuario> listarUsuarios() throws Exception {
		List<Usuario> usuarios;
		try {
			usuarios =(List<Usuario>) usuarioDao.findAllOrderByUsuario();
//			for(Usuario u : usuarios){
//				u.setSenha(PasswordUtil.decryptPassword(u.getSenha()));
//			}
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return usuarios;
	}
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public Usuario buscarPorLogin(String login) {
		try {
			Usuario usr =  usuarioDao.buscarPorLogin(login);
			return usr;
		} catch (Exception e2) {
			logger.error(e2);
		}
		return null;
	}
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public Usuario logar(String usuario, String senha){
		Usuario usuarioLogado = null;
		try {
			usuarioLogado = usuarioDao.verificarLogin(usuario,PasswordUtil.encryptPassword2(senha.trim()));
			LoginUtils.setLoggedUser(usuarioLogado);
			LoginUtils.getLoggedUser();			
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
		}
		return usuarioLogado;

	}
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public Usuario autenticarUsuario(String usuario, String senha){
		Usuario usuarioLogado = null;

		try {
			usuarioLogado = usuarioDao.verificarLogin(usuario,PasswordUtil.encryptPassword2(senha.trim()));
			LoginUtils.setUsuarioAutenticado(usuarioLogado);
			LoginUtils.getUsuarioAutenticado();			
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
		}
		return usuarioLogado;

	}
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public Boolean invalidarUsuarioAutenticado(){
		try {
			LoginUtils.invalidarUsuarioAutenticado();		
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
		}
		return true;

	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Usuario salvarUsuario(Usuario usuario) throws Exception {
		Usuario usuarioSalvo;
		Endereco endereco;
		try {
			usuarioSalvo = buscarPorUsername(usuario.getUsuario());
			if(usuarioSalvo != null && !usuarioSalvo.getId().equals(usuario.getId())){
				throw new UsuarioExistenteException(usuario.getUsuario()); 
			}
			
			if(usuarioSalvo == null && usuario.getId() != null) {
				usuarioSalvo = buscarPorId(usuario.getId());
			}
			
			if(usuarioSalvo != null){
				if(!(usuarioSalvo.getSenha().equals(usuario.getSenha()))){
					usuario.setSenha(PasswordUtil.encryptPassword2(usuario.getSenha()));
				}
			}else {
				usuario.setSenha(PasswordUtil.encryptPassword2(usuario.getSenha()));
			}
			endereco = usuario.getEndereco();
			usuario.setEndereco(null);
			if(endereco != null && 
					((endereco.getLogradouro() != null && !endereco.getLogradouro().equals("")) 
					|| (endereco.getNumero() != null &&  !endereco.getNumero().equals(""))
					|| (endereco.getCidade() != null && !endereco.getCidade().equals(""))
					|| (endereco.getEstado() != null && !endereco.getEstado().equals(""))
					|| (endereco.getCep() != null && !endereco.getCep().equals("")))){
				if(endereco.getId() == null || endereco.getId() == 0 ){
					endereco = enderecoDao.save(endereco);
				}else{
					endereco = enderecoDao.update(endereco);
				}
				usuario.setEndereco(endereco);
			}
			
			if(usuarioSalvo == null ){
				usuarioSalvo =(Usuario) usuarioDao.save(usuario);
			}else{
				usuarioSalvo =(Usuario) usuarioDao.update(usuario);
			}
			
		} catch (UsuarioExistenteException e) {
			logger.error(e);;
			throw e;
		}catch (Exception e) {
			logger.error("Erro ao salvar usuário: " + usuario.getNome());;
			throw e;
		}
		return usuarioSalvo;
	}
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public Usuario buscarPorNome(String nome) throws Exception {
		try {
			return usuarioDao.buscarPorNome(nome);
		} catch (Exception e) {
			logger.info("Não há usuário cadastrado com o nome: "+nome+" no sistema");
		}
		return null;
	}
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public Usuario buscarPorUsername(String nome) throws Exception {
		try {
			return usuarioDao.buscarPorUsername(nome);
		} catch (Exception e) {
			logger.info("Não há usuário cadastrado com o username: "+nome+" no sistema");
		}
		return null;
	}
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public Usuario buscarPorId(Long id) throws Exception {
		try {
			return usuarioDao.buscarPorId(id);
		} catch (Exception e) {
			logger.info("Não há usuário cadastrado com o id: "+id+" no sistema");
		}
		return null;
	}
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public Usuario buscarCompletoPorId(Long id) throws Exception {
		try {
			return usuarioDao.buscarCompletoPorId(id);
		} catch (Exception e) {
			logger.info("Não há usuário cadastrado com o id: "+id+" no sistema");
		}
		return null;
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Usuario excluirUsuario(Usuario usuario) throws Exception {
		try {
			usuarioDao.remover(usuario);	
			logger.info("Usuário com o nome: "+usuario.getNome()+" foi removido do sistema");
		} catch (ConstraintViolationException e) {
			logger.error(e);
			throw new UsuarioNaoExclusaoDependenciaExistenteException(usuario.getNome());
		}catch (Exception e) {
			logger.error(e);
			throw e;
		}
		return usuario;
	}
	
	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Usuario sendPassword(String user) throws Exception
	{
		Date start = new Date();
		logger.info("User - sendPassword - Start");
				
		Usuario loggedUser = new Usuario();
		String newPassword = PasswordUtil.generatePassword();
		try {
			loggedUser = usuarioDao.buscarPorLogin(user);
			//EmailUtil.mailPassword(newPassword, loggedUser);
			loggedUser.setSenha(PasswordUtil.encryptPassword2(newPassword));
			salvarUsuario(loggedUser);
		} catch (Exception e) {
			logger.error("Error sendding email - ", e);
			throw new Exception("ERROR SENDING EMAIL.");
		} finally {
			Date finish = new Date();
			logger.info("User - sendPassword - Execution Time:" + (finish.getTime() - start.getTime()));
		}

		return loggedUser;
	}

}

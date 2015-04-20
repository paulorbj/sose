package br.com.sose.utils;

import br.com.sose.entity.admistrativo.Usuario;
import flex.messaging.FlexContext;
import flex.messaging.FlexSession;


public class LoginUtils {

	public static void setLoggedUser(Usuario user){		
		FlexSession session = FlexContext.getFlexSession();
		session.setAttribute("loggedUser", user);
	}
		
	public static Usuario getLoggedUser(){
		FlexSession session = FlexContext.getFlexSession();
		Usuario loggedUser = null;
		if (session != null) {
			loggedUser = (Usuario)session.getAttribute("loggedUser");
		}
		return loggedUser;
	}
		
	public static void removeLoggedUser(){
		FlexSession session = FlexContext.getFlexSession();
		session.setAttribute("loggedUser", null);		
	}
	
	public static void setUsuarioAutenticado(Usuario user){		
		FlexSession session = FlexContext.getFlexSession();
		session.setAttribute("usuarioAutenticado", user);
	}
	
	public static Usuario getUsuarioAutenticado(){
		FlexSession session = FlexContext.getFlexSession();
		Usuario loggedUser = null;
		if (session != null) {
			loggedUser = (Usuario)session.getAttribute("usuarioAutenticado");
		}
		return loggedUser;
	}
	
	public static void invalidarUsuarioAutenticado(){
		FlexSession session = FlexContext.getFlexSession();
		session.setAttribute("usuarioAutenticado", null);		
	}
	
}

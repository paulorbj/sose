package br.com.sose.utils;


import java.io.File;
import java.io.FilenameFilter;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.sose.mensagens.RecebimentoMessageService;
import flex.messaging.FlexSession;
import flex.messaging.FlexSessionListener;

public class SoseSessionFlex implements FlexSessionListener {
	private static int count = 0;

	protected FlexSession session;

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	RecebimentoMessageService recebimentoMessageService;

	public SoseSessionFlex() {
		logger.debug("FlexClient");
		FlexSession.addSessionCreatedListener(this);
	}

	@Override
	public void sessionCreated(FlexSession session) {
		this.session = session;
		session.addSessionDestroyedListener(this);

		System.out.println("################# Sessao criada #################  " + new Date() + "  numero de sessao: " + ++count);
		logger.debug("################# Sessao criada #################  " + new Date() + "  numero de sesso: " + count);
	}

	@Override
	public void sessionDestroyed(FlexSession session) {

		//		for(FlexClient fc : session.getFlexClients()){
		//			recebimentoMessageService.sendLogoutMessage(fc.getId());
		//		}

		if (session != null && session.isValid()) {
			session.invalidate();	
		}

		System.out.println("################# Sessao destruida #################  " + new Date() + "  numero de sessao " + --count);
		logger.debug("################# Sessao destruida #################  " + new Date() + "  numero de sessao " + count);

		if(count == 0){
			File dir = new File("C:\\arquivos");
			try{
				if(dir != null)
					for (File f : dir.listFiles()){
						f.delete();
					}
			}catch(Exception e){
				e.printStackTrace();
			}

			File dirTemp = new File(PropertiesUtil.getProperty("upload.temporario.location"));
			try{
				for (File f : dirTemp.listFiles()){
					f.delete();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}

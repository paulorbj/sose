package br.com.sose.utils;


import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.sose.mensagens.RecebimentoMessageService;
import flex.messaging.FlexSession;
import flex.messaging.FlexSessionListener;
import flex.messaging.client.FlexClient;

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

		System.out.println("################# Sessao criada #################  " + new Date() + "  Nº" + ++count);
		logger.debug("################# Sessao criada #################  " + new Date() + "  Nº" + ++count);
	}
	
	@Override
	public void sessionDestroyed(FlexSession session) {
		
//		for(FlexClient fc : session.getFlexClients()){
//			recebimentoMessageService.sendLogoutMessage(fc.getId());
//		}
		
		if (session != null && session.isValid()) {
			session.invalidate();	
		}

		System.out.println("################# Sessao destruida #################  " + new Date() + "  Nº " + --count);
		logger.debug("################# Sessao destruida #################  " + new Date() + "  Nº " + --count);
	}
}

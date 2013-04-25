package br.com.sose.mensagens;

import org.apache.log4j.Logger;

import flex.messaging.messages.AsyncMessage;
import flex.messaging.messages.Message;
import flex.messaging.services.MessageService;
import flex.messaging.services.ServiceAdapter;

public class LaudoTecnicoMessageService extends ServiceAdapter {

	// Get Spring context
	// ClassPathXmlApplicationContext ctx = new
	// ClassPathXmlApplicationContext("localApplicationContext.xml");
	private Logger logger = Logger.getLogger(this.getClass());
	
	public void start() {

		System.out.println("Inicializando laudo técnico adapter");
		logger.debug("Inicializando laudo técnico adapter");

	}

	public void stop() {

		System.out.println("Finalizando laudo técnico adapter");
		logger.debug("Finalizando laudo técnico adapter");
	}

	public Object invoke(Message msg) {

		System.out.println("Enviando mensagem laudo técnico");
			logger.debug("Enviando mensagem laudo técnico");
			AsyncMessage newMessage = (AsyncMessage) msg;
			// recuperando servico de mensagens
			MessageService msgService = (MessageService) getDestination()
					.getService();
			// setando parametros no body, este parametro pode ser recuparado no
			// cliente flex
			newMessage.setBody(msg.getBody());
			// enviando mensagem aos clientes que estao subscribed
			msgService.pushMessageToClients(newMessage, true);
		
		return null;
	}
	
}

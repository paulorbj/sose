package br.com.sose.mensagens;

import org.apache.log4j.Logger;

import flex.messaging.messages.AsyncMessage;
import flex.messaging.messages.Message;
import flex.messaging.services.MessageService;
import flex.messaging.services.ServiceAdapter;

public class OrcamentoDiferenciadoMessageService extends ServiceAdapter {

	// Get Spring context
	// ClassPathXmlApplicationContext ctx = new
	// ClassPathXmlApplicationContext("localApplicationContext.xml");
	private Logger logger = Logger.getLogger(this.getClass());
	
	public void start() {

		System.out.println("Inicializando orçamento diferenciado adapter");
		logger.debug("Inicializando orçamento diferenciado adapter");

	}

	public void stop() {

		System.out.println("Finalizando orçamento diferenciado adapter");
		logger.debug("Finalizado orçamento diferenciado adapter");
	}

	public Object invoke(Message msg) {

		System.out.println("Enviando mensagem orçamento diferenciado");
			logger.debug("Enviando mensagem orçamento diferenciado");
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

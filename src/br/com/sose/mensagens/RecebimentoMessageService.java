package br.com.sose.mensagens;

import org.apache.log4j.Logger;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Service;

import br.com.sose.entity.recebimento.OrdemServico;
import br.com.sose.utils.GlobalMessage;

import flex.messaging.MessageBroker;
import flex.messaging.messages.AsyncMessage;
import flex.messaging.messages.Message;
import flex.messaging.services.MessageService;
import flex.messaging.services.ServiceAdapter;
import flex.messaging.util.UUIDUtils;

@Service(value="recebimentoMessageService")
@RemotingDestination(value="recebimentoMessageService")
public class RecebimentoMessageService extends ServiceAdapter {

	// Get Spring context
	// ClassPathXmlApplicationContext ctx = new
	// ClassPathXmlApplicationContext("localApplicationContext.xml");
	private Logger logger = Logger.getLogger(this.getClass());
	
	private volatile boolean running;
	
	public void start() {

		System.out.println("Inicializando recebimento adapter");
		logger.debug("Inicializando recebimento adapter");
		
//		Thread messageSender = new Thread(){
//            public void run(){
//                running = true;
//                while(running){
//            		System.out.println("############# Rodando #############");
//
//                    sendMessageToClients(createTestMessage());
//                    secondsToSleep(3);
//                }
//            }
//        };
//
//        messageSender.start();  

	}

	public Message sendLogoutMessage(String sessionId) {
		GlobalMessage messageGlobal =new GlobalMessage();
		messageGlobal.setMensagem("logout");
		messageGlobal.setConteudo(sessionId);
        final AsyncMessage msg = new AsyncMessage();
        msg.setDestination("RecebimentoMessageServicePush");
        msg.setClientId(UUIDUtils.createUUID());
        msg.setMessageId(UUIDUtils.createUUID());
        msg.setBody(messageGlobal);

        return msg;
    }
	
	private Message createTestMessage() {
		GlobalMessage messageGlobal =new GlobalMessage();
		messageGlobal.setMensagem("TESTE");
		messageGlobal.setConteudo(new OrdemServico());
        final AsyncMessage msg = new AsyncMessage();
        msg.setDestination("RecebimentoMessageServicePush");
        msg.setClientId(UUIDUtils.createUUID());
        msg.setMessageId(UUIDUtils.createUUID());
        msg.setBody(messageGlobal);

        return msg;
    }
	
	public void enviarMensagemRecebimento(String msg, Object obj)   { 	
		GlobalMessage messageGlobal =new GlobalMessage();
		messageGlobal.setMensagem(msg);
		messageGlobal.setConteudo(new OrdemServico());
        final AsyncMessage message = new AsyncMessage();
        message.setDestination("RecebimentoMessageServicePush");
        message.setClientId(UUIDUtils.createUUID());
        message.setMessageId(UUIDUtils.createUUID());
        message.setBody(messageGlobal);

		//send message to destination
		sendMessageToClients(message);
	}

	private void secondsToSleep(int seconds) {
        try{
            Thread.sleep(seconds * 1000);
        }catch(InterruptedException e){
            System.out.println("TestServiceAdapter Interrupted while sending messages");
            e.printStackTrace(); logger.error(e);
        }
    }     
	
    private void sendMessageToClients(Message msg) {
        ((MessageService) getDestination().getService()).pushMessageToClients(msg, false);
    }
    
	public void stop() {

		System.out.println("Finalizando recebimento adapter");
		logger.debug("Inicializando recebimento adapter");
	}

	public Object invoke(Message msg) {

		System.out.println("Enviando mensagem recebimento");
			logger.debug("Enviando mensagem recebimento");
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

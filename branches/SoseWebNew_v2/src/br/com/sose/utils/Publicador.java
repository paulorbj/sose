package br.com.sose.utils;

import java.util.Date;

import flex.messaging.MessageBroker;
import flex.messaging.messages.AsyncMessage;
import flex.messaging.messages.Message;
import flex.messaging.services.MessageService;
import flex.messaging.services.ServiceAdapter;


public class Publicador  {

	private static MessageBroker producerEstoque;
	private static MessageBroker produtorRecebimento;
	private static MessageBroker produtorCadastro;
	private static MessageBroker produtorAreaTecnica;
	private static MessageBroker produtorExpedicao;
	private static MessageBroker produtorProposta;
	private static MessageBroker produtorOrcamento;
	private static MessageBroker produtorReparo;
	private static MessageBroker produtorOrcamentoDiferenciado;
	private static MessageBroker produtorLaudoTecnico;
	private static MessageBroker produtorFaturamento;

	public static void enviarMensagemRecebimento(String msg, Object obj)   { 	

		AsyncMessage message = new AsyncMessage();
		GlobalMessage messageGlobal =new GlobalMessage();
		messageGlobal.setMensagem(msg);
		messageGlobal.setConteudo(obj);
		message.setBody(message);	
		message.setDestination("RecebimentoMessageServicePush");		
		message.setHeader("sender", "From the server");

		//send message to destination
		MessageBroker.getMessageBroker(null).routeMessageToService(message, null);
	}

//	public static void enviarMensagemCadastro(String msg, Object obj)   { 								
//		var message:AsyncMessage = new AsyncMessage();
//	var messageGlobal:GlobalMessage=new GlobalMessage();
//	messageGlobal.mensagem=msg;
//	messageGlobal.conteudo = obj;				
//	message.body=messageGlobal;
//	produtorCadastro.send(message);	
//	}
//
//	public static void enviarMensagemEstoque(String msg, Object obj)   { 								
//		var message:AsyncMessage = new AsyncMessage();
//	var messageGlobal:GlobalMessage=new GlobalMessage();
//	messageGlobal.mensagem=msg;
//	messageGlobal.conteudo = obj;				
//	message.body=messageGlobal;
//	producerEstoque.send(message);	
//	}
//
//	public static void enviarMensagemAreaTecnica(String msg, Object obj)   { 								
//		var message:AsyncMessage = new AsyncMessage();
//	var messageGlobal:GlobalMessage=new GlobalMessage();
//	messageGlobal.mensagem=msg;
//	messageGlobal.conteudo = obj;				
//	message.body=messageGlobal;
//	produtorAreaTecnica.send(message);	
//	}
//
//	public static void enviarMensagemExpedicao(String msg, Object obj)   { 								
//		var message:AsyncMessage = new AsyncMessage();
//	var messageGlobal:GlobalMessage=new GlobalMessage();
//	messageGlobal.mensagem=msg;
//	messageGlobal.conteudo = obj;				
//	message.body=messageGlobal;
//	produtorExpedicao.send(message);	
//	}
//
//	public static void enviarMensagemProposta(String msg, Object obj)   { 								
//		var message:AsyncMessage = new AsyncMessage();
//	var messageGlobal:GlobalMessage=new GlobalMessage();
//	messageGlobal.mensagem=msg;
//	messageGlobal.conteudo = obj;				
//	message.body=messageGlobal;
//	produtorProposta.send(message);	
//	}
//
//	public void publicarProposta(msg:String,os:OrdemServico,proposta:Proposta)   { 								
//		var message:AsyncMessage = new AsyncMessage();
//	var messageGlobal:GlobalMessage=new GlobalMessage();
//	messageGlobal.mensagem=msg;
//	if(os != null){
//		messageGlobal.conteudo = os;				
//	}else{
//		messageGlobal.conteudo = proposta;				
//	}
//	message.body=messageGlobal;
//	produtorProposta.send(message);	
//	}
//
//	public static void enviarMensagemOrcamento(String msg, Object obj)   { 								
//		var message:AsyncMessage = new AsyncMessage();
//	var messageGlobal:GlobalMessage=new GlobalMessage();
//	messageGlobal.mensagem=msg;
//	messageGlobal.conteudo = obj;				
//	message.body=messageGlobal;
//	produtorOrcamento.send(message);	
//	}
//
//	public static void enviarMensagemFaturamento(String msg, Object obj)   { 								
//		var message:AsyncMessage = new AsyncMessage();
//	var messageGlobal:GlobalMessage=new GlobalMessage();
//	messageGlobal.mensagem=msg;
//	messageGlobal.conteudo = obj;				
//	message.body=messageGlobal;
//	produtorFaturamento.send(message);	
//	}
//
//	public static void enviarMensagemReparo(String msg, Object obj)   { 								
//		var message:AsyncMessage = new AsyncMessage();
//	var messageGlobal:GlobalMessage=new GlobalMessage();
//	messageGlobal.mensagem=msg;
//	messageGlobal.conteudo = obj;				
//	message.body=messageGlobal;
//	produtorReparo.send(message);	
//	}
//
//	public static void enviarMensagemOrcamentoDiferenciado(String msg, Object obj)   { 								
//		var message:AsyncMessage = new AsyncMessage();
//	var messageGlobal:GlobalMessage=new GlobalMessage();
//	messageGlobal.mensagem=msg;
//	messageGlobal.conteudo = obj;				
//	message.body=messageGlobal;
//	produtorOrcamentoDiferenciado.send(message);	
//	}
//
//	public static void enviarMensagemLaudoTecnico(String msg, Object obj)   { 								
//		var message:AsyncMessage = new AsyncMessage();
//	var messageGlobal:GlobalMessage=new GlobalMessage();
//	messageGlobal.mensagem=msg;
//	messageGlobal.conteudo = obj;				
//	message.body=messageGlobal;
//	produtorLaudoTecnico.send(message);	
//	}
//
//	@Override
//	public Object invoke(Message message) {
//		if(message.getBody().equals("New")) { 
//			return randomNumbers;
//		}else{
//			AsyncMessage newMessage = (AsyncMessage) message;
//			newMessage.setBody(randomNumbers);
//			MessageService msgService = (MessageService)getDestination().getService(); 
//			msgService.pushMessageToClients(newMessage,	false);
//		}
//		return	null;
//	}
}

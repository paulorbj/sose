package utils
{
	import entities.GlobalMessage;
	import entities.proposta.Proposta;
	import entities.recebimento.OrdemServico;
	
	import mx.messaging.Producer;
	import mx.messaging.messages.AsyncMessage;

	public class Publicador
	{
		
		private static var producerEstoque:Producer;
		private static var produtorRecebimento:Producer;
		private static var produtorCadastro:Producer;
		private static var produtorAreaTecnica:Producer;
		private static var produtorExpedicao:Producer;
		private static var produtorProposta:Producer;
		private static var produtorOrcamento:Producer;
		private static var produtorReparo:Producer;
		private static var produtorOrcamentoDiferenciado:Producer;
		private static var produtorLaudoTecnico:Producer;
		private static var produtorFaturamento:Producer;
		
		// Create the Producer.
		public static function configurarProdutores():void {
			produtorRecebimento = new Producer();
			produtorCadastro = new Producer();
			producerEstoque = new Producer();
			produtorAreaTecnica = new Producer();
			produtorExpedicao = new Producer();
			produtorProposta = new Producer();
			produtorOrcamento = new Producer();
			produtorReparo = new Producer();
			produtorOrcamentoDiferenciado = new Producer();
			produtorLaudoTecnico = new Producer();
			produtorFaturamento = new Producer();
			
			produtorRecebimento.destination = "RecebimentoMessageServicePush";
			produtorCadastro.destination = "CadastroMessageServicePush";
			producerEstoque.destination = "EstoqueMessageServicePush";
			produtorAreaTecnica.destination = "AreaTecnicaMessageServicePush";
			produtorExpedicao.destination = "ExpedicaoMessageServicePush";
			produtorProposta.destination = "PropostaMessageServicePush";
			produtorOrcamento.destination = "OrcamentoMessageServicePush";
			produtorReparo.destination = "ReparoMessageServicePush";
			produtorOrcamentoDiferenciado.destination = "OrcamentoDiferenciadoMessageServicePush";
			produtorLaudoTecnico.destination = "LaudoTecnicoMessageServicePush";
			produtorFaturamento.destination = "FaturamentoMessageServicePush";
		}
		
		public static function enviarMensagemRecebimento(msg:String, obj:Object):void   { 								
			var message:AsyncMessage = new AsyncMessage();
			var messageGlobal:GlobalMessage=new GlobalMessage();
			messageGlobal.mensagem=msg;
			messageGlobal.conteudo = obj;				
			message.body=messageGlobal;
			produtorRecebimento.send(message);	
		}
		
		public static function enviarMensagemCadastro(msg:String, obj:Object):void   { 								
			var message:AsyncMessage = new AsyncMessage();
			var messageGlobal:GlobalMessage=new GlobalMessage();
			messageGlobal.mensagem=msg;
			messageGlobal.conteudo = obj;				
			message.body=messageGlobal;
			produtorCadastro.send(message);	
		}
		
		public static function enviarMensagemEstoque(msg:String, obj:Object):void   { 								
			var message:AsyncMessage = new AsyncMessage();
			var messageGlobal:GlobalMessage=new GlobalMessage();
			messageGlobal.mensagem=msg;
			messageGlobal.conteudo = obj;				
			message.body=messageGlobal;
			producerEstoque.send(message);	
		}
		
		public static function enviarMensagemAreaTecnica(msg:String, obj:Object):void   { 								
			var message:AsyncMessage = new AsyncMessage();
			var messageGlobal:GlobalMessage=new GlobalMessage();
			messageGlobal.mensagem=msg;
			messageGlobal.conteudo = obj;				
			message.body=messageGlobal;
			produtorAreaTecnica.send(message);	
		}
		
		public static function enviarMensagemExpedicao(msg:String, obj:Object):void   { 								
			var message:AsyncMessage = new AsyncMessage();
			var messageGlobal:GlobalMessage=new GlobalMessage();
			messageGlobal.mensagem=msg;
			messageGlobal.conteudo = obj;				
			message.body=messageGlobal;
			produtorExpedicao.send(message);	
		}
		
		public static function enviarMensagemProposta(msg:String, obj:Object):void   { 								
			var message:AsyncMessage = new AsyncMessage();
			var messageGlobal:GlobalMessage=new GlobalMessage();
			messageGlobal.mensagem=msg;
			messageGlobal.conteudo = obj;				
			message.body=messageGlobal;
			produtorProposta.send(message);	
		}
		
		public static function enviarMensagemCompra(msg:String, obj:Object):void   { 								

		}
		
		public function publicarProposta(msg:String,os:OrdemServico,proposta:Proposta):void   { 								
			var message:AsyncMessage = new AsyncMessage();
			var messageGlobal:GlobalMessage=new GlobalMessage();
			messageGlobal.mensagem=msg;
			if(os != null){
				messageGlobal.conteudo = os;				
			}else{
				messageGlobal.conteudo = proposta;				
			}
			message.body=messageGlobal;
			produtorProposta.send(message);	
		}
		
		public static function enviarMensagemOrcamento(msg:String, obj:Object):void   { 								
			var message:AsyncMessage = new AsyncMessage();
			var messageGlobal:GlobalMessage=new GlobalMessage();
			messageGlobal.mensagem=msg;
			messageGlobal.conteudo = obj;				
			message.body=messageGlobal;
			produtorOrcamento.send(message);	
		}
		
		public static function enviarMensagemFaturamento(msg:String, obj:Object):void   { 								
			var message:AsyncMessage = new AsyncMessage();
			var messageGlobal:GlobalMessage=new GlobalMessage();
			messageGlobal.mensagem=msg;
			messageGlobal.conteudo = obj;				
			message.body=messageGlobal;
			produtorFaturamento.send(message);	
		}
		
		public static function enviarMensagemReparo(msg:String, obj:Object):void   { 								
			var message:AsyncMessage = new AsyncMessage();
			var messageGlobal:GlobalMessage=new GlobalMessage();
			messageGlobal.mensagem=msg;
			messageGlobal.conteudo = obj;				
			message.body=messageGlobal;
			produtorReparo.send(message);	
		}
		
		public static function enviarMensagemOrcamentoDiferenciado(msg:String, obj:Object):void   { 								
			var message:AsyncMessage = new AsyncMessage();
			var messageGlobal:GlobalMessage=new GlobalMessage();
			messageGlobal.mensagem=msg;
			messageGlobal.conteudo = obj;				
			message.body=messageGlobal;
			produtorOrcamentoDiferenciado.send(message);	
		}
		
		public static function enviarMensagemLaudoTecnico(msg:String, obj:Object):void   { 								
			var message:AsyncMessage = new AsyncMessage();
			var messageGlobal:GlobalMessage=new GlobalMessage();
			messageGlobal.mensagem=msg;
			messageGlobal.conteudo = obj;				
			message.body=messageGlobal;
			produtorLaudoTecnico.send(message);	
		}
		
		public function Publicador()
		{
		}
	}
}
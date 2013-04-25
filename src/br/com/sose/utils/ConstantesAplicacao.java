package br.com.sose.utils;

public class ConstantesAplicacao {

	public static final String REPARO_COM_CONDICAO_DE_REPARO = "Com condição de reparo";
	public static final String REPARO_SEM_CONDICAO_DE_REPARO = "Sem condição de reparo";
	public static final String REPARO_DEVOLUCAO_SEM_REPARO = "Devolução sem reparo";
	
	public static final TipoPessoa TIPO_PESSOA_CLIENTE = new TipoPessoa(1,"Cliente");
	public static final TipoPessoa TIPO_PESSOA_FORNECEDOR = new TipoPessoa(2,"Fornecedor");
	public static final TipoPessoa TIPO_PESSOA_PRESTADOR_SERVICO = new TipoPessoa(4,"Prestador Serviço");
	public static final TipoPessoa TIPO_PESSOA_TRANSPORTADORA = new TipoPessoa(3,"Transportadora");
	
	public static final TipoNotaFiscal TIPO_NOTA_FISCAL_NORMAL = new TipoNotaFiscal(1,"Normal");
	public static final TipoNotaFiscal TIPO_NOTA_FISCAL_AVAYA_ESTOQUE = new TipoNotaFiscal(2,"Estoque");
	public static final TipoNotaFiscal TIPO_NOTA_FISCAL_AVAYA_SUBSTITUICAO = new TipoNotaFiscal(3,"Substituição");
	public static final TipoNotaFiscal TIPO_NOTA_FISCAL_AVAYA_PADRAO = new TipoNotaFiscal(4,"Padrão");
	public static final TipoNotaFiscal TIPO_NOTA_FISCAL_AVAYA_AVAYA = new TipoNotaFiscal(5,"Avaya");

	public static final String TIPO_PROPOSTA_ORCAMENTO = "Orçamento";
	public static final String TIPO_PROPOSTA_ORCAMENTO_DIFERENCIADO = "Orçamento diferenciado";
}

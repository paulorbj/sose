package br.com.sose.relatorio.componente;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import br.com.sose.entity.admistrativo.Componente;
import br.com.sose.entity.compra.Cotacao;

public class RelatorioCompraVO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Componente componente;

	private Boolean possuiAmostra;

	private Date data;

	private Integer qtdTotalRequisitada;

	private Integer qtdEsperada;

	private Integer qtdPedido;

	private String tecnicos;
	
	private String origem;
	
	private String imagem;
	
	private List<Cotacao> cotacoes;
	
	public Componente getComponente() {
		return componente;
	}

	public void setComponente(Componente componente) {
		this.componente = componente;
	}

	public Boolean getPossuiAmostra() {
		return possuiAmostra;
	}

	public void setPossuiAmostra(Boolean possuiAmostra) {
		this.possuiAmostra = possuiAmostra;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Integer getQtdTotalRequisitada() {
		return qtdTotalRequisitada;
	}

	public void setQtdTotalRequisitada(Integer qtdTotalRequisitada) {
		this.qtdTotalRequisitada = qtdTotalRequisitada;
	}

	public Integer getQtdEsperada() {
		return qtdEsperada;
	}

	public void setQtdEsperada(Integer qtdEsperada) {
		this.qtdEsperada = qtdEsperada;
	}

	public Integer getQtdPedido() {
		return qtdPedido;
	}

	public void setQtdPedido(Integer qtdPedido) {
		this.qtdPedido = qtdPedido;
	}

	public String getTecnicos() {
		return tecnicos;
	}

	public void setTecnicos(String tecnicos) {
		this.tecnicos = tecnicos;
	}

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public List<Cotacao> getCotacoes() {
		return cotacoes;
	}

	public void setCotacoes(List<Cotacao> cotacoes) {
		this.cotacoes = cotacoes;
	}

}

package br.com.sose.entity.grafico;

import java.io.Serializable;

public class RetornoGraficoPizza implements Serializable {

	private static final long serialVersionUID = 1L;

	private String categoria;
	
	private Integer valor;
	
	public RetornoGraficoPizza(String categoria, Integer valor) {
		super();
		this.categoria = categoria;
		this.valor = valor;
	}
	
	public RetornoGraficoPizza() {
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Integer getValor() {
		return valor;
	}

	public void setValor(Integer valor) {
		this.valor = valor;
	}
}

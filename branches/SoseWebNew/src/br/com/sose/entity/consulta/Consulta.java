package br.com.sose.entity.consulta;

import java.util.Date;

import br.com.sose.entity.admistrativo.parceiros.Pessoa;

public class Consulta {

	private String numeroOS;

	private String numeroNotaFiscal;

	private String numeroNotaFiscalSaida;

	private Pessoa cliente;
	
	private Date dataDe;
	
	private Date dataAte;

	public String getNumeroOS() {
		return numeroOS;
	}

	public void setNumeroOS(String numeroOS) {
		this.numeroOS = numeroOS;
	}

	public String getNumeroNotaFiscal() {
		return numeroNotaFiscal;
	}

	public void setNumeroNotaFiscal(String numeroNotaFiscal) {
		this.numeroNotaFiscal = numeroNotaFiscal;
	}

	public String getNumeroNotaFiscalSaida() {
		return numeroNotaFiscalSaida;
	}

	public void setNumeroNotaFiscalSaida(String numeroNotaFiscalSaida) {
		this.numeroNotaFiscalSaida = numeroNotaFiscalSaida;
	}

	public Pessoa getCliente() {
		return cliente;
	}

	public void setCliente(Pessoa cliente) {
		this.cliente = cliente;
	}

	public Date getDataDe() {
		return dataDe;
	}

	public void setDataDe(Date dataDe) {
		this.dataDe = dataDe;
	}

	public Date getDataAte() {
		return dataAte;
	}

	public void setDataAte(Date dataAte) {
		this.dataAte = dataAte;
	}

}

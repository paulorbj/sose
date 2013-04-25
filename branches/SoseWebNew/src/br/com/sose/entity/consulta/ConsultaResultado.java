package br.com.sose.entity.consulta;

import java.util.List;

import org.hibernate.Hibernate;

import br.com.sose.entity.expedicao.NotaFiscalRemessa;
import br.com.sose.entity.recebimento.NotaFiscal;
import br.com.sose.entity.recebimento.OrdemServico;
import br.com.sose.to.ConsultaTO;

public class ConsultaResultado {

	private List<ConsultaTO> listaOrdemServico;
	
	private List<NotaFiscal> listaNotaFiscal;
	
	private List<NotaFiscalRemessa> listaNotaFiscalSaida;

	public List<ConsultaTO> getListaOrdemServico() {
		if(!Hibernate.isInitialized(listaOrdemServico)){
			listaOrdemServico = null;
		}
		return listaOrdemServico;
	}

	public void setListaOrdemServico(List<ConsultaTO> listaOrdemServico) {
		this.listaOrdemServico = listaOrdemServico;
	}

	public List<NotaFiscal> getListaNotaFiscal() {
		if(!Hibernate.isInitialized(listaNotaFiscal)){
			listaNotaFiscal = null;
		}
		return listaNotaFiscal;
	}

	public void setListaNotaFiscal(List<NotaFiscal> listaNotaFiscal) {
		this.listaNotaFiscal = listaNotaFiscal;
	}

	public List<NotaFiscalRemessa> getListaNotaFiscalSaida() {
		if(!Hibernate.isInitialized(listaNotaFiscalSaida)){
			listaNotaFiscalSaida = null;
		}
		return listaNotaFiscalSaida;
	}

	public void setListaNotaFiscalSaida(List<NotaFiscalRemessa> listaNotaFiscalSaida) {
		this.listaNotaFiscalSaida = listaNotaFiscalSaida;
	}
	
	
}

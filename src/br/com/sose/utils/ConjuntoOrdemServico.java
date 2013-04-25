package br.com.sose.utils;

import java.util.HashSet;
import java.util.Set;

import br.com.sose.entity.recebimento.OrdemServico;

public class ConjuntoOrdemServico {

	private OrdemServico osPai;
	
	private Set<OrdemServico> listaFilhas;
	

	public OrdemServico getOsPai() {
		return osPai;
	}

	public void setOsPai(OrdemServico osPai) {
		this.osPai = osPai;
	}

	public Set<OrdemServico> getListaFilhas() {
		if(listaFilhas == null){
			listaFilhas = new HashSet<OrdemServico>();
		}
		return listaFilhas;
	}

	public void setListaFilhas(Set<OrdemServico> listaFilhas) {
		this.listaFilhas = listaFilhas;
	}
	
	
}

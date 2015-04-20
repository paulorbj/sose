package br.com.sose.entity.recebimento;

import java.util.Comparator;

public class OrdemServicoComparator implements Comparator<OrdemServico> {

	public int compare(OrdemServico os, OrdemServico outraOs) {
		if(Long.parseLong(os.getNumeroOrdemServico()) == Long.parseLong(outraOs.getNumeroOrdemServico())){
			return 0;
		}else if(Long.parseLong(os.getNumeroOrdemServico()) < Long.parseLong(outraOs.getNumeroOrdemServico())){
			return -1;
		}else{
			return 1;
		}
	}

}

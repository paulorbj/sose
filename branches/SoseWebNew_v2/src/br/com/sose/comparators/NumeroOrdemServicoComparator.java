package br.com.sose.comparators;

import java.util.Comparator;

import br.com.sose.entity.recebimento.OrdemServico;

public class NumeroOrdemServicoComparator implements Comparator<OrdemServico> {
	public int compare(OrdemServico os, OrdemServico outraOs) {
        return new Long(os.getNumeroOrdemServico()).compareTo(new Long(outraOs.getNumeroOrdemServico()));
    }
}

package br.com.sose.comparators;

import java.util.Comparator;

import br.com.sose.entity.recebimento.OrdemServico;
import br.com.sose.to.OrcRepGenericoTO;

public class OrcRepGenericoTOComparator implements Comparator<OrcRepGenericoTO> {
	public int compare(OrcRepGenericoTO os, OrcRepGenericoTO outraOs) {
        return new Long(os.getNumeroOrdemServico()).compareTo(new Long(outraOs.getNumeroOrdemServico()));
    }
}

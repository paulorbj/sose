package br.com.sose.comparators;

import java.util.Comparator;

import br.com.sose.entity.laudoTecnico.LaudoTecnico;
import br.com.sose.to.OrcRepGenericoTO;

public class LaudoTecnicoComparator implements Comparator<LaudoTecnico> {
	public int compare(LaudoTecnico os, LaudoTecnico outraOs) {
        return new Long(os.getNumeroOrdemServico()).compareTo(new Long(outraOs.getNumeroOrdemServico()));
    }
}

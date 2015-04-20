package br.com.sose.relatorio.laudoTecnico;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.sose.entity.laudoTecnico.LaudoTecnico;

@Service(value="laudoTecnicoDataSourceTeste")
public class LaudoTecnicoDataSourceTeste {
	
	public static List<LaudoTecnico> createBeanCollection(){
		List<LaudoTecnico> listaLaudoTecnico = new ArrayList<LaudoTecnico>();
		LaudoTecnico lt1 = new LaudoTecnico();
		lt1.setControle("0001");
		lt1.setDataCriacao(new Date());
		lt1.setDataFim(new Date());
		lt1.setDataInicio(new Date());
		lt1.setDescricao("Essa é uma descrição Essa é uma descrição Essa é uma descrição Essa é uma descrição Essa é uma descrição " +
				"Essa é uma descrição Essa é uma descrição Essa é uma descrição Essa é uma descrição Essa é uma descrição " +
				"Essa é uma descrição Essa é uma descrição Essa é uma descrição Essa é uma descrição Essa é uma descrição " +
				"Essa é uma descrição Essa é uma descrição Essa é uma descrição Essa é uma descrição Essa é uma descrição " +
				"Essa é uma descrição Essa é uma descrição Essa é uma descrição Essa é uma descrição Essa é uma descrição " +
				"Essa é uma descrição Essa é uma descrição Essa é uma descrição Essa é uma descrição Essa é uma descrição " +
				"Essa é uma descrição Essa é uma descrição Essa é uma descrição Essa é uma descrição Essa é uma descrição");
		lt1.setInformacaoTecnica("Essa é uma Informacao tecnico Essa é uma Informacao tecnico Essa é uma Informacao tecnico " +
				"Essa é uma Informacao tecnicoEssa é uma Informacao tecnicoEssa é uma Informacao tecnicoEssa é uma Informacao tecnico" +
				"Essa é uma Informacao tecnicoEssa é uma Informacao tecnicoEssa é uma Informacao tecnicoEssa é uma Informacao tecnico" +
				"Essa é uma Informacao tecnicoEssa é uma Informacao tecnicoEssa é uma Informacao tecnicoEssa é uma Informacao tecnico" +
				"Essa é uma Informacao tecnicoEssa é uma Informacao tecnicoEssa é uma Informacao tecnicoEssa é uma Informacao tecnico" +
				"Essa é uma Informacao tecnicoEssa é uma Informacao tecnicoEssa é uma Informacao tecnicoEssa é uma Informacao tecnico" +
				"Essa é uma Informacao tecnicoEssa é uma Informacao tecnicoEssa é uma Informacao tecnicoEssa é uma Informacao tecnico");
		lt1.setLaboratorio("Comutação");
		lt1.setNumeroOrdemServico("53012");
		
		listaLaudoTecnico.add(lt1);
		return listaLaudoTecnico;
	}
}

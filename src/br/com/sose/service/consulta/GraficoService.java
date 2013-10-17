package br.com.sose.service.consulta;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.daoImpl.consulta.GraficoDao;
import br.com.sose.entity.admistrativo.Laboratorio;
import br.com.sose.entity.admistrativo.Unidade;
import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.admistrativo.parceiros.Pessoa;
import br.com.sose.entity.grafico.RetornoGraficoPizza;

@Service(value="graficoService")
@RemotingDestination(value="graficoService")
public class GraficoService {

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private GraficoDao graficoDao;

	@RemotingInclude
	public void totalUnidadeFinalizadaPorLaboratorio(Date startDate, Date finalDate){
		//return npiDao.returnNpisGroupByStatus(startDate, finalDate);
	}
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public List<RetornoGraficoPizza> buscarInfoGraficoReparoFinalizadoPorCondicao(Date startDate, Date finalDate, Pessoa cliente, Laboratorio laboratorio, Usuario usuario, Unidade unidade){
		if(cliente == null || cliente.getId().equals(new Long(0))){
			cliente = null;
		}
		if(laboratorio == null || laboratorio.getId().equals(new Long(0))){
			laboratorio = null;
		}
		if(usuario == null || usuario.getId().equals(new Long(0))){
			usuario = null;
		}
		if(unidade == null || unidade.getId().equals(new Long(0))){
			unidade = null;
		}
		List<RetornoGraficoPizza> retorno = graficoDao.buscarInfoGraficoReparoFinalizadoPorCondicao(startDate, finalDate, cliente, laboratorio, usuario, unidade);
		return retorno;
	}
}

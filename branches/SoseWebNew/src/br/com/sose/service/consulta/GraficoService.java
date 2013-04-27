package br.com.sose.service.consulta;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;

import br.com.sose.daoImpl.consulta.GraficoDao;

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
}

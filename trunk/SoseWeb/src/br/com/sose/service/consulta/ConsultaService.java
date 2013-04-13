package br.com.sose.service.consulta;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sose.daoImpl.consulta.ConsultaGeralDao;
import br.com.sose.daoImpl.expedicao.NotaFiscalRemessaDao;
import br.com.sose.daoImpl.recebimento.NotaFiscalDao;
import br.com.sose.daoImpl.recebimento.OrdemServicoDao;
import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.consulta.Consulta;
import br.com.sose.entity.consulta.ConsultaGeralTO;
import br.com.sose.entity.consulta.ConsultaResultado;
import br.com.sose.to.ConsultaTO;
import br.com.sose.utils.ExportXlsUtil;
import flex.messaging.io.ArrayList;

@Service(value="consultaService")
@RemotingDestination(value="consultaService")
public class ConsultaService {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	public NotaFiscalDao notaFiscalDao;

	@Autowired
	public NotaFiscalRemessaDao notaFiscalRemessaDao;

	@Autowired
	public OrdemServicoDao ordemServicoDao;
	
	@Autowired
	private ConsultaGeralDao consultaGeralDao;
	
	@Autowired
	private ExportXlsUtil exportXlsUtil;

	@RemotingInclude
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ConsultaResultado realizarConsultaGeral(Consulta consulta) throws Exception {
		ConsultaResultado consultaResultado = new ConsultaResultado();
		try {
			
			//Busca todas as notas fiscais de entrada, saída e todas as os's associadas ao cliente
//			List<ConsultaTO> os = ordemServicoDao.realizarConsultaGeral(consulta);
//			consultaResultado.setListaOrdemServico(ConsultaTO);

		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return consultaResultado;
	}
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public ConsultaResultado realizarConsultaOrdemServico(Consulta consulta) throws Exception {
		ConsultaResultado consultaResultado = new ConsultaResultado();
		try {
			System.out.println("Iniciando consulta...");
			//Busca todas as notas fiscais de entrada, saída e todas as os's associadas ao cliente
			List<ConsultaTO> os = ordemServicoDao.realizarConsultaOrdemServico(consulta);
			consultaResultado.setListaOrdemServico(os);
			System.out.println("Consulta finalizada...");
		} catch (Exception e) {
			e.printStackTrace(); logger.error(e);
			throw e;
		}
		return consultaResultado;
	}
	
	@RemotingInclude
	@Transactional(readOnly = true)
	public byte[] createFile(List<ConsultaTO> values, Usuario usuario)  throws Exception {
		try {	
			List<Long> idsOs = new  java.util.ArrayList<Long>();
			StringBuilder sb = new StringBuilder();
			sb.append("(");
			for(ConsultaTO os : values){
				idsOs.add(os.getId());
				sb.append(os.getId().toString());
				sb.append(",");
			}
			sb.append("0)");
			
			System.out.println("Iniciou a consulta...." + idsOs.size());
			List<Object[]> listaOs = consultaGeralDao.realizarConsultaGeral(idsOs);
			System.out.println("Retornou da consulta");
			return exportXlsUtil.createFile(listaOs,usuario);
		} catch (Exception e){ 
			throw e;
		}		
	}
	


}

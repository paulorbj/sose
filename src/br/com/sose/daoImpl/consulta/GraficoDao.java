package br.com.sose.daoImpl.consulta;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.sose.daoImpl.HibernateDaoGenerico;
import br.com.sose.entity.admistrativo.Laboratorio;
import br.com.sose.entity.admistrativo.Unidade;
import br.com.sose.entity.admistrativo.Usuario;
import br.com.sose.entity.admistrativo.parceiros.Pessoa;
import br.com.sose.entity.grafico.RetornoGraficoPizza;
import br.com.sose.entity.recebimento.OrdemServico;
import br.com.sose.utils.DateUtils;

@Repository("graficolDao")
public class GraficoDao extends HibernateDaoGenerico<OrdemServico, Long> {

	private Logger logger = Logger.getLogger(this.getClass());

	public GraficoDao() {
		super();
	}


	@SuppressWarnings("unchecked")
	public List<RetornoGraficoPizza> buscarInfoGraficoReparoFinalizadoPorCondicao(Date startDate, Date finalDate, Pessoa cliente, Laboratorio laboratorio, Usuario usuario, Unidade unidade){
		try{
			ArrayList<RetornoGraficoPizza> listaRetorno = new ArrayList<RetornoGraficoPizza> ();
			Hashtable<String, BigInteger> listaContabilidade = new Hashtable<String, BigInteger>();

			StringBuilder sbOrcamento = new StringBuilder();
			sbOrcamento.append("select o.condicao, count(os.id) ");
			sbOrcamento.append("from ordemservico os ");
			sbOrcamento.append("left join unidade uni on os.unidade_id = uni.id ");
			sbOrcamento.append("left join laboratorio l on uni.laboratorio_id = l.id ");
			sbOrcamento.append("left join pessoa c on os.cliente_id = c.id ");
			sbOrcamento.append("left join orcamento o on os.orcamento_id = o.id ");
			sbOrcamento.append("left join usuario u on o.usuario_id = u.id ");
			sbOrcamento.append("where ");
			sbOrcamento.append("(os.orcamento_id is not null and os.reparo_id is null and o.status = 'Finalizado')  ");

			if(cliente != null){
				sbOrcamento.append("AND c.id = :idCliente ");
			}

			if(laboratorio != null){
				sbOrcamento.append("AND l.id = :idLaboratorio ");
			}

			if(usuario != null){
				sbOrcamento.append("AND u.id = :idUsuario ");
			}
			
			if(unidade != null){
				sbOrcamento.append("AND uni.id = :idUnidade ");
			}

			if(startDate == null){
				startDate = new Date(0);
			}
			if(finalDate == null){
				finalDate = new Date();
			}

			sbOrcamento.append(" AND o.dt_fim BETWEEN :startDate AND :finalDate  ");

			sbOrcamento.append("group by o.condicao; ");

			Query q = sessionFactory.getCurrentSession().createSQLQuery(sbOrcamento.toString());

			if(cliente != null){
				q.setParameter("idCliente", cliente.getId());
			}

			if(laboratorio != null){
				q.setParameter("idLaboratorio", laboratorio.getId());
			}

			if(usuario != null){
				q.setParameter("idUsuario", usuario.getId());
			}
			
			if(unidade != null){
				q.setParameter("idUnidade", unidade.getId());
			}

			q.setParameter("startDate", startDate);
			q.setParameter("finalDate", DateUtils.nextDay(finalDate));

			List<Object[]> listaOrcamento = q.list();

			for(Object[] obj : listaOrcamento){
				if(listaContabilidade.get(obj[0].toString()) == null){
					listaContabilidade.put(obj[0].toString(), (BigInteger)obj[1]);
				}else{
					listaContabilidade.put(obj[0].toString(), listaContabilidade.get(obj[0].toString()).add((BigInteger)obj[1]));
				}
			}


			//Consulta para o reparo
			StringBuilder sbReparo= new StringBuilder();
			sbReparo.append("select r.condicao, count(os.id) ");
			sbReparo.append("from ordemservico os ");
			sbReparo.append("left join unidade uni on os.unidade_id = uni.id ");
			sbReparo.append("left join laboratorio l on uni.laboratorio_id = l.id ");
			sbReparo.append("left join pessoa c on os.cliente_id = c.id ");
			sbReparo.append("left join reparo r on os.reparo_id = r.id ");
			sbReparo.append("left join usuario u on r.usuario_id = u.id ");
			sbReparo.append("where ");
			sbReparo.append("(os.reparo_id is not null and r.status = 'Finalizado')  ");

			if(cliente != null){
				sbReparo.append("AND c.id = :idCliente ");
			}

			if(laboratorio != null){
				sbReparo.append("AND l.id = :idLaboratorio ");
			}

			if(usuario != null){
				sbReparo.append("AND u.id = :idUsuario ");
			}
			
			if(unidade != null){
				sbReparo.append("AND uni.id = :idUnidade ");
			}

			if(startDate == null){
				startDate = new Date(0);
			}
			if(finalDate == null){
				finalDate = new Date();
			}

			sbReparo.append(" AND r.dt_fim BETWEEN :startDate AND :finalDate  ");

			sbReparo.append("group by r.condicao; ");

			Query qReparo = sessionFactory.getCurrentSession().createSQLQuery(sbReparo.toString());

			if(cliente != null){
				qReparo.setParameter("idCliente", cliente.getId());
			}

			if(laboratorio != null){
				qReparo.setParameter("idLaboratorio", laboratorio.getId());
			}

			if(usuario != null){
				qReparo.setParameter("idUsuario", usuario.getId());
			}
			
			if(unidade != null){
				qReparo.setParameter("idUnidade", unidade.getId());
			}

			qReparo.setParameter("startDate", startDate);
			qReparo.setParameter("finalDate", DateUtils.nextDay(finalDate));

			List<Object[]> listaReparo = qReparo.list();

			for(Object[] obj : listaReparo){
				if(listaContabilidade.get(obj[0].toString()) == null){
					listaContabilidade.put(obj[0].toString(), (BigInteger)obj[1]);
				}else{
					listaContabilidade.put(obj[0].toString(), listaContabilidade.get(obj[0].toString()).add((BigInteger)obj[1]));
				}
			}

			for(String key : listaContabilidade.keySet()){
				listaRetorno.add(new RetornoGraficoPizza(key, listaContabilidade.get(key).intValue()));
			}

			return listaRetorno;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}

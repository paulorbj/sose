package br.com.sose.daoImpl.areaTecnica;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.sose.daoImpl.HibernateDaoGenerico;
import br.com.sose.entity.admistrativo.Componente;
import br.com.sose.entity.orcamento.Orcamento;
import br.com.sose.entity.orcrepGenerico.RequisicaoComponente;
import br.com.sose.entity.reparo.Reparo;
import br.com.sose.utils.DateUtils;

@Repository("requisicaoComponenteDao")
public class RequisicaoComponenteDao extends HibernateDaoGenerico<RequisicaoComponente, Long> {

	public RequisicaoComponenteDao() {
		super();
	}

	public Integer remover(final RequisicaoComponente requisicao){
		//		
		//			
		//				Query q = sessionFactory.getCurrentSession().createNativeQuery("DELETE FROM RequisicaoComponente WHERE id = :id");
		//				q.setParameter("id", requisicao.getId());
		//				return q.executeUpdate();
		//			}
		//		});
		//		return (Integer) res;
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<RequisicaoComponente> listarRequisicaoPorStatus(final String status) {
		try{
			Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h " +
					"WHERE h.statusString=:status ORDER BY h.dataRequisicao DESC");
			q.setParameter("status", status);
			return q.list();
		}catch(Exception e){
			e.printStackTrace(); 
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<RequisicaoComponente> listarRequisicaoPorStatusNoReparo(final String status) {
		try{
			Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h " +
					"WHERE h.statusString=:status " +
					"AND ((h.reparo IS NOT NULL AND h.reparo IN (SELECT os.reparo FROM OrdemServico os WHERE os.notaFiscalSaida IS NULL)) " +
					"OR (h.reparo IS NULL AND h.orcamento IN (SELECT os.orcamento FROM OrdemServico os WHERE os.notaFiscalSaida IS NULL))) " +
					"ORDER BY h.dataRequisicao DESC");
			q.setParameter("status", status);
			return q.list();
		}catch(Exception e){
			e.printStackTrace(); 
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<RequisicaoComponente> listarRequisicaoEstoque() {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.statusString <> 'Recebido' ");
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public Long listarTotalRequisicoesUltimos6Meses(Componente componente) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT SUM(h.quantidade) FROM "+ entityClass.getName() + " h WHERE h.dataRequisicao > :dataBase AND h.componente = :componente ");
		q.setParameter("componente", componente);
		q.setDate("dataBase", new Date(DateUtils.addDays(new Date().getTime(), -180)));
		return (Long)q.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<RequisicaoComponente> listarRequisicaoPorReparo(final Reparo reparo) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.reparo=:reparo");
		q.setParameter("reparo", reparo);
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<RequisicaoComponente> listarRequisicaoPorOrcamento(final Orcamento orcamento) {

		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.orcamento=:orcamento");
		q.setParameter("orcamento", orcamento);
		return q.list();

	}

}

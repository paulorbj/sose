package br.com.sose.daoImpl.areaTecnica;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.sose.daoImpl.HibernateDaoGenerico;
import br.com.sose.entity.orcamento.Orcamento;
import br.com.sose.entity.orcrepGenerico.RequisicaoComponente;
import br.com.sose.entity.reparo.Reparo;

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
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h " +
				"WHERE h.statusString=:status ORDER BY h.dataRequisicao DESC");
		q.setParameter("status", status);
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<RequisicaoComponente> listarRequisicaoEstoque() {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.statusString <> 'Recebido' ");
		return q.list();
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

package br.com.sose.daoImpl.areaTecnica;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.sose.daoImpl.HibernateDaoGenerico;
import br.com.sose.entity.orcamento.Orcamento;
import br.com.sose.entity.orcrepGenerico.DefeitoOrcRep;
import br.com.sose.entity.reparo.Reparo;

@Repository("defeitoOrcRep")
public class DefeitoOrcRepDao extends HibernateDaoGenerico<DefeitoOrcRep, Long> {

	public DefeitoOrcRepDao() {
		super();
	}

	@SuppressWarnings("unchecked")
	public List<DefeitoOrcRep> listarDefeitosPorReparo(final Reparo reparo) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.reparo = :reparo");
		q.setParameter("reparo", reparo);
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<DefeitoOrcRep> listarDefeitosPorOrcamento(final Orcamento orcamento) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.orcamento = :orcamento");
		q.setParameter("orcamento", orcamento);
		return q.list();
	}

	public Boolean remover(final DefeitoOrcRep defeitoOrcRep){
		Query q = sessionFactory.getCurrentSession().createSQLQuery("DELETE FROM DefeitoOrcRep WHERE id = :id");
		q.setParameter("id", defeitoOrcRep.getId());
		int i = q.executeUpdate();
		if(i>0){
			return true;
		}else{
			return false;
		}
	}

}

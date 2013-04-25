package br.com.sose.daoImpl.areaTecnica;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.sose.daoImpl.HibernateDaoGenerico;
import br.com.sose.entity.orcrepGenerico.OrcRepGenerico;

@Repository("areaTecnicaDao")
public class AreaTecnicaDao extends HibernateDaoGenerico<OrcRepGenerico, Long> {

	public AreaTecnicaDao() {
		super();
	}

	@SuppressWarnings("unchecked")
	public List<OrcRepGenerico> listarOrcamentoReparo() {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h ORDER BY h.dataEntrada");
		return q.list();
	}


}

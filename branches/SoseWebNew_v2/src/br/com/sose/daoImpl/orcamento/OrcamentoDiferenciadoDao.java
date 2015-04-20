package br.com.sose.daoImpl.orcamento;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.sose.daoImpl.HibernateDaoGenerico;
import br.com.sose.entity.orcamento.OrcamentoDiferenciado;

@Repository("orcamentoDiferenciadoDao")
public class OrcamentoDiferenciadoDao extends HibernateDaoGenerico<OrcamentoDiferenciado, Long> {

	public OrcamentoDiferenciadoDao() {
		super();
	}

	@SuppressWarnings("unchecked")
	public List<OrcamentoDiferenciado> listarOrcamentosDiferenciados(final String status) {


		StringBuilder sb = new StringBuilder("SELECT h FROM "+ entityClass.getName() + " h WHERE h.statusString = :status ORDER BY h.dataCriacao DESC");
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());
		query.setParameter("status", status);
		return query.list();

	}

	@SuppressWarnings("unchecked")
	public OrcamentoDiferenciado buscarPorId(final Long id) {


		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h " +
				"LEFT JOIN FETCH h.ordemServico os " +
				"LEFT JOIN FETCH os.placasFilhas pf " +
				"LEFT JOIN FETCH pf.reparo pfRep " +
				"LEFT JOIN FETCH pfRep.listaDefeito  " +
				"LEFT JOIN FETCH h.reparo rep " +
				"LEFT JOIN FETCH rep.listaDefeito WHERE h.id=:id");
		q.setParameter("id", id);
		OrcamentoDiferenciado r = (OrcamentoDiferenciado)q.uniqueResult();
		return r;

	}


}

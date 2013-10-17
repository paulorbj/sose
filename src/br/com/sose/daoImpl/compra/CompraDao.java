package br.com.sose.daoImpl.compra;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.sose.daoImpl.HibernateDaoGenerico;
import br.com.sose.entity.compra.Compra;
import br.com.sose.entity.compra.PedidoCompra;
import br.com.sose.entity.faturamento.Faturamento;

@Repository("compraDao")
public class CompraDao extends HibernateDaoGenerico<Compra, Long> {

	public CompraDao() {
		super();
	}

	@SuppressWarnings("unchecked")
	public List<Compra> listarCompra(final String status) {
		StringBuilder sb = new StringBuilder("SELECT h FROM "+ entityClass.getName() + " h WHERE h.statusString = :status ORDER BY h.dataCriacaoCompra DESC");
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());
		query.setParameter("status", status);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Compra> listarPreCompras() {
		StringBuilder sb = new StringBuilder("SELECT h FROM "+ entityClass.getName() + " h WHERE h.dataCriacaoCompra IS NULL ORDER BY h.nome");
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public Compra buscarPorId(final Long id) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h " +
				"WHERE h.id=:id");
		q.setParameter("id", id);
		Compra r = (Compra)q.uniqueResult();
		return r;
	}
	
}
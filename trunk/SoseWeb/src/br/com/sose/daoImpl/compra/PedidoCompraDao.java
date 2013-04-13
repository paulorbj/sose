package br.com.sose.daoImpl.compra;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.sose.daoImpl.HibernateDaoGenerico;
import br.com.sose.entity.compra.PedidoCompra;

@Repository("pedidoCompraDao")
public class PedidoCompraDao extends HibernateDaoGenerico<PedidoCompra, Long> {

	public PedidoCompraDao() {
		super();
	}

	@SuppressWarnings("unchecked")
	public List<PedidoCompra> listarPedidoCompra(final String status) {


		StringBuilder sb = new StringBuilder("SELECT h FROM "+ entityClass.getName() + " h WHERE h.statusString = :status ORDER BY h.dataCriacao DESC");
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());
		query.setParameter("status", status);
		return query.list();

	}

}
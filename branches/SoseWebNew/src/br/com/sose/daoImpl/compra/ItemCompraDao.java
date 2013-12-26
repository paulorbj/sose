package br.com.sose.daoImpl.compra;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.sose.daoImpl.HibernateDaoGenerico;
import br.com.sose.entity.admistrativo.Componente;
import br.com.sose.entity.compra.Compra;
import br.com.sose.entity.compra.ItemCompra;

@Repository("itemCompraDao")
public class ItemCompraDao extends HibernateDaoGenerico<ItemCompra, Long> {

	public ItemCompraDao() {
		super();
	}

	@SuppressWarnings("unchecked")
	public ItemCompra buscarPorId(final Long id) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h " +
				"LEFT JOIN FETCH h.listaPedidoCompra WHERE h.id=:id");
		q.setParameter("id", id);
		ItemCompra r = (ItemCompra)q.uniqueResult();
		return r;
	}
	
	@SuppressWarnings("unchecked")
	public List<ItemCompra> listarItemCompraPorCompraPorStatus(final Compra compra, final String status) {
		StringBuilder sb = new StringBuilder("SELECT h FROM "+ entityClass.getName() + " h " +
				"LEFT JOIN FETCH h.listaPedidoCompra WHERE h.status = :status AND h.compra = :compra");
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());
		query.setParameter("status", status);
		query.setParameter("compra", compra);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<ItemCompra> listarItemCompraPorCompra(final Compra compra) {
		StringBuilder sb = new StringBuilder("SELECT DISTINCT h FROM "+ entityClass.getName() + " h " +
				"LEFT JOIN FETCH h.listaPedidoCompra WHERE h.compra = :compra");
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());
		query.setParameter("compra", compra);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<ItemCompra> listarUltimas10Compras(final Componente componente) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h " +
				"WHERE h.componente=:componente AND h.status <> :status ORDER BY h.dataEntrada DESC");
		q.setParameter("componente", componente);
		q.setParameter("status", "Pendente");
		q.setMaxResults(10);
		List<ItemCompra> r = q.list();
		return r;
	}
	
	@SuppressWarnings("unchecked")
	public List<ItemCompra> listarUltimasCompras(final Componente componente, int qtdResultados) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h " +
				"WHERE h.componente=:componente AND h.status <> :status ORDER BY h.dataEntrada DESC");
		q.setParameter("componente", componente);
		q.setParameter("status", "Pendente");
		q.setMaxResults(qtdResultados);
		List<ItemCompra> r = q.list();
		return r;
	}
	
	@SuppressWarnings("unchecked")
	public List<ItemCompra> listarItemCompraPendenteNotificacao(final Componente componente) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h " +
				"WHERE h.componente=:componente AND h.status = :status ORDER BY h.dataEntrada DESC");
		q.setParameter("componente", componente);
		q.setParameter("status", "Notificado");
		List<ItemCompra> r = q.list();
		return r;
	}
	
	public Boolean remover(final ItemCompra itemCompra){
		Query q = sessionFactory.getCurrentSession().createSQLQuery("DELETE FROM ItemCompra WHERE id = :id");
		q.setParameter("id", itemCompra.getId());
		int i = q.executeUpdate();
		if(i>0){
			return true;
		}else{
			return false;
		}
	}
	
}
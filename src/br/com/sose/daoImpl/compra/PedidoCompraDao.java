package br.com.sose.daoImpl.compra;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.sose.daoImpl.HibernateDaoGenerico;
import br.com.sose.entity.admistrativo.Atividade;
import br.com.sose.entity.admistrativo.Componente;
import br.com.sose.entity.compra.PedidoCompra;
import br.com.sose.status.estoque.ComponenteEmFalta;

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
	
	@SuppressWarnings("unchecked")
	public PedidoCompra buscarPorId(final Long id) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.id=:id");
		q.setParameter("id", id);
		PedidoCompra r = (PedidoCompra)q.uniqueResult();
		return r;
	}
	
	@SuppressWarnings("unchecked")
	public List<PedidoCompra> buscarPorEstoqueMinimoAguardandoCompra(final Componente componente) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.componente = :componente AND h.origemPedido = :origemPedido AND h.statusString = :status");
		q.setParameter("componente", componente);
		q.setParameter("origemPedido", "Estoque mínimo");
		q.setParameter("status", "Aguardando compra");
		return q.list();
	}	
		
	@SuppressWarnings("unchecked")
	public PedidoCompra buscarPorRequisicao(final Long id) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.requisicao.id=:id");
		q.setParameter("id", id);
		PedidoCompra r = (PedidoCompra)q.uniqueResult();
		return r;
	}
	
	public List<PedidoCompra> listaPedidosCompraEmFaltaPorComponente(Componente componente) throws Exception {
		StringBuilder sb = new StringBuilder("SELECT h FROM "+ entityClass.getName() + " h LEFT JOIN h.requisicao req WHERE req.statusString = :status AND req.componente.id = :idComponente ORDER BY h.dataCriacao DESC");
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());
		query.setParameter("status", ComponenteEmFalta.nome);
		query.setParameter("idComponente", componente.getId());
		return query.list();
	}

	public Boolean remover(final PedidoCompra pedidoCompra){
		Query q = sessionFactory.getCurrentSession().createSQLQuery("DELETE FROM PedidoCompra WHERE id = :id");
		q.setParameter("id", pedidoCompra.getId());
		int i = q.executeUpdate();
		if(i>0){
			return true;
		}else{
			return false;
		}
	}

}
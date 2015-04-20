package br.com.sose.daoImpl.administrativo;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.sose.daoImpl.HibernateDaoGenerico;
import br.com.sose.entity.admistrativo.TipoComponente;

@Repository("tipoComponenteDao")
public class TipoComponenteDao extends HibernateDaoGenerico<TipoComponente, Long> {

	public TipoComponenteDao() {
		super();
	}

	@SuppressWarnings("unchecked")
	public TipoComponente buscarPorNome(final String nome) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.nome = :nome");
		q.setParameter("nome", nome);
		return (TipoComponente)q.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<TipoComponente> findAllAtivoOrderByNome(){
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM " + entityClass.getName() + " h WHERE h.cadastroSistemaAtivo = true ORDER BY h.nome");
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<TipoComponente> findAllOrderByNome(){
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM " + entityClass.getName() + " h ORDER BY h.nome");
		return q.list();
	}

	public Boolean remover(final TipoComponente tipoComponente){
		Query q = sessionFactory.getCurrentSession().createSQLQuery("DELETE FROM TipoComponente WHERE id = :id");
		q.setParameter("id", tipoComponente.getId());
		int i = q.executeUpdate();
		if(i>0){
			return true;
		}else{
			return false;
		}
	}

}

package br.com.sose.daoImpl.administrativo;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.sose.daoImpl.HibernateDaoGenerico;
import br.com.sose.entity.admistrativo.Perfil;

@Repository("perfilDao")
public class PerfilDao extends HibernateDaoGenerico<Perfil, Long> {

	public PerfilDao() {
		super();
	}

	@SuppressWarnings("unchecked")
	public Perfil buscarPorNome(final String nome) {
		String queryString = "SELECT h FROM "+ entityClass.getName() + " h WHERE h.nome = :nome";
		Query q = sessionFactory.getCurrentSession().createQuery(queryString);
		q.setParameter("nome", nome);
		return (Perfil)q.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Perfil> findAllAtivoOrderByNome(){
		String queryString = "SELECT h FROM " + entityClass.getName() + " h WHERE h.cadastroSistemaAtivo = true ORDER BY h.nome";
		Query q = sessionFactory.getCurrentSession().createQuery(queryString);
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<Perfil> findAllOrderByNome(){
		String queryString = "SELECT h FROM " + entityClass.getName() + " h ORDER BY h.nome";
		Query q = sessionFactory.getCurrentSession().createQuery(queryString);
		return q.list();
	}

	public Boolean remover(final Perfil perfil){
		Query q = sessionFactory.getCurrentSession().createSQLQuery("DELETE FROM Perfil WHERE id = :id");
		q.setParameter("id", perfil.getId());
		int i = q.executeUpdate();
		if(i>0){
			return true;
		}else{
			return false;
		}
	}
}

package br.com.sose.daoImpl.administrativo;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.sose.daoImpl.HibernateDaoGenerico;
import br.com.sose.entity.admistrativo.Fabricante;

@Repository("fabricanteDao")
public class FabricanteDao extends HibernateDaoGenerico<Fabricante, Long> {

	public FabricanteDao() {
		super();
	}

	public Fabricante buscarPorNome(final String nome) {
		String queryString = "SELECT h FROM "+ entityClass.getName() + " h WHERE h.nome = :nome";
		Query q = sessionFactory.getCurrentSession().createQuery(queryString);
		q.setParameter("nome", nome);
		return (Fabricante)q.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Fabricante> findAllAtivoOrderByNome(){
		String queryString = "SELECT h FROM " + entityClass.getName() + " h WHERE h.cadastroSistemaAtivo = true ORDER BY h.nome";
		Query q = sessionFactory.getCurrentSession().createQuery(queryString);
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<Fabricante> findAllOrderByNome(){
		String queryString = "SELECT h FROM " + entityClass.getName() + " h ORDER BY h.nome";
		Query q = sessionFactory.getCurrentSession().createQuery(queryString);
		return q.list();

	}

	public Boolean remover(final Fabricante fabricante){
		Query q = sessionFactory.getCurrentSession().createSQLQuery("DELETE FROM Fabricante WHERE id = :id");
		q.setParameter("id", fabricante.getId());
		int i = q.executeUpdate();
		if(i>0){
			return true;
		}else{
			return false;
		}
	}

}

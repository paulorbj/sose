package br.com.sose.daoImpl.administrativo;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.sose.daoImpl.HibernateDaoGenerico;
import br.com.sose.entity.admistrativo.Defeito;

@Repository("defeitoDao")
public class DefeitoDao extends HibernateDaoGenerico<Defeito, Long> {

	public DefeitoDao() {
		super();
	}

	public Defeito buscarPorNome(final String nome) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.nome = :nome");
		q.setParameter("nome", nome);
		return (Defeito)q.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Defeito> findAllAtivoOrderByNome(){
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM " + entityClass.getName() + " h WHERE h.cadastroSistemaAtivo = true ORDER BY h.nome");
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<Defeito> findAllOrderByNome(){
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM " + entityClass.getName() + " h ORDER BY h.nome");
		return q.list();
	}

	public Boolean remover(final Defeito defeito){
		Query q = sessionFactory.getCurrentSession().createSQLQuery("DELETE FROM Defeito WHERE id = :id");
		q.setParameter("id", defeito.getId());
		int i = q.executeUpdate();
		if(i>0){
			return true;
		}else{
			return false;
		}
	}

}

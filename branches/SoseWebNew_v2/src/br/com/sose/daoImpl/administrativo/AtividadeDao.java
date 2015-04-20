package br.com.sose.daoImpl.administrativo;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.sose.daoImpl.HibernateDaoGenerico;
import br.com.sose.entity.admistrativo.Atividade;

@Repository("atividadeDao")
public class AtividadeDao extends HibernateDaoGenerico<Atividade, Long> {

	public AtividadeDao() {
		super();
	}

	public Atividade buscarPorNome(final String nome) {
		String queryString = "SELECT h FROM "+ entityClass.getName() + " h WHERE h.nome = :nome";
		Query q = sessionFactory.getCurrentSession().createQuery(queryString);
		q.setParameter("nome", nome);
		return (Atividade)q.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Atividade> findAllAtivoOrderByNome(){
		String queryString = "SELECT h FROM " + entityClass.getName() + " h WHERE h.cadastroSistemaAtivo = true ORDER BY h.nome";
		Query q = sessionFactory.getCurrentSession().createQuery(queryString);
		return q.list();

	}

	@SuppressWarnings("unchecked")
	public List<Atividade> findAllOrderByNome(){
		String queryString = "SELECT h FROM " + entityClass.getName() + " h ORDER BY h.nome";
		Query q = sessionFactory.getCurrentSession().createQuery(queryString);
		return q.list();
	}

	public Boolean remover(final Atividade atividade){
		Query q = sessionFactory.getCurrentSession().createSQLQuery("DELETE FROM Atividade WHERE id = :id");
		q.setParameter("id", atividade.getId());
		int i = q.executeUpdate();
		if(i>0){
			return true;
		}else{
			return false;
		}
	}
}

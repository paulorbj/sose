package br.com.sose.daoImpl.administrativo;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.sose.daoImpl.HibernateDaoGenerico;
import br.com.sose.entity.admistrativo.Encapsulamento;

@Repository("encapsulamentoDao")
public class EncapsulamentoDao extends HibernateDaoGenerico<Encapsulamento, Long> {

	public EncapsulamentoDao() {
		super();
	}

	public Encapsulamento buscarPorNome(final String nome) {
		String queryString = "SELECT h FROM "+ entityClass.getName() + " h WHERE h.nome = :nome";
		Query q = sessionFactory.getCurrentSession().createQuery(queryString);
		q.setParameter("nome", nome);
		return (Encapsulamento)q.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Encapsulamento> findAllAtivoOrderByNome(){
		String queryString = "SELECT h FROM " + entityClass.getName() + " h WHERE h.cadastroSistemaAtivo = true ORDER BY h.nome";
		Query q = sessionFactory.getCurrentSession().createQuery(queryString);
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<Encapsulamento> findAllOrderByNome(){
		String queryString = "SELECT h FROM " + entityClass.getName() + " h ORDER BY h.nome";
		Query q = sessionFactory.getCurrentSession().createQuery(queryString);
		return q.list();
	}

	public Boolean remover(final Encapsulamento encapsulamento){
		Query q = sessionFactory.getCurrentSession().createSQLQuery("DELETE FROM Encapsulamento WHERE id = :id");
		q.setParameter("id", encapsulamento.getId());
		int i = q.executeUpdate();
		if(i>0){
			return true;
		}else{
			return false;
		}
	}

}

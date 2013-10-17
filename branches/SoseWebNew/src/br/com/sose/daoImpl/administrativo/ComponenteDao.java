package br.com.sose.daoImpl.administrativo;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.sose.daoImpl.HibernateDaoGenerico;
import br.com.sose.entity.admistrativo.Componente;

@Repository("componenteDao")
public class ComponenteDao extends HibernateDaoGenerico<Componente, Long> {

	public ComponenteDao() {
		super();
	}

	public Componente buscarPorNome(final String nome) {
		String queryString = "SELECT h FROM "+ entityClass.getName() + " h WHERE h.nome = :nome";
		Query q = sessionFactory.getCurrentSession().createQuery(queryString);
		q.setParameter("nome", nome);
		return (Componente)q.uniqueResult();

	}

	public Componente buscarPorId(final Long id) {
		String queryString = "SELECT h FROM "+ entityClass.getName() + " h WHERE h.id = :id";
		Query q = sessionFactory.getCurrentSession().createQuery(queryString);
		q.setParameter("id", id);
		return (Componente)q.uniqueResult();
	}
	
	public Componente buscarCompletoPorId(final Long id) {
		String queryString = "SELECT h FROM "+ entityClass.getName() + " h " +
				"LEFT JOIN FETCH h.fabricante f " +
				"LEFT JOIN FETCH h.encapsulamento e " +
				"LEFT JOIN FETCH h.tipo tp  WHERE h.id = :id";
		Query q = sessionFactory.getCurrentSession().createQuery(queryString);
		q.setParameter("id", id);
		Componente componente = (Componente)q.uniqueResult();
		return componente;
	}

	@SuppressWarnings("unchecked")
	public List<Componente> findAllAtivoOrderByNome(){
		String queryString = "SELECT h FROM " + entityClass.getName() + " h WHERE h.cadastroSistemaAtivo = true ORDER BY h.nome";
		Query q = sessionFactory.getCurrentSession().createQuery(queryString);
		return q.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Componente> findAll(){
		String queryString = "SELECT h FROM " + entityClass.getName() + " h ORDER BY h.nome";
		Query q = sessionFactory.getCurrentSession().createQuery(queryString);
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<Componente> findAllOrderByNome(){
		String queryString = "SELECT NEW br.com.sose.entity.admistrativo.Componente(" +
				"h.id," +
				"h.descricao," +
				"h.nome," +
				"h.qtdEstoqueMinimo," +
				"f.nome," +
				"e.nome," +
				"t.nome," +
				"h.qtdEstoque," +
				"h.posicaoEstoque," +
				"h.pinos," +
				"h.valido," +
				"h.qtdComprada) " +
				"FROM " + entityClass.getName() + " h " +
				"LEFT JOIN h.fabricante f " +
				"LEFT JOIN h.encapsulamento e " +
				"LEFT JOIN h.tipo t " +
				"ORDER BY h.nome";
		Query q = sessionFactory.getCurrentSession().createQuery(queryString);
		return q.list();

	}

	public Boolean remover(final Componente componente){
		Query q = sessionFactory.getCurrentSession().createSQLQuery("DELETE FROM Componente WHERE id = :id");
		q.setParameter("id", componente.getId());
		int i = q.executeUpdate();
		if(i>0){
			return true;
		}else{
			return false;
		}
	}

}

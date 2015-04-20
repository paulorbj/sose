package br.com.sose.daoImpl.administrativo;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.sose.daoImpl.HibernateDaoGenerico;
import br.com.sose.entity.admistrativo.Equipamento;

@Repository("equipamentoDao")
public class EquipamentoDao extends HibernateDaoGenerico<Equipamento, Long> {

	public EquipamentoDao() {
		super();
	}

	public Equipamento buscarPorNome(final String nome) {
		String queryString = "SELECT h FROM "+ entityClass.getName() + " h WHERE h.nome = :nome";
		Query q = sessionFactory.getCurrentSession().createQuery(queryString);
		q.setParameter("nome", nome);
		return (Equipamento)q.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Equipamento> findAllAtivoOrderByNome(){
		String queryString = "SELECT h FROM " + entityClass.getName() + " h WHERE h.cadastroSistemaAtivo = true ORDER BY h.nome";
		Query q = sessionFactory.getCurrentSession().createQuery(queryString);
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<Equipamento> findAllOrderByNome(){
		String queryString = "SELECT h FROM " + entityClass.getName() + " h ORDER BY h.nome";
		Query q = sessionFactory.getCurrentSession().createQuery(queryString);
		return q.list();
	}

	public Boolean remover(final Equipamento equipamento){
		Query q = sessionFactory.getCurrentSession().createSQLQuery("DELETE FROM Equipamento WHERE id = :id");
		q.setParameter("id", equipamento.getId());
		int i = q.executeUpdate();
		if(i>0){
			return true;
		}else{
			return false;
		}
	}

}

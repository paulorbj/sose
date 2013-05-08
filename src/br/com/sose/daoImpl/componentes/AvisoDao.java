package br.com.sose.daoImpl.componentes;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.sose.daoImpl.HibernateDaoGenerico;
import br.com.sose.entity.admistrativo.Atividade;
import br.com.sose.entity.componentes.Aviso;

@Repository("avisoDao")
public class AvisoDao extends HibernateDaoGenerico<Aviso, Long> {

	public AvisoDao() {
		super();
	}
	
	@SuppressWarnings("unchecked")
	public List<Aviso> listarAvisos() {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h ORDER BY h.dataCriacao DESC");
		return q.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Aviso> listarAvisosVisiveis() {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.visivel = true ORDER BY h.dataCriacao DESC");
		return q.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Aviso> listarAvisosNaoVisiveis() {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.visivel = false ORDER BY h.dataCriacao DESC");
		return q.list();
	}
	
	public Boolean remover(final Aviso aviso){
		Query q = sessionFactory.getCurrentSession().createSQLQuery("DELETE FROM Aviso WHERE id = :id");
		q.setParameter("id", aviso.getId());
		int i = q.executeUpdate();
		if(i>0){
			return true;
		}else{
			return false;
		}
	}
	
}

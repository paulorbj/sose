package br.com.sose.daoImpl.administrativo;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.sose.daoImpl.HibernateDaoGenerico;
import br.com.sose.entity.admistrativo.Laboratorio;

@Repository("laboratorioDao")
public class LaboratorioDao extends HibernateDaoGenerico<Laboratorio, Long> {

	public LaboratorioDao() {
		super();
	}

	public Laboratorio buscarPorNome(final String nome) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.nome = :nome");
		q.setParameter("nome", nome);
		return (Laboratorio)q.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Laboratorio> findAllAtivoOrderByNome(){		
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM " + entityClass.getName() + " h WHERE h.cadastroSistemaAtivo = true ORDER BY h.nome");
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<Laboratorio> findAllOrderByNome(){
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT NEW br.com.sose.entity.admistrativo.Laboratorio(" +
				"h.id, " +
				"h.nome, " +
				"h.descricao, " +
				"l.usuario" +
				") FROM " + entityClass.getName() + " h LEFT JOIN h.lider l ORDER BY h.nome");
		return q.list();
	}

	public Boolean remover(final Laboratorio laboratorio){
		Query q = sessionFactory.getCurrentSession().createSQLQuery("DELETE FROM laboratorio_usuario WHERE laboratorio_id = :id");
		
		Query q1 = sessionFactory.getCurrentSession().createSQLQuery("DELETE FROM Laboratorio WHERE id = :id");
		q.setParameter("id", laboratorio.getId());
		q1.setParameter("id", laboratorio.getId());

		int i = q.executeUpdate();
		int i1 = q1.executeUpdate();
		if(i1>0){
			return true;
		}else{
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public Laboratorio buscarPorId(final Long id) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h " +
				"LEFT JOIN FETCH h.lider l "+
				"LEFT JOIN FETCH h.tecnicos WHERE h.id=:id");
		q.setParameter("id", id);
		Laboratorio r = (Laboratorio)q.uniqueResult();
		return r;
	}

}

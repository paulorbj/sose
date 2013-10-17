package br.com.sose.daoImpl.lpu;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.sose.daoImpl.HibernateDaoGenerico;
import br.com.sose.entity.admistrativo.Unidade;
import br.com.sose.entity.lpu.ItemLpu;
import br.com.sose.entity.lpu.Lpu;
import br.com.sose.entity.lpu.UnidadeItemLpu;

@Repository("unidadeItemLpuDao")
public class UnidadeItemLpuDao extends HibernateDaoGenerico<UnidadeItemLpu, Long> {

	public UnidadeItemLpuDao() {
		super();
	}
	
	@SuppressWarnings("unchecked")
	public List<UnidadeItemLpu> buscarPorLpu(final Lpu lpu) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.lpu = :lpu");
		q.setParameter("lpu", lpu);
		return q.list();
	}
	
	@SuppressWarnings("unchecked")
	public UnidadeItemLpu buscarUnidadeItemLpu(final Unidade unidade, final Lpu lpu) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.unidadeServilogi = :unidade AND h.lpu = :lpu");
		q.setParameter("lpu", lpu);
		q.setParameter("unidade", unidade);
		return (UnidadeItemLpu)q.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<UnidadeItemLpu> listarPorUnidade(final Unidade unidade, final Lpu lpu) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.unidadeServilogi = :unidade AND h.lpu = :lpu");
		q.setParameter("unidade", unidade);
		q.setParameter("lpu", lpu);
		return q.list();
	}

	public Boolean removerPorLpu(final Lpu lpu){
		Query q = sessionFactory.getCurrentSession().createSQLQuery("DELETE FROM UnidadeItemLpu WHERE lpu = :lpu");
		q.setParameter("lpu", lpu);
		int i = q.executeUpdate();
		if(i>0){
			return true;
		}else{
			return false;
		}
	}
	
	public Boolean remover(final UnidadeItemLpu unidadeItemLpu){
		Query q = sessionFactory.getCurrentSession().createSQLQuery("DELETE FROM UnidadeItemLpu WHERE id = :id");
		q.setParameter("id", unidadeItemLpu.getId());
		int i = q.executeUpdate();
		if(i>0){
			return true;
		}else{
			return false;
		}
	}

}

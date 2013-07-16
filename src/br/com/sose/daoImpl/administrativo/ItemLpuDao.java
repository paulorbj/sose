package br.com.sose.daoImpl.administrativo;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.sose.daoImpl.HibernateDaoGenerico;
import br.com.sose.entity.admistrativo.ItemLpu;
import br.com.sose.entity.admistrativo.Lpu;
import br.com.sose.entity.admistrativo.Unidade;
import br.com.sose.entity.admistrativo.parceiros.Pessoa;

@Repository("itemLpuDao")
public class ItemLpuDao extends HibernateDaoGenerico<ItemLpu, Long> {

	public ItemLpuDao() {
		super();
	}

	@SuppressWarnings("unchecked")
	public List<ItemLpu> buscarPorUnidadeServilogi(final Unidade unidade) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.unidadeServilogi = :unidade");
		q.setParameter("unidade", unidade);
		return q.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<ItemLpu> buscarPorLpu(final Lpu lpu) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.lpu = :lpu");
		q.setParameter("lpu", lpu);
		return q.list();
	}


	public Boolean removerPorLpu(final Lpu lpu){
		Query q = sessionFactory.getCurrentSession().createSQLQuery("DELETE FROM ItemLpu WHERE lpu = :lpu");
		q.setParameter("lpu", lpu);
		int i = q.executeUpdate();
		if(i>0){
			return true;
		}else{
			return false;
		}
	}
	
	public Boolean remover(final ItemLpu itemLpu){
		Query q = sessionFactory.getCurrentSession().createSQLQuery("DELETE FROM ItemLpu WHERE id = :id");
		q.setParameter("id", itemLpu.getId());
		int i = q.executeUpdate();
		if(i>0){
			return true;
		}else{
			return false;
		}
	}

}

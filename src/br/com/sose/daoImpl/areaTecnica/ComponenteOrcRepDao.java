package br.com.sose.daoImpl.areaTecnica;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.sose.daoImpl.HibernateDaoGenerico;
import br.com.sose.entity.orcamento.Orcamento;
import br.com.sose.entity.orcrepGenerico.ComponenteOrcRep;
import br.com.sose.entity.orcrepGenerico.RequisicaoComponente;
import br.com.sose.entity.reparo.Reparo;

@Repository("componenteOrcRepDao")
public class ComponenteOrcRepDao extends HibernateDaoGenerico<ComponenteOrcRep, Long> {

	public ComponenteOrcRepDao() {
		super();
	}

	@SuppressWarnings("unchecked")
	public List<ComponenteOrcRep> listarComponentesPosicaoPorRequisicao(final RequisicaoComponente requisicao) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.requisicao = :requisicao");
		q.setParameter("requisicao", requisicao);
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<ComponenteOrcRep> listarComponentesPosicaoPorReparo(final Reparo reparo) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.reparo = :reparo");
		q.setParameter("reparo", reparo);
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<ComponenteOrcRep> listarComponentesPosicaoPorOrcamento(final Orcamento orcamento) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.orcamento = :orcamento");
		q.setParameter("orcamento", orcamento);
		return q.list();
	}

	public Integer remover(final ComponenteOrcRep componenteOrcRep){
		//		
		//			
		//				Query q = sessionFactory.getCurrentSession().createNativeQuery("DELETE FROM ComponenteOrcRep WHERE id = :id");
		//				q.setParameter("id", componenteOrcRep.getId());
		//				return q.executeUpdate();
		//			}
		//		});
		//		return (Integer) res;
		return null;
	}

}

package br.com.sose.daoImpl.estoque;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import org.hibernate.Query;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.stereotype.Repository;

import br.com.sose.daoImpl.HibernateDaoGenerico;
import br.com.sose.daoImpl.JpaDao;
import br.com.sose.entity.admistrativo.Atividade;
import br.com.sose.entity.estoque.DevolucaoComponente;
import br.com.sose.entity.orcamento.Orcamento;
import br.com.sose.entity.orcrepGenerico.RequisicaoComponente;
import br.com.sose.entity.proposta.Proposta;
import br.com.sose.entity.reparo.Reparo;

@Repository("devolucaoComponenteDao")
public class DevolucaoComponenteDao extends HibernateDaoGenerico<DevolucaoComponente, Long> {

	public DevolucaoComponenteDao() {
		super();
	}

	@SuppressWarnings("unchecked")
	public List<DevolucaoComponente> listarDevolucaoComponente() {
		StringBuilder sb = new StringBuilder("SELECT h FROM "+ entityClass.getName() + " h WHERE h.condicao <> 'Perdido' AND h.recebidoEm IS NULL ORDER BY h.devolvidoPor");
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<DevolucaoComponente> listarDevolucaoComponenteEstoque() {
		StringBuilder sb = new StringBuilder("SELECT h FROM "+ entityClass.getName() + " h WHERE h.condicao <> 'Perdido'  ORDER BY h.recebidoEm");
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<DevolucaoComponente> listarDevolucaoComponentePorCondicao(final String condicao) {
		StringBuilder sb = new StringBuilder("SELECT h FROM "+ entityClass.getName() + " h WHERE h.condicao = :condicao ORDER BY h.recebidoEm");
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());
		query.setParameter("condicao", condicao);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<DevolucaoComponente> listarComponentesPosicaoPorRequisicao(final RequisicaoComponente requisicao) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.requisicao = :requisicao");
		q.setParameter("requisicao", requisicao);
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<DevolucaoComponente> listarComponentesPosicaoPorReparo(final Reparo reparo) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.reparo = :reparo");
		q.setParameter("reparo", reparo);
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<DevolucaoComponente> listarComponentesPosicaoPorOrcamento(final Orcamento orcamento) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.orcamento = :orcamento");
		q.setParameter("orcamento", orcamento);
		return q.list();
	}

	public Integer remover(final DevolucaoComponente devolucaoComponente){
		//		
		//			
		//				Query q = sessionFactory.getCurrentSession().createNativeQuery("DELETE FROM DevolucaoComponente WHERE id = :id");
		//				q.setParameter("id", devolucaoComponente.getId());
		//				return q.executeUpdate();
		//			}
		//		});
		//		return (Integer) res;
		return null;
	}

}

package br.com.sose.daoImpl.faturamento;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.sose.daoImpl.HibernateDaoGenerico;
import br.com.sose.entity.faturamento.Faturamento;
import br.com.sose.entity.recebimento.OrdemServico;

@Repository("faturamentoDao")
public class FaturamentoDao extends HibernateDaoGenerico<Faturamento, Long> {

	public FaturamentoDao() {
		super();
	}

	@SuppressWarnings("unchecked")
	public List<Faturamento> listarPreFaturas() {
		StringBuilder sb = new StringBuilder("SELECT h FROM "+ entityClass.getName() + " h WHERE h.dataCriacaoFatura IS NULL ORDER BY h.nome");
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<Faturamento> listarFaturas() {
		StringBuilder sb = new StringBuilder("SELECT h FROM "+ entityClass.getName() + " h WHERE h.dataCriacaoFatura IS NOT NULL ORDER BY h.nome");
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<Faturamento> listarFaturas(final String status) {
		StringBuilder sb = new StringBuilder("SELECT h FROM "+ entityClass.getName() + " h WHERE h.dataCriacaoFatura IS NOT NULL AND h.statusString = :status ORDER BY h.dataCriacaoFatura DESC");
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());
		query.setParameter("status", status);
		return query.list();
	}
	
//	Feito por Nik
	@SuppressWarnings("unchecked")
	public List<Faturamento> listarFaturasNaoFinalizadas(final Date dataInicio, final Date dataFim) {
		String status = "nao finalizada";//  --> verificar o texto certo
		StringBuilder sb = new StringBuilder("SELECT h FROM "+ entityClass.getName() + " h WHERE h.dataCriacaoFatura IS NOT NULL AND h.statusString = :status ORDER BY h.dataCriacaoFatura DESC");
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());
		query.setParameter("status", status);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public Faturamento buscarPorId(final Long id) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h " +
				"LEFT JOIN FETCH h.listaOrdemServico WHERE h.id=:id");
		q.setParameter("id", id);
		Faturamento r = (Faturamento)q.uniqueResult();
		return r;
	}
	
	public Boolean remover(final Faturamento faturamento){
		Query q = sessionFactory.getCurrentSession().createSQLQuery("DELETE FROM Faturamento WHERE id = :id");
		q.setParameter("id", faturamento.getId());
		int i = q.executeUpdate();
		if(i>0){
			return true;
		}else{
			return false;
		}
	}

}

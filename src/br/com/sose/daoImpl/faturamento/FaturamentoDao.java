package br.com.sose.daoImpl.faturamento;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.sose.daoImpl.HibernateDaoGenerico;
import br.com.sose.entity.admistrativo.parceiros.Pessoa;
import br.com.sose.entity.faturamento.Faturamento;
import br.com.sose.entity.recebimento.OrdemServico;
import br.com.sose.status.faturamento.NaoIniciado;
import br.com.sose.utils.DateUtils;

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
	

	
	//Feito por NIk
		@SuppressWarnings("unchecked")
		public List<Faturamento> listarFaturasFinalizadasPorDataECliente(final Pessoa cliente, final Date de, final Date ate) {
			StringBuilder sb = new StringBuilder("SELECT h FROM "+ entityClass.getName() + " h LEFT JOIN FETCH h.listaOrdemServico WHERE (h.dataPagamentoFatura IS NOT NULL) AND (h.cliente = :cliente) AND (h.dataPagamentoFatura BETWEEN :dataDe AND :dataAte) ORDER BY h.dataPagamentoFatura");
			
			Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());

			query.setParameter("cliente", cliente);

			if(de != null){
				query.setParameter("dataDe", de);
			}else{
				query.setParameter("dataDe", new Date(0));
			}

			if(ate != null){
				query.setParameter("dataAte", DateUtils.nextDay(ate));
			}else{
				query.setParameter("dataAte", DateUtils.nextDay(new Date()));
			}
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

package br.com.sose.daoImpl.compra;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.sose.daoImpl.HibernateDaoGenerico;
import br.com.sose.entity.admistrativo.Atividade;
import br.com.sose.entity.admistrativo.Componente;
import br.com.sose.entity.compra.Cotacao;

@Repository("cotacaoDao")
public class CotacaoDao extends HibernateDaoGenerico<Cotacao, Long> {

	public CotacaoDao() {
		super();
	}

	@SuppressWarnings("unchecked")
	public List<Cotacao> listarCotacaoPorComponente(final Componente componente) {
		StringBuilder sb = new StringBuilder("SELECT h FROM "+ entityClass.getName() + " h WHERE h.componente = :componente");
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());
		query.setParameter("componente", componente);
		return query.list();
	}
	
	public Boolean remover(final Cotacao cotacao){
		Query q = sessionFactory.getCurrentSession().createSQLQuery("DELETE FROM Cotacao WHERE id = :id");
		q.setParameter("id", cotacao.getId());
		int i = q.executeUpdate();
		if(i>0){
			return true;
		}else{
			return false;
		}
	}
}

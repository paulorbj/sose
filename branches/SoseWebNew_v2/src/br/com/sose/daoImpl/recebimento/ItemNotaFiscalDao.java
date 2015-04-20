package br.com.sose.daoImpl.recebimento;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.sose.daoImpl.HibernateDaoGenerico;
import br.com.sose.entity.recebimento.ItemNotaFiscal;
import br.com.sose.entity.recebimento.NotaFiscal;
import br.com.sose.entity.recebimento.OrdemServico;

@Repository("itemNotaFiscalDao")
public class ItemNotaFiscalDao extends HibernateDaoGenerico<ItemNotaFiscal, Long> {

	public ItemNotaFiscalDao() {
		super();
	}
	
	@SuppressWarnings("unchecked")
	public ItemNotaFiscal buscarPorId(final Long id) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h LEFT JOIN FETCH h.ordensServico  WHERE h.id=:id");
		q.setParameter("id", id);
		ItemNotaFiscal r = (ItemNotaFiscal)q.uniqueResult();
		return r;
	}

	@SuppressWarnings("unchecked")
	public List<ItemNotaFiscal> buscarPorNotaFiscal(final NotaFiscal notaFiscal) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT NEW br.com.sose.entity.recebimento.ItemNotaFiscal(" +
				"h.id," +
				"h.nome," +
				"h.quantidade," +
				"h.codigo," +
				"h.ncm," +
				"h.cfop," +
				"h.cst," +
				"h.unidadeMedida," +
				"h.valorUnitario," +
				"u.nome," +
				"l.unidade" +
				") FROM "+ entityClass.getName() + " h " +
				"LEFT JOIN  h.unidade u " +
				"LEFT JOIN  h.lpu l " +
				"WHERE h.notaFiscal.id=:notaFiscal");
		q.setParameter("notaFiscal", notaFiscal.getId());
		List<ItemNotaFiscal> r = (List<ItemNotaFiscal>)q.list();
		return r;

	}

}

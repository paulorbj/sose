package br.com.sose.daoImpl.proposta;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.sose.daoImpl.HibernateDaoGenerico;
import br.com.sose.entity.proposta.ItemProposta;
import br.com.sose.entity.proposta.Proposta;
import br.com.sose.entity.recebimento.OrdemServico;

@Repository("itemPropostaDao")
public class ItemPropostaDao extends HibernateDaoGenerico<ItemProposta, Long> {

	public ItemPropostaDao() {
		super();
	}

	@SuppressWarnings("unchecked")
	public ItemProposta buscarPorOrdemServico(final OrdemServico ordemServico) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.ordemServico=:ordemServico AND h.dataAprovacao IS NOT NULL AND h.dataLiberacao IS NOT NULL");
		q.setParameter("ordemServico", ordemServico);
		ItemProposta r = (ItemProposta)q.uniqueResult();
		return r;
	}
	
	@SuppressWarnings("unchecked")
	public ItemProposta buscarPorOrdemServicoAprovado(final OrdemServico ordemServico) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.ordemServico=:ordemServico AND h.dataAprovacao IS NOT NULL AND h.dataLiberacao IS NOT NULL AND h.isAprovado = true");
		q.setParameter("ordemServico", ordemServico);
		ItemProposta r = (ItemProposta)q.uniqueResult();
		return r;
	}

	@SuppressWarnings("unchecked")
	public ItemProposta buscarPorOrdemServico(final OrdemServico ordemServico, final Proposta proposta) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.ordemServico.id=:ordemServico AND h.proposta.id=:proposta AND h.dataLiberacao IS NOT NULL");
		// h.dataAprovacao IS NOT NULL AND
		q.setParameter("ordemServico", ordemServico.getId());
		q.setParameter("proposta", proposta.getId());
		ItemProposta r = (ItemProposta)q.uniqueResult();
//		sessionFactory.getCurrentSession().close();
		return r;
	}

	@SuppressWarnings("unchecked")
	public ItemProposta buscarPorOS(final OrdemServico ordemServico) {			
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.ordemServico=:ordemServico");
		q.setParameter("ordemServico", ordemServico);
		ItemProposta r = (ItemProposta)q.uniqueResult();
		return r;
	}
	
	@SuppressWarnings("unchecked")
	public List<ItemProposta> listarPorProposta(final Proposta proposta) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.proposta.id=:proposta ");
		q.setParameter("proposta", proposta.getId());
		List<ItemProposta> r = (List<ItemProposta>)q.list();
		return r;
	}

}

package br.com.sose.daoImpl.estoque.avaya;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.sose.daoImpl.HibernateDaoGenerico;
import br.com.sose.entity.estoque.avaya.ItemEstoqueAvaya;

@Repository("itemEstoqueAvayaDao")
public class ItemEstoqueAvayaDao extends HibernateDaoGenerico<ItemEstoqueAvaya, Long> {

	public ItemEstoqueAvayaDao() {
		super();
	}

	@SuppressWarnings("unchecked")
	public List<ItemEstoqueAvaya> listarOrdemServicoPorStatus(final String status) {


		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.statusString = :status");
		q.setParameter("status", status);
		return q.list();

	}

	@SuppressWarnings("unchecked")
	public List<ItemEstoqueAvaya> listar() {
		//
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT NEW br.com.sose.entity.estoque.avaya.ItemEstoqueAvaya(" +
				"h.id," +
				"h.dataOperacao," +
				"h.posicao," +
				"h.statusString," +
				"u.nome," +
				"osOrig.numeroOrdemServico," +
				"osOrig.serieFabricante," +
				"osOrig.serieCliente," +
				"nfOrig.numero," +
				"osSubst.numeroOrdemServico," +
				"osSubst.serieFabricante," +
				"osSubst.serieCliente," +
				"nfSubst.numero," +
				"uOp.usuario," +
				"osOrig.id," +
				"osSubst.id) FROM "+ entityClass.getName() + " h " +
				"LEFT JOIN h.ordemServicoOriginal osOrig " +
				"LEFT JOIN osOrig.notaFiscal nfOrig " +
				"LEFT JOIN osOrig.unidade u " +
				"LEFT JOIN h.ordemServicoSubstituida osSubst " +
				"LEFT JOIN osSubst.notaFiscal nfSubst " +
				"LEFT JOIN h.operacaoRealizadaPor uOp " +
				" WHERE " +
				" h.statusString = 'Estocado' OR " +
				" h.statusString = 'Disponível para estoque' OR " +
				" ((h.statusString = 'Retirado' OR h.statusString = 'Reposto' OR h.statusString = 'Substituído') AND (h.ordemServicoOriginal.notaFiscalSaida IS NULL) )");

		return q.list();

	}

	@SuppressWarnings("unchecked")
	public ItemEstoqueAvaya buscarPorId(final Long id) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h " +
				"WHERE h.id=:id");
		q.setParameter("id", id);
		ItemEstoqueAvaya r = (ItemEstoqueAvaya)q.uniqueResult();
		return r;
	}


}

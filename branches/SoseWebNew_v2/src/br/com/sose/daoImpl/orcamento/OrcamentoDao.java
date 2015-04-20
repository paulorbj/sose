package br.com.sose.daoImpl.orcamento;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.sose.daoImpl.HibernateDaoGenerico;
import br.com.sose.entity.admistrativo.Unidade;
import br.com.sose.entity.orcamento.Orcamento;
import br.com.sose.to.OrcRepGenericoTO;

@Repository("orcamentoDao")
public class OrcamentoDao extends HibernateDaoGenerico<Orcamento, Long> {

	public OrcamentoDao() {
		super();
	}


	@SuppressWarnings("unchecked")
	public Orcamento buscarPorId(final Long id) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT orc FROM "+ entityClass.getName() + " orc " +
				"LEFT JOIN FETCH orc.ordemServico os " +
				"LEFT JOIN FETCH os.notaFiscal nf " +
				"LEFT JOIN FETCH os.unidade u " +
				"LEFT JOIN FETCH u.fabricante f " +
				"LEFT JOIN FETCH u.equipamento e " +
				"LEFT JOIN FETCH u.laboratorio l " +
				"LEFT JOIN FETCH u.prestadorServicoExterno pse " +
				"LEFT JOIN FETCH orc.tecnicoResponsavel tr " +
				"LEFT JOIN FETCH tr.perfil p " +
				"LEFT JOIN FETCH orc.listaRequisicao lr " +
				"LEFT JOIN FETCH orc.listaAtividade la " +
				"LEFT JOIN FETCH orc.listaComponente lc " +
				"LEFT JOIN FETCH orc.listaDefeito ld WHERE orc.id=:id");
		q.setParameter("id", id);
		Orcamento r = (Orcamento)q.uniqueResult();
		return r;
	}

	@SuppressWarnings("unchecked")
	public List<OrcRepGenericoTO> listarOrcamentoOtimizado() {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT NEW br.com.sose.to.OrcRepGenericoTO('Orçamento'," +
				"h.statusString, t.usuario, os.numeroOrdemServico, ospai.numeroOrdemServico, " +
				"c.nomeSistema, u.nome, nf.numero, os.serieFabricante, os.serieCliente,l.nome," +
				"h.prioridade, nf.dataChegada, h.dataEntrada, h.laudoTecnicoAprovado,h.laudoTecnicoReprovado," +
				"h.propostaReprovada,h.rejeitadoPeloLider,h.id,t.id,h.dataLimite,c.prazoDevolucao,os.bloqueado,os.caseAvaya,os.clienteAvaya,h.condicao, l.id, h.componentePendente)  FROM "+ entityClass.getName() + " h " +
				"LEFT JOIN  h.tecnicoResponsavel t " +
				"LEFT JOIN  h.ordemServico os " +
				"LEFT JOIN  os.cliente c " +
				"LEFT JOIN  os.unidadePai ospai " +
				"LEFT JOIN  os.unidade u " +
				"LEFT JOIN  u.laboratorio l " +
				"LEFT JOIN  os.notaFiscal nf " +
				"WHERE h.statusString != 'Finalizado' ORDER BY h.dataEntrada  ");
		return q.list();
	}

	public List<Orcamento> listarHistoricoReparo(final Unidade unidade, final String serieFabricante) {
		StringBuilder sb = new StringBuilder("SELECT h FROM "+ entityClass.getName() + " h WHERE (h.ordemServico.unidade  = :unidade)");
		if(serieFabricante != null){
			sb.append(" AND (h.ordemServico.serieFabricante LIKE :paramSerieFabricante) ");
		}
		//				if(serieCliente != null){
		//					sb.append(" AND (h.ordemServico.serieCliente LIKE :paramSerieCliente) ");
		//				}

		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());

		query.setParameter("unidade", unidade);
		if(serieFabricante != null){
			query.setParameter("paramSerieFabricante", serieFabricante);				}
		//				if(serieCliente != null){
		//					query.setParameter("paramSerieCliente", serieCliente);				}

		return query.list();

	}

}

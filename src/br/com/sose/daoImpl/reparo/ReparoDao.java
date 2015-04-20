package br.com.sose.daoImpl.reparo;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.sose.daoImpl.HibernateDaoGenerico;
import br.com.sose.entity.admistrativo.Unidade;
import br.com.sose.entity.reparo.Reparo;
import br.com.sose.to.OrcRepGenericoTO;

@Repository("reparoDao")
public class ReparoDao extends HibernateDaoGenerico<Reparo, Long> {

	public ReparoDao() {
		super();
	}

	@SuppressWarnings("unchecked")
	public List<OrcRepGenericoTO> listarReparoOtimizado() {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT NEW br.com.sose.to.OrcRepGenericoTO('Reparo'," +
				"h.statusString, t.usuario, os.numeroOrdemServico, ospai.numeroOrdemServico, " +
				"c.nomeSistema, u.nome, nf.numero, os.serieFabricante, os.serieCliente,l.nome," +
				"h.prioridade, nf.dataChegada, h.dataEntrada, h.laudoTecnicoAprovado,h.laudoTecnicoReprovado," +
				"h.propostaAprovada,h.propostaReprovada,h.orcamentoDiferenciadoRejeitado,h.id,t.id,h.dataLimite,c.prazoDevolucao,os.bloqueado,os.caseAvaya,os.clienteAvaya, h.condicao, l.id, h.componentePendente, h.criadoFromOrcamento)  FROM "+ entityClass.getName() + " h " +
				"LEFT JOIN  h.tecnicoResponsavel t " +
				"LEFT JOIN  h.ordemServico os " +
				"LEFT JOIN  os.cliente c " +
				"LEFT JOIN  os.unidadePai ospai " +
				"LEFT JOIN  os.unidade u " +
				"LEFT JOIN  u.laboratorio l " +
				"LEFT JOIN  os.notaFiscal nf " +
				"WHERE (os.notaFiscalSaida IS NULL AND UPPER(c.nomeRazaoSocial) NOT LIKE '%AVAYA%') OR (h.statusString <> 'Finalizado' AND UPPER(c.nomeRazaoSocial) LIKE '%AVAYA%') ORDER BY h.dataEntrada");
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<Reparo> buscarPorOrdemServico(final Long id) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.ordemServico=:ordemServico");
		q.setParameter("id", id);
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public Reparo buscarPorId(final Long id) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h " +
				"LEFT JOIN FETCH h.ordemServico os " +
				"LEFT JOIN FETCH os.unidadePai up " +
				"LEFT JOIN FETCH os.cliente c " +
				"LEFT JOIN FETCH os.notaFiscal nf " +
				"LEFT JOIN FETCH os.unidade u " +
				"LEFT JOIN FETCH u.fabricante f " +
				"LEFT JOIN FETCH u.equipamento e " +
				"LEFT JOIN FETCH u.laboratorio l " +
				"LEFT JOIN FETCH u.prestadorServicoExterno pse " +
				"LEFT JOIN FETCH h.tecnicoResponsavel tr " +
				"LEFT JOIN FETCH tr.perfil p " +
				"LEFT JOIN FETCH h.listaAtividade " +
				"LEFT JOIN FETCH h.listaComponente " +
				"LEFT JOIN FETCH h.listaRequisicao " +
				"LEFT JOIN FETCH h.listaDefeito WHERE h.id=:id");
		q.setParameter("id", id);
		Reparo r = (Reparo)q.uniqueResult();
		return r;
	}
	
	@SuppressWarnings("unchecked")
	public Reparo buscarPorIdAtribuirPrioridade(final Long id) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h " +
				"LEFT JOIN FETCH h.ordemServico os " +
				"LEFT JOIN FETCH os.unidadePai up " +
				"LEFT JOIN FETCH os.cliente c " +
				"LEFT JOIN FETCH os.notaFiscal nf " +
				"LEFT JOIN FETCH os.unidade u " +
				"LEFT JOIN FETCH u.fabricante f " +
				"LEFT JOIN FETCH u.equipamento e " +
				"LEFT JOIN FETCH u.laboratorio l " +
				"LEFT JOIN FETCH u.prestadorServicoExterno pse " +
				"LEFT JOIN FETCH h.tecnicoResponsavel tr " +
				"LEFT JOIN FETCH tr.perfil p " +
				"LEFT JOIN FETCH h.listaAtividade " +
				"LEFT JOIN FETCH h.listaComponente " +
				"LEFT JOIN FETCH h.listaRequisicao " +
				"LEFT JOIN FETCH h.listaDefeito WHERE h.id=:id");
		q.setParameter("id", id);
		Reparo r = (Reparo)q.uniqueResult();
		return r;
	}

	@SuppressWarnings("unchecked")
	public List<Reparo> listarHistoricoReparo(final Unidade unidade, final String serieFabricante) {
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

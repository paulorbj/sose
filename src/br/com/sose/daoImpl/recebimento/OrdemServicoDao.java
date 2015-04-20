package br.com.sose.daoImpl.recebimento;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.stat.SessionStatistics;
import org.hibernate.stat.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.sose.daoImpl.HibernateDaoGenerico;
import br.com.sose.entity.admistrativo.Unidade;
import br.com.sose.entity.admistrativo.parceiros.Pessoa;
import br.com.sose.entity.consulta.Consulta;
import br.com.sose.entity.expedicao.NotaFiscalRemessa;
import br.com.sose.entity.recebimento.NotaFiscal;
import br.com.sose.entity.recebimento.OrdemServico;
import br.com.sose.to.ConsultaTO;
import br.com.sose.utils.DateUtils;

@Repository("ordemServicoDao")
public class OrdemServicoDao extends HibernateDaoGenerico<OrdemServico, Long> {

	private Logger logger = Logger.getLogger(this.getClass());

	public OrdemServicoDao() {
		super();
	}

	@SuppressWarnings("unchecked")
	public OrdemServico buscarPorId2(final Long id) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h LEFT JOIN FETCH h.unidade u  WHERE h.id=:id");
		q.setParameter("id", id);
		OrdemServico r = (OrdemServico)q.uniqueResult();
		return r;
	}

	@SuppressWarnings("unchecked")
	public List<OrdemServico> listarOrdemServicoPorNotaFiscal(final NotaFiscal nf) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h  WHERE h.notaFiscal = :notaFiscal");
		q.setParameter("notaFiscal", nf);
		List<OrdemServico> r = (List<OrdemServico>)q.list();
		return r;
	}

	@SuppressWarnings("unchecked")
	public OrdemServico buscarPorId(final Long id) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h " +
				"LEFT JOIN FETCH h.unidade u " +
				"LEFT JOIN FETCH h.itemNotaFiscal inf " +
				"LEFT JOIN FETCH u.equipamento e " +
				"LEFT JOIN FETCH u.fabricante f " +
				"LEFT JOIN FETCH u.laboratorio l " +
				"LEFT JOIN FETCH h.placasFilhas pf " +
				"LEFT JOIN FETCH pf.orcamento pfOrc " +
				"LEFT JOIN FETCH pf.reparo pfRep " +
				"LEFT JOIN FETCH pfOrc.listaDefeito " +
				"LEFT JOIN FETCH pfRep.listaDefeito " +
				"LEFT JOIN FETCH h.orcamento orc " +
				"LEFT JOIN FETCH h.reparo rep " +
				"LEFT JOIN FETCH orc.listaDefeito " +
				"LEFT JOIN FETCH rep.listaDefeito WHERE h.id=:id");
		q.setParameter("id", id);
		OrdemServico r = (OrdemServico)q.uniqueResult();
		sessionFactory.getCurrentSession().flush();
		sessionFactory.getCurrentSession().clear();
		return r;
	}
	
	@SuppressWarnings("unchecked")
	public OrdemServico adicionarOrdemServicoFaturamento(final Long idOS, final Long idFaturamento) {
		Query q = sessionFactory.getCurrentSession().createQuery("update sosedb_migrado.ordemservico set faturamento_id=:idFaturamento where id=:idOS");
		q.setParameter("idOS", idOS);
		q.setParameter("idFaturamento", idFaturamento);
		OrdemServico r = (OrdemServico)q.uniqueResult();
		return r;
	}

	@SuppressWarnings("unchecked")
	public OrdemServico buscarPorIdCriarProposta(final Long id) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h " +
				"LEFT JOIN FETCH h.cliente c " +
				"LEFT JOIN FETCH h.orcamentoDiferenciado od " +
				" WHERE h.id=:id");
		q.setParameter("id", id);
		OrdemServico r = (OrdemServico)q.uniqueResult();
		return r;
	}

	@SuppressWarnings("unchecked")
	public OrdemServico montarConjunto(final Long id) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h " +
				"LEFT JOIN FETCH h.unidadePai u " +
				"LEFT JOIN FETCH h.reparo rep " +
				"LEFT JOIN FETCH h.orcamento orc " +
				"WHERE h.id=:id");
		q.setParameter("id", id);
		OrdemServico r = (OrdemServico)q.uniqueResult();

		Query q2 = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h " +
				"LEFT JOIN FETCH h.unidadePai u " +
				"LEFT JOIN FETCH h.reparo rep " +
				"LEFT JOIN FETCH h.orcamento orc " +
				"WHERE h.unidadePai.id=:id");
		q2.setParameter("id", id);
		List<OrdemServico> pFilhas = q2.list();

		r.setPlacasFilhas(new HashSet<OrdemServico>());
		r.getPlacasFilhas().addAll(pFilhas);
		return r;

	}

	@SuppressWarnings("unchecked")
	public OrdemServico buscarPorIdSimples(final Long id) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h  WHERE h.id=:id");
		q.setParameter("id", id);
		OrdemServico r = (OrdemServico)q.uniqueResult();
		sessionFactory.getCurrentSession().flush();
		sessionFactory.getCurrentSession().clear();
		return r;
	}

	@SuppressWarnings("unchecked")
	public OrdemServico buscarComPlacasFilhas(final Long id) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h " +
				"LEFT JOIN FETCH h.placasFilhas pf WHERE h.id=:id");
		q.setParameter("id", id);
		OrdemServico r = (OrdemServico)q.uniqueResult();
		return r;
	}

	@SuppressWarnings("unchecked")
	public OrdemServico buscarConsultaId(final Long id) {	
		System.out.println("QUERY DA CONSULTA - INICIO");

		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h " +
				"LEFT JOIN FETCH h.unidade u " +
				"LEFT JOIN FETCH u.fabricante f " +
				"LEFT JOIN FETCH u.equipamento e " +
				"LEFT JOIN FETCH u.laboratorio l " +
				"LEFT JOIN FETCH h.notaFiscal nf " +
				"LEFT JOIN FETCH nf.cliente c " +
				"LEFT JOIN FETCH h.orcamento orc " +
				"LEFT JOIN FETCH h.reparo rep " +
				"LEFT JOIN FETCH h.proposta p " +
				"LEFT JOIN FETCH p.contato cont " +
				"LEFT JOIN FETCH h.notaFiscalSaida nfs " +
				"LEFT JOIN FETCH nfs.cliente nfsc " +
				"LEFT JOIN FETCH nfs.transportador nfst " +
				"WHERE h.id=:id");
		q.setParameter("id", id);
		OrdemServico r = (OrdemServico)q.uniqueResult();
		System.out.println("QUERY DA CONSULTA - FIM");
		SessionStatistics sessionStats = sessionFactory.getCurrentSession().getStatistics();
		Statistics stats = this.sessionFactory.getStatistics();
		System.out.println(stats);
		return r;
	}

	@SuppressWarnings("unchecked")
	public OrdemServico buscarPorNumeroOrdemServico(final String numero) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h " +
				"LEFT JOIN FETCH h.placasFilhas pf " +
				"LEFT JOIN FETCH pf.orcamento pfOrc " +
				"LEFT JOIN FETCH pf.reparo pfRep " +
				"LEFT JOIN FETCH pfOrc.listaDefeito " +
				"LEFT JOIN FETCH pfRep.listaDefeito " +
				"LEFT JOIN FETCH h.orcamento orc " +
				"LEFT JOIN FETCH h.reparo rep " +
				"LEFT JOIN FETCH orc.listaDefeito " +
				"LEFT JOIN FETCH rep.listaDefeito WHERE h.numeroOrdemServico=:numero");
		q.setParameter("numero", numero);
		OrdemServico r = (OrdemServico)q.uniqueResult();
		return r;

	}
	
	@SuppressWarnings("unchecked")
	public OrdemServico buscarPorNumeroOrdemServicoSimples(final String numero) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.numeroOrdemServico = :numero");
		q.setParameter("numero", numero);
		OrdemServico r = (OrdemServico)q.uniqueResult();
		return r;

	}
	
	public String observacaoConsolidadaConsulta(final OrdemServico ordemservico) throws Exception {
		final StringBuilder  stringSql = new StringBuilder("SELECT concat('OBS: ', group_concat(h.texto separator ', OBS: ')) FROM observacao h where ref_ordem_servico_id = :osId and escopo = 3 and origem = 'Consulta' group by h.ref_ordem_servico_id" );
		Query q = sessionFactory.getCurrentSession().createSQLQuery(stringSql.toString());
		q.setParameter("osId", ordemservico.getId());
		String obsRetorno = (String)q.uniqueResult();
		return obsRetorno;
	}

	

	@SuppressWarnings("unchecked")
	public List<ConsultaTO> realizarConsultaOrdemServico(final Consulta consulta) throws Exception {
		try{
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  

			final StringBuilder  stringSql = new StringBuilder("select " +
					"os.numero_ordem_servico as nOS, " +
					"os.numeroordemservicopai, " +
					"os.unidade_pai_id, " +
					"os.garantia, " +
					"os.lpu_id, " + 
					"p.tipo, " +
					"p.numero, " +
					"os.status, " +
					"rep.condicao as repCondicao, " +
					"rep.dt_fim as repDtFim, " +
					"orc.condicao as orcCondicao, " +
					"orc.dt_fim as orcDtFim, " +
					"os.nomecliente, " +
					"os.nomeunidade, " +
					"os.numero_serie_fabricante as nsFabricante, " +
					"os.numero_serie_cliente as nsCliente, " +
					"notafs.numero as nNF, " +
					"notaf.numero as nNFS, " +
					"os.nomelaboratorio, " +
					"(select isAprovado from itemproposta itp where ordem_servico_id = os.id and proposta_id = p.id AND dataAprovacao IS NOT NULL) as isAprovado, " +
					"(select dataaprovacao from itemproposta itp where ordem_servico_id = os.id and proposta_id = p.id AND dataAprovacao IS NOT NULL) as dataAprovacao, " +
					"os.caseavaya, " +
					"os.clienteavaya, " +					
					"(select statusString from itemestoqueavaya where os_original_id = os.id and os_original_id is not null) as statusEstoque, " +
					"(select posicao from itemestoqueavaya where os_original_id = os.id and os_original_id is not null) as estoquePosicao, " +
					"(SELECT concat('OBS: ', group_concat(h.texto separator ', OBS: ')) FROM observacao h where ref_nota_fiscal_id = os.nota_fiscal_id and escopo = 3 group by h.ref_nota_fiscal_id) as obsNotaFiscal, " +
					"(SELECT concat('OBS: ', group_concat(h.texto separator ', OBS: ')) FROM observacao h where ref_faturamento_id = os.faturamento_id and escopo = 3 group by h.ref_faturamento_id) as obsFaturamento, " +
					"(SELECT concat('OBS: ', group_concat(h.texto separator ', OBS: ')) FROM observacao h where ref_nf_saida_id = os.nota_fiscal_saida_id and escopo = 3 group by h.ref_nf_saida_id) as obsNotaFiscalSaida, " +
					"(SELECT concat('OBS: ', group_concat(h.texto separator ', OBS: ')) FROM observacao h where ref_orcamento_id = os.orcamento_id and escopo = 3 group by h.ref_orcamento_id) as obsOrcamento, " +
					"(SELECT concat('OBS: ', group_concat(h.texto separator ', OBS: ')) FROM observacao h where ref_ordem_servico_id = os.id and escopo = 3 group by h.ref_ordem_servico_id) as obsOrdemServico, " +
					"(SELECT concat('OBS: ', group_concat(h.texto separator ', OBS: ')) FROM observacao h where ref_reparo_id = os.reparo_id and escopo = 3 group by h.ref_reparo_id) as obsReparo, " +
					"(SELECT concat('OBS: ', group_concat(h.texto separator ', OBS: ')) FROM observacao h where ref_proposta_id = os.proposta_id and escopo = 3 group by h.ref_proposta_id) as obsProposta, " +
					"notaf.data_chegada, " +
					"orc.id as idOrc, " +	
					"rep.id as idRep, " +
					"os.id as idOs, " +	
					"notaf.data_nota_fiscal, " +
					"notafs.dtEmissao, " +
					"notaf.data_chegada, " +
					"(SELECT concat('OBS: ', group_concat(h.texto separator ', OBS: ')) FROM observacao h where ref_ordem_servico_id = os.id and escopo = 3 and origem = 'Consulta' group by h.ref_ordem_servico_id) as obsConsulta " +
					"from ordemservico os " +
					"left join notafiscal notaf on os.nota_fiscal_id = notaf.id  " +
					"left join notafiscalremessa notafs on os.nota_fiscal_saida_id = notafs.id  " +
					"left join reparo rep on os.reparo_id = rep.id  " +
					"left join orcamento orc on os.orcamento_id = orc.id  " +
					"left join proposta p on os.proposta_id = p.id  ");

			stringSql.append(" WHERE os.data_abertura BETWEEN :dataDe AND :dataAte ");

			if(consulta.getNumeroOS() != null && !consulta.getNumeroOS().isEmpty()){
				stringSql.append("AND os.numero_ordem_servico = :numeroOrdemServico ");
			}

			if(consulta.getNumeroNotaFiscal() != null && !consulta.getNumeroNotaFiscal().isEmpty()){
				stringSql.append("AND notaf.numero = :numeroNotaFiscal ");
			}

			if(consulta.getNumeroNotaFiscalSaida() != null && !consulta.getNumeroNotaFiscalSaida().isEmpty()){
				stringSql.append("AND notafs.numero = :numeroNotaFiscalSaida ");
			}

			if(consulta.getCliente() != null && consulta.getCliente().getId() != 0){
				stringSql.append("AND os.cliente_id = :cliente ");
			}

			stringSql.append(" ORDER BY os.numero_ordem_servico + 0 ");

			Query q = sessionFactory.getCurrentSession().createSQLQuery(stringSql.toString());

			if(consulta.getDataDe() != null){
				q.setParameter("dataDe", consulta.getDataDe());
			}else{
				q.setParameter("dataDe", new Date(0));
			}

			if(consulta.getDataAte() != null){
				q.setParameter("dataAte", DateUtils.nextDay(consulta.getDataAte()));
			}else{
				q.setParameter("dataAte", DateUtils.nextDay(new Date()));
			}

			if(consulta.getNumeroNotaFiscal() != null && !consulta.getNumeroNotaFiscal().isEmpty()){
				q.setParameter("numeroNotaFiscal", consulta.getNumeroNotaFiscal().trim());
			}

			if(consulta.getCliente() != null && consulta.getCliente().getId() != 0){
				q.setParameter("cliente", consulta.getCliente());
			}

			if(consulta.getNumeroNotaFiscalSaida() != null && !consulta.getNumeroNotaFiscalSaida().isEmpty()){
				q.setParameter("numeroNotaFiscalSaida", consulta.getNumeroNotaFiscalSaida().trim());
			}

			if(consulta.getNumeroOS() != null && !consulta.getNumeroOS().isEmpty()){
				q.setParameter("numeroOrdemServico", consulta.getNumeroOS().trim());
			}

			SessionStatistics sessionStats = sessionFactory.getCurrentSession().getStatistics();
			Statistics stats = this.sessionFactory.getStatistics();
			System.out.println(stats);
			List<Object[]> lista = q.list();
			List<ConsultaTO> listaRetorno = converterConsultaTO(lista);
			return listaRetorno;
		}catch(Exception e){
			e.printStackTrace(); logger.error(e);
			return null;
		}
	}

	private List<ConsultaTO> converterConsultaTO(List<Object[]> lista)
	{
		ArrayList<ConsultaTO> listaRetorno = new ArrayList<ConsultaTO> ();
		if(lista == null || lista.isEmpty()){
			return new ArrayList<ConsultaTO>();
		}else{
			ConsultaTO consultaTO = null; 
			for (Object[] obj : lista){
				consultaTO = new ConsultaTO();
				if(obj[0] != null){
					consultaTO.setnOs(obj[0].toString());
				}
				if(obj[1] != null){
					consultaTO.setnOsPai(obj[1].toString());
				}
				if(obj[2] != null){
					consultaTO.setIdUnidadePai(((BigInteger)obj[2]).longValue());
				}
				if(obj[3] != null){
					consultaTO.setGarantia((Boolean)obj[3]);
				}
				if(obj[4] != null){
					consultaTO.setIdLpu(((BigInteger)obj[4]).longValue());
				}
				if(obj[5] != null){
					consultaTO.setPropostaTipo(obj[5].toString());
				}
				if(obj[6] != null){
					consultaTO.setnProposta(obj[6].toString());
				}
				if(obj[7] != null){
					consultaTO.setStatus(obj[7].toString());
				}
				if(obj[8] != null){
					consultaTO.setReparoCondicao(obj[8].toString());
				}
				if(obj[9] != null){
					consultaTO.setReparoDtFim((Date)obj[9]);
				}
				if(obj[10] != null){
					consultaTO.setOrcamentoCondicao(obj[10].toString());
				}
				if(obj[11] != null){
					consultaTO.setOrcamentoDtFim((Date)obj[11]);
				}
				if(obj[12] != null){
					consultaTO.setCliente(obj[12].toString());
				}
				if(obj[13] != null){
					consultaTO.setUnidade(obj[13].toString());
				}
				if(obj[14] != null){
					consultaTO.setnSerieFabricante(obj[14].toString());
				}
				if(obj[15] != null){
					consultaTO.setnSerieCliente(obj[15].toString());
				}
				if(obj[16] != null){
					consultaTO.setnNfSaida(obj[16].toString());
				}
				if(obj[17] != null){
					consultaTO.setnNf(obj[17].toString());
				}
				if(obj[18] != null){
					consultaTO.setLaboratorio(obj[18].toString());
				}
				if(obj[19] != null){
					if(obj[19] instanceof Integer){
						if((Integer)obj[19] == 0){
							consultaTO.setIsAprovado(false);
						}else{
							consultaTO.setIsAprovado(true);
						}
					}else if(obj[19] instanceof Boolean){
						if((Boolean)obj[19]){
							consultaTO.setIsAprovado(true);
						}else{
							consultaTO.setIsAprovado(false);
						}
					}
				}
				if(obj[20] != null){
					consultaTO.setDataAprovacao((Date)obj[20]);

				}
				if(obj[21] != null){
					consultaTO.setCaseAvaya(obj[21].toString());

				}
				if(obj[22] != null){
					consultaTO.setClienteAvaya(obj[22].toString());

				}
				if(obj[23] != null){
					consultaTO.setStatusEstoque(obj[23].toString());

				}
				if(obj[24] != null){
					consultaTO.setPosicaoEstoque(obj[24].toString());

				}
				if(obj[25] != null){
					consultaTO.setObsNotaFiscal(obj[25].toString());

				}
				if(obj[26] != null){
					consultaTO.setObsFaturamento(obj[26].toString());

				}
				if(obj[27] != null){
					consultaTO.setObsNotaFiscalSaida(obj[27].toString());

				}
				if(obj[28] != null){
					consultaTO.setObsOrcamento(obj[28].toString());

				}
				if(obj[29] != null){
					consultaTO.setObsOrdemServico(obj[29].toString());

				}
				if(obj[30] != null){
					consultaTO.setObsReparo(obj[30].toString());

				}
				if(obj[31] != null){
					consultaTO.setObsProposta(obj[31].toString());
				}
				if(obj[32] != null){
					consultaTO.setDataChegada((Date)obj[32]);
				}
				if(obj[33] != null){
					consultaTO.setIdOrcamento(((BigInteger)obj[33]).longValue());
				}
				if(obj[34] != null){
					consultaTO.setIdReparo(((BigInteger)obj[34]).longValue());
				}

				if(obj[35] != null){
					consultaTO.setId(((BigInteger)obj[35]).longValue());
				}
				
				if(obj[36] != null){
					consultaTO.setDataEntradaNF((Date)obj[36]);
				}
				
				if(obj[37] != null){
					consultaTO.setDataEmissaoNFSaida((Date)obj[37]);
				}
				
				if(obj[38] != null){
					consultaTO.setDataChegadaNF((Date)obj[38]);
				}
				
				if(obj[39] != null){
					consultaTO.setObsConsulta(obj[39].toString());
				}
				listaRetorno.add(consultaTO);
			}
		}
		return listaRetorno;
	}

	@SuppressWarnings("unchecked")
	public List<OrdemServico> buscarOSporSeries(final Pessoa cliente, final String serieFabricante, final String serieCliente, final Unidade unidade) {
		StringBuilder sb = new StringBuilder("SELECT h FROM "+ entityClass.getName() + " h WHERE (h.cliente.id  = :cliente) AND h.unidadePai IS NULL");
		if(serieFabricante != null){
			sb.append(" AND (h.serieFabricante LIKE :paramSerieFabricante) ");
		}else{
			sb.append(" AND (h.serieFabricante IS NULL OR h.serieFabricante = '') ");
		}
		if(serieCliente != null){
			sb.append(" AND (h.serieCliente LIKE :paramSerieCliente) ");
		}else{
			sb.append(" AND (h.serieCliente IS NULL OR h.serieCliente = '') ");
		}
//		if(unidade != null){
//			sb.append(" AND (h.unidade.id = :unidade) ");
//		}

		sb.append(" AND h.dataFinalizacao IS NOT NULL ORDER BY h.dataFinalizacao DESC ");

		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());

		query.setParameter("cliente", cliente.getId());
		if(serieFabricante != null){
			query.setParameter("paramSerieFabricante", serieFabricante.trim());				
		}
		if(serieCliente != null){
			query.setParameter("paramSerieCliente", serieCliente.trim());				
		}
//		if(unidade != null){
//			query.setParameter("unidade", unidade.getId());				
//		}

		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public Long buscarMaiorNumeroOrdemServico() {
		StringBuilder sb = new StringBuilder("SELECT MAX(h.id) FROM "+ entityClass.getName() + " h ");
		
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());

		return (Long)query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<OrdemServico> listarHistorico(final Unidade unidade, final String serieFabricante, final String serieCliente) {
		StringBuilder sb = new StringBuilder("SELECT h FROM "+ entityClass.getName() + " h WHERE ");
		if(serieFabricante != null){
			sb.append(" (h.serieFabricante LIKE :paramSerieFabricante) ");
		}
		if(serieCliente != null){
			sb.append(" AND (h.serieCliente LIKE :paramSerieCliente) ");
		}
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());

//		query.setParameter("unidade", unidade);
		if(serieFabricante != null){
			query.setParameter("paramSerieFabricante", serieFabricante);				
		}
		
		if(serieCliente != null){
			query.setParameter("paramSerieCliente", serieCliente);				
		}

		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<OrdemServico> listarOrdemServicoPorNotaFiscalSaida(final NotaFiscalRemessa notaFiscalSaida) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT NEW br.com.sose.entity.recebimento.OrdemServico(" +
				"o.id, " +
				"o.numeroOrdemServico, " +
				"o.serieCliente, " +
				"o.serieFabricante, " +
				"o.caseAvaya, " +
				"o.clienteAvaya, " +
				"up.numeroOrdemServico, " +
				"u.nome, " +
				"nf.numero," +
				"o.garantia," +
				"orc.condicao," +
				"rep.condicao," +
				"c.id," +
				"c.nomeSistema" +
				") FROM OrdemServico o " +
				"LEFT JOIN o.cliente c " +
				"LEFT JOIN o.unidade u " +
				"LEFT JOIN o.unidadePai up " +
				"LEFT JOIN o.notaFiscal nf " +
				"LEFT JOIN o.orcamento orc " +
				"LEFT JOIN o.reparo rep " +
				"WHERE o.notaFiscalSaida.id = :notaFiscalSaida ORDER BY o.id");
		q.setParameter("notaFiscalSaida", notaFiscalSaida.getId());
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<OrdemServico> listarOrdemFaturamentoAFaturar(final Pessoa cliente, final Date de, final Date ate) {
		StringBuilder sb = new StringBuilder("SELECT h FROM "+ entityClass.getName() + " h WHERE (h.dataFinalizacao IS NOT NULL) AND (h.cliente = :cliente) AND (h.unidadePai IS NULL) AND (h.notaFiscalSaida.dtEmissao BETWEEN :dataDe AND :dataAte) AND (h.faturamento IS NULL) ORDER BY h.numeroOrdemServico");
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
	public OrdemServico verificarDisponibilidadeNumeroOrdemServico(final String nOrdemServico) {
		StringBuilder sb = new StringBuilder("SELECT h FROM "+ entityClass.getName() + " h WHERE (h.numeroOrdemServico  = :nOrdemServico)");							
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());
		query.setParameter("nOrdemServico", nOrdemServico);
		return (OrdemServico)query.uniqueResult();

	}
	
	@SuppressWarnings("unchecked")
	public int corrigirOrdemServico(String nOSBuscada, String nOS, String nsFabricante, String nsCliente, String osCliente) {
		StringBuilder sb = new StringBuilder("update ordemservico os set os.numero_ordem_servico = :nos, os.numero_serie_fabricante = :nsf, os.numero_serie_cliente = :nsc, os.ordemservicocliente = :osc WHERE (os.numero_ordem_servico  = :nOSBuscada)");							
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
		query.setParameter("nOSBuscada", nOSBuscada);
		query.setParameter("nos", nOS);
		query.setParameter("nsf", nsFabricante);
		query.setParameter("nsc", nsCliente);
		query.setParameter("osc", osCliente);
		return query.executeUpdate();
	}
	

	public void flush(){
		sessionFactory.getCurrentSession().flush();
	}

}

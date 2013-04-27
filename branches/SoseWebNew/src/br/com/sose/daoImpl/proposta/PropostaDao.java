package br.com.sose.daoImpl.proposta;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.sose.daoImpl.HibernateDaoGenerico;
import br.com.sose.entity.admistrativo.parceiros.Pessoa;
import br.com.sose.entity.proposta.Proposta;
import br.com.sose.entity.recebimento.OrdemServico;

@Repository("propostaDao")
public class PropostaDao extends HibernateDaoGenerico<Proposta, Long> {

	public PropostaDao() {
		super();
	}

	@SuppressWarnings("unchecked")
	public List<Proposta> listarProposta() {


		Query q = sessionFactory.getCurrentSession().createQuery("SELECT o FROM Proposta o WHERE o.statusString <> 'Finalizada'");
		return q.list();

	}

	//				"(SELECT GROUP_CONCAT(DISTINCT  nf.numero SEPARATOR ',') FROM ordemservico os left join notafiscal nf on os.nota_fiscal_id = nf.id where os.proposta_id = h.id GROUP BY os.nota_fiscal_id)"+

	@SuppressWarnings("unchecked")
	public List<Proposta> listarProposta(final String status) {
		StringBuilder sb = new StringBuilder("SELECT NEW br.com.sose.entity.proposta.Proposta(" +
				"h.id," +
				"h.numero," +
				"h.tipo," +
				"h.statusString," +
				"h.dataCriacao," +
				"c.nomeSistema" +
				") FROM "+ entityClass.getName() + " h " +		
				"LEFT JOIN h.cliente c WHERE h.statusString = :status ORDER BY h.dataCriacao DESC");
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());
		query.setParameter("status", status);
		
		List<Proposta> propostaLista = query.list();
		
		String nfList = "select group_concat(distinct nf.numero ORDER BY nf.numero separator ',') from ordemservico os left join notafiscal nf on os.nota_fiscal_id = nf.id where os.proposta_id = :propostaid";
		Query queryNF = sessionFactory.getCurrentSession().createSQLQuery(nfList);
		String nfListResultado = null;
		for(Proposta p : propostaLista){
			queryNF.setParameter("propostaid", p.getId());
			nfListResultado = (String)queryNF.uniqueResult();
			p.setnNFs(nfListResultado);
		}
		return propostaLista;
	}

	@SuppressWarnings("unchecked")
	public List<Pessoa> listarClientesBaixa() {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT NEW br.com.sose.entity.admistrativo.parceiros.Pessoa(c.id,c.nomeSistema) FROM OrdemServico o LEFT JOIN o.cliente c WHERE o.statusString = 'Aguardando proposta' AND o.unidadePai IS NULL GROUP BY o.cliente.nomeSistema ORDER BY o.cliente.nomeSistema");
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<OrdemServico> listarOrdemServicoBaixaProposta(final Pessoa cliente) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT NEW br.com.sose.entity.recebimento.OrdemServico(" +
				"o.id, " +
				"o.numeroOrdemServico, " +
				"o.serieCliente, " +
				"o.serieFabricante, " +
				"o.caseAvaya, " +
				"o.clienteAvaya, " +
				"o.numeroOrdemServicoPai, " +
				"o.nomeUnidade, " +
				"o.numeroNotaFiscal," +
				"r.id," +
				"orc.id," +
				"nf.id," +
				"o.nomeLaboratorio" +
				") FROM OrdemServico o " +
				"LEFT JOIN o.reparo r " +
				"LEFT JOIN o.orcamento orc " +
				"LEFT JOIN o.notaFiscal nf " +
				"WHERE o.cliente = :cliente AND o.unidadePai IS NULL AND o.statusString = 'Aguardando proposta' ORDER BY o.id");
		q.setParameter("cliente", cliente);
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<Proposta> buscarPropostasAberta() {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT p FROM Proposta p WHERE p.cliente = :cliente AND p.dataEnvioCliente IS NULL ORDER BY o.id");
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public Proposta buscarPorId(final Long id) {


		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h " +
				"LEFT JOIN FETCH h.itensProposta ip " +
				"LEFT JOIN FETCH ip.ordemServico os " +
				"LEFT JOIN FETCH os.placasFilhas pf " +
				"LEFT JOIN FETCH os.orcamento orc " +
				"LEFT JOIN FETCH os.orcamentoDiferenciado orcDif " +
				"LEFT JOIN FETCH os.reparo rep " +
				"LEFT JOIN FETCH orc.listaDefeito " +
				"LEFT JOIN FETCH orc.listaAtividade " +
				"LEFT JOIN FETCH orc.listaComponente " +
				"LEFT JOIN FETCH rep.listaDefeito WHERE h.id=:id");
		q.setParameter("id", id);
		Proposta r = (Proposta)q.uniqueResult();
		return r;

	}

}

package br.com.sose.daoImpl.consulta;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.sose.daoImpl.HibernateDaoGenerico;
import br.com.sose.entity.recebimento.OrdemServico;

@Repository("consultaGeralDao")
public class ConsultaGeralDao extends HibernateDaoGenerico<OrdemServico, Long> {

	private Logger logger = Logger.getLogger(this.getClass());

	public ConsultaGeralDao() {
		super();
	}


	@SuppressWarnings("unchecked")
	public OrdemServico buscarConsultaId(final Long id) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h " +
				"LEFT JOIN FETCH h.notaFiscal nf " +
				"LEFT JOIN FETCH nf.itensDaNotaFiscal " +
				"LEFT JOIN FETCH h.notaFiscalSaida nfs " +
				"LEFT JOIN FETCH nfs.ordensServico " +
				"LEFT JOIN FETCH nfs.volumes " +
				"LEFT JOIN FETCH nfs.cliente cnfs " +
				"LEFT JOIN FETCH cnfs.enderecos " +
				"LEFT JOIN FETCH h.proposta p " +
				"LEFT JOIN FETCH p.itensProposta ip " +
				"LEFT JOIN FETCH h.orcamento orc " +
				"LEFT JOIN FETCH h.reparo rep " +
				"LEFT JOIN FETCH orc.listaDefeito " +
				"LEFT JOIN FETCH rep.listaDefeito WHERE h.id=:id");
		q.setParameter("id", id);
		OrdemServico r = (OrdemServico)q.uniqueResult();
		return r;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> realizarConsultaGeral(final List<Long> listaOs) throws Exception {

		try{
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  

			List<Object[]> returnList = null;

			String stringSql = "select " +
					"os.numero_ordem_servico as nOS, " +
					"os.numeroordemservicopai as nOSPai, " + 
					"os.status as osStatus, " +
					"os.nomecliente, " +
					"u.nome as nomeUnidade, " +
					"os.numero_serie_fabricante as nsFabricante, " +
					"os.numero_serie_cliente as nsCliente, " +
					"os.ordemservicocliente as osCliente, " +
					"fab.nome as nomefabricante, " +
					"os.nomeunidadelpu, " +
					"lab.nome as nomeLaboratorio, " +
					"os.nomeprestadorservico, " +
					"notaf.numero as nNF, " +
					"notaf.data_nota_fiscal as dataNotaFiscal, " +
					"notaf.data_criacao, " +
					"notaf.data_chegada, " +
					"inf.valor_unitario as infValorUnitario, " +
					"os.clienteavaya, " +
					"os.caseavaya, " +
					"p.numero as nProposta, " +
					"p.dataCriacao as datacriacaoproposta, " +
					"(select isAprovado from itemproposta itp where ordem_servico_id = os.id and proposta_id = p.id AND dataAprovacao IS NOT NULL) as isAprovado, " +
					"(select dataaprovacao from itemproposta itp where ordem_servico_id = os.id and proposta_id = p.id AND dataAprovacao IS NOT NULL) as dataAprovacao, " +
					"notafs.numero as numeroNotaFS, " +
					"notafs.dtEmissao, " +
					"(SELECT concat('OBS: ', group_concat(h.texto separator ', OBS: ')) FROM observacao h where ref_nota_fiscal_id = os.nota_fiscal_id and escopo = 3 group by h.ref_nota_fiscal_id) as obsNotaFiscal, " +
					"(SELECT concat('OBS: ', group_concat(h.texto separator ', OBS: ')) FROM observacao h where ref_faturamento_id = os.faturamento_id and escopo = 3 group by h.ref_faturamento_id) as obsFaturamento, " +
					"(SELECT concat('OBS: ', group_concat(h.texto separator ', OBS: ')) FROM observacao h where ref_nf_saida_id = os.nota_fiscal_saida_id and escopo = 3 group by h.ref_nf_saida_id) as obsNotaFiscalSaida, " +
					"(SELECT concat('OBS: ', group_concat(h.texto separator ', OBS: ')) FROM observacao h where ref_orcamento_id = os.orcamento_id and escopo = 3 group by h.ref_orcamento_id) as obsOrcamento, " +
					"(SELECT concat('OBS: ', group_concat(h.texto separator ', OBS: ')) FROM observacao h where ref_ordem_servico_id = os.id and escopo = 3 group by h.ref_ordem_servico_id) as obsOrdemServico, " +
					"(SELECT concat('OBS: ', group_concat(h.texto separator ', OBS: ')) FROM observacao h where ref_reparo_id = os.reparo_id and escopo = 3 group by h.ref_reparo_id) as obsReparo, " +
					"(SELECT concat('OBS: ', group_concat(h.texto separator ', OBS: ')) FROM observacao h where ref_proposta_id = os.proposta_id and escopo = 3 group by h.ref_proposta_id) as obsProposta, " +
					"rep.dt_fim as repDtFim, " +
					"u.codigo as unidadeCodigo, " +
					"rep.id as idRep, " +
					"rep.condicao as repCondicao, " +
					"orc.id as idOrc, " +
					"orc.condicao as orcCondicao, " +
					"p.tipo as tipoProposta, " +
					"os.garantia, " +
					"lp.id as idLpu, " +
					"(select statusString from itemestoqueavaya where os_original_id = os.id and os_original_id is not null) as statusEstoque, " +
					"(select posicao from itemestoqueavaya where os_original_id = os.id and os_original_id is not null) as estoquePosicao, " +
					"notafs.dtSaida, " +
					"(SELECT concat('OBS: ', group_concat(h.texto separator ', OBS: ')) FROM observacao h where ref_ordem_servico_id = os.id and escopo = 3 and origem = 'Consulta' group by h.ref_ordem_servico_id) as obsConsulta " +
					"from ordemservico os " +
					"left join lpu lp on os.lpu_id = lp.id  " +
					"left join itemnotafiscal inf on os.item_nota_fiscal_id = inf.id  " +
					"left join notafiscal notaf on os.nota_fiscal_id = notaf.id  " +
					"left join notafiscalremessa notafs on os.nota_fiscal_saida_id = notafs.id  " +
					"left join reparo rep on os.reparo_id = rep.id  " +
					"left join orcamento orc on os.orcamento_id = orc.id  " +
					"left join unidade u on os.unidade_id = u.id  " +
					"left join fabricante fab on u.fabricante_id = fab.id  " +
					"left join laboratorio lab on u.laboratorio_id = lab.id  " +
					"left join proposta p on os.proposta_id = p.id where os.id in ";

			StringBuilder sb = new StringBuilder();
			sb.append(" (?");
			if(listaOs != null && !listaOs.isEmpty()){
				for(int i=0;i<listaOs.size();i++){
					sb.append(",?");
				}
			}
			sb.append(")");

			stringSql = stringSql+sb.toString();

			Query q = sessionFactory.getCurrentSession().createSQLQuery(stringSql);

			q.setParameter(0, 0);
			for(int i=0;i<listaOs.size();i++){
				q.setParameter(i+1, listaOs.get(i));
			}

			returnList =  q.list();

			return returnList;
		}catch(Exception e){
			e.printStackTrace(); logger.error(e);
			return null;
		}

	}


}

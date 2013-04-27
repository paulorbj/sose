package br.com.sose.daoImpl.consulta;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.sose.daoImpl.HibernateDaoGenerico;
import br.com.sose.entity.recebimento.OrdemServico;

@Repository("graficolDao")
public class GraficoDao extends HibernateDaoGenerico<OrdemServico, Long> {
	
	private Logger logger = Logger.getLogger(this.getClass());

	public GraficoDao() {
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
}

package br.com.sose.daoImpl.laudoTecnico;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.sose.daoImpl.HibernateDaoGenerico;
import br.com.sose.entity.laudoTecnico.LaudoTecnico;

@Repository("laudoTecnicoDao")
public class LaudoTecnicoDao extends HibernateDaoGenerico<LaudoTecnico, Long> {

	public LaudoTecnicoDao() {
		super();
	}

	@SuppressWarnings("unchecked")
	public List<LaudoTecnico> listarLaudosTecnicos(final String status) {


		StringBuilder sb = new StringBuilder("SELECT NEW br.com.sose.entity.laudoTecnico.LaudoTecnico(" +
				"h.id," +
				"h.controle," +
				"h.statusString," +
				"os.numeroOrdemServico," +
				"up.numeroOrdemServico," +
				"c.nomeSistema," +
				"un.nome," +
				"os.serieFabricante," +
				"os.serieCliente," +
				"l.nome," +
				"u.usuario) FROM "+ entityClass.getName() + " h " +
				"LEFT JOIN h.ordemServico os " +
				"LEFT JOIN os.unidadePai up " +
				"LEFT JOIN os.cliente c " +
				"LEFT JOIN os.unidade un " +
				"LEFT JOIN un.laboratorio l " +
				"LEFT JOIN h.criadoPor u " +
				"WHERE h.statusString = :status ORDER BY h.dataCriacao DESC");
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());
		query.setParameter("status", status);
		return query.list();

	}

	@SuppressWarnings("unchecked")
	public LaudoTecnico buscarPorId(final Long id) {		
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h " +
				"LEFT JOIN FETCH h.ordemServico os " +
				"LEFT JOIN FETCH os.placasFilhas pf " +
				"LEFT JOIN FETCH pf.orcamento pfOrc " +
				"LEFT JOIN FETCH pfOrc.listaDefeito  " +
				"LEFT JOIN FETCH pf.reparo pfRep " +
				"LEFT JOIN FETCH pfRep.listaDefeito  " +
				"LEFT JOIN FETCH h.orcamento orc " +
				"LEFT JOIN FETCH orc.listaDefeito  " +
				"LEFT JOIN FETCH h.reparo rep " +
				"LEFT JOIN FETCH rep.listaDefeito WHERE h.id=:id");
		q.setParameter("id", id);
		LaudoTecnico r = (LaudoTecnico)q.uniqueResult();
		return r;
	}

	@SuppressWarnings("unchecked")
	public LaudoTecnico buscarUltimoLaudoPorOrdemServico(final Long id) {		
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h " +
				"WHERE h.controle = (SELECT MAX(x.controle) FROM LaudoTecnico x where x.ordemServico.id=:id)");
		q.setParameter("id", id);
		LaudoTecnico r = (LaudoTecnico)q.uniqueResult();
		return r;
	}


}

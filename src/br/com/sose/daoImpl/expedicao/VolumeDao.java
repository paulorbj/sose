package br.com.sose.daoImpl.expedicao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.sose.daoImpl.HibernateDaoGenerico;
import br.com.sose.entity.expedicao.NotaFiscalRemessa;
import br.com.sose.entity.expedicao.Volume;

@Repository("volumeDao")
public class VolumeDao extends HibernateDaoGenerico<Volume, Long> {

	public VolumeDao() {
		super();
	}

	public Volume buscarPorIdOtimizado(final Long id) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT NEW br.com.sose.entity.expedicao.Volume(" +
				"h.id, " +
				"h.totalItens, " +
				"h.pesoBruto," +
				"h.tipoEmbalagem, " +
				"n.id " +
				") FROM "+ entityClass.getName() + " h LEFT JOIN h.notaFiscalSaida n WHERE h.id=:id");
		q.setParameter("id", id);
		Volume r = (Volume)q.uniqueResult();
		return r;

	}

	@SuppressWarnings("unchecked")
	public List<Volume> listarPorNotaFiscalSaida(final NotaFiscalRemessa notaFiscalSaida) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT NEW br.com.sose.entity.expedicao.Volume(" +
				"h.id, " +
				"h.totalItens, " +
				"h.pesoBruto," +
				"h.tipoEmbalagem, " +
				"n.id " +
				") FROM "+ entityClass.getName() + " h LEFT JOIN h.notaFiscalSaida n WHERE n.id=:notaFiscalSaida");
		q.setParameter("notaFiscalSaida", notaFiscalSaida.getId());
		List<Volume> r = (List<Volume>)q.list();
		return r;

	}


}

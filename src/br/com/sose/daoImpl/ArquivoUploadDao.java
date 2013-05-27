package br.com.sose.daoImpl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.sose.utils.ArquivoUpload;

@Repository("arquivoUploadDao")
public class ArquivoUploadDao extends HibernateDaoGenerico<ArquivoUpload, Long> {

	public ArquivoUploadDao() {
		super();
	}

	@SuppressWarnings("unchecked")
	public ArquivoUpload buscarPorNome(final String nome) {
		String queryString = "SELECT h FROM "+ entityClass.getName() + " h WHERE h.nome = :nome";
		Query q = sessionFactory.getCurrentSession().createQuery(queryString);
		q.setParameter("nome", nome);
		return (ArquivoUpload)q.uniqueResult();

	}

	@SuppressWarnings("unchecked")
	public ArquivoUpload buscarPorId(final Long id) {
		String queryString = "SELECT h FROM "+ entityClass.getName() + " h WHERE h.id = :id";
		Query q = sessionFactory.getCurrentSession().createQuery(queryString);
		q.setParameter("id", id);
		return (ArquivoUpload)q.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<ArquivoUpload> listarPorEntidadePorIdentificador(String entidade, Long idEntidade) {
		String queryString = "SELECT h FROM "+ entityClass.getName() + " h WHERE h.identificadorEntidade = :idEntidade AND h.tipoEntidade = :tipoEntidade";
		Query q = sessionFactory.getCurrentSession().createQuery(queryString);
		q.setParameter("tipoEntidade", entidade);
		q.setParameter("idEntidade", idEntidade);
		List<ArquivoUpload> listaTemp = (List<ArquivoUpload>)q.list();
		return listaTemp;
	}

	public Boolean remover(final ArquivoUpload arquivoUpload){
		Query q = sessionFactory.getCurrentSession().createSQLQuery("DELETE FROM ArquivoUpload WHERE id = :id");
		q.setParameter("id", arquivoUpload.getId());
		int i = q.executeUpdate();
		if(i>0){
			return true;
		}else{
			return false;
		}
	}

}

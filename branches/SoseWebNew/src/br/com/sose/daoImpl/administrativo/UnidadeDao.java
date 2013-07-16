package br.com.sose.daoImpl.administrativo;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.sose.daoImpl.HibernateDaoGenerico;
import br.com.sose.entity.admistrativo.Unidade;

@Repository("unidadeDao")
public class UnidadeDao extends HibernateDaoGenerico<Unidade, Long> {

	public UnidadeDao() {
		super();
	}

	@SuppressWarnings("unchecked")
	public Unidade buscarPorNome(final String nome) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.nome = :nome");
		q.setParameter("nome", nome);
		return (Unidade)q.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Unidade> listarPorNome(final String nome) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.nome = :nome");
		q.setParameter("nome", nome);
		List<Unidade> listUnidade = (List<Unidade>)q.list();
		return listUnidade;
	}
	
	@SuppressWarnings("unchecked")
	public Unidade buscarPorIdCompleto(final Long id) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h " +
				"LEFT JOIN FETCH h.equipamento e " +
				"LEFT JOIN FETCH h.fabricante f " +
				"LEFT JOIN FETCH h.laboratorio l " +
				"LEFT JOIN FETCH h.prestadorServicoExterno p " +
				"WHERE h.id = :id");
		q.setParameter("id", id);
		return (Unidade)q.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public Unidade buscarPorNomeCodigo(final String nome, final String codigo) {
		Query q = null;
		if(codigo != null && codigo != ""){
			q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.nome = :nome AND h.codigo = :codigo");
			q.setParameter("nome", nome);
			q.setParameter("codigo", codigo);
		}else{
			q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.nome = :nome AND h.codigo is null");
			q.setParameter("nome", nome);
		}
		return (Unidade)q.uniqueResult();

	}

	@SuppressWarnings("unchecked")
	public List<Unidade> findAllAtivoOrderByNome(){
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT new br.com.sose.entity.admistrativo.Unidade(" +
				"h.id," +
				"h.nome," +
				"h.codigo," +
				"f.nome," +
				"e.nome," +
				"l.nome," +
				"p.nomeSistema" +
				") FROM " + entityClass.getName() + " h " +
				"LEFT JOIN h.fabricante f " +
				"LEFT JOIN h.equipamento e " +
				"LEFT JOIN h.laboratorio l " +
				"LEFT JOIN h.prestadorServicoExterno p " +
				"WHERE h.cadastroSistemaAtivo = true ORDER BY h.nome");
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<Unidade> findAllOrderByNome(){
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT new br.com.sose.entity.admistrativo.Unidade(" +
				"h.id," +
				"h.nome," +
				"h.codigo," +
				"f.nome," +
				"e.nome," +
				"l.nome," +
				"p.nomeSistema" +
				") FROM " + entityClass.getName() + " h " +
				"LEFT JOIN h.fabricante f " +
				"LEFT JOIN h.equipamento e " +
				"LEFT JOIN h.laboratorio l " +
				"LEFT JOIN h.prestadorServicoExterno p " +
				"ORDER BY h.nome");
		return q.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Unidade> findAllOrderByNomeCombo(){
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM " + entityClass.getName() + " h " +
				"LEFT JOIN FETCH h.fabricante f " +
				"LEFT JOIN FETCH h.equipamento e " +
				"LEFT JOIN FETCH h.laboratorio l " +
				"LEFT JOIN FETCH h.prestadorServicoExterno p " +
				"ORDER BY h.nome");
		return q.list();
	}

	public Boolean remover(final Unidade unidade){
		Query q = sessionFactory.getCurrentSession().createSQLQuery("DELETE FROM Unidade WHERE id = :id");
		q.setParameter("id", unidade.getId());
		int i = q.executeUpdate();
		if(i>0){
			return true;
		}else{
			return false;
		}
	}

}

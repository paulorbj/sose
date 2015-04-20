package br.com.sose.daoImpl.administrativo;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.sose.daoImpl.HibernateDaoGenerico;
import br.com.sose.entity.admistrativo.Usuario;

@Repository("usuarioDao")
public class UsuarioDao extends HibernateDaoGenerico<Usuario, Long> {

	public UsuarioDao() {
		super();
	}

	public Usuario buscarPorLogin(final String login){
		final String  queryString = "FROM " + entityClass.getName() + " WHERE usuario = :login";
		Query query = sessionFactory.getCurrentSession().createQuery(queryString);
		query.setParameter("login", login);
		query.setMaxResults(1);
		Usuario res = (Usuario) query.uniqueResult();
		return res;
	}
	
	public Usuario buscarPorLoginCompleto(final String login){
		final String  queryString = "FROM " + entityClass.getName() + " h " +
				"LEFT JOIN FETCH h.perfil p " +
				"LEFT JOIN FETCH h.endereco e " +
				"WHERE h.usuario = :login";
		Query query = sessionFactory.getCurrentSession().createQuery(queryString);
		query.setParameter("login", login);
		query.setMaxResults(1);
		Usuario res = (Usuario) query.uniqueResult();
		return res;
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> findAllOrderByUsuario(){
		String queryString = "SELECT NEW br.com.sose.entity.admistrativo.Usuario(" +
				"h.id, " +
				"h.usuario," +
				"h.nome," +
				"h.rg," +
				"h.cpf," +
				"h.dataNascimento," +
				"p.nome," +
				"e.telefone," +
				"e.fax" +
				") FROM " + entityClass.getName() + " h " +
				"LEFT JOIN h.perfil p " +
				"LEFT JOIN h.endereco e " +
				"ORDER BY h.usuario";

		Query q = sessionFactory.getCurrentSession().createQuery(queryString);
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public Usuario verificarLogin(String userName, String userPassword){
		//		
		final String  queryString = "FROM " + entityClass.getName() + " h JOIN FETCH h.perfil p WHERE usuario = :userName AND senha = :userPassword";
		Query query = sessionFactory.getCurrentSession().createQuery(queryString);
		query.setParameter("userName", userName);		
		query.setParameter("userPassword", userPassword);
		Usuario usrs = (Usuario)query.uniqueResult();
		return usrs;

	}

	@SuppressWarnings("unchecked")
	public Usuario buscarPorNome(final String nome) {
		String queryString = "SELECT h FROM "+ entityClass.getName() + " h WHERE h.nome = :nome";
		Query q = sessionFactory.getCurrentSession().createQuery(queryString);
		q.setParameter("nome", nome);
		return (Usuario)q.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public Usuario buscarPorUsername(final String username) {
		String queryString = "SELECT h FROM "+ entityClass.getName() + " h WHERE h.usuario = :username";
		Query q = sessionFactory.getCurrentSession().createQuery(queryString);
		q.setParameter("username", username);
		return (Usuario)q.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public Usuario buscarPorId(final Long id) {
		String queryString = "SELECT h FROM "+ entityClass.getName() + " h WHERE h.id = :id";
		Query q = sessionFactory.getCurrentSession().createQuery(queryString);
		q.setParameter("id", id);
		return (Usuario)q.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public Usuario buscarCompletoPorId(final Long id) {
		String queryString = "SELECT h FROM "+ entityClass.getName() + " h " +
				"LEFT JOIN FETCH h.perfil p " +
				"LEFT JOIN FETCH h.endereco e  WHERE h.id = :id";
		Query q = sessionFactory.getCurrentSession().createQuery(queryString);
		q.setParameter("id", id);
		Usuario usuario = (Usuario)q.uniqueResult();
		return usuario;
	}

	public Boolean remover(final Usuario usuario){
		Query q = sessionFactory.getCurrentSession().createSQLQuery("DELETE FROM Usuario WHERE id = :id");
		q.setParameter("id", usuario.getId());
		int i = q.executeUpdate();
		if(i>0){
			return true;
		}else{
			return false;
		}
	}

}

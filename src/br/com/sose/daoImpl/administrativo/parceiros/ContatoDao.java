package br.com.sose.daoImpl.administrativo.parceiros;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.sose.daoImpl.HibernateDaoGenerico;
import br.com.sose.entity.admistrativo.parceiros.Contato;
import br.com.sose.entity.admistrativo.parceiros.Pessoa;

@Repository("contatoDao")
public class ContatoDao extends HibernateDaoGenerico<Contato, Long> {

	public ContatoDao() {
		super();
	}

	public Contato buscarPorNome(final String nome) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.nome = :nome");
		q.setParameter("nome", nome);
		return (Contato)q.uniqueResult();
	}

	public Boolean remover(final Contato contato){
		Query q = sessionFactory.getCurrentSession().createSQLQuery("DELETE FROM Contato WHERE id = :id");
		q.setParameter("id", contato.getId());
		int i = q.executeUpdate();
		if(i>0){
			return true;
		}else{
			return false;
		}
	}
	
	public Boolean removerByPessoa(final Pessoa pessoa){
		Query q = sessionFactory.getCurrentSession().createSQLQuery("DELETE FROM Contato WHERE cliente_id = :id");
		q.setParameter("id", pessoa.getId());
		int i = q.executeUpdate();
		if(i>0){
			return true;
		}else{
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Contato> listarPorCliente(final Pessoa pessoa) {

		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.cliente = :cliente");
		q.setParameter("cliente", pessoa);
		return q.list();
	}
}

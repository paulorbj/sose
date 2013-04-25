package br.com.sose.daoImpl.administrativo.parceiros;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.sose.daoImpl.HibernateDaoGenerico;
import br.com.sose.entity.admistrativo.parceiros.Endereco;
import br.com.sose.entity.admistrativo.parceiros.Pessoa;

@Repository("enderecoDao")
public class EnderecoDao extends HibernateDaoGenerico<Endereco, Long> {

	public EnderecoDao() {
		super();
	}

	public Boolean remover(final Endereco endereco){
		Query q = sessionFactory.getCurrentSession().createSQLQuery("DELETE FROM Endereco WHERE id = :id");
		q.setParameter("id", endereco.getId());
		int i = q.executeUpdate();
		if(i>0){
			return true;
		}else{
			return false;
		}
	}
	
	public Boolean removerByPessoa(final Pessoa pessoa){
		Query q = sessionFactory.getCurrentSession().createSQLQuery("DELETE FROM Endereco WHERE cliente_id = :id");
		q.setParameter("id", pessoa.getId());
		int i = q.executeUpdate();
		if(i>0){
			return true;
		}else{
			return false;
		}
	}

	public Endereco buscarPorIdOtimizado(final Long id) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT NEW br.com.sose.entity.admistrativo.parceiros.Endereco(" +
				"h.id, " +
				"h.logradouro, " +
				"h.numero," +
				"h.complemento, " +
				"h.bairro, " +
				"h.pais, " +
				"h.cidade," +
				"h.cep, " +
				"h.estado, " +
				"h.enderecoComercial," +
				"h.enderecoCobranca, " +
				"h.enderecoEntrega, " +
				"h.telefone," +
				"h.fax, " +
				"c.nomeSistema, " +
				"h.cadastroSistemaAtivo " +
				") FROM "+ entityClass.getName() + " h LEFT JOIN h.cliente c WHERE h.id=:id");
		q.setParameter("id", id);
		Endereco r = (Endereco)q.uniqueResult();
		return r;
	}

	@SuppressWarnings("unchecked")
	public List<Endereco> listarPorCliente(final Pessoa cliente) {

		Query q = sessionFactory.getCurrentSession().createQuery("SELECT NEW br.com.sose.entity.admistrativo.parceiros.Endereco(" +
				"h.id, " +
				"h.logradouro, " +
				"h.numero," +
				"h.complemento, " +
				"h.bairro, " +
				"h.pais, " +
				"h.cidade," +
				"h.cep, " +
				"h.estado, " +
				"h.enderecoComercial," +
				"h.enderecoCobranca, " +
				"h.enderecoEntrega, " +
				"h.telefone," +
				"h.fax, " +
				"c.nomeSistema, " +
				"h.cadastroSistemaAtivo " +
				") FROM "+ entityClass.getName() + " h LEFT JOIN h.cliente c WHERE c.id=:cliente");
		q.setParameter("cliente", cliente.getId());
		List<Endereco> r = (List<Endereco>)q.list();
		return r;

	}

}

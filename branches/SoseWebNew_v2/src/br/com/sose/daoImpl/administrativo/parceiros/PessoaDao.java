package br.com.sose.daoImpl.administrativo.parceiros;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.sose.daoImpl.HibernateDaoGenerico;
import br.com.sose.entity.admistrativo.parceiros.Pessoa;

@Repository("pessoaDao")
public class PessoaDao extends HibernateDaoGenerico<Pessoa, Long> {

	

	public PessoaDao() {
		super();
	}

	@SuppressWarnings("unchecked")
	public List<Pessoa> listarPessoaPorTipo(final Integer tipo) {
		List<Pessoa> returnList = null;
		Query q = this.sessionFactory.getCurrentSession().createQuery("SELECT NEW br.com.sose.entity.admistrativo.parceiros.Pessoa(" +
				"h.id, " +
				"h.nomeRazaoSocial, " +
				"h.nomeSistema, " +
				"h.cpfCnpj," +
				"h.rgIe," +
				"h.tipo," +
				"h.tipoPessoa," +
				"h.possuiContrato, " +
				"h.inscricaoMunicipal" +
				") FROM "+ entityClass.getName() + " h WHERE h.tipo = :tipo");
		q.setParameter("tipo", tipo);
		returnList = q.list();
		return returnList;

	}

	@SuppressWarnings("unchecked")
	public List<Pessoa> listarPessoas() {
		Query q = this.sessionFactory.getCurrentSession().createQuery("SELECT NEW br.com.sose.entity.admistrativo.parceiros.Pessoa(" +
				"h.id, " +
				"h.nomeRazaoSocial, " +
				"h.nomeSistema, " +
				"h.cpfCnpj," +
				"h.rgIe," +
				"h.tipo," +
				"h.tipoPessoa," +
				"h.possuiContrato, " +
				"h.inscricaoMunicipal" +
				") FROM "+ entityClass.getName() + " h ");
		System.out.println("listarPessoas - INICIO");
		return q.list();
	}

	public Pessoa buscarPorId(final Long id) {
		Query q = this.sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h " +
				"LEFT JOIN FETCH h.enderecos " +
				"LEFT JOIN FETCH h.contatos WHERE h.id=:id");
		q.setParameter("id", id);
		Pessoa r = (Pessoa)q.uniqueResult();
		return r;
	}

	public Pessoa buscarPorIdOtimizado(final Long id) {
		Query q = this.sessionFactory.getCurrentSession().createQuery("SELECT NEW br.com.sose.entity.admistrativo.parceiros.Pessoa(" +
				"h.id, " +
				"h.nomeRazaoSocial, " +
				"h.nomeSistema," +
				"h.cpfCnpj, " +
				"h.rgIe, " +
				"h.inscricaoMunicipal, " +
				"h.data," +
				"h.prazoDevolucao, " +
				"h.cadastroSistemaAtivo, " +
				"h.tipo," +
				"h.tipoPessoa, " +
				"h.possuiContrato," +
				"h.possuiPedidoCompra, " +
				"h.tempoGarantia" +
				") FROM "+ entityClass.getName() + " h WHERE h.id=:id");
		q.setParameter("id", id);
		Pessoa r = (Pessoa)q.uniqueResult();
		return r;
	}

	@SuppressWarnings("unchecked")
	public List<Pessoa> listarPessoaPorTipoOtimizado(final Integer tipo) {

		Query q = this.sessionFactory.getCurrentSession().createQuery("SELECT NEW br.com.sose.entity.admistrativo.parceiros.Pessoa(" +
				"h.id, " +
				"h.nomeRazaoSocial, " +
				"h.nomeSistema," +
				"h.cpfCnpj, " +
				"h.rgIe, " +
				"h.inscricaoMunicipal, " +
				"h.data," +
				"h.prazoDevolucao, " +
				"h.cadastroSistemaAtivo, " +
				"h.tipo," +
				"h.tipoPessoa, " +
				"h.possuiContrato," +
				"h.possuiPedidoCompra, " +
				"h.tempoGarantia" +
				") FROM "+ entityClass.getName() + " h WHERE h.tipo=:tipo");
		q.setParameter("tipo", tipo);
		List<Pessoa> r = (List<Pessoa>)q.list();
		return r;

	}

}

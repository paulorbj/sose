package br.com.sose.daoImpl.expedicao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.sose.daoImpl.HibernateDaoGenerico;
import br.com.sose.entity.admistrativo.parceiros.Pessoa;
import br.com.sose.entity.consulta.Consulta;
import br.com.sose.entity.expedicao.NotaFiscalRemessa;
import br.com.sose.entity.recebimento.OrdemServico;

@Repository("notaFiscalRemessaDao")
public class NotaFiscalRemessaDao extends HibernateDaoGenerico<NotaFiscalRemessa, Long> {

	public NotaFiscalRemessaDao() {
		super();
	}

	@SuppressWarnings("unchecked")
	public List<NotaFiscalRemessa> realizarConsultaGeral(final Consulta consulta) throws Exception {
		try{
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  

			final StringBuilder  queryString = new StringBuilder( "SELECT a FROM " + entityClass.getName() + " a WHERE ");

			queryString.append(" a.dtCriacao BETWEEN :dataDe AND :dataAte ");

			if(consulta.getNumeroOS() != null && !consulta.getNumeroOS().isEmpty()){
				queryString.append("AND a.numeroOrdemServico = :numeroOrdemServico ");
			}

			if(consulta.getNumeroNotaFiscal() != null && !consulta.getNumeroNotaFiscal().isEmpty()){
				queryString.append("AND a.notaFiscal.numero = :numeroNotaFiscal ");
			}

			if(consulta.getNumeroNotaFiscalSaida() != null && !consulta.getNumeroNotaFiscalSaida().isEmpty()){
				queryString.append("AND a.notaFiscalSaida.numero = :numeroNotaFiscalSaida ");
			}

			if(consulta.getCliente() != null && consulta.getCliente().getId() != 0){
				queryString.append("AND a.cliente = :cliente ");
			}

			Query q = sessionFactory.getCurrentSession().createQuery(queryString.toString());

			if(consulta.getDataDe() != null){
				q.setParameter("dataDe", consulta.getDataDe());
			}else{
				q.setParameter("dataDe", new Date(0));
			}

			if(consulta.getDataAte() != null){
				q.setParameter("dataAte", consulta.getDataAte());
			}else{
				q.setParameter("dataAte",  new Date());
			}

			if(consulta.getNumeroNotaFiscal() != null && !consulta.getNumeroNotaFiscal().isEmpty()){
				q.setParameter("numeroNotaFiscal", consulta.getNumeroNotaFiscal().trim());
			}

			if(consulta.getCliente() != null && consulta.getCliente().getId() != 0){
				q.setParameter("cliente", consulta.getCliente());
			}

			if(consulta.getNumeroNotaFiscalSaida() != null && !consulta.getNumeroNotaFiscalSaida().isEmpty()){
				q.setParameter("numeroNotaFiscalSaida", consulta.getNumeroNotaFiscalSaida().trim());
			}

			if(consulta.getNumeroOS() != null && !consulta.getNumeroOS().isEmpty()){
				q.setParameter("numeroOrdemServico", consulta.getNumeroOS().trim());
			}

			return q.list();
		}catch(Exception e){
			return null;
		}


	}

	@SuppressWarnings("unchecked")
	public List<NotaFiscalRemessa> listarPreNotaFiscalSaida() {


		StringBuilder sb = new StringBuilder("SELECT h FROM "+ entityClass.getName() + " h WHERE h.dtCriacao IS NULL ORDER BY h.nome");
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());
		return query.list();

	}

	@SuppressWarnings("unchecked")
	public List<NotaFiscalRemessa> listarNotaFiscalSaida() {


		StringBuilder sb = new StringBuilder("SELECT h FROM "+ entityClass.getName() + " h WHERE h.dtCriacao IS NOT NULL ORDER BY h.nome");
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());
		return query.list();

	}

	@SuppressWarnings("unchecked")
	public List<NotaFiscalRemessa> listarNotaFiscalSaida(final String status) {
		StringBuilder sb = new StringBuilder("SELECT NEW br.com.sose.entity.expedicao.NotaFiscalRemessa(" +
				"h.id," +
				"h.numero," +
				"h.statusString," +
				"h.dtSaida," +
				"h.dtCriacao, " +
				"c.nomeSistema" +
				") FROM "+ entityClass.getName() + " h " +
				"LEFT JOIN h.cliente c " +
				"WHERE h.dtCriacao IS NOT NULL AND h.statusString = :status " +
				"ORDER BY h.dtCriacao DESC");
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());
		query.setParameter("status", status);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<Pessoa> listarClientesBaixa() {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT NEW br.com.sose.entity.admistrativo.parceiros.Pessoa(c.id,c.nomeSistema) FROM OrdemServico o LEFT JOIN o.cliente c WHERE o.statusString = 'Aguardando expedição' AND o.notaFiscalSaida IS NULL GROUP BY o.cliente.nomeSistema ORDER BY o.cliente.nomeSistema");
		return q.list();
	}

	//	@SuppressWarnings("unchecked")
	//	public List<OrdemServico> listarOrdemServicoBaixaExpedicao(final Pessoa cliente) {
	//
	//		Query q = sessionFactory.getCurrentSession().createQuery("SELECT NEW br.com.sose.entity.recebimento.OrdemServico(" +
	//				"o.id, " +
	//				"o.numeroOrdemServico, " +
	//				"o.serieCliente, " +
	//				"o.serieFabricante, " +
	//				"o.caseAvaya, " +
	//				"o.clienteAvaya, " +
	//				"up.numeroOrdemServico, " +
	//				"u.nome, " +
	//				"nf.numero," +
	//				"o.garantia," +
	//				"orc.condicao," +
	//				"rep.condicao," +
	//				"c.id," +
	//				"c.nomeSistema" +
	//				") FROM OrdemServico o " +
	//				"LEFT JOIN o.cliente c " +
	//				"LEFT JOIN o.unidade u " +
	//				"LEFT JOIN o.unidadePai up " +
	//				"LEFT JOIN o.notaFiscal nf " +
	//				"LEFT JOIN o.orcamento orc " +
	//				"LEFT JOIN o.reparo rep " +
	//				"WHERE o.cliente = :cliente AND o.statusString = 'Aguardando expedição' AND o.notaFiscalSaida IS NULL ORDER BY o.id");
	//		q.setParameter("cliente", cliente);
	//		return q.list();
	//
	//	}

	@SuppressWarnings("unchecked")
	public List<OrdemServico> listarOrdemServicoBaixaExpedicao(final Pessoa cliente) {

		Query q = sessionFactory.getCurrentSession().createQuery("SELECT o  FROM OrdemServico o " +
				"WHERE o.cliente = :cliente AND o.statusString = 'Aguardando expedição' AND o.notaFiscalSaida IS NULL ORDER BY o.id");
		q.setParameter("cliente", cliente);
		return q.list();

	}

	//	@SuppressWarnings("unchecked")
	//	public NotaFiscalRemessa buscarPorIdBaixaOS(final Long id) {		
	//		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h " +
	//				"FROM "+ entityClass.getName() + " h " +
	//				"LEFT JOIN FETCH h.cliente c " +
	//				"WHERE h.id=:id");
	//		q.setParameter("id", id);
	//		NotaFiscalRemessa r = (NotaFiscalRemessa)q.uniqueResult();
	//		
	//		Query q2 = sessionFactory.getCurrentSession().createQuery("SELECT NEW br.com.sose.entity.recebimento.OrdemServico(" +
	//				"o.id, " +
	//				"o.numeroOrdemServico, " +
	//				"o.serieCliente, " +
	//				"o.serieFabricante, " +
	//				"o.caseAvaya, " +
	//				"o.clienteAvaya, " +
	//				"up.numeroOrdemServico, " +
	//				"u.nome, " +
	//				"nf.numero," +
	//				"o.garantia," +
	//				"orc.condicao," +
	//				"rep.condicao," +
	//				"c.id," +
	//				"c.nomeSistema" +
	//				") FROM OrdemServico o " +
	//				"LEFT JOIN o.cliente c " +
	//				"LEFT JOIN o.unidade u " +
	//				"LEFT JOIN o.unidadePai up " +
	//				"LEFT JOIN o.notaFiscal nf " +
	//				"LEFT JOIN o.orcamento orc " +
	//				"LEFT JOIN o.reparo rep " +
	//				"WHERE o.notaFiscalSaida.id = :id  ORDER BY o.id");
	//		q2.setParameter("id", id);
	//		
	//		List<OrdemServico> oss = q2.list();
	//		
	//		if(oss != null){
	//		r.setOrdensServico(new HashSet<OrdemServico>());
	//		r.getOrdensServico().addAll(oss);
	//		}
	//		return r;
	//
	//	}

	@SuppressWarnings("unchecked")
	public NotaFiscalRemessa buscarPorIdBaixaOS(final Long id) {		
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h " +
				"FROM "+ entityClass.getName() + " h " +
				"LEFT JOIN FETCH h.ordensServico oss " +
				"WHERE h.id=:id");
		q.setParameter("id", id);
		NotaFiscalRemessa r = (NotaFiscalRemessa)q.uniqueResult();
		return r;
	}

	@SuppressWarnings("unchecked")
	public NotaFiscalRemessa buscarPorId(final Long id) {		
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h " +
				"FROM "+ entityClass.getName() + " h " +
				"LEFT JOIN FETCH h.cliente c " +
				"LEFT JOIN FETCH h.enderecoEntrega e " +
				"LEFT JOIN FETCH h.ordensServico os " +
				"LEFT JOIN FETCH h.volumes " +
				"WHERE h.id=:id");
		q.setParameter("id", id);
		NotaFiscalRemessa r = (NotaFiscalRemessa)q.uniqueResult();
		return r;

	}

	@SuppressWarnings("unchecked")
	public NotaFiscalRemessa buscarPorIdListagemNotaFiscalSaida(final Long id) {		
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h " +
				"FROM "+ entityClass.getName() + " h " +
				"LEFT JOIN FETCH h.ordensServico os " +
				"LEFT JOIN FETCH h.volumes " +
				"LEFT JOIN FETCH h.cliente c " +
				"LEFT JOIN FETCH c.enderecos  " +
				"WHERE h.id=:id");
		q.setParameter("id", id);
		NotaFiscalRemessa r = (NotaFiscalRemessa)q.uniqueResult();

		Query q2 = sessionFactory.getCurrentSession().createQuery("SELECT o FROM OrdemServico o " +
				"WHERE o.notaFiscalSaida.id = :id  ORDER BY o.id");
		q2.setParameter("id", id);

		List<OrdemServico> oss = q2.list();

		if(oss != null){
			r.setOrdensServico(new HashSet<OrdemServico>());
			r.getOrdensServico().addAll(oss);
		}
		return r;

	}

	@SuppressWarnings("unchecked")
	public NotaFiscalRemessa buscarPorIdOtimizado(final Long id) {


		Query q = sessionFactory.getCurrentSession().createQuery("SELECT NEW br.com.sose.entity.expedicao.NotaFiscalRemessa(" +
				"h.id, " +
				"h.nome," +
				"h.dataCriacaoPreExpedicao, " +
				"h.numero, " +
				"h.statusString, " +
				"h.dtSaida, " +
				"h.dtCriacao, " +
				"h.dtIniciacao, " +
				"h.dtEmissao, " +
				"h.dtFinalizacao, " +
				"h.placaVeiculo, " +
				"h.nomeMotorista, " +
				"h.numeroDocumento, " +
				"h.numeroConhecimento, " +
				"h.recebidoPor, " +
				"h.dataRecebimentoMaterial, " +
				"h.valorCorreio, " +
				"h.valorFrete, " +
				"h.saidaRegistradaEm, " +
				"h.solicitacaoRegistradaEm, " +
				"h.tipo, " +
				"h.observacao, " +
				"h.observacaoAnterior, " +
				"h.codFrete," +
				"c.id, " +
				"t.id, " +
				"e.id " +
				") FROM "+ entityClass.getName() + " h " +
				" LEFT JOIN h.cliente c  " +
				" LEFT JOIN h.transportador t  " +
				" LEFT JOIN h.enderecoEntrega e  " +
				" WHERE h.id=:id");
		q.setParameter("id", id);
		NotaFiscalRemessa r = (NotaFiscalRemessa)q.uniqueResult();
		return r;

	}

	@SuppressWarnings("unchecked")
	public NotaFiscalRemessa verificarDisponibilidadeNumeroNotaFiscalSaida(final String nNotaFiscalSaida) {
		StringBuilder sb = new StringBuilder("SELECT h FROM "+ entityClass.getName() + " h WHERE h.statusString = 'Emitida' AND (h.numero  = :nNotaFiscalSaida)");							
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());
		query.setParameter("nNotaFiscalSaida", nNotaFiscalSaida.trim());
		return (NotaFiscalRemessa)query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public NotaFiscalRemessa verificarNumeroNotaFiscalSaida(final String nNotaFiscalSaida) {
		StringBuilder sb = new StringBuilder("SELECT h FROM "+ entityClass.getName() + " h WHERE (h.statusString = 'Emitida' OR h.statusString = 'Finalizada') AND (h.numero  = :nNotaFiscalSaida)");							
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());
		query.setParameter("nNotaFiscalSaida", nNotaFiscalSaida.trim());
		return (NotaFiscalRemessa)query.uniqueResult();
	}

}

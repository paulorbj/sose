package br.com.sose.daoImpl.recebimento;

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
import br.com.sose.entity.recebimento.ItemNotaFiscal;
import br.com.sose.entity.recebimento.NotaFiscal;
import br.com.sose.entity.recebimento.OrdemServico;

@Repository("notaFiscalDao")
public class NotaFiscalDao extends HibernateDaoGenerico<NotaFiscal, Long> {

	public NotaFiscalDao() {
		super();
	}

	@SuppressWarnings("unchecked")
	public NotaFiscal buscarPorId(final Long id) {


		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h " +
				"LEFT JOIN FETCH h.ordensServico " +
				"LEFT JOIN FETCH h.itensDaNotaFiscal WHERE h.id=:id");
		q.setParameter("id", id);
		NotaFiscal nf = (NotaFiscal)q.uniqueResult();
		return nf;

		//TODO - Essa chamada está funcionando
		//super.sendMessage("RecebimentoMessageServicePush", "method", "delete", res);;

	}
	
	@SuppressWarnings("unchecked")
	public List<NotaFiscal> buscarPorNumeroCliente(final String numero, final Long idCliente) {
		List<NotaFiscal> notasFiscaisRetorno = null;
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h  " +
				"WHERE h.numero = :numero AND h.cliente.id = :idCliente AND (h.statusString <> 'Nova')");
		q.setParameter("numero", numero);
		q.setParameter("idCliente", idCliente);
		notasFiscaisRetorno = q.list();
		return notasFiscaisRetorno;
	}

	@SuppressWarnings("unchecked")
	public NotaFiscal buscarPorId2(final Long id) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h " +
				"LEFT JOIN FETCH h.cliente c " +
				"LEFT JOIN FETCH h.itensDaNotaFiscal inf " +
				"WHERE h.id=:id");
		q.setParameter("id", id);
		NotaFiscal nf = (NotaFiscal)q.uniqueResult();

		Query q2 = sessionFactory.getCurrentSession().createQuery("SELECT h FROM OrdemServico h " +
				"LEFT JOIN FETCH h.cliente c " +
				"LEFT JOIN FETCH h.itemNotaFiscal inf " +
				"LEFT JOIN FETCH h.unidade uOs " +
				"LEFT JOIN FETCH uOs.equipamento e1 " +
				"LEFT JOIN FETCH uOs.fabricante f1 " +
				"LEFT JOIN FETCH uOs.laboratorio la1 " +
				"LEFT JOIN FETCH inf.unidade u " +
				"LEFT JOIN FETCH u.equipamento e " +
				"LEFT JOIN FETCH u.fabricante f " +
				"LEFT JOIN FETCH u.laboratorio la " +
				"LEFT JOIN FETCH inf.lpu l " +
				"LEFT JOIN FETCH h.notaFiscal nf " +
				"WHERE h.notaFiscal.id=:id");
		q2.setParameter("id", id);

		List<OrdemServico> listaOs = q2.list();
		nf.setOrdensServico(new HashSet<OrdemServico>());
		nf.getOrdensServico().addAll(listaOs);
		
		Query q3 = sessionFactory.getCurrentSession().createQuery("SELECT h FROM ItemNotaFiscal h " +
				"LEFT JOIN FETCH h.unidade u " +
				"LEFT JOIN FETCH h.lpu l " +
				"WHERE h.notaFiscal.id=:id");
		q3.setParameter("id", id);

		List<ItemNotaFiscal> listaInf = q3.list();
		nf.setItensDaNotaFiscal(new HashSet<ItemNotaFiscal>());
		nf.getItensDaNotaFiscal().addAll(listaInf);
		return nf;
	}

	@SuppressWarnings("unchecked")
	public NotaFiscal buscarPorIdSimples(final Long id) {


		Query q = sessionFactory.getCurrentSession().createQuery("SELECT NEW br.com.sose.entity.recebimento.NotaFiscal(" +
				"h.id," +
				"h.numero," +
				"h.pedidoDeCompra," +
				"h.caseAvaya," +
				"h.clienteAvaya," +
				"h.statusString," +
				"h.tipo," +
				"c.nomeSistema," +
				"h.dataNotaFiscal," +
				"h.dataChegada," +
				"h.dataCriacao," +
				"h.valorNotaFiscal," +
				"h.observacao," +
				"h.observacaoAnterior" +
				") FROM "+ entityClass.getName() + " h " +
				"LEFT JOIN h.cliente c " +
				"WHERE h.id=:id");
		q.setParameter("id", id);
		NotaFiscal nf = (NotaFiscal)q.uniqueResult();
		return nf;

	}

	@SuppressWarnings("unchecked")
	public List<NotaFiscal> realizarConsultaGeral(final Consulta consulta) throws Exception {



		try{
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  

			final StringBuilder  queryString = new StringBuilder( "SELECT a FROM " + entityClass.getName() + " a WHERE ");

			queryString.append(" a.dataCriacao BETWEEN :dataDe AND :dataAte ");

			if(consulta.getNumeroOS() != null && !consulta.getNumeroOS().isEmpty()){
				queryString.append("AND a.arquivo.titulo = :titulo ");
			}

			if(consulta.getNumeroNotaFiscal() != null && !consulta.getNumeroNotaFiscal().isEmpty()){
				queryString.append("AND a.numero = :numeroNotaFiscal ");
			}

			if(consulta.getNumeroNotaFiscalSaida() != null && !consulta.getNumeroNotaFiscalSaida().isEmpty()){
				queryString.append("AND a.arquivo.titulo = :titulo ");
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
				q.setParameter("numeroNotaFiscal", consulta.getNumeroNotaFiscal());
			}

			if(consulta.getCliente() != null && consulta.getCliente().getId() != 0){
				q.setParameter("cliente", consulta.getCliente());
			}

			if(consulta.getNumeroNotaFiscalSaida() != null && !consulta.getNumeroNotaFiscalSaida().isEmpty()){
				q.setParameter("numeroNotaFiscalSaida", consulta.getNumeroNotaFiscalSaida());
			}

			if(consulta.getNumeroOS() != null && !consulta.getNumeroOS().isEmpty()){
				q.setParameter("numeroOS", consulta.getNumeroOS());
			}

			return q.list();
		}catch(Exception e){
			return null;
		}


	}


	@SuppressWarnings("unchecked")
	public List<NotaFiscal> listarNotaFiscalPorCliente(final Pessoa cliente) {


		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.cliente=:cliente");
		q.setParameter("cliente", cliente);
		return q.list();

	}

	@SuppressWarnings("unchecked")
	public List<NotaFiscal> listarNotaFiscal(final String status) {


		StringBuilder sb = new StringBuilder("SELECT NEW br.com.sose.entity.recebimento.NotaFiscal(" +
				"h.id," +
				"h.numero," +
				"h.caseAvaya," +
				"h.clienteAvaya," +
				"h.statusString," +
				"h.dataNotaFiscal," +
				"h.dataChegada," +
				"h.dataCriacao," +
				"c.nomeSistema" +
				") FROM "+ entityClass.getName() + " h " +
				"LEFT JOIN h.cliente c " +
				"WHERE h.statusString = :status ORDER BY h.dataCriacao DESC");
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());
		query.setParameter("status", status);
		return query.list();

	}

}

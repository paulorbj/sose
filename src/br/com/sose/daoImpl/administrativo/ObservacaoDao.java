package br.com.sose.daoImpl.administrativo;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.sose.daoImpl.HibernateDaoGenerico;
import br.com.sose.entity.admistrativo.Observacao;
import br.com.sose.entity.expedicao.NotaFiscalRemessa;
import br.com.sose.entity.faturamento.Faturamento;
import br.com.sose.entity.laudoTecnico.LaudoTecnico;
import br.com.sose.entity.orcamento.Orcamento;
import br.com.sose.entity.proposta.Proposta;
import br.com.sose.entity.recebimento.NotaFiscal;
import br.com.sose.entity.recebimento.OrdemServico;
import br.com.sose.entity.reparo.Reparo;

@Repository("observacaoDao")
public class ObservacaoDao extends HibernateDaoGenerico<Observacao, Long> {

	public ObservacaoDao() {
		super();
	}

	@SuppressWarnings("unchecked")
	public List<Observacao> listarTodasObservacoes() {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE ORDER BY h.data");
		return q.list();

	}

	@SuppressWarnings("unchecked")
	public List<Observacao> listarTodasObservacoesSemSigilo() {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.sigiloso = false ORDER BY h.data");
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<Observacao> listarTodasObservacoesComSigilo() {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.sigiloso = true ORDER BY h.data");
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<Observacao> listarPorNotaFiscal(final NotaFiscal notaFiscal) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.notaFiscal = :notaFiscal ORDER BY h.data");
		q.setParameter("notaFiscal", notaFiscal);
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<Observacao> listarPorNotaFiscalSaida(final NotaFiscalRemessa notaFiscalSaida) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.notaFiscalSaida = :notaFiscalSaida ORDER BY h.data");
		q.setParameter("notaFiscalSaida", notaFiscalSaida);
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<Observacao> listarPorOrdemServico(final OrdemServico ordemServico) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.ordemServico = :ordemServico ORDER BY h.data");
		q.setParameter("ordemServico", ordemServico);
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<Observacao> listarPorReparo(final Reparo reparo) {


		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.reparo = :reparo ORDER BY h.data");
		q.setParameter("reparo", reparo);
		return q.list();

	}

	@SuppressWarnings("unchecked")
	public List<Observacao> listarPorOrcamento(final Orcamento orcamento) {


		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.orcamento = :orcamento ORDER BY h.data");
		q.setParameter("orcamento", orcamento);
		return q.list();

	}

	@SuppressWarnings("unchecked")
	public List<Observacao> listarPorProposta(final Proposta proposta) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.proposta = :proposta ORDER BY h.data");
		q.setParameter("proposta", proposta);
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<Observacao> listarPorLaudoTecnico(final LaudoTecnico laudoTecnico) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.laudoTecnico = :laudoTecnico ORDER BY h.data");
		q.setParameter("laudoTecnico", laudoTecnico);
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<Observacao> listarPorFaturamento(final Faturamento faturamento) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.faturamento = :faturamento ORDER BY h.data");
		q.setParameter("faturamento", faturamento);
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<Observacao> listarObservacoes(final Integer escopo, final String origem) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.origem = :origem AND h.escopo = :escopo ORDER BY h.data");
		q.setParameter("origem", origem);
		q.setParameter("escopo", escopo);
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<Observacao> listarObservacoesNotaFiscal(final NotaFiscal notaFiscal) {
		List<Observacao> listRetorno = null;
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.notaFiscal = :notaFiscal AND h.origem = 'Recebimento' AND h.escopo = 3  ORDER BY h.data");
		q.setParameter("notaFiscal", notaFiscal);
		listRetorno = q.list();
//		sessionFactory.getCurrentSession().close();
		return listRetorno;
	}

	@SuppressWarnings("unchecked")
	public List<Observacao> listarObservacoesOrdemServico(final OrdemServico ordemServico) {
		List<Observacao> listRetorno = null;
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.ordemServico = :ordemServico AND h.origem = 'Recebimento' AND h.escopo = 3  ORDER BY h.data");
		q.setParameter("ordemServico", ordemServico);
		listRetorno = q.list();
//		sessionFactory.getCurrentSession().close();
		return listRetorno;
	}

	@SuppressWarnings("unchecked")
	public List<Observacao> listarObservacoesOrcamento(final Orcamento orcamento) {
		List<Observacao> listRetorno = null;
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.orcamento = :orcamento AND h.origem = 'Orçamento' AND h.escopo = 3  ORDER BY h.data");
		q.setParameter("orcamento", orcamento);
		listRetorno = q.list();
//		sessionFactory.getCurrentSession().close();
		return listRetorno;
	}

	@SuppressWarnings("unchecked")
	public List<Observacao> listarObservacoesProposta(final Proposta proposta) {
		List<Observacao> listRetorno = null;
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.proposta = :proposta AND h.origem = 'Proposta' AND h.escopo = 3  ORDER BY h.data");
		q.setParameter("proposta", proposta);
		listRetorno = q.list();
//		sessionFactory.getCurrentSession().close();
		return listRetorno;
	}

	@SuppressWarnings("unchecked")
	public List<Observacao> listarObservacoesItemProposta(final OrdemServico ordemServico) {
		List<Observacao> listRetorno = null;
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.ordemServico = :ordemServico AND h.origem = 'Proposta' AND h.escopo = 3  ORDER BY h.data");
		q.setParameter("ordemServico", ordemServico);
		listRetorno = q.list();
//		sessionFactory.getCurrentSession().close();
		return listRetorno;
	}

	@SuppressWarnings("unchecked")
	public List<Observacao> listarObservacoesReparo(final Reparo reparo) {
		List<Observacao> listRetorno = null;
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.reparo = :reparo AND h.origem = 'Reparo' AND h.escopo = 3  ORDER BY h.data");
		q.setParameter("reparo", reparo);
		listRetorno = q.list();
//		sessionFactory.getCurrentSession().close();
		return listRetorno;
	}

	@SuppressWarnings("unchecked")
	public List<Observacao> listarObservacoesNotaFiscalSaida(final NotaFiscalRemessa notaFiscalSaida) {
		List<Observacao> listRetorno = null; 
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.notaFiscalSaida = :notaFiscalSaida AND h.origem = 'Expedição' AND h.escopo = 3  ORDER BY h.data");
		q.setParameter("notaFiscalSaida", notaFiscalSaida);
		listRetorno = q.list();
//		sessionFactory.getCurrentSession().close();
		return listRetorno;
	}

	@SuppressWarnings("unchecked")
	public List<Observacao> listarObservacoesExpedicao(final OrdemServico ordemServico) {
		List<Observacao> listRetorno = null; 
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.ordemServico = :ordemServico AND h.origem = 'Expedição' AND h.escopo = 3  ORDER BY h.data");
		q.setParameter("ordemServico", ordemServico);
		listRetorno = q.list();
//		sessionFactory.getCurrentSession().close();
		return listRetorno;
	}

	@SuppressWarnings("unchecked")
	public List<Observacao> listarObservacoesFaturamento(final Faturamento faturamento) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.faturamento = :faturamento AND h.origem = 'Faturamento' AND h.escopo = 3  ORDER BY h.data");
		q.setParameter("faturamento", faturamento);
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<Observacao> listarPorOrdemServicoOuNotaFiscal(final OrdemServico ordemServico,final NotaFiscal notaFiscal) {
		List<Observacao> listRetorno = null; 
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT h FROM "+ entityClass.getName() + " h WHERE h.ordemServico = :ordemServico OR h.notaFiscal = :notaFiscal ORDER BY h.data");
		q.setParameter("ordemServico", ordemServico);
		q.setParameter("notaFiscal", notaFiscal);
		listRetorno = q.list();
//		sessionFactory.getCurrentSession().close();
		return listRetorno;
	}

	public Integer remover(final Observacao observacao){
		Query q = sessionFactory.getCurrentSession().createSQLQuery("DELETE FROM Observacao WHERE id = :id");
		q.setParameter("id", observacao.getId());
		return q.executeUpdate();
	}
}
